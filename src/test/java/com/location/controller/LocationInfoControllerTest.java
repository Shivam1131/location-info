package com.location.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationinfo.controller.LocationInfoController;
import com.locationinfo.dto.RequestBean;
import com.locationinfo.service.LocationInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the LocationController REST controller.
 *
 * @see com.locationinfo.controller.LocationInfoController
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class LocationInfoControllerTest {

    private static final String GET_PLACES_URL = "/api/getLocationInfo";
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    LocationInfoService locationService;

    @InjectMocks
    private LocationInfoController locationController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
    }


    @Test
    @DisplayName("Test: Post API to get location details")
    public void testGetPlacesShouldReturnStatusOk() throws Exception {
        RequestBean requestBean = new RequestBean();
        requestBean.setLocation("chicago");
        requestBean.setCategoryName("Travel");
        String jsonStr = objectMapper.writeValueAsString(requestBean);
        MvcResult result = mockMvc.perform(post(GET_PLACES_URL).contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, result.getResponse().getStatus(), "Status Matched OK");
    }


    @Test
    @DisplayName("Test: Bad Request Post API without body params")
    public void testGetPlacesShouldReturnStatusBadRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(GET_PLACES_URL))
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus(), "Status Matched Bad Request");
    }

    @Test
    @DisplayName("Test : API request Without location name in body params")
    public void testGetPlacesShouldWithoutLocationReturnBadRequest() throws Exception {
        RequestBean requestBean = new RequestBean();
        requestBean.setCategoryName("Travel");
        String jsonStr = objectMapper.writeValueAsString(requestBean);
        MvcResult result = mockMvc.perform(post(GET_PLACES_URL).contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr)).andReturn();
        assertEquals(400, result.getResponse().getStatus(), "Status Matched");

    }
}
