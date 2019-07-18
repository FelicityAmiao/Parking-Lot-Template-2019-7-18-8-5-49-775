package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkOrderRepository extends JpaRepository<ParkOrder, Long> {
}
