package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingLot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParkingLotRepositoryTest {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Test
    public void should_return_parking_lot_when_call_save_given_parking_lot() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Jerry Parking Lot");
        parkingLot.setCapacity(20);
        parkingLot.setPosition("1");
        parkingLotRepository.save(parkingLot);
        assertEquals(1, parkingLotRepository.findAll().size());
    }

    @Test
    public void should_throw_exception_when_call_save_given_capacity_minus() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Jerry Parking Lot");
        parkingLot.setCapacity(-20);
        parkingLot.setPosition("1");
        assertThrows(Exception.class, ()->parkingLotRepository.saveAndFlush(parkingLot));
    }

    @Test
    public void should_throw_exception_when_call_save_given_multiple_name() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Jerry Parking Lot");
        parkingLot.setCapacity(20);
        parkingLot.setPosition("1");

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setName("Jerry Parking Lot");
        parkingLot2.setCapacity(20);
        parkingLot2.setPosition("1");

        parkingLotRepository.saveAndFlush(parkingLot);
        parkingLotRepository.saveAndFlush(parkingLot2);
        assertEquals(1, parkingLotRepository.findAll().size());
    }


}
