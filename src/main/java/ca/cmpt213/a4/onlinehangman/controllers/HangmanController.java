package ca.cmpt213.a4.onlinehangman.controllers;

import ca.cmpt213.a4.onlinehangman.model.Game;
import ca.cmpt213.a4.onlinehangman.model.GameStatus;
import ca.cmpt213.a4.onlinehangman.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return renderView(model);
    }
    @PostMapping("/submitGuess")
    public String submitGuessesLetter(@RequestParam("guessedChar") String guessedChar,Model model){
        game.gameLogic(Character.toLowerCase(guessedChar.charAt(0)));
        return renderView(model);

    }

    @GetMapping("/game/{Id}")
    public String findGame(@PathVariable("Id") long Id, Model model){
        for (Game gameId: gameList ){
            if (gameId.getId() == Id){
                game = gameId;
                model.addAttribute("game", game);
                return renderView(model);
            }
        }

        promptMessage.setMessage("Game does not exist with Id: " + Id);
        model.addAttribute("promptMessage", promptMessage);

        return "gamenotfound";



    }

    public String renderView(Model model){
        if (game.getStatus() == GameStatus.WON){
            promptMessage.setMessage("This Game is over and you WON!");
            model.addAttribute("promptMessage", promptMessage);
            model.addAttribute("game", game);
            return "gameOver";
        }
        else if(game.getStatus() == GameStatus.LOST){
            promptMessage.setMessage("This Game over and you LOST!");
            model.addAttribute("promptMessage", promptMessage);
            model.addAttribute("game", game);
            return "gameOver";
        }
        else {
            model.addAttribute("game",game);
            return "game";
        }

    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Game ID not found.")
    @ExceptionHandler(GameNotFoundException.class)
    public String GameNotFoundExceptionHandler() {

        return "gamenotfound";
    }

}
