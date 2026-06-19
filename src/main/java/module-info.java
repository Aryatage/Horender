module com.mycompany.horender {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.almasb.fxgl.all;
    opens com.mycompany.horender to com.almasb.fxgl.all;
    exports com.mycompany.horender;
}
