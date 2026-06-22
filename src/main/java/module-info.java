module com.mycompany.horender {
    // FXGL
    requires com.almasb.fxgl.all;
    
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    
    // Abre pacotes para FXML
    opens com.mycompany.horender.Controller to javafx.fxml;
    opens com.mycompany.horender.Factories to javafx.fxml;
    
    // Exporta pacote principal
    exports com.mycompany.horender;
    exports com.mycompany.horender.Components;
    exports com.mycompany.horender.Controller;
    exports com.mycompany.horender.Factories;
    exports com.mycompany.horender.Handlers;
}