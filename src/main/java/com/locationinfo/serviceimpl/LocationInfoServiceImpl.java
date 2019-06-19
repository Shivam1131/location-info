package com.locationinfo.serviceimpl;

import com.locationinfo.constants.AppConstants;
import com.locationinfo.dto.LocationDTO;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ResponseBean;
import com.locationinfo.exception.LocationDetailsException;
import com.locationinfo.service.LocationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Sadashiv Kadam
 */
@Service
public class LocationInfoServiceImpl implements LocationInfoService {

    @Autowired
    private GoogleServiceProvider googleServiceProvider;

    @Autowired
    private FourSquareServiceProvider fourSquareServiceProvider;

    /**
     * Method to get geocode details combine from google geocode and foursquare API
     *
     * @param requestBean contains location and optional categoryType
     * @return ResponseBean  along with place details
     */

    @Override
    public ResponseEntity<ResponseBean> getLocation(RequestBean requestBean) {

        ResponseBean responseBean = new ResponseBean();

        try {

            Set<LocationDTO> locationSet;
            Map<String, Object> data = new HashMap<>();

            locationSet=fourSquareServiceProvider.getLocationInfo(requestBean);
            locationSet.addAll(googleServiceProvider.getLocationInfo(requestBean));
            if (locationSet.isEmpty())
                responseBean.setMessage(AppConstants.NO_DATA_FOUND);
            else
                responseBean.setMessage(AppConstants.DATA_RETRIEVED_SUCCESS);

            //Filter data based on categoryName
            if(requestBean.getCategoryName()!=null && !requestBean.getCategoryName().isEmpty() && !CollectionUtils.isEmpty(locationSet)){
                locationSet.retainAll(
                        locationSet.stream().
                                filter(location -> (location.getCategory()!=null ? location.getCategory():"").toLowerCase().
                                        contains(requestBean.getCategoryName().toLowerCase())
                        ).collect(Collectors.toList())
                );
                if(CollectionUtils.isEmpty(locationSet)){
                    responseBean.setMessage(AppConstants.NO_DATA_FOUND_FOR_CATEGORY);
                }
            }
            data.put("result",locationSet);
            responseBean.setHttpStatus(HttpStatus.OK);
            responseBean.setData(data);
            responseBean.setStatus(AppConstants.SUCCESS);

        }catch (Exception e){
           throw new LocationDetailsException(e.getMessage(),"401") ;
        }
        return new  ResponseEntity<>(responseBean,responseBean.getHttpStatus()) ;

    }

}
