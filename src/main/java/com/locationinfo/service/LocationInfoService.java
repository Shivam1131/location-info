package com.locationinfo.service;

import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ResponseBean;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface LocationInfoService {

    ResponseBean getLocation(RequestBean requestBean);
}
