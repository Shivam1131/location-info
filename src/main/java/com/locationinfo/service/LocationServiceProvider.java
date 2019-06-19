package com.locationinfo.service;

import com.locationinfo.dto.LocationDTO;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ResponseBean;

import java.util.Set;
/**
 * @author Sadashiv Kadam
 */
public interface LocationServiceProvider {

    Set<LocationDTO> getLocationInfo(RequestBean requestBean) ;

}
