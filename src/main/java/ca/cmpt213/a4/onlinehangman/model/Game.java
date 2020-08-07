package ca.cmpt213.a4.onlinehangman.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private long Id;
    private String guessedWord;
    private int numberOfGuesses;
    private int numberOfIncorrectGuesses;
    private GameStatus status;



    private String gameArrayToWord;

    private final int MAX_INCORRECT_GUESSES = 7;
    ArrayList<Character> gameArray = new ArrayList<>();
    ArrayList<Character> inputLetters = new ArrayList<>();
    public String allInputs;


    public Game(long Id) {
        this.Id = Id;
        this.guessedWord = "hello";
        this.numberOfGuesses = 0;
        this.numberOfIncorrectGuesses = 0;
        this.status = GameStatus.ACTIVE;
        gameArrayToWord = " ";
        allInputs = " ";
        fillArray();
    }


    public void gameLogic(Character c) {
        if (getStatus() == GameStatus.ACTIVE) {
            showGameArray();

            if (!isGameArrayMatchWord() && getNumberOfIncorrectGuesses() <= MAX_INCORRECT_GUESSES) {

                inputLetters.add(c);
                showAllInput();
                if (isLetterInWord(c)) {
                    System.out.println("Correct Guess");

                    setNumberOfGuesses(inputLetters.size());
                    System.out.println("User Guessed " + inputLetters);
                    if (isGameArrayMatchWord()){
                        setStatus(GameStatus.WON);
                    }

                } else {
                    System.out.println("Wrong Guess");
                    setNumberOfIncorrectGuesses();
                    setNumberOfGuesses(inputLetters.size());
                    System.out.println("User Guessed " + inputLetters);
                    System.out.println("Total Wrong Guess: " + getNumberOfIncorrectGuesses());
                    if (getNumberOfIncorrectGuesses() > MAX_INCORRECT_GUESSES) {
                        System.out.println("You lost");
                        setStatus(GameStatus.LOST);
                        System.out.println("The Guess was: " + guessedWord);
                    }

                }
            }
        }


    }

    public void fillArray() {
        for (int i = 0; i < guessedWord.length(); i++) {
            gameArray.add('_');
        }
    }


    private boolean isLetterInWord(Character userInput) {
        int counter = 0;
        for (int i = 0; i < guessedWord.length(); i++) {
            if (guessedWord.charAt(i) == userInput) {
                gameArray.set(i, userInput);
                counter += 1;
            }
        }
        return counter > 0;
    }

    public String showGameArray() {
        gameArrayToWord = "";
        for (int i = 0; i < guessedWord.length(); i++) {
            gameArrayToWord = gameArrayToWord + gameArray.get(i);
        }
        System.out.println(gameArrayToWord);

        return gameArrayToWord;
    }

    public String showAllInput(){
        allInputs = " ";
        for (int i = 0; i < inputLetters.size(); i++) {
            allInputs = allInputs + inputLetters.get(i);
        }

        return allInputs;
    }

    public ArrayList<Character> getInputLetters() {
        return inputLetters;
    }


    //check if gameArray == word
    private boolean isGameArrayMatchWord() {
        for (int i = 0; i < guessedWord.length(); i++) {
            if (guessedWord.charAt(i) != gameArray.get(i)) {
                return false;
            }
        }
        System.out.println("You Won!");
        setStatus(GameStatus.WON);
        return true;
    }


    private String getRandomWord() {
        String randomWord = "";

        // https://stackoverflow.com/questions/19844649/java-read-file-and-store-text-in-an-array
        try {
            String token1 = "";

            // create Scanner inFile1
            Scanner inFile1 = new Scanner(new File("src/commonWords.txt"));

            ArrayList<String> temps = new ArrayList<String>();

            while (inFile1.hasNextLine()) {
                // find next line
                token1 = inFile1.nextLine();
                temps.add(token1);
            }
            inFile1.close();
            Random rand = new Random();
            int randomIndex = rand.nextInt(temps.size());
            randomWord = (temps.get(randomIndex));


        } catch (IOException e) {
            System.out.println("Error! Read unsuccessful");
        }

        return randomWord;

    }
    public String getGameArrayToWord() {
        return showGameArray();
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getGuessedWord() {
        return guessedWord;
    }

    public void setGuessedWord(String guessedWord) {
        this.guessedWord = guessedWord;
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }

    public void setNumberOfGuesses(int numberOfGuesses) {

        this.numberOfGuesses = numberOfGuesses;
    }

    public int getNumberOfIncorrectGuesses() {
        return numberOfIncorrectGuesses;
    }

    public void setNumberOfIncorrectGuesses() {
        this.numberOfIncorrectGuesses += 1;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
