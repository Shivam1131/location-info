package com.locationinfo.controller;


import com.locationinfo.constants.AppConstants;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ResponseBean;
import com.locationinfo.service.LocationInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sadashiv Kadam
 */
@RestController
@RequestMapping("/api")
public class LocationInfoController {

    @Autowired
    private LocationInfoService locationInfoService;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * @apiNote API to get list of location details from fourSquare and google Geocode
     * & combines the result
     *
     * @param requestBean contains location and optional categoryType
     * @return ResponseBean along with location details
     */
    @PostMapping("/getLocationInfo")
    public ResponseEntity<ResponseBean> getLocationInfo(@RequestBody RequestBean requestBean) {

            logger.info("getLocationInfo() called with param : "+requestBean.getLocation());

            ResponseBean responseBean = new ResponseBean();

            if (null != requestBean.getLocation() && !requestBean.getLocation().isEmpty()) {
                return locationInfoService.getLocation(requestBean);
            }else {
                responseBean.setStatus(AppConstants.FAILURE);
                responseBean.setHttpStatus(HttpStatus.BAD_REQUEST);
                responseBean.setMessage(AppConstants.LOCATION_NAME_MISSING);
                //return new ResponseEntity<>(responseBeanDTO, HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(responseBean, HttpStatus.BAD_REQUEST);
                //return locationInfoService.getLocation(requestBean);
            }

    }
}

