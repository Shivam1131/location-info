package com.locationinfo.controller;


import com.locationinfo.constants.AppConstants;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ServiceResponseBean;
import com.locationinfo.service.LocationInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
     * @param requestBean contains location and optional categoryType
     * @return ServiceResponseBean along with location details
     * @apiNote API to get list of location details from fourSquare and google Geocode
     * & combines the result
     */
    @PostMapping(value = "/getLocationInfo", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ServiceResponseBean> getLocationInfo(@RequestBody RequestBean requestBean) {

        logger.info("getLocationInfo() called with param :{} ", requestBean);

        ResponseEntity<ServiceResponseBean> response;
        ServiceResponseBean responseBean = new ServiceResponseBean();

        try {
            if (!StringUtils.isEmpty(requestBean.getLocation())) {
                responseBean = locationInfoService.getLocation(requestBean);
                response = new ResponseEntity<>(responseBean, responseBean.getHttpStatus());

            } else {

                responseBean.setStatus(AppConstants.FAILURE);
                responseBean.setHttpStatus(HttpStatus.BAD_REQUEST);
                responseBean.setMessage(AppConstants.LOCATION_NAME_MISSING);
                response = new ResponseEntity<>(responseBean, responseBean.getHttpStatus());
            }
        } catch (Exception e) {
            responseBean.setStatus(AppConstants.FAILURE);
            responseBean.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseBean.setMessage(AppConstants.FAIL_TO_EXECUTE);
            response = new ResponseEntity<>(responseBean, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}

