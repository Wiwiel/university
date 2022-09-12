package com.testtask.university.controllers;

import com.testtask.university.models.Teacher;
import com.testtask.university.services.TeacherService;
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
@WebMvcTest(value = TeacherController.class)
@WithMockUser
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    Teacher mockTeacher = new Teacher("notRealTeacher");

    String exampleTeacherJson = "{\"name\":\"notRealTeacher\"}";

    @Test
    void getTeacherSuccess() throws Exception {
        Mockito.when(teacherService.getTeacher(Mockito.anyInt())).thenReturn(Optional.of(mockTeacher));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/teachers/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getTeacherNotFound() throws Exception {
        Mockito.when(teacherService.getTeacher(Mockito.anyInt())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/teachers/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void createTeacherSuccess() throws Exception {
        Mockito.when(teacherService.createTeacher(Mockito.anyString())).thenReturn(mockTeacher);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/teachers")
                .accept(MediaType.APPLICATION_JSON).content(exampleTeacherJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void modifyTeacherSuccess() throws Exception {
        Mockito.when(teacherService.saveTeacher(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.of(mockTeacher));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/teachers/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleTeacherJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void modifyTeacherNotFound() throws Exception {
        Mockito.when(teacherService.saveTeacher(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/teachers/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleTeacherJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void deleteTeacherSuccess() throws Exception {
        Mockito.when(teacherService.deleteTeacher(Mockito.anyInt())).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/teachers/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteTeacherNotFound() throws Exception {
        Mockito.when(teacherService.deleteTeacher(Mockito.anyInt())).thenReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/teachers/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void getAllTeachers() throws Exception {
        Mockito.when(teacherService.getTeachers()).thenReturn(new ArrayList<Teacher>());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/teachers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}