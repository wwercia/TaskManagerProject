package com.example.taskmanagerproject.main.viewClasses;

import com.example.taskmanagerproject.main.App;
import com.example.taskmanagerproject.main.daoClasses.Book;
import com.example.taskmanagerproject.main.daoClasses.BookDao;
import com.example.taskmanagerproject.main.daoClasses.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BooksSection {

    public final HBox getBoxForBooksSection() {
        return boxForBooksSection;
    }

    private final HBox boxForBooksSection = new HBox(47);

    private final VBox booksForReadBooksPanes = new VBox();
    private final Label wordBooksRead = new Label("Books read");
    private final VBox boxForReadBooks = new VBox();
    private final VBox boxForBooksToReadPanes = new VBox();
    private final Label wordBooksToRead = new Label("Books to read");
    private final VBox boxForBooksToRead = new VBox();
    private final VBox boxForButtons = new VBox(10);
    private final Button addBookButton = new Button("add book");
    private final Button deleteBookButton = new Button("delete book");
    private final Button editBookButton = new Button("edit book");


    private HashMap<Label, HBox> readBooks = new HashMap<>();

    private final ArrayList<Book> booksRead = new ArrayList<>();
    private final ArrayList<Book> booksToRead = new ArrayList<>();

    private final BookDao bookDao = new BookDao();


    public void initExpensesSection() {
        Font.loadFont(getClass().getResourceAsStream("Pacifico-Regular.ttf"), 10);
        wordBooksRead.getStyleClass().add("book_section_main_words");
        wordBooksRead.setStyle("-fx-font-family: 'Pacifico';");
        wordBooksToRead.getStyleClass().add("book_section_main_words");
        wordBooksToRead.setStyle("-fx-font-family: 'Pacifico';");

        ArrayList<Book> allBooks = bookDao.getAllBooks();

        for (Book book : allBooks) {
            if (book.isRead()) {
                booksRead.add(book);
            } else {
                booksToRead.add(book);
            }
        }

        for (Book book : booksRead) {
            Label labelForBookName = new Label(book.getName());
            Label labelForBookOpinion = new Label(book.getOpinion() + "/5");
            addStyle(labelForBookName);
            addStyle(labelForBookOpinion);
            HBox boxforBook = new HBox(20);
            boxforBook.getChildren().addAll(labelForBookName, labelForBookOpinion);
            boxForReadBooks.getChildren().add(boxforBook);
            readBooks.put(labelForBookName, boxforBook);
        }

        for (Book book : booksToRead) {
            Button button = new Button();
            button.getStyleClass().add("book_section_button");
            button.setMinSize(15, 15);
            button.setMaxSize(15, 15);
            button.setPrefSize(15, 15);
            Label labelForBookName = new Label(book.getName());
            addStyle(labelForBookName);
            HBox boxforBook = new HBox(5);
            boxforBook.setAlignment(Pos.CENTER_LEFT);
            boxforBook.getChildren().addAll(button, labelForBookName);
            button.setOnAction(event -> {
                //przenosi książke do przeczytanych książek

                String opinion = displayWindowToGetOpinion(book);
                if(opinion == null || opinion.length() == 0){
                    return;
                }
                book.setOpinion(opinion);
                bookDao.updateOpinion(book);
                book.setRead(true);
                bookDao.updateIsReadField(book);

                boxforBook.getChildren().removeAll(button,labelForBookName);
                Label labelForBookNamee = new Label(book.getName());
                Label labelForBookOpinionn = new Label(book.getOpinion() + "/5");
                addStyle(labelForBookNamee);
                addStyle(labelForBookOpinionn);
                HBox boxforBookk = new HBox(20);
                boxforBookk.getChildren().addAll(labelForBookNamee, labelForBookOpinionn);
                boxForReadBooks.getChildren().add(boxforBookk);
                readBooks.put(labelForBookNamee, boxforBookk);

            });
            boxForBooksToRead.getChildren().add(boxforBook);
        }


        boxForButtons.setAlignment(Pos.BASELINE_RIGHT);

        addBookButton.getStyleClass().add("book_section_manage_buttons");
        deleteBookButton.getStyleClass().add("book_section_manage_buttons");
        editBookButton.getStyleClass().add("book_section_manage_buttons");

        booksForReadBooksPanes.getChildren().addAll(wordBooksRead, boxForReadBooks);
        boxForBooksToReadPanes.getChildren().addAll(wordBooksToRead, boxForBooksToRead);
        boxForButtons.getChildren().addAll(addBookButton, deleteBookButton, editBookButton);
        boxForBooksSection.getChildren().addAll(booksForReadBooksPanes, boxForBooksToReadPanes, boxForButtons);
    }

    private void addStyle(Label label) {
        label.getStyleClass().add("book_section_books");
        label.setStyle("-fx-font-family: 'Pacifico';");
    }

    private String opinionnn;
    private String displayWindowToGetOpinion(Book book){

        opinionnn = null;

        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Getting opinion");

        VBox boxForBoxes = new VBox(15);

        boxForBoxes.getStyleClass().add("modern-container");

        HBox boxForWords = new HBox(80);
        boxForWords.setAlignment(Pos.CENTER);
        Label enterOpinionWord = new Label("Enter your opinion of " + book.getName());
        boxForWords.getChildren().add(enterOpinionWord);

        addStyle(enterOpinionWord);

        HBox boxForTextFields = new HBox(5);
        boxForTextFields.setAlignment(Pos.CENTER);
        TextField opinion = new TextField();
        opinion.setPrefHeight(50);
        opinion.setPrefWidth(60);
        boxForTextFields.getChildren().add(opinion);

        VBox boxForButton = new VBox();
        boxForButton.setAlignment(Pos.CENTER);
        Button confirmbutton = new Button("confirm");
        confirmbutton.getStyleClass().add("grey-button");

        confirmbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String taskk = opinion.getText();
                opinionnn = taskk;
                optionsStage.close();
            }
        });

        System.out.println("this has to be displayed");

        boxForButton.getChildren().add(confirmbutton);

        boxForBoxes.getChildren().addAll(boxForWords, boxForTextFields, boxForButton);

        Scene optionsScene = new Scene(boxForBoxes, 260, 175);

        optionsScene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());

        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();
        return opinionnn;

    }

}
