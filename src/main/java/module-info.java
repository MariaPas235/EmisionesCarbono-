module org.example {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;
    requires kernel;
    requires layout;

    opens org.example to javafx.fxml;
    opens org.example.Model to javafx.base, org.hibernate.orm.core; // Habilitar acceso para JavaFX y Hibernate

    exports org.example;
    exports org.example.View;
    opens org.example.View to javafx.fxml;
}
