package ca.cmpt213.a4.onlinehangman.controllers;

import ca.cmpt213.a4.onlinehangman.model.Game;
import ca.cmpt213.a4.onlinehangman.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class HangmanController {

    private Message promptMessage; //a resusable String object to display a prompt message at the screen
    private ArrayList<Game> gameList = new ArrayList<>();
    private AtomicLong Id = new AtomicLong(0);
    private Game game;



    //works like a constructor, but wait until dependency injection is done, so it's more like a setup
    @PostConstruct
    public void hangmanControllerInit() {
        promptMessage = new Message("Initializing...");
    }

    @GetMapping({"/welcome", "/"})
    public String showWelcomePage(Model model) {
        promptMessage.setMessage("Welcome to Hangman Game");
        model.addAttribute("promptMessage", promptMessage);

        // take the user to welcome.html
        return "welcome";
    }
    @PostMapping("/game")
    public String gamePage(Model model){
        game = new Game(Id.incrementAndGet());
        gameList.add(game);
        model.addAttribute("game", game);
        return "game";
    }
    @PostMapping("/submitGuess")
    public String submitGuessesLetter(){
        return "";

    }
}
