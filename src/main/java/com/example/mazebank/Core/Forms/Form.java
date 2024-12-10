package com.example.mazebank.Core.Forms;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Date;

public class Form {
    private final int form_id;
  private String form_path;
  private Date date;
  private String lName;
  private String fName;
  private String status;

  public Form(int form_id, String form_path, Date date, String lName, String fName, String status) {
      this.form_id = form_id;
      this.form_path = form_path;
      this.date = date;
      this.lName = lName;
      this.fName = fName;
      this.status = status;
  }

    public String getForm_path() {
        return form_path;
    }

    public void setForm_path(String form_path) {
        this.form_path = form_path;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public int getForm_id() {
        return form_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
