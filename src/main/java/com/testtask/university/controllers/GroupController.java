package com.testtask.university.controllers;

import com.testtask.university.dto.InputNameDto;
import com.testtask.university.models.Group;
import com.testtask.university.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/groups"}, produces = APPLICATION_JSON_VALUE)
public class GroupController {
    @Autowired
    GroupService groupService;

    @GetMapping("/{id}")
    public ResponseEntity getGroup(@PathVariable Integer id){
        Optional<Group> group = groupService.getGroup(id);
        if(group.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new Gson().toJson(group.get()));
    }

    @PostMapping
    public ResponseEntity createGroup(@Validated @RequestBody InputNameDto groupName){
        Optional<Group> group = groupService.createGroup(groupName.getName());
        if(group.isEmpty())
            return ResponseEntity.badRequest().body("Group "+ groupName.getName() + " already exists");
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(group.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity modifyGroup(@PathVariable Integer id, @Validated @RequestBody InputNameDto groupName){
        Optional<Group> group = groupService.saveGroup(id, groupName.getName());
        if(group.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new Gson().toJson(group.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGroup(@PathVariable Integer id){
        if (groupService.deleteGroup(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity getAllGroups(){
        List<Group> groups = groupService.getGroups();
        return ResponseEntity.ok(new Gson().toJson(groups));
    }
}
