package alten.core.entities.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductInventoryStatus {
    INSTOCK("In stock"),
    LOWSTOCK("Low stock"),
    OUTOFSTOCK("Out of stock");

    private final String label;

    ProductInventoryStatus(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}

