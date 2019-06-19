package com.locationinfo.exception;

import lombok.Data;

/**
 * @author Sadashiv Kadam
 */
@Data
public class LocationDetailsException extends RuntimeException{
    private String errorCode;

    public LocationDetailsException() {
        super();
    }

    public LocationDetailsException(String message){
        super(message);
    }

    public LocationDetailsException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
