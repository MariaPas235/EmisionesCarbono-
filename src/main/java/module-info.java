module org.example {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens org.example to javafx.fxml;
    opens org.example.Model to org.hibernate.orm.core; // Abre el paquete org.example.Model para Hibernate

    exports org.example;
    exports org.example.View;
    opens org.example.View to javafx.fxml;
}
