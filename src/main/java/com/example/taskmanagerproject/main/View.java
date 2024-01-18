package com.example.taskmanagerproject.main;

import com.example.taskmanagerproject.main.viewClasses.BooksSection;
import com.example.taskmanagerproject.main.viewClasses.CalendarSection;
import com.example.taskmanagerproject.main.viewClasses.ExpensesSection;
import com.example.taskmanagerproject.main.viewClasses.TasksSection;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.MalformedURLException;

public class View {

    private VBox boxForMenuButtons = new VBox(10);
    private Button buttonForCalendar = new Button("Calendar");
    private Button buttonForTasks = new Button("Tasks");
    private Button buttonForExpenses = new Button("Expenses");
    private Button buttonForBookTracker = new Button("Books");

    private HBox boxForBoxes = new HBox(15);


    private Button previousClickedButton = null;
    private VBox currentlyDisplayingSection = null;
    public HBox initView() throws MalformedURLException {

        CalendarSection calendarSection = new CalendarSection();
        calendarSection.initCalendarSection();
        VBox boxForCalendarSection = calendarSection.getBoxForCalendarSection();
        currentlyDisplayingSection = boxForCalendarSection;

        TasksSection tasksSection = new TasksSection();
        tasksSection.initTasksSection();
        VBox boxForTasksSection = tasksSection.getBoxOnlyForReturning();

        ExpensesSection expensesSection = new ExpensesSection();
        expensesSection.initExpensesSection();
        VBox boxForExpensesSection = expensesSection.getBoxForExpensesSection();

        BooksSection booksSection = new BooksSection();
        booksSection.initExpensesSection();
        VBox boxForBooksSection = booksSection.getBoxForBooksSection();

        Font.loadFont(getClass().getResourceAsStream("Pacifico-Regular.ttf"), 10);

        previousButtonName = "buttonForCalendar";
        previousClickedButton = buttonForCalendar;
        buttonForCalendar.getStyleClass().add("clicked-button");
        buttonForTasks.getStyleClass().add("button");
        buttonForExpenses.getStyleClass().add("button");
        buttonForBookTracker.getStyleClass().add("button");

        buttonForCalendar.setOnAction(event -> {
            changeButtonsColor(buttonForCalendar, "buttonForCalendar");
            if(!currentlyDisplayingSection.equals(boxForCalendarSection)){
                boxForBoxes.getChildren().remove(currentlyDisplayingSection);
                boxForBoxes.getChildren().add(boxForCalendarSection);
                currentlyDisplayingSection = boxForCalendarSection;
            }
        });
        buttonForTasks.setOnAction(event -> {
            changeButtonsColor(buttonForTasks, "buttonForTasks");
            if(!currentlyDisplayingSection.equals(boxForTasksSection)){
                boxForBoxes.getChildren().remove(currentlyDisplayingSection);
                boxForBoxes.getChildren().add(boxForTasksSection);
                currentlyDisplayingSection = boxForTasksSection;
            }
        });
        buttonForExpenses.setOnAction(event -> {
            changeButtonsColor(buttonForExpenses, "buttonForExpenses");
            if(!currentlyDisplayingSection.equals(boxForExpensesSection)){
                boxForBoxes.getChildren().remove(currentlyDisplayingSection);
                boxForBoxes.getChildren().add(boxForExpensesSection);
                currentlyDisplayingSection = boxForExpensesSection;
            }
        });
        buttonForBookTracker.setOnAction(event -> {
            changeButtonsColor(buttonForBookTracker, "buttonForBookTracker");
            if(!currentlyDisplayingSection.equals(boxForBooksSection)){
                boxForBoxes.getChildren().remove(currentlyDisplayingSection);
                boxForBoxes.getChildren().add(boxForBooksSection);
                currentlyDisplayingSection = boxForBooksSection;
            }
        });

        boxForMenuButtons.getStyleClass().add("modern-container");
        boxForBoxes.getStyleClass().add("container-for-displaying-things");

        boxForMenuButtons.setAlignment(Pos.CENTER);
        boxForMenuButtons.getChildren().addAll(buttonForCalendar, buttonForTasks, buttonForExpenses, buttonForBookTracker);
        boxForMenuButtons.setPrefWidth(110);

        boxForBoxes.getChildren().addAll(boxForMenuButtons, boxForCalendarSection);


        return boxForBoxes;
    }

    private String previousButtonName = null;

    private void changeButtonsColor(Button button, String name) {
        if (previousClickedButton != null && previousClickedButton.equals(button)) {
            return;
        } else {
            finalColorChange(button, name);
            if (previousClickedButton != null) {
                finalColorChange(previousClickedButton, previousButtonName);
            }
            previousClickedButton = button;
            previousButtonName = name;
        }
    }


    private void finalColorChange(Button button, String name) {
        if (button.getStyleClass().contains("clicked-button")) {
            System.out.println(name + " now has basic color");
            // If it has "clicked-button" class, remove it and add "unclicked-button"
            button.getStyleClass().remove("clicked-button");
            button.getStyleClass().add("unclicked-button");
        } else if (button.getStyleClass().contains("unclicked-button")) {
            System.out.println(name + " now has new color");
            // If it has "unclicked-button" class, remove it and add "clicked-button"
            button.getStyleClass().remove("unclicked-button");
            button.getStyleClass().add("clicked-button");
        } else {
            // If it has neither class, add "clicked-button"
            System.out.println(name + " now has new color");
            button.getStyleClass().add("clicked-button");
        }
    }

}
