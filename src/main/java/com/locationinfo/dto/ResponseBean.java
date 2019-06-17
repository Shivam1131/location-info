package com.locationinfo.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public class ResponseBean {

    private String status;
    private String message;
    private HttpStatus httpStatus;
    private Map<String, Object> data;


}
