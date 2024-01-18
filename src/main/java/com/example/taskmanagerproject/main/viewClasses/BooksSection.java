package com.example.taskmanagerproject.main.viewClasses;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class BooksSection {

    public VBox getBoxForBooksSection() {
        return boxForBooksSection;
    }

    private VBox boxForBooksSection = new VBox(25);

    private Label test = new Label();

    public void initExpensesSection(){
        Font.loadFont(getClass().getResourceAsStream("Pacifico-Regular.ttf"), 10);

        test.setText("Books Section");
        test.getStyleClass().add("good-font-text");
        test.setStyle("-fx-font-family: 'Pacifico';");

        boxForBooksSection.getChildren().add(test);
    }

}
