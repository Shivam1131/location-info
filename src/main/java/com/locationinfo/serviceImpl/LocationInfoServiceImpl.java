package com.locationinfo.serviceImpl;


import com.locationinfo.constants.AppConstants;
import com.locationinfo.dto.LocationDTO;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ResponseBean;
import com.locationinfo.service.LocationInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LocationInfoServiceImpl implements LocationInfoService {

    private static final Logger log = LoggerFactory.getLogger(LocationInfoServiceImpl.class);

    @Autowired
    private GoogleServiceProvider googleServiceProvider;

    @Autowired
    private FourSquareServiceProvider fourSquareServiceProvider;



    @Override
    public ResponseBean getLocation(RequestBean requestBean) {

        //Generate parsed response and add it to map in response bean

        ResponseBean responseBean = new ResponseBean();

        try {

            Set<LocationDTO> locationSet;
            Map<String, Object> data = new HashMap<>();

            //locationSet=fourSquareServiceProvider.getLocationInfo(requestBean);
            //locationSet.addAll(googleServiceProvider.getLocationInfo(requestBean));
            locationSet = googleServiceProvider.getLocationInfo(requestBean);

            if (locationSet.isEmpty())
                responseBean.setMessage(AppConstants.NO_DATA_FOUND);
            else
                responseBean.setMessage(AppConstants.DATA_RETRIEVED_SUCCESS);


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
            //System.out.println("locationSet  : "+locationSet);

            responseBean.setHttpStatus(HttpStatus.OK);
            responseBean.setData(data);
            responseBean.setStatus(AppConstants.SUCCESS);

        }catch (Exception e){
            e.printStackTrace();
        }

        return responseBean;

    }

}
