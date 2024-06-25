package com.example.blackjack21.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Dealer {
   public ArrayList<Card> deck;
   public String winner;
   public int totalBet;
}
