package com.jsenen.hubwarehouse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute {
    private String attributeLabel;
    private String attributeValue;
    private String attributeUnit; // Este campo puede ser opcional
}

