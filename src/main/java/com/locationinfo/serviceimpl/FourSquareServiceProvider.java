package com.locationinfo.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationinfo.dto.LocationDTO;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.service.LocationServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.locationinfo.constants.AppConstants.*;

/**
 * @author Sadashiv Kadam
 */
@Service
public class FourSquareServiceProvider implements LocationServiceProvider {

    @Autowired
    RestTemplate restTemplate;
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * @param requestBean contains location and optional categoryType
     * @return set of location details
     * @implNote get set of LocationDTO from fourSquare api
     */
    @Override
    public Set<LocationDTO> getLocationInfo(RequestBean requestBean) {

        Set<LocationDTO> locationSet = new HashSet<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String baseUrl = FOUR_SQUARE_BASE_URL + "?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "&near=" + requestBean.getLocation();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String result = restTemplate.postForObject(baseUrl, new HttpEntity<>(headers), String.class);
            getFourSquareResponse(result, locationSet, mapper);
        } catch (Exception e) {
            logger.info("No results from fourSquare");
        }
        return locationSet;
    }

    /**
     * @param response return from the rest service of foursquare & mapper
     * @return Set<LocationDTO> of locations from google geocode and foursquare
     * @implNote data parsing of response object return from foursquare rest service
     */
    public void getFourSquareResponse(String response, Set<LocationDTO> locationDTOSet, ObjectMapper mapper) {

        Map<String, Object> map;
        LinkedHashMap<String, Object> places;

        try {
            map = mapper.readValue(response, Map.class);
            places = (LinkedHashMap<String, Object>) map.get("response");
            ArrayList<LinkedHashMap<String, Object>> venues = (ArrayList<LinkedHashMap<String, Object>>) places.get("venues");
            LocationDTO locationDTO;
            LinkedHashMap<String, Object> location;
            ArrayList<Object> categoriess;

            for (LinkedHashMap<String, Object> venue : venues) {
                locationDTO = new LocationDTO();
                locationDTO.setName(String.valueOf(venue.get("name")));
                location = (LinkedHashMap<String, Object>) venue.get("location");
                locationDTO.setLatitude(location.get("lat").toString());
                locationDTO.setLongitude(location.get("lng").toString());
                locationDTO.setCountry(location.get("country").toString());
                locationDTO.setPostalCode(null != location.get("postalCode") ? location.get("postalCode").toString() : "NA");
                locationDTO.setPostalCode(null != location.get("city") ? location.get("city").toString() : "NA");
                locationDTO.setPostalCode(null != location.get("state") ? location.get("state").toString() : "NA");
                locationDTO.setPostalCode(null != location.get("formattedAddress") ? location.get("formattedAddress").toString() : "NA");
                categoriess = (ArrayList<Object>) venue.get("categories");

                for (Object category : categoriess) {
                    location = (LinkedHashMap<String, Object>) category;
                    locationDTO.setCategory(location.get("name").toString());
                }
                locationDTOSet.add(locationDTO);
            }

        } catch (Exception e) {
            logger.info("Exception at FourSquareServiceProvider : {}", e);
        }
    }

}
