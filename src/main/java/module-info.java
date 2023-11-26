module com.example.intellij_infinite_timer_real {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    requires java.desktop;
//    requires com.gluonhq.charm.glisten.mvc.View;

    opens com.example.intellij_infinite_timer_real to javafx.fxml;
    exports com.example.intellij_infinite_timer_real;
}