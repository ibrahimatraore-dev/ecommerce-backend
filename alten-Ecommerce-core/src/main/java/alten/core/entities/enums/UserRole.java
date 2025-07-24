package alten.core.entities.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    UserRole(String value) {
        this.value = value;
    }

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}