package com.example.mazebank.Core.Forms;

import com.example.mazebank.Core.Credit.Credit;
import com.example.mazebank.Core.Users.User;

import java.util.Date;

public class FormAccount extends Form {
    public FormAccount(int form_id, User user, String form_path, Date date, FormStatus status, int formType) {
        super(form_id, user, form_path, date, status, formType);
    }

    public FormAccount(Form form){
        super(form.getForm_id(), form.getCreator(), form.getForm_path(), form.getDate(), form.getStatus(), form.getFormType().ordinal());

    }


    //se vor putea creea bank account-uri
    public void setFormDetails(){

    }
}
