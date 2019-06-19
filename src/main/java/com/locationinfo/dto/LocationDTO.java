package com.locationinfo.dto;

import lombok.Data;
/**
 * @author Sadashiv Kadam
 */
@Data
public class LocationDTO {

    private String name;
    private String category;
    private String city;
    private String state;
    private String country;
    private String countryCode;
    private String postalCode;
    private String address;
    private String latitude;
    private String longitude;
    private String googlePlaceId;

}
