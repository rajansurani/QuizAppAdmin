package com.example.quizappadmin.Model;

public class Institute {

    private String name, email, instituteCode;

    public Institute(String name, String email, String instituteCode) {
        this.name = name;
        this.email = email;
        this.instituteCode = instituteCode;
    }

    public Institute(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstituteCode() {
        return instituteCode;
    }

    public void setInstituteCode(String instituteCode) {
        this.instituteCode = instituteCode;
    }
}
