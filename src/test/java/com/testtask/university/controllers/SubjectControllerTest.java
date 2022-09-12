package com.testtask.university.controllers;

import com.testtask.university.dto.InputSubjectDto;
import com.testtask.university.models.Subject;
import com.testtask.university.models.Teacher;
import com.testtask.university.services.SubjectService;
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
@WebMvcTest(value = SubjectController.class)
@WithMockUser
class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService;

    Teacher mockTeacher = new Teacher("notRealTeacher");
    Subject mockSubject = new Subject("notRealSubject", mockTeacher);
    Subject mockSubjectNull = new Subject("notRealSubject", null);

    String exampleSubjectJson = "{\"name\":\"notRealSubject\",\"teacherId\":\"1\"}";

    @Test
    void getSubjectSuccess() throws Exception {
        Mockito.when(subjectService.getSubject(Mockito.anyInt())).thenReturn(Optional.of(mockSubject));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/subjects/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getSubjectNotFound() throws Exception {
        Mockito.when(subjectService.getSubject(Mockito.anyInt())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/subjects/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void createSubjectSuccess() throws Exception {
        Mockito.when(subjectService.createSubject(Mockito.any(InputSubjectDto.class))).thenReturn(Optional.of(mockSubject));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/subjects")
                .accept(MediaType.APPLICATION_JSON).content(exampleSubjectJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void createSubjectBadRequest() throws Exception {
        Mockito.when(subjectService.createSubject(Mockito.any(InputSubjectDto.class))).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/subjects")
                .accept(MediaType.APPLICATION_JSON).content(exampleSubjectJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void modifySubjectSuccess() throws Exception {
        Mockito.when(subjectService.saveSubject(Mockito.anyInt(), Mockito.any(InputSubjectDto.class))).thenReturn(Optional.of(mockSubject));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/subjects/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleSubjectJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void modifySubjectNotFound() throws Exception {
        Mockito.when(subjectService.saveSubject(Mockito.anyInt(), Mockito.any(InputSubjectDto.class))).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/subjects/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleSubjectJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void modifySubjectBadRequest() throws Exception {
        Mockito.when(subjectService.saveSubject(Mockito.anyInt(), Mockito.any(InputSubjectDto.class))).thenReturn(Optional.of(mockSubjectNull));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/subjects/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleSubjectJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void deleteSubjectSuccess() throws Exception {
        Mockito.when(subjectService.deleteSubject(Mockito.anyInt())).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/subjects/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteSubjectNotFound() throws Exception {
        Mockito.when(subjectService.deleteSubject(Mockito.anyInt())).thenReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/subjects/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void getAllSubjects() throws Exception {
        Mockito.when(subjectService.getSubjects()).thenReturn(new ArrayList<Subject>());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/subjects")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}