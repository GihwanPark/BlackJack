package com.example.blackjack21.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String roomId;
    private String sender;
    private String message;
    private String command; // HIT / STOP ... Command Message
    private String value; // 배팅값
    private String type; // Dealer or Player
    private String playerIndex; // 보낸 플레이어의 index 값
    private String cardImgPath; // 카드 이미지 경로
}
