package View;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;

public class PlayerView extends Component {

    private AnimatedTexture texture;
    private AnimationChannel animIDLE;

    public PlayerView() {
        try {
            // Carrega a imagem via ClassLoader (garantido)
            Image IDLE = loadImage("/assets/textures/IDLE.png");
            System.out.println("Sprite carregado: " + IDLE.getWidth() + "x" + IDLE.getHeight());

            // Cria o canal de animação
            animIDLE = new AnimationChannel(IDLE, 7, 384, 336, Duration.seconds(1.5), 0, 6);

            // Inicializa a textura animada
            texture = new AnimatedTexture(animIDLE);
            texture.setSmooth(false);
            texture.loop();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao carregar animação do player", e);
        }
    }

    private Image loadImage(String path) {
        // Tenta carregar via ClassLoader (funciona em JAR e IDE)
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Arquivo não encontrado: " + path);
            }
            return new Image(is);
        } catch (IOException e) {
            // Fallback: tenta com FXGL (caso o classloader falhe)
            try {
                return com.almasb.fxgl.dsl.FXGL.image(path.replace("/assets/", ""));
            } catch (Exception ex) {
                throw new RuntimeException("Falha ao carregar imagem: " + path, e);
            }
        }
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        texture.onUpdate(tpf);
    }

    // Método para trocar animação (futuro)
    public void playAnimation(AnimationChannel channel) {
        texture.stop();
        texture = new AnimatedTexture(channel);
        texture.loop();
        entity.getViewComponent().addChild(texture);
    }
}