package ca.cmpt213.a4.onlinehangman.model;


/*
* Message class construct the desired message to send to Thymeleaf template
* */

public class Message {
    private String message;

    public Message() {
        this.message = "";
    }

    public Message(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
