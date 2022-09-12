package com.testtask.university.services;

import com.testtask.university.models.Room;
import com.testtask.university.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService (RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public Optional<Room> getRoom(Integer id){
        return roomRepository.findById(id);
    }

    public Optional<Room> createRoom(String roomName){
        Optional<Room> roomCheck = roomRepository.findByName(roomName);
        if (roomCheck.isPresent())
            return Optional.empty();
        Room newRoom = new Room(roomName);
        roomRepository.save(newRoom);
        return Optional.of(newRoom);
    }

    public Optional<Room> saveRoom(Integer id, String newRoomName){
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()){
            room.get().setName(newRoomName);
            roomRepository.save(room.get());
        }
        return room;
    }

    public boolean deleteRoom(Integer id){
        if(roomRepository.findById(id).isPresent()) {
            roomRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    public List<Room> getRooms(){
        return roomRepository.findAll();
    }
}
