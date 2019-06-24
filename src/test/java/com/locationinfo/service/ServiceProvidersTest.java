package com.locationinfo.service;

import com.locationinfo.dto.LocationDTO;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.serviceimpl.FourSquareServiceProvider;
import com.locationinfo.serviceimpl.GoogleServiceProvider;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * Test class for the Service Providers.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ServiceProvidersTest {

    @Autowired
    FourSquareServiceProvider fourSquareServiceProvider;

    @Autowired
    GoogleServiceProvider googleServiceProvider;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Foursquare Test: Get Location Success")
    public void testFourSquaregetLocationSuccess() {
        Set<LocationDTO> placeDTOS = fourSquareServiceProvider.getLocationInfo(new RequestBean("Pune", "Shop"));
        Assert.assertTrue(!CollectionUtils.isEmpty(placeDTOS));
    }

    @Test
    @DisplayName("Foursquare Test: Get Location Empty Request")
    public void testFourSquaregetLocationEmptyRequestData() {
        Set<LocationDTO> placeDTOS = fourSquareServiceProvider.getLocationInfo(new RequestBean("", ""));
        Assert.assertTrue(CollectionUtils.isEmpty(placeDTOS));
    }

    @Test
    @DisplayName("Google Test: Get Location Success")
    public void testGoogleServiceProviderSuccess() {
        Set<LocationDTO> placeDTOS = googleServiceProvider.getLocationInfo(new RequestBean("Pune", "Shop"));
        Assert.assertTrue(!CollectionUtils.isEmpty(placeDTOS));
    }

    @Test
    @DisplayName("Google Test: Get Location Empty Request")
    public void testGoogleServiceProviderEmptyRequestData() {
        Set<LocationDTO> placeDTOS = googleServiceProvider.getLocationInfo(new RequestBean("", ""));
        Assert.assertTrue(CollectionUtils.isEmpty(placeDTOS));
    }
}
