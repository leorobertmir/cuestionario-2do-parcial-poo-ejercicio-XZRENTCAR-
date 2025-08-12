module com.rentas.xzrentcar {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;

    opens com.rentas.xzrentcar to javafx.fxml, java.sql;

    exports com.rentas.xzrentcar;
}