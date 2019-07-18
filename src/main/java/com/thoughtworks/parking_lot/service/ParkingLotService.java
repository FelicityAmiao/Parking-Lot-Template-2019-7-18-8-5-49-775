package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

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
}
