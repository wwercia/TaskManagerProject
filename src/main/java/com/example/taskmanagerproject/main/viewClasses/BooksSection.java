package com.example.taskmanagerproject.main.viewClasses;

import com.example.taskmanagerproject.main.App;
import com.example.taskmanagerproject.main.daoClasses.Book;
import com.example.taskmanagerproject.main.daoClasses.BookDao;
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

public class BooksSection {

    public final HBox getBoxForBooksSection() {
        return boxForBooksSection;
    }

    private final HBox boxForBooksSection = new HBox(20);

    private final VBox boxForBooksss = new VBox();

    private final VBox booksForReadBooksPanes = new VBox();
    private final Label wordBooksRead = new Label("Books read");
    private final VBox boxForReadBooks = new VBox();
    private final ScrollPane scrollPaneForReadBooks = new ScrollPane(boxForReadBooks);
    private final VBox boxForBooksToReadPanes = new VBox();
    private final Label wordBooksToRead = new Label("Books to read");
    private final VBox boxForBooksToRead = new VBox();
    private final ScrollPane scrollPaneForBooksToRead = new ScrollPane(boxForBooksToRead);
    private final VBox boxForButtons = new VBox(10);
    private final Button addBookButton = new Button("add book");
    private final Button deleteBookButton = new Button("delete book");
    private final Button editBookButton = new Button("edit book");


    private final HashMap<Label, HBox> readBooks = new HashMap<>();
    private final HashMap<Button, Label> booksToReadMap = new HashMap<>();

    private final ArrayList<Book> booksRead = new ArrayList<>();
    private final ArrayList<Book> booksToRead = new ArrayList<>();

    private final BookDao bookDao = new BookDao();


    public void initBooksSection() {
        Font.loadFont(getClass().getResourceAsStream("Pacifico-Regular.ttf"), 10);
        wordBooksRead.getStyleClass().add("book_section_main_words");
        wordBooksRead.setStyle("-fx-font-family: 'Pacifico';");
        wordBooksToRead.getStyleClass().add("book_section_main_words");
        wordBooksToRead.setStyle("-fx-font-family: 'Pacifico';");

        scrollPaneForReadBooks.getStyleClass().add("container-for-displaying-things");
        boxForReadBooks.getStyleClass().add("container-for-displaying-things");
        boxForReadBooks.setPrefSize(370, 250);
        scrollPaneForReadBooks.setFitToWidth(true);
        scrollPaneForReadBooks.setFitToHeight(true);
        scrollPaneForBooksToRead.getStyleClass().add("container-for-displaying-things");
        boxForBooksToRead.getStyleClass().add("container-for-displaying-things");
        boxForBooksToRead.setPrefSize(370, 250);
        scrollPaneForBooksToRead.setFitToWidth(true);
        scrollPaneForBooksToRead.setFitToHeight(true);

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
            HBox boxforBook = new HBox(8);
            boxforBook.getChildren().addAll(labelForBookName, labelForBookOpinion);
            boxForReadBooks.getChildren().add(0, boxforBook);
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
                if (opinion == null || opinion.length() == 0) {
                    return;
                }
                book.setOpinion(opinion);
                bookDao.updateOpinion(book);
                book.setRead(true);
                bookDao.updateIsReadField(book);
                boxforBook.getChildren().removeAll(button, labelForBookName);
                Label labelForBookNamee = new Label(book.getName());
                Label labelForBookOpinionn = new Label(book.getOpinion() + "/5");
                addStyle(labelForBookNamee);
                addStyle(labelForBookOpinionn);
                HBox boxforBookk = new HBox(8);
                boxforBookk.getChildren().addAll(labelForBookNamee, labelForBookOpinionn);
                boxForReadBooks.getChildren().add(0, boxforBookk);
                readBooks.put(labelForBookNamee, boxforBookk);
                booksRead.add(book);
            });
            boxForBooksToRead.getChildren().add(0, boxforBook);
            //readBooksMap.put(book, readBooks);
            booksToReadMap.put(button, labelForBookName);
        }

        addBookButton.setOnAction(event -> addBook());
        deleteBookButton.setOnAction(event -> deleteBook());
        editBookButton.setOnAction(event -> editBook());

        boxForButtons.setAlignment(Pos.BASELINE_RIGHT);

        addBookButton.getStyleClass().add("book_section_manage_buttons");
        deleteBookButton.getStyleClass().add("book_section_manage_buttons");
        editBookButton.getStyleClass().add("book_section_manage_buttons");

        //booksForReadBooksPanes.getChildren().addAll(wordBooksRead, boxForReadBooks);
        booksForReadBooksPanes.getChildren().addAll(wordBooksRead, scrollPaneForReadBooks);
        //boxForBooksToReadPanes.getChildren().addAll(wordBooksToRead, boxForBooksToRead);
        boxForBooksToReadPanes.getChildren().addAll(wordBooksToRead, scrollPaneForBooksToRead);
        boxForButtons.getChildren().addAll(addBookButton, deleteBookButton, editBookButton);

        boxForBooksss.getChildren().addAll(booksForReadBooksPanes, boxForBooksToReadPanes);

        // boxForBooksSection.getChildren().addAll(booksForReadBooksPanes, boxForBooksToReadPanes, boxForButtons);
        boxForBooksSection.getChildren().addAll(boxForBooksss, boxForButtons);
    }

    private void addStyle(Label label) {
        label.getStyleClass().add("book_section_books");
        //label.setStyle("-fx-font-family: 'Pacifico';");
    }

    private String opinionnn;

    private String displayWindowToGetOpinion(Book book) {

        opinionnn = null;

        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Getting opinion");

        VBox boxForBoxes = new VBox(15);

        boxForBoxes.getStyleClass().add("modern-container");

        HBox boxForWords = new HBox(80);
        boxForWords.setAlignment(Pos.CENTER);

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);

        Label enterOpinionWord = new Label("Enter your opinion of");
        Label nameOfBook = new Label(book.getName());

        box.getChildren().addAll(enterOpinionWord, nameOfBook);
        boxForWords.getChildren().addAll(box);

        enterOpinionWord.getStyleClass().add("book_section_enter_opinion");
        nameOfBook.getStyleClass().add("book_section_enter_opinion");

        HBox boxForTextFields = new HBox(5);
        boxForTextFields.setAlignment(Pos.CENTER);

        HBox boxForOpinion = new HBox(8);
        boxForOpinion.setAlignment(Pos.CENTER);

        TextField opinion = new TextField();
        opinion.setPrefHeight(50);
        opinion.setPrefWidth(60);

        Label label = new Label("/5");
        addStyle(label);

        boxForOpinion.getChildren().addAll(opinion, label);
        boxForTextFields.getChildren().add(boxForOpinion);

        VBox boxForButton = new VBox();
        boxForButton.setAlignment(Pos.CENTER);
        Button confirmbutton = new Button("confirm");
        confirmbutton.getStyleClass().add("grey-button");

        confirmbutton.setOnAction(event -> {
            opinionnn = opinion.getText();
            optionsStage.close();

        });

        System.out.println("this has to be displayed");

        boxForButton.getChildren().add(confirmbutton);

        boxForBoxes.getChildren().addAll(boxForWords, boxForTextFields, boxForButton);

        Scene optionsScene = new Scene(boxForBoxes, 260, 215);

        optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());

        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();
        return opinionnn;

    }

    private Book bookToAdd;

    private void addBook() {
        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Adding book");

        VBox boxForBoxes = new VBox(15);

        boxForBoxes.getStyleClass().add("modern-container");

        HBox boxForWords = new HBox(80);
        boxForWords.setAlignment(Pos.CENTER);
        Label taskWord = new Label("Enter book name");
        boxForWords.getChildren().add(taskWord);

        taskWord.getStyleClass().add("book_section_enter_opinion");
        taskWord.setStyle("-fx-font-family: 'Pacifico';");

        HBox boxForTextFields = new HBox(5);
        boxForTextFields.setAlignment(Pos.CENTER);
        TextField fieldWithBookName = new TextField();
        fieldWithBookName.setPrefHeight(100);
        fieldWithBookName.setPrefWidth(300);
        boxForTextFields.getChildren().add(fieldWithBookName);

        VBox boxForButton = new VBox();
        boxForButton.setAlignment(Pos.CENTER);
        Button addBookButton = new Button("confirm");
        addBookButton.getStyleClass().add("grey-button");

        addBookButton.setOnAction(actionEvent -> {
            String bookName = fieldWithBookName.getText();
            if (bookName == null || bookName.isEmpty()) {
                optionsStage.close();
                return;
            }
            bookToAdd = new Book(bookName, false, null);
            bookDao.addBook(bookToAdd);
            booksToRead.add(bookToAdd);

            Button button = new Button();
            button.getStyleClass().add("book_section_button");
            button.setMinSize(15, 15);
            button.setMaxSize(15, 15);
            button.setPrefSize(15, 15);
            Label labelForBookName = new Label(bookToAdd.getName());
            addStyle(labelForBookName);
            HBox boxforBook = new HBox(5);
            boxforBook.setAlignment(Pos.CENTER_LEFT);
            boxforBook.getChildren().addAll(button, labelForBookName);
            button.setOnAction(event -> {
                //przenosi książke do przeczytanych książek

                String opinion = displayWindowToGetOpinion(bookToAdd);
                if (opinion == null || opinion.length() == 0) {
                    return;
                }
                bookToAdd.setOpinion(opinion);
                bookDao.updateOpinion(bookToAdd);
                bookToAdd.setRead(true);
                bookDao.updateIsReadField(bookToAdd);

                boxforBook.getChildren().removeAll(button, labelForBookName);
                Label labelForBookNamee = new Label(bookToAdd.getName());
                Label labelForBookOpinionn = new Label(bookToAdd.getOpinion() + "/5");
                addStyle(labelForBookNamee);
                addStyle(labelForBookOpinionn);
                HBox boxforBookk = new HBox(8);
                boxforBookk.getChildren().addAll(labelForBookNamee, labelForBookOpinionn);
                boxForReadBooks.getChildren().add(0, boxforBookk);
                readBooks.put(labelForBookNamee, boxforBookk);
                booksRead.add(bookToAdd);
            });
            boxForBooksToRead.getChildren().add(0, boxforBook);
            booksToReadMap.put(button, labelForBookName);
            optionsStage.close();
        });
        boxForButton.getChildren().add(addBookButton);
        boxForBoxes.getChildren().addAll(boxForWords, boxForTextFields, boxForButton);
        Scene optionsScene = new Scene(boxForBoxes, 370, 230);
        optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();
    }

    private void deleteBook() {
        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Deleting book");
        VBox boxForBoxes = new VBox(15);
        boxForBoxes.getStyleClass().add("modern-container");
        HBox boxForWords = new HBox(80);
        boxForWords.setAlignment(Pos.CENTER);
        Label enterBookName = new Label("Enter book name");
        boxForWords.getChildren().add(enterBookName);
        enterBookName.getStyleClass().add("book_section_enter_opinion");
        enterBookName.setStyle("-fx-font-family: 'Pacifico';");

        HBox boxForTextFields = new HBox(5);
        boxForTextFields.setAlignment(Pos.CENTER);
        TextField fieldWithBookName = new TextField();
        fieldWithBookName.setPrefHeight(100);
        fieldWithBookName.setPrefWidth(300);
        boxForTextFields.getChildren().add(fieldWithBookName);

        VBox boxForButton = new VBox();
        boxForButton.setAlignment(Pos.CENTER);
        Button confirmButton = new Button("confirm");
        confirmButton.getStyleClass().add("grey-button");

        confirmButton.setOnAction(actionEvent -> {
            String bookName = fieldWithBookName.getText();
            if (bookName == null || bookName.isEmpty()) {
                optionsStage.close();
                return;
            }
            Book bookToDelete = null;
            for (Book book : booksToRead) {
                if (book.getName().equals(bookName.trim())) {
                    bookToDelete = book;
                }
            }
            if (bookToDelete == null) {
                for (Book book : booksRead) {
                    if (book.getName().equals(bookName.trim())) {
                        bookToDelete = book;
                    }
                }
            }
            if (bookToDelete == null) {
                optionsStage.close();
                return;
            }

            bookDao.deleteBook(bookToDelete);
            booksToRead.remove(bookToDelete);
            booksRead.remove(bookToDelete);

            // usuwa z ekranu jeśli książka jest w przeczytanych książkach
            HBox boxToDelete = null;
            for (Map.Entry<Label, HBox> entry : readBooks.entrySet()) {
                if (entry.getKey().getText().equals(bookToDelete.getName())) {
                    boxToDelete = entry.getValue();
                }
            }
            boolean isDone = boxForReadBooks.getChildren().remove(boxToDelete);
            if (isDone) {
                optionsStage.close();
                return;
            }

            // usuwa z ekranu jeśli książka jest w nieprzeczytanych książkach
            HBox boxToDeletee = null;
            for (Map.Entry<Button, Label> entry : booksToReadMap.entrySet()) {
                if (entry.getValue().getText().equals(bookToDelete.getName())) {
                    boxToDeletee = new HBox(entry.getKey(), entry.getValue());
                }
            }
            boxForBooksToRead.getChildren().remove(boxToDeletee);
            optionsStage.close();
        });
        System.out.println("this has to be displayed");
        boxForButton.getChildren().add(confirmButton);
        boxForBoxes.getChildren().addAll(boxForWords, boxForTextFields, boxForButton);
        Scene optionsScene = new Scene(boxForBoxes, 370, 230);
        optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();
    }

    private Book bookToEdit = null;

    private void editBook() {
        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Editing book");

        VBox boxForBoxes = new VBox(15);

        boxForBoxes.getStyleClass().add("modern-container");

        Label labelForOldName = new Label("Enter old name");
        labelForOldName.getStyleClass().add("book_section_enter_opinion");
        labelForOldName.setStyle("-fx-font-family: 'Pacifico';");

        HBox boxForTextFieldOldName = new HBox();
        TextField fieldForEnterOldName = new TextField();
        fieldForEnterOldName.setPrefHeight(100);
        fieldForEnterOldName.setPrefWidth(300);

        Button confirmButtonForOldName = new Button("confirm");

        Label labelForNewName = new Label("Enter new name");
        labelForNewName.getStyleClass().add("book_section_enter_opinion");
        labelForNewName.setStyle("-fx-font-family: 'Pacifico';");

        HBox boxForTextFieldNewName = new HBox();
        TextField fieldForEnterNewName = new TextField();
        fieldForEnterNewName.setPrefHeight(100);
        fieldForEnterNewName.setPrefWidth(300);

        Button confirmButtonForNewName = new Button("confirm");

        confirmButtonForOldName.setOnAction(event -> {
            String bookName = fieldForEnterOldName.getText();
            if (bookName == null || bookName.isEmpty()) {
                optionsStage.close();
                return;
            }
            System.out.println(bookName);
            ArrayList<Book> books = new ArrayList<>();
            books.addAll(booksRead);
            books.addAll(booksToRead);
            boolean isBookName = false;
            for (Book book : books) {
                if (book.getName().equals(bookName)) {
                    bookToEdit = book;
                    isBookName = true;
                }
            }
            if (!isBookName) {
                fieldForEnterNewName.setText("");
                return;
            }
            fieldForEnterNewName.setText(bookName);
        });

        confirmButtonForNewName.setOnAction(event -> {

            String newBookName = fieldForEnterNewName.getText();
            if (newBookName == null || newBookName.isEmpty()) {
                optionsStage.close();
                return;
            }

            // jakoś musisz zrobić że zmienia sie nazwa w aplikacji na ekranie

            // zmienia nazwe na ekranie jeśli jest w przeczytanych
            Label labelToChange = null;
            for (Map.Entry<Label, HBox> entry : readBooks.entrySet()) {
                if (entry.getKey().getText().equals(bookToEdit.getName())) {
                    labelToChange = entry.getKey();
                }
            }

            System.out.println("czy labelToChange1 jest null? " + (labelToChange == null));
            if (labelToChange != null) {
                bookToEdit.setName(newBookName);
                labelToChange.setText(bookToEdit.getName());
                System.out.println("udało się 1");
                bookDao.updateNameField(bookToEdit);
                optionsStage.close();
                return;
            }

            // usuwa z ekranu jeśli książka jest w nieprzeczytanych książkach
            Label labelTochange2 = null;
            for (Map.Entry<Button, Label> entry : booksToReadMap.entrySet()) {
                if (entry.getValue().getText().equals(bookToEdit.getName())) {
                    labelTochange2 = entry.getValue();
                }
            }

            System.out.println("czy boxToChange2 jest null? " + (labelTochange2 == null));
            if (labelTochange2 != null) {
                bookToEdit.setName(newBookName);
                labelTochange2.setText(bookToEdit.getName());
                System.out.println("udało się 2");
                bookDao.updateNameField(bookToEdit);
                optionsStage.close();
            }


            optionsStage.close();

        });

        boxForTextFieldOldName.setAlignment(Pos.CENTER);
        boxForTextFieldNewName.setAlignment(Pos.CENTER);
        boxForTextFieldOldName.getChildren().add(fieldForEnterOldName);
        boxForTextFieldNewName.getChildren().add(fieldForEnterNewName);

        boxForBoxes.setAlignment(Pos.CENTER);
        boxForBoxes.getChildren().addAll(labelForOldName, boxForTextFieldOldName,
                confirmButtonForOldName, labelForNewName, boxForTextFieldNewName, confirmButtonForNewName);
        Scene optionsScene = new Scene(boxForBoxes, 370, 465);
        optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();

    }

}
