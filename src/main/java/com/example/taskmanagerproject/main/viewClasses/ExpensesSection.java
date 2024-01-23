package com.example.taskmanagerproject.main.viewClasses;

import com.example.taskmanagerproject.main.daoClasses.Expense;
import com.example.taskmanagerproject.main.daoClasses.ExpenseDao;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

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
    private final Button addExpenseButton = new Button("add expense");
    private final Button editExpenseButton = new Button("edit expense");
    private final Button addMoney = new Button("add money");
    private final ExpenseDao expenseDao = new ExpenseDao();

    public void initExpensesSection() {
        Font.loadFont(getClass().getResourceAsStream("Pacifico-Regular.ttf"), 10);

        scrollPaneForBoxForDisplayingExpenses.getStyleClass().add("container-for-displaying-things");
        boxForDisplayingExpenses.getStyleClass().add("container-for-displaying-things");
        scrollPaneForBoxForDisplayingExpenses.setFitToWidth(true);
        scrollPaneForBoxForDisplayingExpenses.setFitToHeight(true);

        for(String font : Font.getFontNames()){
            System.out.println(font);
        }

        expensesWord.setText("Expenses");
        expensesWord.getStyleClass().add("good-font-text");
        expensesWord.setStyle("-fx-font-family: 'Pacifico';");

        Integer moneyLeft = 50;

        Label LabelforStartAmountOfMoney = new Label();
        LabelforStartAmountOfMoney.getStyleClass().add("left_money");
        LabelforStartAmountOfMoney.setText("money left: " + moneyLeft + "zł");
        boxForDisplayingExpenses.getChildren().add(LabelforStartAmountOfMoney);

        ArrayList<Expense> expenses = expenseDao.getEverything();
        for(Expense expense : expenses){
            Label labelForData = new Label();
            labelForData.getStyleClass().add("expenses");
            labelForData.setText(expense.getPlusOrMinus() + expense.getAmount() + "zł, " + expense.getSpentOrGetFrom() + ", " + expense.getDate());
            Label labelForMoneyLeft = new Label();
            if(expense.getPlusOrMinus().equals("-")){
                labelForData.getStyleClass().add("spend_money");
                moneyLeft = moneyLeft - expense.getAmount();
                labelForMoneyLeft.setText("money left: " + moneyLeft + "zł");
            }else if(expense.getPlusOrMinus().equals("+")){
                labelForData.getStyleClass().add("add_money");
                moneyLeft = moneyLeft + expense.getAmount();
                labelForMoneyLeft.setText("money left: " + moneyLeft + "zł");
            }
            labelForMoneyLeft.getStyleClass().add("left_money");
            boxForDisplayingExpenses.getChildren().add(0,labelForData);
            boxForDisplayingExpenses.getChildren().add(0,labelForMoneyLeft);
        }



        boxForWordAndDisplaying.setPrefWidth(365);

        boxForManageButtons.setAlignment(Pos.BASELINE_RIGHT);
        addExpenseButton.getStyleClass().add("manage_tasks_button");
        editExpenseButton.getStyleClass().add("manage_tasks_button");
        addMoney.getStyleClass().add("manage_tasks_button");

        boxForManageButtons.getChildren().addAll(addExpenseButton, editExpenseButton, addMoney);
        boxForWordAndDisplaying.getChildren().addAll(expensesWord, scrollPaneForBoxForDisplayingExpenses);
        boxForExpensesSection.getChildren().addAll(boxForWordAndDisplaying, boxForManageButtons);
    }

}
