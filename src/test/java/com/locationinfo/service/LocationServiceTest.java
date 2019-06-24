package com.locationinfo.service;

import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ServiceResponseBean;
import com.locationinfo.serviceimpl.LocationInfoServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class for the LocationInfoServiceImpl class.
 *
 * @see LocationInfoServiceImpl
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceTest {

    @Autowired
    private LocationInfoService locationInfoService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test: calling getLoction with valid data")
    public void getLocationWithValidLocationDetails() throws Exception {
        ServiceResponseBean responseBean = locationInfoService.getLocation(new RequestBean("mumbai", ""));
        Assert.assertTrue(responseBean.getMessage().equals("Location data retrieved successfully."));
        Assert.assertTrue(responseBean.getStatus().equals("Success"));
        Assert.assertTrue(responseBean.getHttpStatus() == HttpStatus.OK);
        Assert.assertTrue(!responseBean.getData().isEmpty());
    }

    @Test
    @DisplayName("Test: calling getLoction with invalid data")
    public void getLocationWithInvalidLocation() throws Exception {
        ServiceResponseBean responseBean = locationInfoService.getLocation(new RequestBean("jfhkajdfhksdjfhsk", ""));
        Assert.assertTrue(responseBean.getMessage().equals("No data found for the location."));
        Assert.assertTrue(responseBean.getStatus().equals("Success"));
        Assert.assertTrue(responseBean.getHttpStatus() == HttpStatus.OK);
    }

    @Test
    @DisplayName("Test: getLocationInfo with non existing category")
    public void getLocationNoDataFoundForCategory() throws Exception {
        ServiceResponseBean responseBean = locationInfoService.getLocation(new RequestBean("Pune", "mobile"));
        Assert.assertTrue(responseBean.getMessage().equals("No data found for the given category."));
        Assert.assertTrue(responseBean.getStatus().equals("Success"));
        Assert.assertTrue(responseBean.getHttpStatus() == HttpStatus.OK);
    }
}
