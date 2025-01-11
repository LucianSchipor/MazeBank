package com.example.mazebank.Core.Forms;

import com.example.mazebank.Core.Models.UserLoggedIn;

import java.time.LocalDate;
import java.util.Date;

public class Form {
    private final int form_id;
    private final String form_path;
    private final Date date;
    private final int user_id;
    private FormStatus status;
    private FormType formType;


    //used when putting forms in DB


    //Used when getting forms from DB
    public Form(int form_id, int user_id, String form_path, Date date, FormStatus status, int formType) {
        this.form_id = form_id;
        this.form_path = form_path;
        this.date = date;
        this.user_id = user_id;
        this.status = status;
        if (formType == 0) {
            this.formType = FormType.ACCOUNT;
        } else {
            this.formType = FormType.FUNDS;
        }
    }

    public Form(FormType formType) {
        form_id = 0;
        form_path = "";
        this.status = FormStatus.PENDING;
        this.formType = formType;
        this.date = java.sql.Date.valueOf(LocalDate.now());
        this.user_id = UserLoggedIn.getInstance().getLoggedInUser().getUserId();

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

    public int getUser_id() {
        return user_id;
    }

    public FormType getFormType() {
        return formType;
    }

}
