package com.locationinfo.service;

import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ResponseBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
/**
 * @author Sadashiv Kadam
 */
@Service
public interface LocationInfoService {

    ResponseEntity<ResponseBean> getLocation(RequestBean requestBean);
}
