package com.example.taskmanagerproject.main.viewClasses;

import com.example.taskmanagerproject.main.daoClasses.Book;
import com.example.taskmanagerproject.main.daoClasses.BookDao;
import com.example.taskmanagerproject.main.daoClasses.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

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
            Label labelForBookOpinion = new Label(book.getOpinion());
            addStyle(labelForBookName);
            addStyle(labelForBookOpinion);
            HBox boxforBook = new HBox(20);
            boxforBook.getChildren().addAll(labelForBookName, labelForBookOpinion);
            boxForReadBooks.getChildren().add(boxforBook);
        }

        for (Book book : booksToRead) {
            Button button = new Button();
            button.getStyleClass().add("book_section_button");
            button.setOnAction(event -> {
                //przenosi książke do przeczytanych książek
            });
            button.setMinSize(15, 15);
            button.setMaxSize(15, 15);
            button.setPrefSize(15, 15);
            Label labelForBookOpinion = new Label(book.getName());
            addStyle(labelForBookOpinion);
            HBox boxforBook = new HBox(5);
            boxforBook.setAlignment(Pos.CENTER_LEFT);
            boxforBook.getChildren().addAll(button, labelForBookOpinion);
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

}
