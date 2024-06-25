package com.example.blackjack21.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Card {
    public int value; // 숫자
    public String shape; // 무늬
    private String imgPath; // 이미지 경로
    public boolean status; // 덱에 있는지 여부
}
