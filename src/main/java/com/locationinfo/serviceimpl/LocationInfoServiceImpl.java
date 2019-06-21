package com.locationinfo.serviceimpl;

import com.locationinfo.constants.AppConstants;
import com.locationinfo.dto.LocationDTO;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ServiceResponseBean;
import com.locationinfo.service.LocationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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
     * @return ServiceResponseBean  along with place details
     */

    @Override
    public ServiceResponseBean getLocation(RequestBean requestBean) {

        ServiceResponseBean responseBean = new ServiceResponseBean();

        try {

            Set<LocationDTO> locationSet;
            Map<String, Object> data = new ConcurrentHashMap<>();

            locationSet = fourSquareServiceProvider.getLocationInfo(requestBean);
            // locationSet.addAll(googleServiceProvider.getLocationInfo(requestBean));

            if (locationSet.isEmpty())
                responseBean.setMessage(AppConstants.NO_DATA_FOUND);
            else {
                responseBean.setMessage(AppConstants.DATA_RETRIEVED_SUCCESS);

                if (!StringUtils.isEmpty(requestBean.getCategoryName())) {
                    locationSet.retainAll(
                            locationSet.stream().
                                    filter(location -> (location.getCategory() != null ? location.getCategory() : "").toLowerCase().
                                            contains(requestBean.getCategoryName().toLowerCase())
                                    ).collect(Collectors.toList())
                    );
                    if (CollectionUtils.isEmpty(locationSet)) {
                        responseBean.setMessage(AppConstants.NO_DATA_FOUND_FOR_CATEGORY);
                    }
                }
            }
            data.put(AppConstants.RESULT, locationSet);
            responseBean.setHttpStatus(HttpStatus.OK);
            responseBean.setData(data);
            responseBean.setStatus(AppConstants.SUCCESS);

        } catch (Exception e) {
            responseBean.setStatus(AppConstants.FAILURE);
            responseBean.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseBean.setMessage(AppConstants.FAIL_TO_EXECUTE);
        }
        return responseBean;

    }

}
