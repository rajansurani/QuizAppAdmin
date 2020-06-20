package com.example.quizappadmin.Model;

import java.io.Serializable;

public class User implements Serializable {

    private String name, email, instituteCode, branch, semester, role;

    public User(String name, String email, String instituteCode, String branch, String semester, String role) {
        this.name = name;
        this.email = email;
        this.instituteCode = instituteCode;
        this.branch = branch;
        this.semester = semester;
        this.role = role;
    }

    public User(){}

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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
