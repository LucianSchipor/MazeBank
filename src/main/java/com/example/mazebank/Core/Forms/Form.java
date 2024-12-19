package com.example.mazebank.Core.Forms;

import java.util.Date;

public class Form {
    private final int form_id;
    private final String form_path;
    private final Date date;
    private final int user_id;
    private int status;

    public Form(int form_id, int user_id, String form_path, Date date, int status) {
        this.form_id = form_id;
        this.form_path = form_path;
        this.date = date;
        this.user_id = user_id;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
