module com.mycompany.horender {
    requires com.almasb.fxgl.all;   // módulo principal do FXGL
    requires javafx.controls;       // necessário para FXML e controles
    requires javafx.fxml;           // carregamento de arquivos FXML
    requires javafx.graphics;       // renderização e cenas

    opens com.mycompany.horender.Controllers to javafx.fxml;
    exports com.mycompany.horender;
}
