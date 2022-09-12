package com.testtask.university.controllers;

import com.testtask.university.dto.InputLectureDto;
import com.testtask.university.models.Group;
import com.testtask.university.models.Lecture;
import com.testtask.university.models.Room;
import com.testtask.university.models.Subject;
import com.testtask.university.services.LectureService;
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

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = LectureController.class)
@WithMockUser
class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectureService lectureService;

    Subject mockSubject = new Subject("notRealSubject", null);
    Room mockRoom = new Room("notRealRoom");
    Group mockGroup = new Group("notRealGroup");
    Lecture mockLecture = new Lecture(mockSubject, mockRoom, mockGroup, DayOfWeek.MONDAY);
    Lecture mockLectureNulls = new Lecture(mockSubject, null, null, DayOfWeek.MONDAY);
    String exampleLectureJson = "{\"subjectId\":\"1\",\"roomId\":\"1\",\"groupId\":\"1\",\"day\":\"MONDAY\"}";

    @Test
    void getLectureSuccess() throws Exception {
        Mockito.when(lectureService.getLecture(Mockito.anyInt())).thenReturn(Optional.of(mockLecture));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/lectures/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getLectureNotFound() throws Exception {
        Mockito.when(lectureService.getLecture(Mockito.anyInt())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/lectures/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void createLectureSuccess() throws Exception {
        Mockito.when(lectureService.createLecture(Mockito.any(InputLectureDto.class))).thenReturn(mockLecture);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/lectures")
                .accept(MediaType.APPLICATION_JSON).content(exampleLectureJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void createLectureBadRequest() throws Exception {
        Mockito.when(lectureService.createLecture(Mockito.any(InputLectureDto.class))).thenReturn(mockLectureNulls);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/lectures")
                .accept(MediaType.APPLICATION_JSON).content(exampleLectureJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void modifyLectureSuccess() throws Exception {
        Mockito.when(lectureService.saveLecture(Mockito.anyInt(), Mockito.any(InputLectureDto.class))).thenReturn(Optional.of(mockLecture));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/lectures/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleLectureJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void modifyLectureNotFound() throws Exception {
        Mockito.when(lectureService.saveLecture(Mockito.anyInt(), Mockito.any(InputLectureDto.class))).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/lectures/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleLectureJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void modifyLectureBadRequest() throws Exception {
        Mockito.when(lectureService.saveLecture(Mockito.anyInt(), Mockito.any(InputLectureDto.class))).thenReturn(Optional.of(mockLectureNulls));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/lectures/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleLectureJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void deleteLectureSuccess() throws Exception {
        Mockito.when(lectureService.deleteLecture(Mockito.anyInt())).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/lectures/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteLectureNotFound() throws Exception {
        Mockito.when(lectureService.deleteLecture(Mockito.anyInt())).thenReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/lectures/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void getAllLectures() throws Exception {
        Mockito.when(lectureService.getLectures()).thenReturn(new ArrayList<Lecture>());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/lectures")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}