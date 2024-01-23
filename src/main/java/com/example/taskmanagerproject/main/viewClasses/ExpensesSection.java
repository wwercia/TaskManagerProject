package com.example.taskmanagerproject.main.viewClasses;

import com.example.taskmanagerproject.main.App;
import com.example.taskmanagerproject.main.daoClasses.Expense;
import com.example.taskmanagerproject.main.daoClasses.ExpenseDao;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class ExpensesSection {

    public HBox getBoxForExpensesSection() {
        return boxForExpensesSection;
    }

    private final HBox boxForExpensesSection = new HBox(25);
    private final VBox boxForWordAndDisplaying = new VBox(15);
    private final Label expensesWord = new Label();
    private final VBox boxForDisplayingExpenses = new VBox(10);
    private final ScrollPane scrollPaneForBoxForDisplayingExpenses = new ScrollPane(boxForDisplayingExpenses);
    private final VBox boxForManageButtons = new VBox(10);
    private final Button addActionButton = new Button("add action");
    private final Button editActionButton = new Button("edit action");
    private final ExpenseDao expenseDao = new ExpenseDao();
    private int moneyLeft = 50;

    public void initExpensesSection() {
        Font.loadFont(getClass().getResourceAsStream("Pacifico-Regular.ttf"), 10);

        scrollPaneForBoxForDisplayingExpenses.getStyleClass().add("container-for-displaying-things");
        boxForDisplayingExpenses.getStyleClass().add("container-for-displaying-things");
        scrollPaneForBoxForDisplayingExpenses.setFitToWidth(true);
        scrollPaneForBoxForDisplayingExpenses.setFitToHeight(true);

        for (String font : Font.getFontNames()) {
            System.out.println(font);
        }

        expensesWord.setText("Expenses");
        expensesWord.getStyleClass().add("good-font-text");
        expensesWord.setStyle("-fx-font-family: 'Pacifico';");

        Label LabelforStartAmountOfMoney = new Label();
        LabelforStartAmountOfMoney.getStyleClass().add("left_money");
        LabelforStartAmountOfMoney.setText("money left: " + moneyLeft + "zł");
        boxForDisplayingExpenses.getChildren().add(LabelforStartAmountOfMoney);

        ArrayList<Expense> expenses = expenseDao.getEverything();
        for (Expense expense : expenses) {
            Label labelForData = new Label();
            labelForData.getStyleClass().add("expenses");
            labelForData.setText(expense.getPlusOrMinus() + expense.getAmount() + "zł, " + expense.getDescription() + ", " + expense.getDate());
            Label labelForMoneyLeft = new Label();
            if (expense.getPlusOrMinus().equals("-")) {
                labelForData.getStyleClass().add("spend_money");
                moneyLeft = moneyLeft - expense.getAmount();
                labelForMoneyLeft.setText("money left: " + moneyLeft + "zł");
            } else if (expense.getPlusOrMinus().equals("+")) {
                labelForData.getStyleClass().add("add_money");
                moneyLeft = moneyLeft + expense.getAmount();
                labelForMoneyLeft.setText("money left: " + moneyLeft + "zł");
            }
            labelForMoneyLeft.getStyleClass().add("left_money");
            boxForDisplayingExpenses.getChildren().add(0, labelForData);
            boxForDisplayingExpenses.getChildren().add(0, labelForMoneyLeft);
        }

        addActionButton.setOnAction(action -> addAction());
        editActionButton.setOnAction(action -> editAction());


        boxForWordAndDisplaying.setPrefWidth(365);
        boxForDisplayingExpenses.setPrefHeight(500);

        boxForManageButtons.setAlignment(Pos.BASELINE_RIGHT);
        addActionButton.getStyleClass().add("manage_tasks_button");
        editActionButton.getStyleClass().add("manage_tasks_button");

        boxForManageButtons.getChildren().addAll(addActionButton, editActionButton);
        boxForWordAndDisplaying.getChildren().addAll(expensesWord, scrollPaneForBoxForDisplayingExpenses);
        boxForExpensesSection.getChildren().addAll(boxForWordAndDisplaying, boxForManageButtons);
    }

    private void addAction() {
        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Adding action");

        VBox boxForBoxes = new VBox(15);
        boxForBoxes.getStyleClass().add("modern-container");


        Label dateWord = new Label("Date:");
        dateWord.getStyleClass().add("enter_data");

        HBox boxForDateField = new HBox(5);
        boxForDateField.setAlignment(Pos.CENTER);
        TextField dateField = new TextField();
        dateField.setPrefHeight(60);
        dateField.setPrefWidth(300);
        boxForDateField.getChildren().add(dateField);

        Label amountWord = new Label("Amount:");
        amountWord.getStyleClass().add("enter_data");

        HBox boxForAmountField = new HBox(5);
        boxForAmountField.setAlignment(Pos.CENTER);
        TextField amountField = new TextField();
        amountField.setPrefHeight(60);
        amountField.setPrefWidth(300);
        boxForAmountField.getChildren().add(amountField);

        Label plusOrMinusWord = new Label("+ / -");
        plusOrMinusWord.getStyleClass().add("enter_data");

        HBox boxForPlusOrMinusField = new HBox(5);
        boxForPlusOrMinusField.setAlignment(Pos.CENTER);
        TextField plusOrMinusField = new TextField();
        plusOrMinusField.setPrefHeight(60);
        plusOrMinusField.setPrefWidth(300);
        boxForPlusOrMinusField.getChildren().add(plusOrMinusField);

        Label descriptionWord = new Label("Description:");
        descriptionWord.getStyleClass().add("enter_data");

        HBox boxForDescriptionField = new HBox(5);
        boxForDescriptionField.setAlignment(Pos.CENTER);
        TextField descriptionField = new TextField();
        descriptionField.setPrefHeight(60);
        descriptionField.setPrefWidth(300);
        boxForDescriptionField.getChildren().add(descriptionField);

        Button confirmButton = new Button("confirm");
        confirmButton.getStyleClass().add("grey-button");

        confirmButton.setOnAction(actionEvent -> {
            String date = dateField.getText();
            String amountInText = amountField.getText();
            String plusOrMinus = plusOrMinusField.getText();
            String description = descriptionField.getText();

            if (date == null || date.isEmpty() || amountInText == null || amountInText.isEmpty() || plusOrMinus == null || plusOrMinus.isEmpty() || description == null || description.isEmpty()) {
                optionsStage.close();
                return;
            }

            int amount;
            try{
                amount = Integer.parseInt(amountInText);
            }catch (NumberFormatException e){
                System.out.println(e);
                return;
            }

            Expense expense = new Expense(date, amount, plusOrMinus.trim(), description);
            expenseDao.addExpense(expense);

            System.out.println("amount: " + expense.getAmount());
            System.out.println("date: " + expense.getDate());
            System.out.println("plus or minus: " + expense.getPlusOrMinus());
            System.out.println("description: " + expense.getDescription());

            Label labelForData = new Label();
            labelForData.getStyleClass().add("expenses");
            labelForData.setText(expense.getPlusOrMinus() + expense.getAmount() + "zł, " + expense.getDescription() + ", " + expense.getDate());
            Label labelForMoneyLeft = new Label();
            if (expense.getPlusOrMinus().equals("-")) {
                labelForData.getStyleClass().add("spend_money");
                moneyLeft = moneyLeft - expense.getAmount();
                labelForMoneyLeft.setText("money left: " + moneyLeft + "zł");
            } else if (expense.getPlusOrMinus().equals("+")) {
                labelForData.getStyleClass().add("add_money");
                moneyLeft = moneyLeft + expense.getAmount();
                labelForMoneyLeft.setText("money left: " + moneyLeft + "zł");
            }
            labelForMoneyLeft.getStyleClass().add("left_money");
            boxForDisplayingExpenses.getChildren().add(0, labelForData);
            boxForDisplayingExpenses.getChildren().add(0, labelForMoneyLeft);
            optionsStage.close();
        });

        boxForBoxes.setAlignment(Pos.CENTER);
        boxForBoxes.getChildren().addAll(dateWord, boxForDateField, amountWord, boxForAmountField, plusOrMinusWord,
                boxForPlusOrMinusField, descriptionWord, boxForDescriptionField, confirmButton);
        Scene optionsScene = new Scene(boxForBoxes, 380, 588);
        optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();
    }

    private void editAction() {

    }

}
