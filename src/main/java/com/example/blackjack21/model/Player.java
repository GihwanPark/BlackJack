package com.example.blackjack21.model;
import lombok.*;
import lombok.experimental.Accessors;
import java.util.ArrayList;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
// 플레이어 정보
public class Player{

    String name;

    int coin; // 가진 코인

    ArrayList<Card> hand;

    boolean status;  // 더 받을지 말지
    public int score; // 점수

    public static Player join(String name){
        Player player = new Player();
        player.name = name;
        player.coin = 10;
        player.score = 0;
        player.status = true;
        player.hand = null;
        return player;
    }
}
