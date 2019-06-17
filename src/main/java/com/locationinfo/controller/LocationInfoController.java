package com.locationinfo.controller;


import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ResponseBean;
import com.locationinfo.service.LocationInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@RequestMapping("/api")
public class LocationInfoController {

    @Autowired
    private LocationInfoService locationInfoService;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @PostMapping("/getLocationInfo")
    public ResponseBean getLocationInfo(@RequestBody RequestBean requestBean) {

            logger.info("getLocationInfo() called with param : "+requestBean.getLocation());

            ResponseBean responseBean = new ResponseBean();

            if (null != requestBean.getLocation() && !requestBean.getLocation().isEmpty()) {
                return locationInfoService.getLocation(requestBean);
            }else {
                //responseBean.setSuccess(false);
                //responseBean.setHttpStatus(HttpStatus.BAD_REQUEST);
                //responseBean.setMessage("Location name is missing in request.");
                //return new ResponseEntity<>(responseBeanDTO, HttpStatus.BAD_REQUEST);
                return locationInfoService.getLocation(requestBean);
            }

    }
}

