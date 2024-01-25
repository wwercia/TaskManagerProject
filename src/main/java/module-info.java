module com.example.taskmanagerproject.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.taskmanagerproject.main to javafx.fxml;
    exports com.example.taskmanagerproject.main.mainn;
}
