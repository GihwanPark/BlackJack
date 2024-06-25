package com.example.blackjack21.repository;

import com.example.blackjack21.model.Player;
import com.example.blackjack21.model.Room;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PlayerRepository {
    private Map<String , Player> playerMap;

    @PostConstruct
    private void init(){
        playerMap = new LinkedHashMap<>();
    }

    public List<Player> getAllPlayers(){
        List<Player> pList = new ArrayList<>(playerMap.values());

        return pList;
    }

    public Player findByPlayerName(String name){return playerMap.get(name);}

    public Player joinRoom(String name) {
        Player player = Player.join(name);
        playerMap.put(player.getName(), player);

        return player;
    }
}
