package com.jsenen.hubwarehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private int from;
    private int to;
    private double cost;
    private double costIncTax;
}

