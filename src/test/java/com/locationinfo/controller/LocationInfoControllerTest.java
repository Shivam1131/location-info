package com.locationinfo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationinfo.dto.RequestBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationInfoControllerTest {

    @Autowired
    private LocationInfoController locationInfoController;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(locationInfoController).build();
    }

    @Test
    @DisplayName("Test: Post API for getting places for location")
    public void successForValidLocation() throws Exception {
        RequestBean locationFilterDTO = new RequestBean();
        locationFilterDTO.setLocation("mumbai");
        locationFilterDTO.setCategoryName("");
        String jsonStr = objectMapper.writeValueAsString(locationFilterDTO);
        MvcResult result = mockMvc.perform(post("/api/getLocationInfo").contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, result.getResponse().getStatus(), "Match status OK");
        Assert.assertTrue(result.getResponse().getContentAsString().contains("Location data retrieved successfully"));
    }

    @Test
    @DisplayName("Test: Post API for No Data found for invalid location")
    public void noDataFoundForInvalidLocation() throws Exception {
        RequestBean locationFilterDTO = new RequestBean();
        locationFilterDTO.setLocation("jfhkajdfhksdjfhsk");
        locationFilterDTO.setCategoryName("");
        String jsonStr = objectMapper.writeValueAsString(locationFilterDTO);
        MvcResult result = mockMvc.perform(post("/api/getLocationInfo").contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, result.getResponse().getStatus(), "Match status OK");
        Assert.assertTrue(result.getResponse().getContentAsString().contains("No data found for the location"));
    }

    @Test
    @DisplayName("Test: Post API for no data found for category")
    public void noDataFoundForCategory() throws Exception {
        RequestBean locationFilterDTO = new RequestBean();
        locationFilterDTO.setLocation("Pune");
        locationFilterDTO.setCategoryName("mobile");
        String jsonStr = objectMapper.writeValueAsString(locationFilterDTO);
        MvcResult result = mockMvc.perform(post("/api/getLocationInfo").contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, result.getResponse().getStatus(), "Match status OK");
        Assert.assertTrue(result.getResponse().getContentAsString().contains("No data found for the given category."));
    }

    @Test
    @DisplayName("Test: Post API for location name missing")
    public void locationNameMissingInRequest() throws Exception {
        RequestBean locationFilterDTO = new RequestBean();
        locationFilterDTO.setLocation("");
        locationFilterDTO.setCategoryName("");
        String jsonStr = objectMapper.writeValueAsString(locationFilterDTO);
        MvcResult result = mockMvc.perform(post("/api/getLocationInfo").contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals(400, result.getResponse().getStatus(), "Match status OK");
        Assert.assertTrue(result.getResponse().getContentAsString().contains("Location name is missing in request"));
    }
}