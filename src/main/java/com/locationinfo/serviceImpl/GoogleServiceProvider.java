package com.locationinfo.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationinfo.dto.LocationDTO;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.service.LocationServiceProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.locationinfo.constants.AppConstants.GOOGLE_API_KEY;
import static com.locationinfo.constants.AppConstants.GOOGLE_BASE_URL;

@Service
@PropertySource("classpath:application.properties")
public class GoogleServiceProvider implements LocationServiceProvider {

    private CloseableHttpClient httpClient;
    private static final Logger log = LoggerFactory.getLogger(GoogleServiceProvider.class);

    private enum StatusCode {OK, ZERO_RESULTS, OVER_DAILY_LIMIT, INVALID_REQUEST, UNKNOWN_ERROR}

  /*  @Autowired
    private RestTemplate restTemplate;*/

  /*  @Value("${geocode-apikey}")
    private String googleApiKey;*/

    /*@Value("${geocode-baseUrl}")
    private String geocodeBaseUrl ;*/



    @Override
    public Set<LocationDTO> getLocationInfo(RequestBean requestBean){

        ObjectMapper mapper = new ObjectMapper();
        Set<LocationDTO> locationSet = new HashSet<>() ;
        LocationDTO locationDTO;
        HashMap<String, Object> parsedObject;
        Map map;

        try {
            httpClient = HttpClients.createDefault();
            String baseUrl = GOOGLE_BASE_URL + "?key=" + GOOGLE_API_KEY + "&address=" +requestBean.getLocation()/*+ URLEncoder.encode(query, "UTF-8")*/;
            HttpGet httpGet = new HttpGet(baseUrl);
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");

            CloseableHttpResponse responseData =httpClient.execute(httpGet);

            map = mapper.readValue(responseData.getEntity().getContent(), Map.class);

            log.info("google result info : "+new JSONObject(map));

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
                Map<String, Object> data = localityData.stream().filter(locality -> locality.get("types").toString().contains("locality")).findAny().orElse(null);
                locationDTO.setCity(null != data ? data.get("long_name").toString() : "NA");

                // Get Postal code from the address component
                data = localityData.stream().filter(locality -> locality.get("types").toString().contains("postal_code")).findAny().orElse(null);
                locationDTO.setPostalCode(null != data ? data.get("long_name").toString() : "NA");

                // Get Country from the address component
                data = localityData.stream().filter(locality -> locality.get("types").toString().contains("country")).findAny().orElse(null);
                locationDTO.setCountry(null != data ? data.get("long_name").toString() : "NA");
                locationDTO.setCountryCode(null != data ? data.get("short_name").toString() : "NA");

                locationSet.add(locationDTO);
        }

        }catch (Exception e){
            e.printStackTrace();
        }

    return locationSet;
    }
}
