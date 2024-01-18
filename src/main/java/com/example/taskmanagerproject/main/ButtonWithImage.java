package com.example.taskmanagerproject.main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ButtonWithImage extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Button with Image");

        Button button = new Button();

        button.getStyleClass().add("button-with-image");

        //button.setPrefWidth(50);
        //button.setPrefHeight(40);

        // Load the image (assuming it has a transparent background)
        Image image = new Image(getClass().getResourceAsStream("doneSymbol.png"));

        // Create an ImageView with the loaded image
        ImageView imageView = new ImageView(image);

        // Set the size of the ImageView
        //imageView.setFitWidth(50);
        //imageView.setFitHeight(40);

        // Set content display to GRAPHIC_ONLY to avoid any extra space around the image
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        // Set the ImageView as the graphic content of the button
        button.setGraphic(imageView);

        StackPane root = new StackPane();
        root.getChildren().add(button);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}