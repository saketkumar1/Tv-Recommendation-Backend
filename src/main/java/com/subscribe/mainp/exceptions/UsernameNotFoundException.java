package com.subscribe.mainp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameNotFoundException extends RuntimeException{

    String fieldName;
    public UsernameNotFoundException(String fieldName) {
        super(String.format("%s already exists", fieldName));
        this.fieldName = fieldName;
    }
}
