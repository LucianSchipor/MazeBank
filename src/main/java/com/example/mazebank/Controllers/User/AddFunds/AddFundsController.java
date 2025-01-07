package com.example.mazebank.Controllers.User.AddFunds;

import com.example.mazebank.Core.Bank.BankInfo;
import com.example.mazebank.Core.Credit.Credit;
import com.example.mazebank.Core.Credit.LoanCalculator;
import com.example.mazebank.Core.Models.UserLoggedIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddFundsController implements Initializable {
    public TextField requested_sum_fld;
    public ChoiceBox period_choicebox;
    public ChoiceBox currency_choicebox;
    public Label monthly_rate_lbl;
    public Label interest_lbl;
    public Label analysis_comission_lbl;
    public Label total_credit_value_lbl;
    public Label effective_anual_interest_lbl;
    public Label total_payment_amount_lbl;
    public Button createForm_btn;
    public VBox details_container;
    public Button calculate_btn;
    public CheckBox check_box;
    Credit credit = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        check_box.setSelected(false);
        check_box.setOnAction(n -> {
            if(check_box.isSelected()){
                this.createForm_btn.setDisable(false);
            }
            else{
                this.createForm_btn.setDisable(true);
            }
        });
        details_container.setVisible(false);
        createForm_btn.setOnAction(this::onApply);
        calculate_btn.setOnAction(this::onCalculate);
        createForm_btn.setDisable(true);
        ObservableList<String> Currencies = FXCollections.observableArrayList();
        Currencies.add("RON");
        Currencies.add("EUR");
        Currencies.add("CHF");
        Currencies.add("GBP");
        currency_choicebox.setItems(Currencies);

        ObservableList<Object> Periods = FXCollections.observableArrayList();
        Periods.add(String.valueOf(6));
        Periods.add(String.valueOf(12));
        Periods.add(String.valueOf(24));
        Periods.add(String.valueOf(48));
        period_choicebox.setItems(Periods);
    }

    private void onApply(ActionEvent actionEvent) {

    }

    private void onCalculate(javafx.event.ActionEvent actionEvent) {
        CalculateCredit();
        this.details_container.setVisible(true);
        monthly_rate_lbl.setText(String.valueOf(credit.getCredit_monthly_rate()) + " " + credit.getCredit_currency());
        interest_lbl.setText(String.valueOf(credit.getCredit_intrest()) + "%");
        total_credit_value_lbl.setText(String.valueOf(credit.getCredit_total_sum()) + " " + credit.getCredit_currency());
        effective_anual_interest_lbl.setText(String.valueOf(credit.getCredit_intrest()) + "%");
        total_payment_amount_lbl.setText(String.valueOf(credit.getCredit_monthly_rate() * credit.getCredit_period()) + " " + credit.getCredit_currency());
    }

    private void CalculateCredit(){
        var credit_totalSum = Float.valueOf(requested_sum_fld.getText());
        var credit_period = Integer.valueOf(period_choicebox.getValue().toString());
        var credit_currency = currency_choicebox.getValue().toString();
        var credit_intrest = BankInfo.getInstance().getIntrest();
        var credit_monthly_rate = (float)LoanCalculator.calculateMonthlyRate(credit_totalSum, credit_period);

        var loggedUser = UserLoggedIn.getInstance().getLoggedInUser();
        this.credit = new Credit(loggedUser.getUserId(), credit_totalSum, credit_period, credit_currency, credit_monthly_rate, credit_intrest);
    }



}
