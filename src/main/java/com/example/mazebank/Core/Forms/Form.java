package com.example.mazebank.Core.Forms;

import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Users.User;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Form {
    private int form_id;
    private String form_path;
    private final Date date;
    private User creator;
    private FormStatus status;
    private FormType formType;


    //Used when getting forms from DB
    public Form(int form_id, User user, String form_path, Date date, FormStatus status, int formType) {
        this.form_id = form_id;
        this.form_path = form_path;
        this.date = date;
        this.creator = user;
        this.status = status;
        if (formType == 0) {
            this.formType = FormType.ACCOUNT;
        } else {
            this.formType = FormType.FUNDS;
        }
    }

    //used when putting forms in DB
    public Form(FormType formType) {
        form_id = 0;
        form_path = "";
        this.status = FormStatus.PENDING;
        this.formType = formType;
        this.date = java.sql.Date.valueOf(LocalDate.now());
        this.creator = UserLoggedIn.getInstance().getLoggedInUser();

    }

    public String getForm_path() {
        return form_path;
    }

    public Date getDate() {
        return date;
    }

    public int getForm_id() {
        return form_id;
    }

    public FormStatus getStatus() {
        return status;
    }

    public void setStatus(FormStatus status) {
        this.status = status;
    }

    public void SetFormPath(String path) {
        this.form_path = path;
    }

    public void SetFormId(int id){
        this.form_id = id;
    }

    public List<Pair<String, String>> GetBasicFormDetails(){
        List<Pair<String, String>> dets = new ArrayList<>();

        dets.add(new Pair<>("Username: ", creator.getUsername()));
        dets.add(new Pair<>("Email: ", creator.getEmail()));
        dets.add(new Pair<>("Form Status: ", status.name()));
        dets.add(new Pair<>("Creation Date: ", date.toString()));
        return dets;
    }


    public User GetCreator() {
        return creator;
    }
    public int getUser_id() {
        return creator.getUserId();
    }

    public FormType getFormType() {
        return formType;
    }

}
