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
import java.util.HashMap;
import java.util.Map;
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
    private final Button deleteActionButton = new Button("delete action");
    private final ExpenseDao expenseDao = new ExpenseDao();

    private final HashMap<Expense, Label> allExpenses = new HashMap<>();

    // 1 - labelWithAction, 2 - labelWithMoneyLeft
    private final HashMap<Label, Label> actionLabelsAndMoneyLeftLabels = new HashMap<>();
    private int moneyLeft = 50;
    private final int moneyAtBeggining = 50;

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
            allExpenses.put(expense, labelForData);
            actionLabelsAndMoneyLeftLabels.put(labelForData, labelForMoneyLeft);
            labelForMoneyLeft.getStyleClass().add("left_money");
            boxForDisplayingExpenses.getChildren().add(0, labelForData);
            boxForDisplayingExpenses.getChildren().add(0, labelForMoneyLeft);
        }

        addActionButton.setOnAction(action -> addAction());
        editActionButton.setOnAction(action -> editAction());
        deleteActionButton.setOnAction(action -> deleteAction());


        boxForWordAndDisplaying.setPrefWidth(365);
        boxForDisplayingExpenses.setPrefHeight(500);

        boxForManageButtons.setAlignment(Pos.BASELINE_RIGHT);
        addActionButton.getStyleClass().add("manage_tasks_button");
        editActionButton.getStyleClass().add("manage_tasks_button");
        deleteActionButton.getStyleClass().add("manage_tasks_button");

        boxForManageButtons.getChildren().addAll(addActionButton, editActionButton, deleteActionButton);
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
            try {
                amount = Integer.parseInt(amountInText);
            } catch (NumberFormatException e) {
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
            allExpenses.put(expense, labelForData);
            actionLabelsAndMoneyLeftLabels.put(labelForData, labelForMoneyLeft);
            labelForMoneyLeft.getStyleClass().add("left_money");
            boxForDisplayingExpenses.getChildren().add(0, labelForData);
            boxForDisplayingExpenses.getChildren().add(0, labelForMoneyLeft);
            optionsStage.close();
        });

        boxForBoxes.setAlignment(Pos.CENTER);
        boxForBoxes.getChildren().addAll(dateWord, boxForDateField, amountWord, boxForAmountField, plusOrMinusWord,
                boxForPlusOrMinusField, descriptionWord, boxForDescriptionField, confirmButton);
        Scene optionsScene = new Scene(boxForBoxes, 380, 588);
        //optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        optionsScene.getStylesheets().add("file:/C:/Users/wwerc/IdeaProjects/TaskManagerProject/src/main/resources/com/example/taskmanagerproject/main/styles.css");
        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();
    }


    private Expense expenseToBeEdited;
    private Label labelToBeEdited;

    private void editAction() {

        // first we have to get Expense we want to edit by date and description
        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Editing action");

        VBox boxForBoxes = new VBox(15);

        boxForBoxes.getStyleClass().add("modern-container");

        HBox boxForWords = new HBox(80);
        boxForWords.setAlignment(Pos.CENTER);
        Label taskWord = new Label("Enter data:");
        taskWord.getStyleClass().add("enter_data");
        boxForWords.getChildren().add(taskWord);

        Label dateWord = new Label("Date:");
        dateWord.getStyleClass().add("enter_data");

        HBox boxForDateField = new HBox(5);
        boxForDateField.setAlignment(Pos.CENTER);
        TextField dateField = new TextField();
        dateField.setPrefHeight(60);
        dateField.setPrefWidth(300);
        boxForDateField.getChildren().add(dateField);

        Label descriptionWord = new Label("Description:");
        descriptionWord.getStyleClass().add("enter_data");

        HBox boxForDescriptionField = new HBox(5);
        boxForDescriptionField.setAlignment(Pos.CENTER);
        TextField descriptionField = new TextField();
        descriptionField.setPrefHeight(60);
        descriptionField.setPrefWidth(300);
        boxForDescriptionField.getChildren().add(descriptionField);

        VBox boxForButton = new VBox();
        boxForButton.setAlignment(Pos.CENTER);
        Button confirmButton = new Button("confirm");
        confirmButton.getStyleClass().add("grey-button");

        confirmButton.setOnAction(actionEvent -> {
            String date = dateField.getText();
            String description = descriptionField.getText();

            if (date == null || date.isEmpty() || description == null || description.isEmpty()) {
                return;
            }

            for (Map.Entry<Expense, Label> entry : allExpenses.entrySet()) {
                if (entry.getKey().getDate().equals(date) && entry.getKey().getDescription().equals(description)) {
                    expenseToBeEdited = entry.getKey();
                    labelToBeEdited = entry.getValue();
                }
            }

            if (expenseToBeEdited == null || labelToBeEdited == null) {
                expenseToBeEdited = null;
                labelToBeEdited = null;
                return;
            }

            optionsStage.close();
        });

        boxForBoxes.setAlignment(Pos.CENTER);
        boxForDateField.setAlignment(Pos.CENTER);
        boxForDescriptionField.setAlignment(Pos.CENTER);
        boxForButton.getChildren().add(confirmButton);
        boxForBoxes.getChildren().addAll(boxForWords, dateWord, boxForDateField, descriptionWord, boxForDescriptionField, boxForButton);
        Scene optionsScene = new Scene(boxForBoxes, 380, 380);
        //optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        optionsScene.getStylesheets().add("file:/C:/Users/wwerc/IdeaProjects/TaskManagerProject/src/main/resources/com/example/taskmanagerproject/main/styles.css");
        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();


        if (expenseToBeEdited == null || labelToBeEdited == null) {
            return;
        }

        Stage optionStage = new Stage();
        optionStage.initModality(Modality.APPLICATION_MODAL);
        optionStage.setTitle("Adding action");

        VBox boxForBoxes2 = new VBox(15);
        boxForBoxes2.getStyleClass().add("modern-container");


        Label dateWord2 = new Label("Date:");
        dateWord2.getStyleClass().add("enter_data");

        HBox boxForDateField2 = new HBox(5);
        boxForDateField2.setAlignment(Pos.CENTER);
        TextField dateField2 = new TextField();
        dateField2.setPrefHeight(60);
        dateField2.setPrefWidth(300);
        dateField2.setText(expenseToBeEdited.getDate());
        boxForDateField2.getChildren().add(dateField2);

        Label amountWord = new Label("Amount:");
        amountWord.getStyleClass().add("enter_data");

        HBox boxForAmountField = new HBox(5);
        boxForAmountField.setAlignment(Pos.CENTER);
        TextField amountField = new TextField();
        amountField.setPrefHeight(60);
        amountField.setPrefWidth(300);
        amountField.setText(expenseToBeEdited.getAmount() + "");
        boxForAmountField.getChildren().add(amountField);

        Label plusOrMinusWord = new Label("+ / -");
        plusOrMinusWord.getStyleClass().add("enter_data");

        HBox boxForPlusOrMinusField = new HBox(5);
        boxForPlusOrMinusField.setAlignment(Pos.CENTER);
        TextField plusOrMinusField = new TextField();
        plusOrMinusField.setPrefHeight(60);
        plusOrMinusField.setPrefWidth(300);
        plusOrMinusField.setText(expenseToBeEdited.getPlusOrMinus());
        boxForPlusOrMinusField.getChildren().add(plusOrMinusField);

        Label descriptionWord2 = new Label("Description:");
        descriptionWord2.getStyleClass().add("enter_data");

        HBox boxForDescriptionField2 = new HBox(5);
        boxForDescriptionField2.setAlignment(Pos.CENTER);
        TextField descriptionField2 = new TextField();
        descriptionField2.setPrefHeight(60);
        descriptionField2.setPrefWidth(300);
        descriptionField2.setText(expenseToBeEdited.getDescription());
        boxForDescriptionField2.getChildren().add(descriptionField2);

        Button confirmButton2 = new Button("confirm");
        confirmButton2.getStyleClass().add("grey-button");

        confirmButton2.setOnAction(actionEvent -> {

            // code for updating data
            String date = dateField2.getText();
            String amountInText = amountField.getText();
            String plusOrMinus = plusOrMinusField.getText();
            String description = descriptionField2.getText();

            if (date == null || date.isEmpty() || amountInText == null || amountInText.isEmpty() || plusOrMinus == null || plusOrMinus.isEmpty() || description == null || description.isEmpty()) {
                optionsStage.close();
                System.out.println("something went wrong");
                return;
            }

            System.out.println("it is okay");

            int amount;
            try {
                amount = Integer.parseInt(amountInText);
            } catch (NumberFormatException e) {
                System.out.println(e);
                return;
            }
            /*

            // potrzebne do ustawienia dobrego moneyLeft
            String oldPlusOrMinus = expenseToBeEdited.getPlusOrMinus();
            int oldAmount = expenseToBeEdited.getAmount();


            System.out.println("money left przed: " + moneyLeft);
            if(oldPlusOrMinus.equals("-")){
                moneyLeft -= oldAmount;
                System.out.println("money left po odjęciu: " + moneyLeft);
            } else if (oldPlusOrMinus.equals("+")) {
                moneyLeft += oldAmount;
                System.out.println("money left po dodaniu: " + moneyLeft);
            }


            expenseToBeEdited.setDate(date);
            expenseToBeEdited.setAmount(amount);
            expenseToBeEdited.setPlusOrMinus(plusOrMinus);
            expenseToBeEdited.setDescription(description);

            //then we have to display window with data and update it
            labelToBeEdited.setText(expenseToBeEdited.getPlusOrMinus() + expenseToBeEdited.getAmount() + "zł, " + expenseToBeEdited.getDescription() + ", " + expenseToBeEdited.getDate());
            expenseDao.updateExpense(expenseToBeEdited);

            Label labelForMoneyLeft = actionLabelsAndMoneyLeftLabels.get(labelToBeEdited);

            if (expenseToBeEdited.getPlusOrMinus().equals("-")) {
                labelToBeEdited.getStyleClass().add("spend_money");
                moneyLeft = moneyLeft - expenseToBeEdited.getAmount();
                labelForMoneyLeft.setText("money left: " + moneyLeft + "zł");
            } else if (expenseToBeEdited.getPlusOrMinus().equals("+")) {
                labelToBeEdited.getStyleClass().add("add_money");
                moneyLeft = moneyLeft + expenseToBeEdited.getAmount();
                labelForMoneyLeft.setText("money left: " + moneyLeft + "zł");
            }

            System.out.println("final money left: " + moneyLeft);
            */

            expenseToBeEdited.setDate(date);
            expenseToBeEdited.setAmount(amount);
            expenseToBeEdited.setPlusOrMinus(plusOrMinus);
            expenseToBeEdited.setDescription(description);
            expenseDao.updateExpense(expenseToBeEdited);

            moneyLeft = moneyAtBeggining;
            boxForDisplayingExpenses.getChildren().clear();

            // after updating just load everything again because it's easier

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
                allExpenses.put(expense, labelForData);
                actionLabelsAndMoneyLeftLabels.put(labelForData, labelForMoneyLeft);
                labelForMoneyLeft.getStyleClass().add("left_money");
                boxForDisplayingExpenses.getChildren().add(0, labelForData);
                boxForDisplayingExpenses.getChildren().add(0, labelForMoneyLeft);
            }


            expenseToBeEdited = null;
            labelToBeEdited = null;
            optionStage.close();
        });

        boxForBoxes2.setAlignment(Pos.CENTER);
        boxForBoxes2.getChildren().addAll(dateWord2, boxForDateField2, amountWord, boxForAmountField, plusOrMinusWord,
                boxForPlusOrMinusField, descriptionWord2, boxForDescriptionField2, confirmButton2);
        Scene optionsScene2 = new Scene(boxForBoxes2, 380, 588);
        //optionsScene2.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        optionsScene2.getStylesheets().add("file:/C:/Users/wwerc/IdeaProjects/TaskManagerProject/src/main/resources/com/example/taskmanagerproject/main/styles.css");
        optionStage.setScene(optionsScene2);
        optionStage.showAndWait();

    }

    private Expense expenseToBeDeleted;
    private void deleteAction() {
        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("deleting action");

        VBox boxForBoxes = new VBox(15);

        boxForBoxes.getStyleClass().add("modern-container");

        HBox boxForWords = new HBox(80);
        boxForWords.setAlignment(Pos.CENTER);
        Label taskWord = new Label("Enter data:");
        taskWord.getStyleClass().add("enter_data");
        boxForWords.getChildren().add(taskWord);

        Label dateWord = new Label("Date:");
        dateWord.getStyleClass().add("enter_data");

        HBox boxForDateField = new HBox(5);
        boxForDateField.setAlignment(Pos.CENTER);
        TextField dateField = new TextField();
        dateField.setPrefHeight(60);
        dateField.setPrefWidth(300);
        boxForDateField.getChildren().add(dateField);

        Label descriptionWord = new Label("Description:");
        descriptionWord.getStyleClass().add("enter_data");

        HBox boxForDescriptionField = new HBox(5);
        boxForDescriptionField.setAlignment(Pos.CENTER);
        TextField descriptionField = new TextField();
        descriptionField.setPrefHeight(60);
        descriptionField.setPrefWidth(300);
        boxForDescriptionField.getChildren().add(descriptionField);

        VBox boxForButton = new VBox();
        boxForButton.setAlignment(Pos.CENTER);
        Button confirmButton = new Button("confirm");
        confirmButton.getStyleClass().add("grey-button");

        confirmButton.setOnAction(actionEvent -> {
            String date = dateField.getText();
            String description = descriptionField.getText();

            if (date == null || date.isEmpty() || description == null || description.isEmpty()) {
                return;
            }

            for (Map.Entry<Expense, Label> entry : allExpenses.entrySet()) {
                if (entry.getKey().getDate().equals(date) && entry.getKey().getDescription().equals(description)) {
                    expenseToBeDeleted = entry.getKey();
                }
            }

            expenseDao.deleteExpense(expenseToBeDeleted);

            // after updating just load everything again because it's easier
            moneyLeft = moneyAtBeggining;
            boxForDisplayingExpenses.getChildren().clear();
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
                allExpenses.put(expense, labelForData);
                actionLabelsAndMoneyLeftLabels.put(labelForData, labelForMoneyLeft);
                labelForMoneyLeft.getStyleClass().add("left_money");
                boxForDisplayingExpenses.getChildren().add(0, labelForData);
                boxForDisplayingExpenses.getChildren().add(0, labelForMoneyLeft);
            }

            expenseToBeDeleted = null;
            optionsStage.close();
        });

        boxForBoxes.setAlignment(Pos.CENTER);
        boxForDateField.setAlignment(Pos.CENTER);
        boxForDescriptionField.setAlignment(Pos.CENTER);
        boxForButton.getChildren().add(confirmButton);
        boxForBoxes.getChildren().addAll(boxForWords, dateWord, boxForDateField, descriptionWord, boxForDescriptionField, boxForButton);
        Scene optionsScene = new Scene(boxForBoxes, 380, 380);
        //optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        optionsScene.getStylesheets().add("file:/C:/Users/wwerc/IdeaProjects/TaskManagerProject/src/main/resources/com/example/taskmanagerproject/main/styles.css");
        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();
    }

}
