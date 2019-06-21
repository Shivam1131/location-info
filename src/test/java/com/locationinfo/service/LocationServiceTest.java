package com.locationinfo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ServiceResponseBean;
import com.locationinfo.serviceimpl.FourSquareServiceProvider;
import com.locationinfo.serviceimpl.GoogleServiceProvider;
import com.locationinfo.serviceimpl.LocationInfoServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test class for the LocationInfoServiceImpl class.
 *
 * @see LocationInfoServiceImpl
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceTest {

    @Mock
    FourSquareServiceProvider fourSquareProviderService;

    @Mock
    GoogleServiceProvider googleProviderService;

    @Autowired
    private LocationInfoService locationInfoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(locationInfoService).build();
    }

    @Test
    @DisplayName("Test: calling getLoction with valid data")
    public void getLocationWithValidLocationDetails() throws Exception {
        RequestBean requestBean = new RequestBean();
        requestBean.setLocation("mumbai");
        requestBean.setCategoryName("");
        ServiceResponseBean responseBean = locationInfoService.getLocation(requestBean);
        Assert.assertTrue(responseBean.getMessage().equals("Location data retrieved successfully."));
        Assert.assertTrue(responseBean.getStatus().equals("Success"));
        Assert.assertTrue(responseBean.getHttpStatus() == HttpStatus.OK);
        Assert.assertTrue(!responseBean.getData().isEmpty());
    }


    @Test
    @DisplayName("Test: calling getLoction with invalid data")
    public void getLocationWithInvalidLocation() throws Exception {
        RequestBean requestBean = new RequestBean();
        requestBean.setLocation("jfhkajdfhksdjfhsk");
        requestBean.setCategoryName("");
        ServiceResponseBean responseBean = locationInfoService.getLocation(requestBean);
        Assert.assertTrue(responseBean.getMessage().equals("No data found for the location."));
        Assert.assertTrue(responseBean.getStatus().equals("Success"));
        Assert.assertTrue(responseBean.getHttpStatus() == HttpStatus.OK);
    }

    @Test
    @DisplayName("Test: getLocationInfo with non existing category")
    public void getLocationNoDataFoundForCategory() throws Exception {
        RequestBean requestBean = new RequestBean();
        requestBean.setLocation("Pune");
        requestBean.setCategoryName("mobile");
        ServiceResponseBean responseBean = locationInfoService.getLocation(requestBean);
        Assert.assertTrue(responseBean.getMessage().equals("No data found for the given category."));
        Assert.assertTrue(responseBean.getStatus().equals("Success"));
        Assert.assertTrue(responseBean.getHttpStatus() == HttpStatus.OK);
    }

    @Test
    @DisplayName("Test: getLocationInfo without location name")
    public void getLocationInfowithoutLocationName() throws Exception {
        RequestBean requestBean = new RequestBean();
        requestBean.setLocation("");
        requestBean.setCategoryName("");
        ServiceResponseBean responseBean = locationInfoService.getLocation(requestBean);
        Assert.assertTrue(responseBean.getMessage().equals("No data found for the location."));
        Assert.assertTrue(responseBean.getStatus().equals("Success"));
        Assert.assertTrue(responseBean.getHttpStatus() == HttpStatus.OK);
    }

}
