module com.example.mutify_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires jlayer;
    requires javafx.media;

    opens com.example.mutify_javafx to javafx.fxml;
    exports com.example.mutify_javafx;
    exports com.music.page.Mutify.functions;
    opens com.music.page.Mutify.functions to javafx.fxml;

}