module com.mycompany.horender {
    // FXGL
    requires com.almasb.fxgl.all;
    
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    
    // Abre pacotes para FXML
    opens com.mycompany.horender.Controllers to javafx.fxml;
    opens com.mycompany.horender.Factories to com.almasb.fxgl.core;
    opens com.mycompany.horender.Views to com.almasb.fxgl.core;
    opens com.mycompany.horender.Components to com.almasb.fxgl.core;
    
    // Exporta pacote principal
    exports com.mycompany.horender;
}
    /*
module com.mycompany.horender {
    // Módulos necessários
    requires com.almasb.fxgl.all;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    // Abre pacotes para injeção do FXGL
    opens com.mycompany.horender.Components to com.almasb.fxgl.core;
    opens com.mycompany.horender.View to com.almasb.fxgl.core;        // se PlayerView também for injetada
    opens com.mycompany.horender.Controllers to javafx.fxml;

    // Exporta o pacote principal (necessário para a aplicação)
    exports com.mycompany.horender;
}*/