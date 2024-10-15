package com.jsenen.hubwarehouse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ManufacturerPartNumberSearchReturn {
    private int numberOfResults;
    private List<FarnellComponent> products;

    // Getters y setters

    public int getNumberOfResults() {
        return numberOfResults;
    }

    public void setNumberOfResults(int numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    public List<FarnellComponent> getProducts() {
        return products;
    }

    public void setProducts(List<FarnellComponent> products) {
        this.products = products;
    }
}
