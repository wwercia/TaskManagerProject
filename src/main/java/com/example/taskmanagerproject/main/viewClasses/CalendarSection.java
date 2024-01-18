package com.example.taskmanagerproject.main.viewClasses;

import com.example.taskmanagerproject.main.App;
import com.example.taskmanagerproject.main.daoClasses.Day;
import com.example.taskmanagerproject.main.daoClasses.DayDao;
import com.example.taskmanagerproject.main.daoClasses.Event;
import com.example.taskmanagerproject.main.daoClasses.EventDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.util.*;

public class CalendarSection {

    public VBox getBoxForCalendarSection() {
        return boxForCalendarSection;
    }

    private final VBox boxForCalendarSection = new VBox(25);
    private final HBox boxForMonthNameAndForAddEventButton = new HBox(100);
    private final Label monthName = new Label();
    private final Button buttonAddNewEvent = new Button("Add event");
    private final Button buttonDeleteEvent = new Button("Delete event");
    private final VBox boxForCalendar = new VBox(5);
    private final HBox firstWeek = new HBox(10);
    private final HBox secondWeek = new HBox(10);
    private final HBox thirdWeek = new HBox(10);
    private final HBox fourthWeek = new HBox(10);
    private final HBox fifthWeek = new HBox(10);
    private final VBox boxForEventDescription = new VBox(0);
    private final ScrollPane boxForEventDescriptionn = new ScrollPane(boxForEventDescription);
    private final Label eventsText = new Label("Events:");

    private String currentMonth = "January";
    private final DayDao dayDao = new DayDao();
    private final EventDao eventDao = new EventDao();

    private final HashMap<Day, Button> days = new HashMap<>();

    public void initCalendarSection() {
        Font.loadFont(getClass().getResourceAsStream("Pacifico-Regular.ttf"), 10);

        monthName.setText("January");
        monthName.getStyleClass().add("good-font-text");
        monthName.setStyle("-fx-font-family: 'Pacifico';");

        boxForEventDescriptionn.getStyleClass().add("container-for-displaying-things");
        boxForEventDescription .getStyleClass().add("container-for-displaying-things");
        boxForEventDescriptionn.setFitToWidth(true);
        boxForEventDescriptionn.setFitToHeight(true);

        buttonAddNewEvent.getStyleClass().add("add-new-event-button");
        buttonDeleteEvent.getStyleClass().add("add-new-event-button");

        buttonDeleteEvent.setOnAction(event -> deleteEvent());
        buttonAddNewEvent.setOnAction(event -> addEvent());

        boxForMonthNameAndForAddEventButton.setPrefHeight(75);

        VBox boxForMonthName = new VBox();
        boxForMonthName.setAlignment(Pos.CENTER_RIGHT);
        boxForMonthName.setPrefWidth(200);
        boxForMonthName.getChildren().add(monthName);

        VBox boxForEventButtons = new VBox(5);
        boxForEventButtons.setPrefWidth(150);
        boxForEventButtons.setAlignment(Pos.CENTER_RIGHT);
        boxForEventButtons.getChildren().addAll(buttonDeleteEvent, buttonAddNewEvent);

        boxForMonthNameAndForAddEventButton.getChildren().addAll(boxForMonthName, boxForEventButtons);

        HBox boxForNamesOfDay = new HBox(10);
        boxForNamesOfDay.setAlignment(Pos.TOP_CENTER);
        ArrayList<Label> dayNames = new ArrayList<>();
        dayNames.add(new Label("M"));
        dayNames.add(new Label("T"));
        dayNames.add(new Label("W"));
        dayNames.add(new Label("T"));
        dayNames.add(new Label("F"));
        dayNames.add(new Label("S"));
        dayNames.add(new Label("S"));

        for (Label label : dayNames) {
            label.getStyleClass().add("days-of-week");
            label.setStyle("-fx-font-family: 'Pacifico';");
            label.setAlignment(Pos.CENTER);
            label.setPrefWidth(60);
            boxForNamesOfDay.getChildren().add(label);
        }

        boxForCalendar.getChildren().add(boxForNamesOfDay);

        addWeek(1);
        addWeek(2);
        addWeek(3);
        addWeek(4);
        addWeek(5);

        firstWeek.getStyleClass().add("calendar");
        secondWeek.getStyleClass().add("calendar");
        thirdWeek.getStyleClass().add("calendar");
        fourthWeek.getStyleClass().add("calendar");
        fifthWeek.getStyleClass().add("calendar");

        boxForCalendar.setAlignment(Pos.CENTER);
        boxForCalendar.getChildren().addAll(firstWeek, secondWeek, thirdWeek, fourthWeek, fifthWeek);

        eventsText.getStyleClass().add("event-text");
        eventsText.setStyle("-fx-font-family: 'Pacifico';");
        Calendar calendar = Calendar.getInstance();
        eventsText.setText("Events on " + calendar.get(Calendar.DAY_OF_MONTH));

        //boxForCalendarSection.getChildren().addAll(boxForMonthNameAndForAddEventButton, boxForCalendar, boxForEventDescription);
        boxForCalendarSection.getChildren().addAll(boxForMonthNameAndForAddEventButton, boxForCalendar, boxForEventDescriptionn);
    }

    private ArrayList<Button> createGhostButtons(int numberOfGhostButtons) {
        ArrayList<Button> ghostButtons = new ArrayList<>();
        for (int i = 0; i < numberOfGhostButtons; i++) {
            Button ghostbutton = new Button("1");
            ghostbutton.setPrefWidth(60);
            ghostbutton.setVisible(false);
            ghostButtons.add(ghostbutton);
        }
        return ghostButtons;
    }

    private void addWeek(int numberOfWeek) {

        if (numberOfWeek == 1) {
            addFirstWeek();
            return;
        }

        ArrayList<Day> weekDays = new ArrayList<>();
        HBox box = null;
        if (numberOfWeek == 2) {
            weekDays = dayDao.getSecondWeek(currentMonth.toLowerCase().trim());
            box = secondWeek;
        } else if (numberOfWeek == 3) {
            weekDays = dayDao.getThirdWeek(currentMonth.toLowerCase().trim());
            box = thirdWeek;
        } else if (numberOfWeek == 4) {
            weekDays = dayDao.getFourthWeek(currentMonth.toLowerCase().trim());
            box = fourthWeek;
        } else if (numberOfWeek == 5) {
            weekDays = dayDao.getFifthWeek(currentMonth.toLowerCase().trim());
            box = fifthWeek;
        }

        ArrayList<Button> weekButtons = new ArrayList<>();
        for (Day day : weekDays) {
            Button buttonToAdd = new Button(day.getNumber() + "");
            buttonToAdd.setPrefWidth(60);

            ArrayList<Event> events = eventDao.getEventsInSpecifiedDay(day.getNumber(), day.getMonth());
            displaySpecifiedEventsWhenButtonClicked(day, buttonToAdd);

            if (events.size() != 0) {
                buttonToAdd.setText("");
                changeLookToButtonWithEvent(buttonToAdd, day.getNumber());
            }


            weekButtons.add(buttonToAdd);
            days.put(day, buttonToAdd);

            Calendar calendar = Calendar.getInstance();
            if (day.getNumber() == calendar.get(Calendar.DAY_OF_MONTH)) {
                buttonToAdd.getStyleClass().add("today-button");
                buttonToAdd.fire();
            }
        }

        if (box != null)
            box.getChildren().addAll(weekButtons);
    }

    private void addFirstWeek() {
        ArrayList<Day> firstWeekDays = dayDao.getFirstWeek(currentMonth.toLowerCase().trim());
        ArrayList<Button> firstWeekButtons = new ArrayList<>();

        for (Day day : firstWeekDays) {
            Button buttonToAdd = new Button(day.getNumber() + "");
            buttonToAdd.setPrefWidth(60);

            ArrayList<Event> events = eventDao.getEventsInSpecifiedDay(day.getNumber(), day.getMonth());
            displaySpecifiedEventsWhenButtonClicked(day, buttonToAdd);

            if (events.size() != 0) {
                buttonToAdd.setText("");
                changeLookToButtonWithEvent(buttonToAdd, day.getNumber());
            }


            firstWeekButtons.add(buttonToAdd);
            days.put(day, buttonToAdd);

            Calendar calendar = Calendar.getInstance();
            if (day.getNumber() == calendar.get(Calendar.DAY_OF_MONTH)) {
                buttonToAdd.getStyleClass().add("today-button");
                buttonToAdd.fire();
            }
        }

        // uzupełnia luke w pierwszym tygodniu aby było ładnie
        int numberOfGhostButtons = 0;
        if (firstWeekButtons.size() == 6) {
            numberOfGhostButtons = 1;
        } else if (firstWeekButtons.size() == 5) {
            numberOfGhostButtons = 2;
        } else if (firstWeekButtons.size() == 4) {
            numberOfGhostButtons = 3;
        } else if (firstWeekButtons.size() == 3) {
            numberOfGhostButtons = 4;
        } else if (firstWeekButtons.size() == 2) {
            numberOfGhostButtons = 5;
        } else if (firstWeekButtons.size() == 1) {
            numberOfGhostButtons = 6;
        }
        firstWeek.getChildren().addAll(createGhostButtons(numberOfGhostButtons));
        firstWeek.getChildren().addAll(firstWeekButtons);
    }

    private Button currentlyClickedButton;

    private void displaySpecifiedEventsWhenButtonClicked(Day day, Button button) {
        button.setOnAction(eventt -> {

            currentlyClickedButton = button;

            ArrayList<Event> events = eventDao.getEventsInSpecifiedDay(day.getNumber(), day.getMonth());

            if (events.size() != 0) {
                button.setText("");
                changeLookToButtonWithEvent(button, day.getNumber());
            }

            //clear previous events before displaying new events
            boxForEventDescription.getChildren().clear();

            if (events.size() == 0) {
                Label labelToAdd = new Label();
                labelToAdd.setText("No events available");
                labelToAdd.getStyleClass().add("events");
                labelToAdd.setStyle("-fx-font-family: 'Pacifico';");
                eventsText.setText("Events on " + day.getNumber());
                boxForEventDescription.getChildren().addAll(eventsText, labelToAdd);
            } else {
                Label label = new Label();
                label.setText("Events on " + day.getNumber());
                label.getStyleClass().add("event-text");
                label.setStyle("-fx-font-family: 'Pacifico';");
                boxForEventDescription.getChildren().add(label);
            }


            ArrayList<Label> labelsToAdd = new ArrayList<>();
            for (Event event : events) {
                Label labelToAdd = new Label();
                String text = String.format("- %s  start: %s  end: %s", event.getEvent(), event.getStartTime(), event.getEndTime());
                labelToAdd.setText(text);
                labelToAdd.getStyleClass().add("events");
                labelToAdd.setStyle("-fx-font-family: 'Pacifico';");
                labelsToAdd.add(labelToAdd);
            }

            boxForEventDescription.getChildren().addAll(labelsToAdd);
        });
    }

    private void changeLookToButtonWithEvent(Button button, int day) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
            button.getStyleClass().add("today-button-with-event");
            button.setText(day + "");
        } else {
            button.getStyleClass().add("button-with-event");
            button.setText(day + "");
        }
    }

    private void changeLookToButtonWithoutEvent(Button button, int day) {

        Calendar calendar = Calendar.getInstance();

        if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
            button.getStyleClass().add("today-button-without-event");
            button.setText(day + "");

        } else {
            button.getStyleClass().add("button-without-event");
            button.setText(day + "");
        }
    }

    private Event eventToDelete;

    private void deleteEvent() {
        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Deleting event");

        VBox boxForBoxes = new VBox(8);

        boxForBoxes.getStyleClass().add("modern-container");

        VBox boxForEnterDataLabel = new VBox();
        boxForEnterDataLabel.setAlignment(Pos.CENTER);
        Label label = new Label("Enter data:");
        addStyle(label);
        boxForEnterDataLabel.getChildren().add(label);

        HBox boxForWords = new HBox(120);
        boxForWords.setAlignment(Pos.CENTER);
        Label dayWord = new Label("day");
        Label startTimeWord = new Label("start");
        Label endTimeWord = new Label("end");
        boxForWords.getChildren().addAll(dayWord, startTimeWord, endTimeWord);

        addStyle(dayWord);
        addStyle(startTimeWord);
        addStyle(endTimeWord);

        HBox boxForTextFields = new HBox(15);
        boxForTextFields.setAlignment(Pos.CENTER);
        TextField dayField = new TextField();
        TextField startTimeField = new TextField();
        TextField endTimeField = new TextField();
        boxForTextFields.getChildren().addAll(dayField, startTimeField, endTimeField);

        dayField.setPrefHeight(50);
        startTimeField.setPrefHeight(50);
        endTimeField.setPrefHeight(50);

        VBox boxForButton = new VBox();
        boxForButton.setAlignment(Pos.CENTER);
        Button deleteEventButton = new Button("Delete event");
        deleteEventButton.getStyleClass().add("grey-button");

        deleteEventButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String dayy = dayField.getText();
                int day = 40;
                if (dayy.length() > 0) {
                    day = Integer.parseInt(dayy);
                }
                String start = startTimeField.getText();
                String end = endTimeField.getText();
                eventToDelete = new Event(start, end, day, currentMonth, null);
                if (!isEverythingFilled(day, start, end)) {
                    optionsStage.close();
                    return;
                }

                eventDao.deleteEvent(eventToDelete);

                Day dayyy = new Day(day, null, currentMonth, null);
                ArrayList<Event> events = eventDao.getEventsInSpecifiedDay(dayyy.getNumber(), dayyy.getMonth());

                Button button = null;
                for (Map.Entry<Day, Button> entry : days.entrySet()) {
                    if (Objects.equals(entry.getKey().getNumber(), dayyy.getNumber())) {
                        button = entry.getValue();
                    }
                }
                System.out.println(dayyy.getNumber());
                if (button != null && events.size() == 0) {
                    button.setText("");
                    changeLookToButtonWithoutEvent(button, dayyy.getNumber());
                }

                if (button != null && button.equals(currentlyClickedButton)) {
                    button.fire();
                }

                optionsStage.close();
            }
        });
        boxForButton.getChildren().add(deleteEventButton);

        boxForBoxes.getChildren().addAll(boxForEnterDataLabel, boxForWords, boxForTextFields, boxForButton);

        Scene optionsScene = new Scene(boxForBoxes, 500, 240);

        optionsScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();
    }

    private Event eventToAdd;

    private void addEvent() {

        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Adding event");

        VBox boxForBoxes = new VBox(8);

        boxForBoxes.getStyleClass().add("modern-container");

        VBox boxForEnterDataLabel = new VBox();
        boxForEnterDataLabel.setAlignment(Pos.CENTER);
        Label label = new Label("Enter data:");
        addStyle(label);
        boxForEnterDataLabel.getChildren().add(label);

        HBox boxForWords = new HBox(80);
        boxForWords.setAlignment(Pos.CENTER);
        Label dayWord = new Label("day");
        Label startTimeWord = new Label("start");
        Label endTimeWord = new Label("end");
        Label eventWord = new Label("event");
        boxForWords.getChildren().addAll(dayWord, startTimeWord, endTimeWord, eventWord);

        addStyle(dayWord);
        addStyle(startTimeWord);
        addStyle(endTimeWord);
        addStyle(eventWord);

        HBox boxForTextFields = new HBox(5);
        TextField dayField = new TextField();
        TextField startTimeField = new TextField();
        TextField endTimeField = new TextField();
        TextField eventField = new TextField();
        boxForTextFields.getChildren().addAll(dayField, startTimeField, endTimeField, eventField);

        dayField.setPrefHeight(50);
        startTimeField.setPrefHeight(50);
        endTimeField.setPrefHeight(50);
        eventField.setPrefHeight(50);

        VBox boxForButton = new VBox();
        boxForButton.setAlignment(Pos.CENTER);
        Button addEventButton = new Button("Add event");
        addEventButton.getStyleClass().add("grey-button");

        addEventButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String dayy = dayField.getText();
                int day = 40;
                if (dayy.length() > 0) {
                    day = Integer.parseInt(dayy);
                }
                String start = startTimeField.getText();
                String end = endTimeField.getText();
                String eventtt = eventField.getText();
                System.out.println("start: " + start);
                System.out.println("end: " + end);
                if (!isEverythingFilled(day, start, end, eventtt)) {
                    optionsStage.close();
                    return;
                }
                eventToAdd = new Event(start, end, day, currentMonth, eventtt);
                eventDao.addEvent(eventToAdd);

                Day dayyy = new Day(day, null, currentMonth, null);
                ArrayList<Event> events = eventDao.getEventsInSpecifiedDay(dayyy.getNumber(), dayyy.getMonth());

                Button button = null;
                for (Map.Entry<Day, Button> entry : days.entrySet()) {
                    if (Objects.equals(entry.getKey().getNumber(), dayyy.getNumber())) {
                        button = entry.getValue();
                    }
                }
                System.out.println(dayyy.getNumber());
                if (button != null && events.size() != 0) {
                    button.setText("");
                    changeLookToButtonWithEvent(button, dayyy.getNumber());
                }

                if (button != null && button.equals(currentlyClickedButton)) {
                    button.fire();
                }

                optionsStage.close();
            }
        });

        boxForButton.getChildren().add(addEventButton);

        boxForBoxes.getChildren().addAll(boxForEnterDataLabel, boxForWords, boxForTextFields, boxForButton);

        Scene optionsScene = new Scene(boxForBoxes, 500, 240);

        optionsScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();
    }

    private boolean isEverythingFilled(int day, String start, String end, String event) {
        return day != 40 && !start.isEmpty() && !end.isEmpty() && !event.isEmpty();
    }

    private boolean isEverythingFilled(int day, String start, String end) {
        return day != 40 && !start.isEmpty() && !end.isEmpty();
    }

    private void addStyle(Label label) {
        label.getStyleClass().add("event-text");
        label.setStyle("-fx-font-family: 'Pacifico';");
    }
}
