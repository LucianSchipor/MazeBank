package com.example.mazebank.Core.Forms;

import com.example.mazebank.Core.Credit.Credit;

import java.util.Date;

public class FormAccount extends Form {
    public FormAccount(int form_id, int user_id, String form_path, Date date, FormStatus status, int formType) {
        super(form_id, user_id, form_path, date, status, formType);
    }

    //se vor putea creea bank account-uri
    public void setFormDetails(){

    }
}
