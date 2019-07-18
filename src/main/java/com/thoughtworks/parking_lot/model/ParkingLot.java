package com.thoughtworks.parking_lot.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ParkingLot {
    @Id
    @Column(unique = true)
    private String name;
    @Min(0)
    private int capacity;
    private String position;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_1")
    private List<ParkOrder> parkOrders = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<ParkOrder> getParkOrders() {
        return parkOrders;
    }

    public void setParkOrders(List<ParkOrder> parkOrders) {
        this.parkOrders = parkOrders;
    }
}
