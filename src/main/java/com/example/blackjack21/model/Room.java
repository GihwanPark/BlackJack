package com.example.blackjack21.model;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Room {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static Room create(String name){
        Room room = new Room();

        room.roomId = UUID.randomUUID().toString();
        room.name = name;
        return room;
    }
}
