package com.example.mazebank.Core.Forms;

import com.example.mazebank.Core.Credit.Credit;
import com.example.mazebank.Core.Users.User;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormCredit extends Form {
    private Credit credit;
    List<Pair<String, String>> dets = new ArrayList<>();
    private List<Pair<String, String>> FinancialDetails;

    public FormCredit(int form_id, User user, String form_path, Date date, FormStatus status, int formType) {
        super(form_id, user, form_path, date, status, formType);
    }

    public FormCredit(Form form, Credit credit) {
        super(form.getForm_id(), form.getCreator(), form.getForm_path(), form.getDate(), form.getStatus(), form.getFormType().ordinal());
        this.credit = credit;
    }

    public List<Pair<String, String>> getDetails() {
        return this.dets;
    }

    public void setFinancialDetails(List<Pair<String, String>> FinancialDetails) {
        this.FinancialDetails = FinancialDetails;
    }

    public void setDetails(){
        dets.addAll(super.getBasicFormDetails());
        dets.addAll(FinancialDetails);

    }
}
