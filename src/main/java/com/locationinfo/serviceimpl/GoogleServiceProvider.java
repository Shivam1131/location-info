package com.locationinfo.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationinfo.constants.AppConstants;
import com.locationinfo.dto.LocationDTO;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.exception.LocationDetailsException;
import com.locationinfo.service.LocationServiceProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.locationinfo.constants.AppConstants.GOOGLE_API_KEY;
import static com.locationinfo.constants.AppConstants.GOOGLE_BASE_URL;
/**
 * @author Sadashiv Kadam
 */
@Service
@PropertySource("classpath:application.properties")
public class GoogleServiceProvider implements LocationServiceProvider {

    /**
     * @implNote get set of LocationDTO from geocode api
     *
     * @param requestBean contains location and optional categoryType
     * @return set of location details
     */

    @Override
    public Set<LocationDTO> getLocationInfo(RequestBean requestBean){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();
        Set<LocationDTO> locationSet = new HashSet<>() ;
        LocationDTO locationDTO;
        HashMap<String, Object> parsedObject;
        Map map;

        try {
            String baseUrl = GOOGLE_BASE_URL + "?key=" + GOOGLE_API_KEY + "&address=" +requestBean.getLocation()/*+ URLEncoder.encode(query, "UTF-8")*/;
            HttpGet httpGet = new HttpGet(baseUrl);
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");

            CloseableHttpResponse responseData =httpClient.execute(httpGet);

            map = mapper.readValue(responseData.getEntity().getContent(), Map.class);

            List<Map<String, Object>> places = (List<Map<String, Object>>)map.get("results");

            for (Map<String, Object> place: places) {
                locationDTO = new LocationDTO();

                if(StringUtils.isEmpty(place.get("formatted_address"))) {
                    locationDTO.setAddress(place.get("formatted_address").toString());
                }
                if(null != place.get("place_id")) {
                    locationDTO.setGooglePlaceId(place.get("place_id").toString());
                }
                parsedObject = (HashMap<String, Object>)place.get("geometry");
                parsedObject = (HashMap<String, Object>)parsedObject.get("location");

                //get lat and lng from location component
                locationDTO.setLatitude(parsedObject.get("lat").toString());
                locationDTO.setLongitude(parsedObject.get("lng").toString());

                List<Map<String, Object>> localityData = (List<Map<String, Object>>)place.get("address_components");

                // Get City from the address component
                Map<String, Object> data = localityData.stream().filter(locality -> locality.get(AppConstants.TYPES).toString().contains("locality")).findAny().orElse(null);
                locationDTO.setCity(null != data ? data.get(AppConstants.LONG_NAME).toString() : "NA");

                // Get Postal code from the address component
                data = localityData.stream().filter(locality -> locality.get(AppConstants.TYPES).toString().contains("postal_code")).findAny().orElse(null);
                locationDTO.setPostalCode(null != data ? data.get(AppConstants.LONG_NAME).toString() : "NA");

                // Get Country from the address component
                data = localityData.stream().filter(locality -> locality.get(AppConstants.TYPES).toString().contains("country")).findAny().orElse(null);
                locationDTO.setCountry(null != data ? data.get(AppConstants.LONG_NAME).toString() : "NA");
                locationDTO.setCountryCode(null != data ? data.get("short_name").toString() : "NA");

                locationSet.add(locationDTO);
                httpClient.close();
        }

        }catch (Exception e){
            throw new LocationDetailsException(e.getMessage());
        }

    return locationSet;
    }
}
