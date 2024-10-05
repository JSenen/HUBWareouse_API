package com.jsenen.hubwarehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attribute {
    private String attributeLabel;
    private String attributeValue;
    private String attributeUnit; // Este campo puede ser opcional
}

