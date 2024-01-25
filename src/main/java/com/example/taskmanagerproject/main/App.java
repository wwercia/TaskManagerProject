package com.example.taskmanagerproject.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    private View view;

    @Override
    public void start(Stage stage) {
        view = new View();
        HBox root = view.initView();
        // v - width, v1 - height
        //System.out.println(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        Scene scene = new Scene(root, 625, 525);
        //scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Planning app");
        stage.setScene(scene);
        stage.show();
        //scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        scene.getStylesheets().add("file:/C:/Users/wwerc/IdeaProjects/TaskManagerProject/src/main/resources/com/example/taskmanagerproject/main/styles.css");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
