package com.example.blackjack21.controller;

import com.example.blackjack21.model.Card;
import com.example.blackjack21.model.ChatMessage;
import com.example.blackjack21.model.Dealer;
import com.example.blackjack21.model.Player;
import com.example.blackjack21.repository.PlayerRepository;
import com.example.blackjack21.service.DealerService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StompGameRoomsController {

    private final SimpMessagingTemplate template;
    private final PlayerRepository playerRepository;
    private final DealerService dealerService;
    public ArrayList<Card> deck = new ArrayList<>();
    public Player player = new Player();
    public List<Player> playerList = new ArrayList<>();

    @MessageMapping(value = "/blackjack/enter")
    public void enter(ChatMessage message) {
        player = playerRepository.findByPlayerName(message.getSender());
        playerList = playerRepository.getAllPlayers();

        message.setMessage(player.getName() + "님이 입장하셨습니다.");
        message.setType("Dealer");
        message.setCommand("ENTER");
        message.setPlayerIndex(Integer.toString(playerList.indexOf(player)));
        message.setValue("10");
        template.convertAndSend("/sub/blackjack/room/" + message.getRoomId(), message);
    }

    // 채팅
    @MessageMapping("/blackjack/message")
    public void message(ChatMessage message) {
        template.convertAndSend("/sub/blackjack/room/" + message.getRoomId(), message);
    }

    // 딜러가 데이터 처리 후 게임방에 뿌려줌
    @MessageMapping("/blackjack/dealer")
    public void dealer(ChatMessage message, Dealer dealer){
        template.convertAndSend("/sub/blackjack/room/" + message.getRoomId(), dealer);
    }

    // 게임 메세지 코드
    // 클라이언트가 서버에게 요청 (BET / HIT / STOP)
    @MessageMapping("/blackjack/command") // JSON 형식으로 자동파싱
    public void handleCommand(ChatMessage message){
        switch (message.getCommand()){
            case "START" : // 게임 시작, 카드 관련 초기화
                deck = dealerService.initDeck(deck);
                message.setMessage("게임을 시작합니다!");
                message.setType("Dealer");
                break;

            case "BET" :
                player = playerRepository.findByPlayerName(message.getSender());
                playerList = playerRepository.getAllPlayers();
                dealerService.batting(message, player, playerList);
                break;

            case "HIT" :
                dealerService.give(message, deck);
                break;

            case "STOP" : // 그만 받기
                break;

            case "RESULT" : // 승자를 가려내고, 배팅 금액 지급, 덱 리셋
               // dealerService.winner();
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + message.getCommand());
        }
        template.convertAndSend("/sub/blackjack/room/" + message.getRoomId(), message);
    }
}
