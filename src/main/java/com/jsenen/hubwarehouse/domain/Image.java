package com.jsenen.hubwarehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private String baseName;
    private String mainImageURL;
    private String thumbNailImageURL;
}
