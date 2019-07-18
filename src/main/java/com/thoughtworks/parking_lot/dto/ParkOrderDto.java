package com.thoughtworks.parking_lot.dto;

import com.thoughtworks.parking_lot.model.ParkOrder;

public class ParkOrderDto {
    private ParkOrder parkOrder;
    private String errorMsg;
    public ParkOrderDto(ParkOrder parkOrder, String errorMag) {
        this.parkOrder = parkOrder;
        this.errorMsg = errorMag;
    }

    public ParkOrder getParkOrder() {
        return parkOrder;
    }

    public void setParkOrder(ParkOrder parkOrder) {
        this.parkOrder = parkOrder;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
