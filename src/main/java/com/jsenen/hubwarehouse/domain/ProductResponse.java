package com.jsenen.hubwarehouse.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductResponse {

    @JsonProperty("Product")
    private Product product;

    // Método getter para acceder al producto
    public Product getProduct() {
        return product;
    }

    // Clase interna para definir la estructura del producto
    public static class Product {

        @JsonProperty("Description")
        private Description description;

        @JsonProperty("Manufacturer")
        private Manufacturer manufacturer;

        // Método getter para Description
        public Description getDescription() {
            return description;
        }

        // Método getter para Manufacturer
        public Manufacturer getManufacturer() {
            return manufacturer;
        }
    }

    // Definición de la clase Description
    public static class Description {
        @JsonProperty("ProductDescription")
        private String productDescription;

        @JsonProperty("DetailedDescription")
        private String detailedDescription;

        public String getProductDescription() {
            return productDescription;
        }

        public String getDetailedDescription() {
            return detailedDescription;
        }
    }

    // Definición de la clase Manufacturer
    public static class Manufacturer {
        @JsonProperty("Name")
        private String name;

        public String getName() {
            return name;
        }
    }
}
