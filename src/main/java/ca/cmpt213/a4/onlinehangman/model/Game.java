package ca.cmpt213.a4.onlinehangman.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
 * Game class models the information about a Game.
 * This class also has all the Game logic.
 * This class has all the Game related fields and Methods
 */



public class Game {
    private long Id;
    private String guessedWord;
    private int numberOfGuesses;
    private int numberOfIncorrectGuesses;
    private GameStatus status;


    private String gameArrayToWord;

    private final int MAX_INCORRECT_GUESSES = 7;
    private ArrayList<Character> gameArray = new ArrayList<>();
    private ArrayList<Character> inputLetters = new ArrayList<>();
    public String allInputs;
    public int duplicateCorrectGuessCounter;
    public int duplicateInCorrectGuessCounter;


    public Game(long Id) {
        this.Id = Id;
        this.guessedWord = getRandomWord();
        this.numberOfGuesses = 0;
        this.numberOfIncorrectGuesses = 0;
        this.status = GameStatus.ACTIVE;
        gameArrayToWord = " ";
        allInputs = " ";
        duplicateCorrectGuessCounter = 0;
        duplicateInCorrectGuessCounter = 0;
        fillArray();
    }


    public void gameLogic(Character c) {
        if (getStatus() == GameStatus.ACTIVE) {
            showGameArray();

            if (!isGameArrayMatchWord() && getNumberOfIncorrectGuesses() <= MAX_INCORRECT_GUESSES) {

                inputLetters.add(c);
                showAllInput();
                if (isLetterInWord(c)) {

                    duplicateCorrectGuess(c);

                    setNumberOfGuesses(inputLetters.size());
                    if (isGameArrayMatchWord()) {
                        setStatus(GameStatus.WON);
                    }

                } else {
                    duplicateInCorrectGuess(c);
                    setNumberOfIncorrectGuesses();
                    setNumberOfGuesses(inputLetters.size());
                    if (getNumberOfIncorrectGuesses() > MAX_INCORRECT_GUESSES) {

                        setStatus(GameStatus.LOST);

                    }

                }
            }
        }


    }

    //    https://stackoverflow.com/questions/275944/how-do-i-count-the-number-of-occurrences-of-a-char-in-a-string
    private void duplicateCorrectGuess(Character c) {
        long count = allInputs.chars().filter(ch -> ch == c).count();
        if (count > 1) {
            duplicateCorrectGuessCounter++;
        }

    }

    private void duplicateInCorrectGuess(Character c) {
        long count = allInputs.chars().filter(ch -> ch == c).count();
        if (count > 1) {
            duplicateInCorrectGuessCounter++;
        }

    }


    private void fillArray() {
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

    private String showGameArray() {
        gameArrayToWord = "";
        for (int i = 0; i < guessedWord.length(); i++) {
            gameArrayToWord = gameArrayToWord + gameArray.get(i);
        }


        return gameArrayToWord;
    }

    private void showAllInput() {
        allInputs = " ";
        for (int i = 0; i < inputLetters.size(); i++) {
            allInputs = allInputs + inputLetters.get(i);
        }

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
