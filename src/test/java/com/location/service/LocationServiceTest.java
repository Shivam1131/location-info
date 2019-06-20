package com.location.service;

import com.locationinfo.dto.RequestBean;
import com.locationinfo.dto.ResponseBean;
import com.locationinfo.serviceimpl.FourSquareServiceProvider;
import com.locationinfo.serviceimpl.GoogleServiceProvider;
import com.locationinfo.serviceimpl.LocationInfoServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test class for the LocationInfoServiceImpl class.
 *
 * @see LocationInfoServiceImpl
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class LocationServiceTest {

    @Mock
    FourSquareServiceProvider fourSquareProviderService;

    @Mock
    GoogleServiceProvider googleProviderService;

    @InjectMocks
    LocationInfoServiceImpl locationInfoService;

    MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(locationInfoService).build();
    }


    @Test
    @DisplayName("LocationInfoService Test: Non empty for location parameter")
    public void testLocationInfoServiceFailure() throws Exception {

        final ResponseEntity<ResponseBean> response = locationInfoService.getLocation(new RequestBean("", ""));
        //Verify test cases
        Assert.assertTrue(response.getBody().getMessage().equalsIgnoreCase("No data found for the location."));
    }

}
