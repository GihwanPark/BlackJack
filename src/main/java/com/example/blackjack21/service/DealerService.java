package com.example.blackjack21.service;

import com.example.blackjack21.model.Card;
import com.example.blackjack21.model.ChatMessage;
import com.example.blackjack21.model.Player;
import com.example.blackjack21.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DealerService {
    Player player;
    List<Player> playerArrayList = new ArrayList<>();

    // 점수 계산 함수
    public int calculate(List<Card> hand){
        int sum = 0;
        for(int i =0; i < hand.size(); i++){
            sum += hand.get(i).getValue();
        }
        // 21이상부터 0점으로 계산
        if(sum > 20) {
            sum = 0;
        }

        return sum;
    }

    // 배팅
    public void batting(ChatMessage message,Player player, List<Player> playerArrayList){
        if(Integer.parseInt(message.getValue()) <= player.getCoin()){ //  정상 배팅
            player.setCoin(player.getCoin() - Integer.parseInt(message.getValue()));
            message.setPlayerIndex(Integer.toString(playerArrayList.indexOf(player)));
            message.setMessage(message.getSender() + "님이 " + message.getValue() + "코인 배팅!");
            message.setType("Dealer");
            message.setValue(Integer.toString(player.getCoin()));
            message.setCommand("BET");
        } else { // 배팅숫자가 더 높거나 int 아닐때
            message.setType("Dealer");
            message.setMessage(message.getSender() + "님의 보유 코인이 부족합니다.");
        }
    }

    //승자 가려내기, 승자의 인덱스 반환
    public int winner(List<Player> playerList){
        int total = 0;
        int winnerIndex = 0;
        for(int i = 0; i < playerList.size(); i++){
            if(total < playerList.get(i).getScore()){
                total = playerList.get(i).getScore();
                winnerIndex = i;
            }
        }
        return winnerIndex;
    }

    // 배팅 금액 배분
    public void distributeBat(int winnerIndex, List<Player> playerList){
        int totalBat = 0;

        for(int i = 0; i < playerList.size(); i++){
           // totalBat += playerList.get(i).getBat();
        }

        playerList.get(winnerIndex).setCoin(playerList.get(winnerIndex).getCoin() + totalBat);
    }

    // 카드 나눠주기, 카드 자료형으로 반환
    public void give(ChatMessage message, ArrayList<Card> deck) {
        Random random = new Random();
        boolean hasAvailableCard = false;

        // 사용 가능한 카드가 있는지 확인
        for (Card card : deck) {
            if (card.isStatus()) {
                hasAvailableCard = true;
                break;
            }
        }

        if (!hasAvailableCard) {
            throw new IllegalStateException("No available cards in the deck");
        }

        int i;
        while (true) {
            i = random.nextInt(52);
            Card card = deck.get(i);
            if (card.isStatus()) {
                card.setStatus(false);
                message.setCardImgPath(card.getImgPath());
                System.out.println(message.getCardImgPath()); // 로그 확인
                message.setType("Dealer");
                message.setCommand("HIT");
                break;
            }
        }
    }

    public ArrayList<Card> initDeck(ArrayList<Card> deck){
        String[] shapes = {"h", "d", "c", "s"};
        for(int i = 0; i < 4; i++){
            String shape = shapes[i];
            for(int j = 1; j <14; j++){
                Card card = new Card();
                card.setStatus(true);
                card.setShape(shape);
                card.setValue(j);
                card.setImgPath("/image/" + card.getValue() + card.getShape() + ".png");

                deck.add(card);
            }
        }
        return deck;
    }

}
