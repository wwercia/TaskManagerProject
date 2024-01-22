package com.example.taskmanagerproject.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class App extends Application {

    private View view;

    @Override
    public void start(Stage stage) throws Exception {
        view = new View();
        HBox root = view.initView();
        // v - width, v1 - height
        Scene scene = new Scene(root, 625, 525);
        //scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Planning app");
        stage.setScene(scene);
        stage.show();
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
