package com.mycompany.horender.Views;

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
    private AnimationChannel animWALK;

    public PlayerView() {
        try {
            // Carrega spritesheets
            Image idleSheet = loadImage("/assets/textures/IDLE.png");
            Image walkSheet = loadImage("/assets/textures/WALK.png");

            // Cria canais de animação
            // IDLE: 7 frames, tamanho 384x336, duração 1.5s
            animIDLE = new AnimationChannel(idleSheet, 7, 384, 336, Duration.seconds(1.5), 0, 6);
            // WALK: 8 frames (exemplo), tamanho 384x336, duração 0.8s
            animWALK = new AnimationChannel(walkSheet, 8, 384, 336, Duration.seconds(0.8), 0, 7);

            // Inicializa com idle
            texture = new AnimatedTexture(animIDLE);
            texture.setSmooth(false);
            texture.loop();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao carregar animações do player", e);
        }
    }

    private Image loadImage(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Arquivo não encontrado: " + path);
            }
            return new Image(is);
        } catch (IOException e) {
            // Fallback usando FXGL (se o classloader falhar)
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

    /**
     * Troca para a animação idle.
     */
    public void playIdle() {
        playAnimation(animIDLE);
    }

    /**
     * Troca para a animação walk.
     */
    public void playWalk() {
        playAnimation(animWALK);
    }

    /**
     * Espelha o sprite horizontalmente.
     * @param flipped true = virado para esquerda, false = direita
     */
    public void setFlipped(boolean flipped) {
        if (texture != null) {
            texture.setScaleX(flipped ? -1.0 : 1.0);
        }
    }

    // Método interno para troca de animação
    private void playAnimation(AnimationChannel channel) {
        if (texture != null) {
            texture.stop();
            entity.getViewComponent().removeChild(texture);
        }
        texture = new AnimatedTexture(channel);
        texture.loop();
        entity.getViewComponent().addChild(texture);
    }
}