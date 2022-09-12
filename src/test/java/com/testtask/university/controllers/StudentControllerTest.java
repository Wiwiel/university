package com.testtask.university.controllers;

import com.testtask.university.dto.InputStudentDto;
import com.testtask.university.models.Group;
import com.testtask.university.models.Student;
import com.testtask.university.services.StudentService;
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
@WebMvcTest(value = StudentController.class)
@WithMockUser
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    Group mockGroup = new Group("notRealGroup");
    Student mockStudent = new Student("notRealStudent", mockGroup);
    Student mockStudentNull = new Student("notRealStudent", null);

    String exampleStudentJson = "{\"name\":\"notRealStudent\",\"groupId\":\"1\"}";

    @Test
    void getStudentSuccess() throws Exception {
        Mockito.when(studentService.getStudent(Mockito.anyInt())).thenReturn(Optional.of(mockStudent));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/students/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getStudentNotFound() throws Exception {
        Mockito.when(studentService.getStudent(Mockito.anyInt())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/students/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void createStudentSuccess() throws Exception {
        Mockito.when(studentService.createStudent(Mockito.any(InputStudentDto.class))).thenReturn(Optional.of(mockStudent));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/students")
                .accept(MediaType.APPLICATION_JSON).content(exampleStudentJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void createStudentBadRequest() throws Exception {
        Mockito.when(studentService.createStudent(Mockito.any(InputStudentDto.class))).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/students")
                .accept(MediaType.APPLICATION_JSON).content(exampleStudentJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void modifyStudentSuccess() throws Exception {
        Mockito.when(studentService.saveStudent(Mockito.anyInt(), Mockito.any(InputStudentDto.class))).thenReturn(Optional.of(mockStudent));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/students/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleStudentJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void modifyStudentNotFound() throws Exception {
        Mockito.when(studentService.saveStudent(Mockito.anyInt(), Mockito.any(InputStudentDto.class))).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/students/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleStudentJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void modifyStudentBadRequest() throws Exception {
        Mockito.when(studentService.saveStudent(Mockito.anyInt(), Mockito.any(InputStudentDto.class))).thenReturn(Optional.of(mockStudentNull));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/students/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleStudentJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void deleteStudentSuccess() throws Exception {
        Mockito.when(studentService.deleteStudent(Mockito.anyInt())).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/students/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteStudentNotFound() throws Exception {
        Mockito.when(studentService.deleteStudent(Mockito.anyInt())).thenReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/students/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void getAllStudents() throws Exception {
        Mockito.when(studentService.getStudents()).thenReturn(new ArrayList<Student>());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/students")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}