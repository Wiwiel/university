package com.testtask.university.controllers;

import com.testtask.university.models.Group;
import com.testtask.university.services.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = GroupController.class)
@WithMockUser
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    Group mockGroup = new Group("notRealGroup");

    String exampleGroupJson = "{\"name\":\"notRealGroup\"}";

    @Test
    void getGroupSuccess() throws Exception {
        Mockito.when(groupService.getGroup(Mockito.anyInt())).thenReturn(Optional.of(mockGroup));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/groups/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getGroupNotFound() throws Exception {
        Mockito.when(groupService.getGroup(Mockito.anyInt())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/groups/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void createGroupSuccess() throws Exception {
        Mockito.when(groupService.createGroup(Mockito.anyString())).thenReturn(Optional.of(mockGroup));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/groups")
                .accept(MediaType.APPLICATION_JSON).content(exampleGroupJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void createGroupBadRequest() throws Exception {
        Mockito.when(groupService.createGroup(Mockito.anyString())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/groups")
                .accept(MediaType.APPLICATION_JSON).content(exampleGroupJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void modifyGroupSuccess() throws Exception {
        Mockito.when(groupService.saveGroup(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.of(mockGroup));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/groups/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleGroupJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void modifyGroupNotFound() throws Exception {
        Mockito.when(groupService.saveGroup(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/groups/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleGroupJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void deleteGroupSuccess() throws Exception {
        Mockito.when(groupService.deleteGroup(Mockito.anyInt())).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/groups/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteGroupNotFound() throws Exception {
        Mockito.when(groupService.deleteGroup(Mockito.anyInt())).thenReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/groups/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void getAllGroups() throws Exception {
        Mockito.when(groupService.getGroups()).thenReturn(new ArrayList<Group>());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/groups")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}