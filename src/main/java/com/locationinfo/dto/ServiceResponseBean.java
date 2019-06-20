package com.locationinfo.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @author Sadashiv Kadam
 */
@Data
public class ServiceResponseBean {

    private String status;
    private String message;
    private HttpStatus httpStatus;
    private Map<String, Object> data;


}
