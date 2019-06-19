package com.locationinfo.dto;

import lombok.Data;
/**
 * @author Sadashiv Kadam
 */
@Data
public class RequestBean {
    String location;
    String categoryName;

    public RequestBean(){super();}

    public RequestBean(String location,String categoryName){
        this.location=location;
        this.categoryName=categoryName;
    }

}
