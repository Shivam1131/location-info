package com.locationinfo.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public class ResponseBean {

    private Boolean success = true;
    private String message;
    private Map<String, Object> data;
    private HttpStatus httpStatus;

}
