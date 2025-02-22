package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.dto.ParkOrderDto;
import com.thoughtworks.parking_lot.model.ParkOrder;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping
    public ParkingLot addParkingLot(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.add(parkingLot);
    }

    @DeleteMapping("/{nameId}")
    public void deleteParkingLot(@PathVariable String nameId) {
        parkingLotService.delete(nameId);
    }

    @GetMapping
    public List<ParkingLot> getPageParkingLots(@RequestParam int page, @RequestParam int pageSize) {
        return parkingLotService.getPageParkingLots(page, pageSize);
    }

    @GetMapping("/{nameId}")
    public ParkingLot getParkingLotByNameId(@PathVariable String nameId) {
        return parkingLotService.getParkingLotByNameId(nameId);
    }

    @PutMapping
    public ParkingLot modifyParkingLot(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.update(parkingLot);
    }

    @PostMapping(path = "/{nameId}/park-orders", produces = "application/json")
    public ParkOrderDto addParkOrder(@PathVariable String nameId, @RequestParam String carId) {
        return parkingLotService.addParkOrder(nameId, carId);
    }

    @PutMapping("/{nameId}/park-orders")
    public ParkOrder updateOrder(@PathVariable String nameId, @RequestParam String carId) {
        return parkingLotService.updateParkOrder(nameId, carId);
    }
}
