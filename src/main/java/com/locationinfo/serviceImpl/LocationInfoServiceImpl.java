package com.locationinfo.serviceImpl;


import com.locationinfo.dto.LocationDTO;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ResponseBean;
import com.locationinfo.service.LocationInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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


            locationSet=fourSquareServiceProvider.getLocationInfo(requestBean);
            locationSet.addAll(googleServiceProvider.getLocationInfo(requestBean));
            //locationSet = googleServiceProvider.getLocationInfo(requestBean);

            data.put("result",locationSet);

           // locationSet = googleServiceProvider.getLocationInfo(requestBean);

            System.out.println("locationSet  : "+locationSet);

          //  locationSet.addAll(fourSquareServiceProvider.getLocationInfo(requestBean));


            log.info("getLocationInfo() from LocationInfoServiceImpl : "+requestBean);

            responseBean.setHttpStatus(HttpStatus.OK);
            responseBean.setData(data);
            responseBean.setMessage("Location data retrieved successfully.");

        }catch (Exception e){
            e.printStackTrace();
        }

        return responseBean;

    }

}
