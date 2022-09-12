package com.testtask.university.services;

import com.testtask.university.models.Group;
import com.testtask.university.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService (GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public Optional<Group> getGroup(Integer id){
        return groupRepository.findById(id);
    }

    public Optional<Group> createGroup(String groupName){
        Optional<Group> groupCheck = groupRepository.findByName(groupName);
        if (groupCheck.isPresent())
            return Optional.empty();
        Group newGroup = new Group(groupName);
        groupRepository.save(newGroup);
        return Optional.of(newGroup);
    }

    public Optional<Group> saveGroup(Integer id, String newGroupName){
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()){
            group.get().setName(newGroupName);
            groupRepository.save(group.get());
        }
        return group;
    }

    public boolean deleteGroup(Integer id){
        if(groupRepository.findById(id).isPresent()) {
            groupRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    public List<Group> getGroups(){
        return groupRepository.findAll();
    }
}
