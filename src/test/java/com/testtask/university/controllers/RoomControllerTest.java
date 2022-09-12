package com.testtask.university.controllers;

import com.testtask.university.models.Room;
import com.testtask.university.services.RoomService;
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
@WebMvcTest(value = RoomController.class)
@WithMockUser
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    Room mockRoom = new Room("notRealRoom");

    String exampleRoomJson = "{\"name\":\"notRealRoom\"}";

    @Test
    void getRoomSuccess() throws Exception {
        Mockito.when(roomService.getRoom(Mockito.anyInt())).thenReturn(Optional.of(mockRoom));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/rooms/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getRoomNotFound() throws Exception {
        Mockito.when(roomService.getRoom(Mockito.anyInt())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/rooms/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void createRoomSuccess() throws Exception {
        Mockito.when(roomService.createRoom(Mockito.anyString())).thenReturn(Optional.of(mockRoom));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/rooms")
                .accept(MediaType.APPLICATION_JSON).content(exampleRoomJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void createRoomBadRequest() throws Exception {
        Mockito.when(roomService.createRoom(Mockito.anyString())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/rooms")
                .accept(MediaType.APPLICATION_JSON).content(exampleRoomJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void modifyRoomSuccess() throws Exception {
        Mockito.when(roomService.saveRoom(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.of(mockRoom));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/rooms/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleRoomJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void modifyRoomNotFound() throws Exception {
        Mockito.when(roomService.saveRoom(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/rooms/1")
                .accept(MediaType.APPLICATION_JSON).content(exampleRoomJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void deleteRoomSuccess() throws Exception {
        Mockito.when(roomService.deleteRoom(Mockito.anyInt())).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/rooms/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteRoomNotFound() throws Exception {
        Mockito.when(roomService.deleteRoom(Mockito.anyInt())).thenReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/rooms/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void getAllRooms() throws Exception {
        Mockito.when(roomService.getRooms()).thenReturn(new ArrayList<Room>());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/rooms")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}