package com.testtask.university.controllers;

import com.google.gson.Gson;
import com.testtask.university.dto.InputNameDto;
import com.testtask.university.models.Room;
import com.testtask.university.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/rooms"}, produces = APPLICATION_JSON_VALUE)
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping("/{id}")
    public ResponseEntity getRoom(@PathVariable Integer id){
        Optional<Room> room = roomService.getRoom(id);
        if(room.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new Gson().toJson(room.get()));
    }

    @PostMapping
    public ResponseEntity createRoom(@Validated @RequestBody InputNameDto roomName){
        Optional<Room> room = roomService.createRoom(roomName.getName());
        if(room.isEmpty())
            return ResponseEntity.badRequest().body("Room "+ roomName.getName() + " already exists");
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(room.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity modifyRoom(@PathVariable Integer id, @Validated @RequestBody InputNameDto roomName){
        Optional<Room> room = roomService.saveRoom(id, roomName.getName());
        if(room.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new Gson().toJson(room.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRoom(@PathVariable Integer id){
        if (roomService.deleteRoom(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity getAllRooms(){
        List<Room> rooms = roomService.getRooms();
        return ResponseEntity.ok(new Gson().toJson(rooms));
    }
}
