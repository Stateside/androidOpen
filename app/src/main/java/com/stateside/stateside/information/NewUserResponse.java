package com.stateside.stateside.information;

import java.util.ArrayList;

public class NewUserResponse {
    long id;
    boolean error;
    String message;

    ArrayList<String> fullName;
    ArrayList<String> email;
    ArrayList<String> phone;

    public long getId() {
        return id;
    }

    public ArrayList<String> getFullName() {
        return fullName;
    }

    public void setFullName(ArrayList<String> fullName) {
        this.fullName = fullName;
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }

    public ArrayList<String> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<String> phone) {
        this.phone = phone;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
