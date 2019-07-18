package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingLot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
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

    @Test
    public void should_delete_parking_lot_when_call_delete_given_parking_lot_id() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Jerry Parking Lot");
        parkingLot.setCapacity(20);
        parkingLot.setPosition("1");
        parkingLotRepository.save(parkingLot);

        parkingLotRepository.deleteById(parkingLot.getName());
        assertEquals(0, parkingLotRepository.findAll().size());
    }

    @Test
    public void should_list_parking_lots_when_call_findAll_page_pageSize() {
        for (int i = 0; i < 15; i++) {
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.setName("Jerry Parking Lot" + i);
            parkingLot.setCapacity(20);
            parkingLot.setPosition("1");
            parkingLotRepository.save(parkingLot);
        }
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Jerry Parking Lot 16");
        parkingLot.setCapacity(20);
        parkingLot.setPosition("1");
        parkingLotRepository.save(parkingLot);

        Page<ParkingLot> all = parkingLotRepository.findAll(new PageRequest(0, 15));
        assertEquals(false, all.getContent().contains(parkingLot));
    }


    @Test
    public void should_parking_lot_when_call_findById_given_name_id() {
        for (int i = 0; i < 15; i++) {
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.setName("Jerry Parking Lot" + i);
            parkingLot.setCapacity(20);
            parkingLot.setPosition("1");
            parkingLotRepository.save(parkingLot);
        }
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Jerry Parking Lot 16");
        parkingLot.setCapacity(300);
        parkingLot.setPosition("1");
        parkingLotRepository.save(parkingLot);

        assertEquals(parkingLot.getCapacity(), parkingLotRepository.findById("Jerry Parking Lot 16").orElse(null).getCapacity());
    }

    @Test
    public void should_parking_lot_when_call_save_given_modified_parking_lot() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Jerry Parking Lot 16");
        parkingLot.setCapacity(300);
        parkingLot.setPosition("1");
        parkingLotRepository.save(parkingLot);

        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setName("Jerry Parking Lot 16");
        parkingLot1.setCapacity(22);
        parkingLot1.setPosition("1");
        ParkingLot save = parkingLotRepository.save(parkingLot1);

        assertEquals(parkingLot1.getCapacity(), save.getCapacity());
    }


}
