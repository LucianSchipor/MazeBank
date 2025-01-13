package com.example.mazebank.Controllers.User.AddFunds;

import com.example.mazebank.Core.Bank.BankInfo;
import com.example.mazebank.Core.Credit.Credit;
import com.example.mazebank.Core.Credit.LoanCalculator;
import com.example.mazebank.Core.Forms.Form;
import com.example.mazebank.Core.Forms.FormCredit;
import com.example.mazebank.Core.Forms.FormType;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Repositories.Forms.DB_Forms;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddFundsController implements Initializable {
    public TextField requested_sum_fld;
    public ChoiceBox<Object> period_choicebox;
    public ChoiceBox<String> currency_choicebox;
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
    public TextField companyName_fld;
    public TextField employment_contract_fld;
    public TextField net_monthly_income_fld;
    public TextField debts_fld;
    Credit credit = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CheckFieldsAreNotEmpty();
        check_box.setSelected(false);
        check_box.setOnAction(n -> this.createForm_btn.setDisable(!check_box.isSelected()));
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
        try {
            //TODO -> de schimbat cu a avea 2 tipuri de formulare, care mostenesc Forms.
            Form form = new Form(FormType.FUNDS);
            FormCredit creditForm = new FormCredit(form, this.credit);
            creditForm.SetFinancialDetails(CreateFinancialDetailsList());
            creditForm.SetDetails();
            DB_Forms.CreateForm(creditForm);
        } catch (Exception e) {
            System.out.println("[LOG][AddFunds] - " + e.getCause() + " at: " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One field is empty!");
            alert.showAndWait();
        } finally {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success!");
            alert.setTitle("Success!");
            alert.setContentText("Credit request has been created successfully!");
            alert.showAndWait();
        }

    }

    private void onCalculate(javafx.event.ActionEvent actionEvent) {
        try {
            CalculateCredit();
            monthly_rate_lbl.setText(credit.getCredit_monthly_rate() + " " + credit.getCredit_currency());
            interest_lbl.setText(credit.getCredit_intrest() + "%");
            total_credit_value_lbl.setText(credit.getCredit_total_sum() + " " + credit.getCredit_currency());
            effective_anual_interest_lbl.setText(credit.getCredit_intrest() + "%");
            total_payment_amount_lbl.setText(credit.getCredit_monthly_rate() * credit.getCredit_period() + " " + credit.getCredit_currency());
            this.details_container.setVisible(true);
        } catch (Exception e) {
            System.out.println("[LOG][AddFunds] - " + e.getCause() + " at: " + e.getLocalizedMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One field is empty!");
            alert.showAndWait();
        }

    }

    private void CalculateCredit() {
        try {
            float credit_totalSum = Float.parseFloat(requested_sum_fld.getText());
            int credit_period = Integer.parseInt(period_choicebox.getValue().toString());
            var credit_currency = currency_choicebox.getValue();
            var credit_intrest = BankInfo.getInstance().getIntrest();
            var credit_monthly_rate = (float) LoanCalculator.calculateMonthlyRate(credit_totalSum, credit_period);
            var loggedUser = UserLoggedIn.getInstance().getLoggedInUser();
            this.credit = new Credit(loggedUser.getUserId(), credit_totalSum, credit_period, credit_currency, credit_monthly_rate, credit_intrest);
        } catch (Exception e) {
            System.out.println("[LOG][AddFunds] - " + e.getCause() + " at: " + e.getLocalizedMessage());
        }
    }

    private void CheckFieldsAreNotEmpty() {
        calculate_btn.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> companyName_fld.getText().isEmpty() ||
                                employment_contract_fld.getText().isEmpty() ||
                                net_monthly_income_fld.getText().isEmpty() ||
                                debts_fld.getText().isEmpty() ||
                                requested_sum_fld.getText().isEmpty() ||
                                period_choicebox.getValue() == null,
                        companyName_fld.textProperty(),
                        employment_contract_fld.textProperty(),
                        net_monthly_income_fld.textProperty(),
                        debts_fld.textProperty(),
                        requested_sum_fld.textProperty(),
                        period_choicebox.valueProperty()
                )
        );
    }

    private List<Pair<String, String>> CreateFinancialDetailsList() {
        List<Pair<String, String>> FinancialDetails = new ArrayList<>();
        FinancialDetails.add(
                new Pair<>(
                        "Employer: ",
                        String.valueOf(companyName_fld.getText())
                ));
        FinancialDetails.add(
                new Pair<>(
                        "Employment Contract: ",
                        employment_contract_fld.getText()
                ));
        FinancialDetails.add(
                new Pair<>(
                        "Net Monthly Income: ",
                        net_monthly_income_fld.getText()
                ));
        FinancialDetails.add(
                new Pair<>(
                        "Existing Debts ",
                        debts_fld.getText()
                ));
        FinancialDetails.add(
                new Pair<>(
                        "Total sum requested: ",
                        String.valueOf(credit.getCredit_total_sum())
                ));
        FinancialDetails.add(
                new Pair<>(
                        "Currency: ",
                        String.valueOf(credit.getCredit_currency())
                ));
        FinancialDetails.add(
                new Pair<>(
                        "Period: ",
                        String.valueOf(credit.getCredit_period())
                ));
        return FinancialDetails;
    }


}
