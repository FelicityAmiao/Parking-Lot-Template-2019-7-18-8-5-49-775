package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.dto.ParkOrderDto;
import com.thoughtworks.parking_lot.model.ParkOrder;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkOrderRepository;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {

    public static final String PARK_LOT_IS_NOT_FOUND = "The park is not find!";
    public static final String PARK_IS_FULL = "The park lot is full, please go to other way";

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkOrderRepository parkOrderRepository;

    public ParkingLot add(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public void delete(String nameId) {
        parkingLotRepository.deleteById(nameId);
    }

    public List<ParkingLot> getPageParkingLots(int page, int pageSize) {
        return parkingLotRepository.findAll(new PageRequest(page, pageSize)).getContent();
    }

    public ParkingLot getParkingLotByNameId(String nameId) {
        return parkingLotRepository.findById(nameId).orElse(null);
    }

    public ParkingLot update(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public ParkOrderDto addParkOrder(String nameId, String carId) {
        ParkingLot parkingLot = parkingLotRepository.findById(nameId).orElse(null);
        if (parkingLot == null) {
            return new ParkOrderDto(null, PARK_LOT_IS_NOT_FOUND);
        }
        if (parkingLot.getParkOrders().size() >= parkingLot.getCapacity()) {
            return new ParkOrderDto(null, PARK_IS_FULL);
        }
        ParkOrder parkOrder = createParkOrder(nameId, carId);
        parkingLot.getParkOrders().add(parkOrder);
        parkingLotRepository.save(parkingLot);
        parkOrderRepository.save(parkOrder);
        return new ParkOrderDto(parkOrder, null);
    }

    private ParkOrder createParkOrder(String nameId, String carId) {
        ParkOrder parkOrder = new ParkOrder();
        parkOrder.setParkingLotName(nameId);
        parkOrder.setcarId(carId);
        parkOrder.setCreateTime(new Date());
        return parkOrder;
    }

    public ParkOrder updateParkOrder(String nameId, String carId) {
        ParkingLot parkingLot = parkingLotRepository.findById(nameId).orElse(null);
        ParkOrder allByParkingLotNameAndcarId = parkOrderRepository.findAllByParkingLotNameAndCarId(nameId, carId).get(0);
        if(parkingLot == null || allByParkingLotNameAndcarId == null || !allByParkingLotNameAndcarId.isOrderStatus()) {
            return null;
        }
        allByParkingLotNameAndcarId.setEndTime(new Date());
        allByParkingLotNameAndcarId.setOrderStatus(false);
        parkOrderRepository.save(allByParkingLotNameAndcarId);
        ParkOrder parkOrder = parkingLot.getParkOrders().stream().filter(item -> item.getId() == allByParkingLotNameAndcarId.getId()).collect(Collectors.toList()).get(0);
        parkingLot.getParkOrders().remove(parkOrder);
        parkingLot.getParkOrders().add(allByParkingLotNameAndcarId);
        return allByParkingLotNameAndcarId;
    }
}
