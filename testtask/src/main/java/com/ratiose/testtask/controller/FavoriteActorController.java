package com.ratiose.testtask.controller;

import com.ratiose.testtask.service.FavoriteActorService;
import com.ratiose.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/favoriteActor")
public class FavoriteActorController {

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteActorService favoriteActorService;

    @RequestMapping(value = "/add", method = POST)
    public ResponseEntity addFavoriteActor(@RequestParam String email,
                                           @RequestParam String password,
                                           @RequestParam String actorName,
                                           HttpSession session) {
        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (favoriteActorService.addFavoriteActor(email, actorName) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/remove", method = POST)
    public ResponseEntity removeFavoriteActor(@RequestParam String email,
                                              @RequestParam String password,
                                              @RequestParam String actorName,
                                              HttpSession session) {
        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (favoriteActorService.removeFavoriteActor(email, actorName)) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
