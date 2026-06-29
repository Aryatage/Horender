package com.mycompany.horender.Views;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.InputStream;

public class MonsterView extends Component {

    private AnimatedTexture texture;
    private Group visualGroup;         // contêiner que será escalado
    private Node visualNode;          // a textura ou retângulo

    public MonsterView() {
        try {
            Image sheet = loadImage("/assets/textures/MONSTER.png");
            AnimationChannel idleChannel = new AnimationChannel(sheet, 6, 228, 168, Duration.seconds(0.7), 0, 5);
            texture = new AnimatedTexture(idleChannel);
            texture.loop();
            visualNode = texture;
        } catch (Exception e) {
            // Placeholder visual
            Rectangle rect = new Rectangle(64, 64, Color.RED);
            rect.setStroke(Color.DARKRED);
            visualNode = rect;
        }
        visualGroup = new Group(visualNode);
    }

    private Image loadImage(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) throw new RuntimeException("Imagem não encontrada: " + path);
            return new Image(is);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao carregar " + path, e);
        }
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(visualGroup);
    }

    @Override
    public void onUpdate(double tpf) {
        if (texture != null) {
            texture.onUpdate(tpf);
        }
    }

    /**
     * Espelha visualmente o monstro.
     * @param flipped true = olhar para a direita (sprite original olha para esquerda)
     */
    public void setFlipped(boolean flipped) {
        visualGroup.setScaleX(flipped ? -1.0 : 1.0);
    }
}