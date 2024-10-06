package com.jsenen.hubwarehouse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FarnellApiResponse {
    private ManufacturerPartNumberSearchReturn manufacturerPartNumberSearchReturn;

    // Getters y setters

    public ManufacturerPartNumberSearchReturn getManufacturerPartNumberSearchReturn() {
        return manufacturerPartNumberSearchReturn;
    }

    public void setManufacturerPartNumberSearchReturn(ManufacturerPartNumberSearchReturn manufacturerPartNumberSearchReturn) {
        this.manufacturerPartNumberSearchReturn = manufacturerPartNumberSearchReturn;
    }
}
