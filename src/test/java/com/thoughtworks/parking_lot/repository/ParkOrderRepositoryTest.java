package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkOrder;
import com.thoughtworks.parking_lot.model.ParkingLot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkOrderRepositoryTest {

    @Autowired
    private ParkOrderRepository parkOrderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Test
    public void should_return_park_order_when_call_park_given_parklot_name_and_car_ID() throws Exception {

        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setName("No.1 Park");
        parkingLot1.setCapacity(22);
        parkingLotRepository.save(parkingLot1);

        ParkOrder order = new ParkOrder();
        order.setParkingLotName("No.1 Park");
        order.setCarID("YA223DS");
        order.setCreateTime(new Date());
        ParkingLot parkingLot = parkingLotRepository.findById("No.1 Park").orElse(null);
        ParkOrder parkOrder = null;
        ParkingLot save = null;
        if(parkingLot == null) {
            throw new Exception();
        }else {
            if(parkingLot.getParkOrders().size() < parkingLot.getCapacity()) {
                parkingLot.getParkOrders().add(order);
                save = parkingLotRepository.save(parkingLot);
                parkOrder = parkOrderRepository.save(order);
            }else {
                throw new Exception();
            }
        }
        assertSame(order, parkOrder);
        assertEquals(1, save.getParkOrders().size());
    }
}