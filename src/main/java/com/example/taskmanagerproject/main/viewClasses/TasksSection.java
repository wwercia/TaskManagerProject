package com.example.taskmanagerproject.main.viewClasses;

import com.example.taskmanagerproject.main.App;
import com.example.taskmanagerproject.main.daoClasses.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.*;

public class TasksSection {

    public VBox getBoxOnlyForReturning() {
        return boxOnlyForReturning;
    }

    private final VBox boxOnlyForReturning = new VBox(25);
    private final HBox mainBox = new HBox(120);
    private final VBox boxForButtons = new VBox(10);
    private final Button deleteTaskButton = new Button("delete task");
    private final Button addTaskButton = new Button("add task");
    private final Button markTaskButton = new Button("mark task");
    private final Button editTaskButton = new Button("edit task");
    private final VBox boxForWordAndTasks = new VBox(30);
    private final VBox boxForTasks = new VBox(5);
    private final Label toDoWord = new Label();

    private final HashMap<Task, Button> tasks = new HashMap<>();
    private final ArrayList<Task> listOfTasksInOrder = new ArrayList<>();
    private final ArrayList<Label> labels = new ArrayList<>();
    private final ArrayList<Button> buttons = new ArrayList<>();
    private final ArrayList<HBox> hBoxes = new ArrayList<>();
    private final TaskDao taskDao = new TaskDao();
    private final TodayDao todayDao = new TodayDao();


    public void initTasksSection() {
        Font.loadFont(getClass().getResourceAsStream("Pacifico-Regular.ttf"), 10);

        toDoWord.setText("To do:");
        toDoWord.getStyleClass().add("good-font-text");
        toDoWord.setStyle("-fx-font-family: 'Pacifico';");

        Calendar calendar = Calendar.getInstance();
        int daynumber = calendar.get(Calendar.DAY_OF_MONTH);

        if (daynumber != todayDao.getLastDay().getDay()) {
            for (Task task : taskDao.getEverything()) {
                task.setDone(false);
                taskDao.updateIsDoneField(task);
            }
            todayDao.addNewDay(new Today(calendar.get(Calendar.DAY_OF_MONTH)));
        }

        // tasks
        for (Task task : taskDao.getEverything()) {
            Button button = new Button("");
            if (task.isDone()) {
                button.getStyleClass().add("task_button_done");
            } else {
                button.getStyleClass().add("task_button");
            }
            button.setStyle("-fx-font-family: 'Pacifico';");
            button.setPrefWidth(50);
            button.setPrefHeight(40);
            button.setOnAction(event -> {
                task.setDone(!task.isDone());
                taskDao.updateIsDoneField(task);
                updateButtonColor(button, task.isDone());
            });
            Label labelForTask = new Label(task.getTask());
            changeLabelColor(labelForTask, task.getImportanceLevel());

            HBox boxforTask = new HBox(10);
            boxforTask.getChildren().addAll(button, labelForTask);
            boxForTasks.getChildren().add(boxforTask);
            tasks.put(task, button);
            hBoxes.add(boxforTask);
            buttons.add(button);
            labels.add(labelForTask);
            listOfTasksInOrder.add(task);
        }

        //buttons
        deleteTaskButton.getStyleClass().add("manage_tasks_button");
        addTaskButton.getStyleClass().add("manage_tasks_button");
        markTaskButton.getStyleClass().add("manage_tasks_button");
        editTaskButton.getStyleClass().add("manage_tasks_button");

        deleteTaskButton.setOnAction(event -> deleteTask());

        addTaskButton.setOnAction(event -> addTask());

        markTaskButton.setOnAction(event -> markTask());

        editTaskButton.setOnAction(event -> editTask());


        mainBox.setPrefHeight(450);
        mainBox.setPrefWidth(500);

        boxForButtons.setAlignment(Pos.BASELINE_RIGHT);
        boxForButtons.getChildren().addAll(deleteTaskButton, addTaskButton, editTaskButton, markTaskButton);
        boxForWordAndTasks.getChildren().addAll(toDoWord, boxForTasks);
        boxOnlyForReturning.getChildren().add(mainBox);
        mainBox.getChildren().addAll(boxForWordAndTasks, boxForButtons);

    }

    private void updateButtonColor(Button button, boolean isDone) {
        if (isDone) {
            button.getStyleClass().remove("task_button");
            button.getStyleClass().add("task_button_done");
        } else {
            button.getStyleClass().remove("task_button_done");
            button.getStyleClass().add("task_button");
        }
    }


    // this is not working
    // this is supposed to set graphics when button is clicked
    private void updateButtonColorWithImage(Button button, boolean isDone) {
        if (isDone) {
            Image image = new Image(Objects.requireNonNull(App.class.getResourceAsStream("doneSymbol.png")));
            // Create an ImageView with the loaded image
            ImageView imageView = new ImageView(image);
            // Set the size of the ImageView
            imageView.setFitWidth(40);

            imageView.setFitHeight(32);
            // Set content display to GRAPHIC_ONLY to avoid any extra space around the image
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            // Set the ImageView as the graphic content of the button
            button.setGraphic(imageView);
        } else {
            button.setGraphic(null);
        }
    }
    private Task taskToAdd;

    private void addTask() {

        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Adding task");

        VBox boxForBoxes = new VBox(15);

        boxForBoxes.getStyleClass().add("modern-container");

        HBox boxForWords = new HBox(80);
        boxForWords.setAlignment(Pos.CENTER);
        Label taskWord = new Label("Task:");
        boxForWords.getChildren().add(taskWord);

        addStyle(taskWord);

        HBox boxForTextFields = new HBox(5);
        boxForTextFields.setAlignment(Pos.CENTER);
        TextField taskField = new TextField();
        taskField.setPrefHeight(100);
        taskField.setPrefWidth(300);
        boxForTextFields.getChildren().add(taskField);

        VBox boxForButton = new VBox();
        boxForButton.setAlignment(Pos.CENTER);
        Button addTaskButton = new Button("add task");
        addTaskButton.getStyleClass().add("grey-button");

        addTaskButton.setOnAction(actionEvent -> {
            String task = taskField.getText();

            if (task == null || task.isEmpty()) {
                optionsStage.close();
                return;
            }
            taskToAdd = new Task(task, 2, false);
            taskDao.addTask(taskToAdd);
            listOfTasksInOrder.add(taskToAdd);

            Button buttonForNewTask = new Button("");
            buttonForNewTask.getStyleClass().add("task_button");
            buttonForNewTask.setStyle("-fx-font-family: 'Pacifico';");
            buttonForNewTask.setPrefWidth(50);
            buttonForNewTask.setPrefHeight(40);
            buttonForNewTask.setOnAction(event -> {
                taskToAdd.setDone(!taskToAdd.isDone());
                taskDao.updateIsDoneField(taskToAdd);
                updateButtonColor(buttonForNewTask, taskToAdd.isDone());
            });
            Label labelForNewTask = new Label(taskToAdd.getTask());
            labelForNewTask.getStyleClass().add("events");
            labelForNewTask.setStyle("-fx-font-family: 'Pacifico';");

            //test
            changeLabelColor(labelForNewTask, taskToAdd.getImportanceLevel());

            HBox boxforTask = new HBox(10);
            boxforTask.getChildren().addAll(buttonForNewTask, labelForNewTask);
            boxForTasks.getChildren().add(boxforTask);

            tasks.put(taskToAdd, buttonForNewTask);
            hBoxes.add(boxforTask);
            buttons.add(buttonForNewTask);
            labels.add(labelForNewTask);
            listOfTasksInOrder.add(taskToAdd);
            optionsStage.close();
        });
        boxForButton.getChildren().add(addTaskButton);
        boxForBoxes.getChildren().addAll(boxForWords, boxForTextFields, boxForButton);
        Scene optionsScene = new Scene(boxForBoxes, 400, 240);
        optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();

    }

    private void deleteTask() {

        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Deleting task");

        VBox boxForBoxes = new VBox(15);

        boxForBoxes.getStyleClass().add("modern-container");

        HBox boxForWords = new HBox(80);
        boxForWords.setAlignment(Pos.CENTER);
        Label taskWord = new Label("Enter task number:");
        boxForWords.getChildren().add(taskWord);

        addStyle(taskWord);

        HBox boxForTextFields = new HBox(5);
        boxForTextFields.setAlignment(Pos.CENTER);
        TextField taskField = new TextField();
        taskField.setPrefHeight(50);
        taskField.setPrefWidth(60);
        boxForTextFields.getChildren().add(taskField);

        VBox boxForButton = new VBox();
        boxForButton.setAlignment(Pos.CENTER);
        Button deleteTaskButton = new Button("delete task");
        deleteTaskButton.getStyleClass().add("grey-button");

        deleteTaskButton.setOnAction(actionEvent -> {
            String taskk = taskField.getText();
            int task = 100;
            if (taskk.length() > 0) {
                task = Integer.parseInt(taskk);
            }
            if(task == 100){
                optionsStage.close();
                return;
            }
            Task taskToDelete = null;
            for (int i = 0; i < listOfTasksInOrder.size(); i++) {
                if (i == task - 1) {
                    taskToDelete = listOfTasksInOrder.get(i);
                    break;
                }
            }
            if (taskToDelete == null) {
                return;
            }

            taskDao.deleteTask(taskToDelete.getId());

            Button buttonToRemove = null;
            for (Map.Entry<Task, Button> entry : tasks.entrySet()) {
                if (entry.getKey().equals(taskToDelete)) {
                    buttonToRemove = entry.getValue();
                }
            }

            int index = buttons.indexOf(buttonToRemove);
            HBox box = hBoxes.get(index);

            System.out.println(buttonToRemove);
            System.out.println(box);

            if (buttonToRemove != null && box != null) {
                boxForTasks.getChildren().remove(box);
            }

            optionsStage.close();
        });

        System.out.println("this has to be displayed");

        boxForButton.getChildren().add(deleteTaskButton);

        boxForBoxes.getChildren().addAll(boxForWords, boxForTextFields, boxForButton);

        Scene optionsScene = new Scene(boxForBoxes, 300, 200);

        optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());

        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();
    }

    private Task taskToBeEdited;
    private Label labelOfTaskToBeEdited;
    private void editTask() {

        // I need this to get Task and Label
        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Editing task");

        VBox boxForBoxes = new VBox(15);

        boxForBoxes.getStyleClass().add("modern-container");

        HBox boxForWords = new HBox(80);
        boxForWords.setAlignment(Pos.CENTER);
        Label taskWord = new Label("Enter number of task you want to edit");
        boxForWords.getChildren().add(taskWord);

        addStyle(taskWord);

        HBox boxForTextFields = new HBox(5);
        boxForTextFields.setAlignment(Pos.CENTER);
        TextField taskField = new TextField();
        taskField.setPrefHeight(50);
        taskField.setPrefWidth(60);
        boxForTextFields.getChildren().add(taskField);

        VBox boxForButton = new VBox();
        boxForButton.setAlignment(Pos.CENTER);
        Button addEventButton = new Button("confirm");
        addEventButton.getStyleClass().add("grey-button");

        addEventButton.setOnAction(actionEvent -> {
            String taskk = taskField.getText();
            int task = 100;
            if (taskk.length() > 0) {
                task = Integer.parseInt(taskk);
            }
            if(task == 100){
                optionsStage.close();
                return;
            }
            for (int i = 0; i < listOfTasksInOrder.size(); i++) {
                if (i == task - 1) {
                    taskToBeEdited = listOfTasksInOrder.get(i);
                    break;
                }
            }
            if (taskToBeEdited == null) {
                return;
            }
            Button buttonForGettingIndex = null;
            for (Map.Entry<Task, Button> entry : tasks.entrySet()) {
                if (entry.getKey().equals(taskToBeEdited)) {
                    buttonForGettingIndex = entry.getValue();
                }
            }
            int index = buttons.indexOf(buttonForGettingIndex);
            labelOfTaskToBeEdited = labels.get(index);
            optionsStage.close();
        });

        boxForButton.getChildren().add(addEventButton);

        boxForBoxes.getChildren().addAll(boxForWords, boxForTextFields, boxForButton);

        Scene optionsScene = new Scene(boxForBoxes, 500, 200);

        optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());

        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();


        if(taskToBeEdited == null){
            return;
        }

        // here is code for editing task
        Stage optionsStage2 = new Stage();
        optionsStage2.initModality(Modality.APPLICATION_MODAL);
        optionsStage2.setTitle("Editing task");

        VBox boxForBoxes2 = new VBox(15);

        boxForBoxes2.getStyleClass().add("modern-container");

        HBox boxForWords2 = new HBox(80);
        boxForWords2.setAlignment(Pos.CENTER);
        Label taskWord2 = new Label("Edit task:");
        boxForWords2.getChildren().add(taskWord2);

        addStyle(taskWord2);

        HBox boxForTextFields2 = new HBox(5);
        boxForTextFields2.setAlignment(Pos.CENTER);
        TextField taskField2 = new TextField();
        taskField2.setPrefHeight(75);
        taskField2.setPrefWidth(300);

        taskField2.setText(taskToBeEdited.getTask());

        boxForTextFields2.getChildren().add(taskField2);

        VBox boxForButton2 = new VBox();
        boxForButton2.setAlignment(Pos.CENTER);
        Button addEventButton2 = new Button("confirm");
        addEventButton2.getStyleClass().add("grey-button");

        addEventButton2.setOnAction(actionEvent -> {
            labelOfTaskToBeEdited.setText(taskField2.getText());
            taskToBeEdited.setTask(taskField2.getText());
            boolean isGood = taskDao.updateTask(taskToBeEdited);
            System.out.println(isGood);
            optionsStage2.close();
        });

        boxForButton2.getChildren().add(addEventButton2);

        boxForBoxes2.getChildren().addAll(boxForWords2, boxForTextFields2, boxForButton2);

        Scene optionsScene2 = new Scene(boxForBoxes2, 380, 220);

        optionsScene2.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());

        optionsStage2.setScene(optionsScene2);
        optionsStage2.showAndWait();
    }

    private final Button notImportant = new Button("not important");
    private Button previousClickedButton = notImportant;
    private Task taskToBeMarked = null;
    private int importanceLevel = 1;
    private void markTask() {
        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Marking task");

        VBox boxForBoxes = new VBox(15);

        boxForBoxes.getStyleClass().add("modern-container");

        HBox boxForWords = new HBox(80);
        boxForWords.setAlignment(Pos.CENTER);
        Label taskWord = new Label("Choose level of importance:");
        boxForWords.getChildren().add(taskWord);
        addStyle(taskWord);

        VBox boxForButtons = new VBox(5);
        boxForButtons.setAlignment(Pos.CENTER);

        Button kindOfImportant = new Button("kind of important");
        Button important = new Button("important");
        Button veryImportant = new Button("very important");
        notImportant.setOnAction(event -> {
            importanceLevel = 1;
            changeButtonColor(notImportant);
        });
        kindOfImportant.setOnAction(event -> {
            importanceLevel = 2;
            changeButtonColor(kindOfImportant);
        });
        important.setOnAction(event -> {
            importanceLevel = 3;
            changeButtonColor(important);
        });
        veryImportant.setOnAction(event -> {
            importanceLevel = 4;
            changeButtonColor(veryImportant);
        });
        notImportant.getStyleClass().add("important-buttons-clicked");
        kindOfImportant.getStyleClass().add("important-buttons-not-clicked");
        important.getStyleClass().add("important-buttons-not-clicked");
        veryImportant.getStyleClass().add("important-buttons-not-clicked");
        boxForButtons.getChildren().addAll(notImportant, kindOfImportant, important, veryImportant);

        HBox boxForTaskNumber = new HBox(12);
        boxForTaskNumber.setAlignment(Pos.CENTER);
        Label label = new Label("Task number : ");
        addStyle(label);
        TextField taskNumberField = new TextField();
        taskNumberField.setPrefWidth(50);
        taskNumberField.setPrefHeight(40);
        boxForTaskNumber.getChildren().addAll(label, taskNumberField);

        VBox boxForButton = new VBox();
        boxForButton.setAlignment(Pos.CENTER);
        Button confirmbutton = new Button("confirm");
        confirmbutton.getStyleClass().add("grey-button");

        confirmbutton.setOnAction(actionEvent -> {
            String taskk = taskNumberField.getText();
            int taskNumber = 100;
            if (taskk.length() > 0) {
                taskNumber = Integer.parseInt(taskk);
            }
            if(taskNumber == 100){
                optionsStage.close();
                return;
            }

            for (int i = 0; i < listOfTasksInOrder.size(); i++) {
                if (i == taskNumber - 1) {
                    taskToBeMarked = listOfTasksInOrder.get(i);
                    break;
                }
            }

            Button buttonForGettingIndex = null;
            for (Map.Entry<Task, Button> entry : tasks.entrySet()) {
                if (entry.getKey().equals(taskToBeMarked)) {
                    buttonForGettingIndex = entry.getValue();
                }
            }
            int index = buttons.indexOf(buttonForGettingIndex);
            Label labelToChangeColor = labels.get(index);

            taskToBeMarked.setImportanceLevel(importanceLevel);
            System.out.println(taskDao.updateImportanceLevel(taskToBeMarked));

            System.out.println("index: " + index);
            System.out.println("importance level: " + importanceLevel);
            System.out.println("task level: " + taskToBeMarked.getImportanceLevel());

            changeLabelColor(labelToChangeColor, importanceLevel);

            previousClickedButton = notImportant;
            taskToBeMarked = null;
            importanceLevel = 1;

            optionsStage.close();
        });
        boxForButton.getChildren().add(confirmbutton);
        boxForBoxes.getChildren().addAll(boxForWords, boxForButtons, boxForTaskNumber, boxForButton);
        Scene optionsScene = new Scene(boxForBoxes, 350, 390);
        optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();
    }

    private void changeButtonColor(Button button) {
        if (previousClickedButton != null) {
            // Reset the color of the previous button
            previousClickedButton.getStyleClass().remove("important-buttons-clicked");
            previousClickedButton.getStyleClass().add("important-buttons-not-clicked");
        }

        // Change the color of the clicked button
        button.getStyleClass().remove("important-buttons-not-clicked");
        button.getStyleClass().add("important-buttons-clicked");

        // Update the previously clicked button
        previousClickedButton = button;
    }

    private void changeLabelColor(Label label, int importanceLevel){

        System.out.println(label.getStyleClass().remove("not-important"));
        System.out.println(label.getStyleClass().remove("kind-of-important"));
        System.out.println(label.getStyleClass().remove("important"));
        System.out.println(label.getStyleClass().remove("very-important"));

        if(importanceLevel == 1){
            label.getStyleClass().add("not-important");
        }else if(importanceLevel == 2){
            label.getStyleClass().add("kind-of-important");
        }else if(importanceLevel == 3){
            label.getStyleClass().add("important");
        }else if(importanceLevel == 4){
            label.getStyleClass().add("very-important");
        }
        label.setStyle("-fx-font-family: 'Pacifico';");

    }

    private void addStyle(Label label) {
        label.getStyleClass().add("event-text");
        label.setStyle("-fx-font-family: 'Pacifico';");
    }

}
