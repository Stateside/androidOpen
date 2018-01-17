package com.stateside.stateside.information;

import com.google.gson.annotations.SerializedName;

public class GanadorResponse {

    boolean error;
    String message;
    String name;
    @SerializedName("last_name")
    String lastName;
    @SerializedName("winner ")
    int winner;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
