package com.khmal.hospital.service.exception_handling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Violation {
    private final String message;

    public Violation( String message) {
        this.message = message;
    }
}
