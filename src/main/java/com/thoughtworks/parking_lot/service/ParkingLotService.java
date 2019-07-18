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

@Service
public class ParkingLotService {

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
            return new ParkOrderDto(null, "The park is not find!");
        }
        if (parkingLot.getParkOrders().size() >= parkingLot.getCapacity()) {
            return new ParkOrderDto(null, "The park lot is full, please go to other way");
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
        parkOrder.setCarID(carId);
        parkOrder.setCreateTime(new Date());
        return parkOrder;
    }
}
