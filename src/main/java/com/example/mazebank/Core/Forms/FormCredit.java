package com.example.mazebank.Core.Forms;

import com.example.mazebank.Core.Credit.Credit;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormCredit extends Form {
    private Credit credit;
    private List<Pair<String, String>> FinancialDetails;

    public FormCredit(int form_id, int user_id, String form_path, Date date, FormStatus status, int formType) {
        super(form_id, user_id, form_path, date, status, formType);
    }

    public FormCredit(Credit credit) {
        super(FormType.FUNDS);
        this.credit = credit;
    }

    public List<Pair<String, String>> GetFinancialDetails() {
        return this.FinancialDetails;
    }

    public void SetFinancialDetails(List<Pair<String, String>> FinancialDetails) {
        this.FinancialDetails = FinancialDetails;
    }

    public List<String> GetCreditDetails() {
        return new ArrayList<>() {{
            this.add("Total requested: " + credit.getCredit_total_sum() + " " + credit.getCredit_currency());
            this.add("Period: " + credit.getCredit_period());
            this.add("Monthly rate: " + credit.getCredit_monthly_rate());
            this.add("Interest:" + credit.getCredit_intrest());
        }};
    }

}
