package com.example.blackjack21.repository;

import com.example.blackjack21.model.Room;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RoomRepository {
    private Map<String , Room> roomMap;

    @PostConstruct
    private void init(){
        roomMap = new LinkedHashMap<>();
    }

    public List<Room> findAllRooms(){
        List<Room> result = new ArrayList<>(roomMap.values());
        Collections.reverse(result);

        return result;
    }

    public Room findByRoomId(String id){
        return roomMap.get(id);
    }

    public Room createRoom(String name){
        Room room = Room.create(name);
        roomMap.put(room.getRoomId(), room);

        return room;
    }
}
