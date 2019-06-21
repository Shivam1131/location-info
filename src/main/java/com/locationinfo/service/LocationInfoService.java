package com.locationinfo.service;

import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ServiceResponseBean;
import org.springframework.stereotype.Service;

/**
 * @author Sadashiv Kadam
 */
@Service
public interface LocationInfoService {

    ServiceResponseBean getLocation(RequestBean requestBean);
}
