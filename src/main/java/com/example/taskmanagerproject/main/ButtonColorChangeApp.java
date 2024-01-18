package com.example.taskmanagerproject.main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ButtonColorChangeApp extends Application {

    private Button previousClickedButton = null;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Button Color Change");

        Button button1 = createButton("Button 1");
        Button button2 = createButton("Button 2");
        Button button3 = createButton("Button 3");
        Button button4 = createButton("Button 4");

        VBox root = new VBox(10, button1, button2, button3, button4);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 200);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("button");

        button.setOnAction(event -> changeButtonColor(button));

        return button;
    }

    private void changeButtonColor(Button button) {
        if (previousClickedButton != null) {
            // Reset the color of the previous button
            previousClickedButton.getStyleClass().remove("clicked-button");
            previousClickedButton.getStyleClass().add("unclicked-button");
        }

        // Change the color of the clicked button
        button.getStyleClass().remove("unclicked-button");
        button.getStyleClass().add("clicked-button");

        // Update the previously clicked button
        previousClickedButton = button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}