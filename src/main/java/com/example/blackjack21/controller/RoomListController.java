package com.example.blackjack21.controller;

import com.example.blackjack21.model.Player;
import com.example.blackjack21.repository.PlayerRepository;
import com.example.blackjack21.repository.RoomRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "/blackjack")
public class RoomListController {
    private final RoomRepository repository;
    private final PlayerRepository playerRepository;
    private static String nickname;

    @PostMapping("/save")
    public String saveName(@RequestParam("nickname") String name, RedirectAttributes rttr) {
        nickname = name;
        rttr.addFlashAttribute("joinPlayer", playerRepository.joinRoom(name));
        return "redirect:/blackjack/roomList";
    }

    @GetMapping(value = "/roomList")
    public ModelAndView rooms(){
        log.info("# All Game Rooms");
        ModelAndView mv = new ModelAndView("roomList");
        mv.addObject("list", repository.findAllRooms());

        return mv;
    }

    @PostMapping(value = "/room")
    public String create(@RequestParam("name") String name, RedirectAttributes rttr){
        log.info("# Create GameRoom, name: " + name + ", writer : " + playerRepository.findByPlayerName(nickname));
        rttr.addFlashAttribute( "roomName", repository.createRoom(name));
        return "redirect:/blackjack/roomList";
    }

    @GetMapping("/room")
    public String getRoom(String roomId, Model model){
        log.info("# get Game Room, roomId: " + roomId);
        log.info(playerRepository.findByPlayerName(nickname).getName());
        model.addAttribute("player", playerRepository.findByPlayerName(nickname));
        model.addAttribute("playerList", playerRepository.getAllPlayers());
        model.addAttribute("room", repository.findByRoomId(roomId));
        return "roomTest";
    }

}
