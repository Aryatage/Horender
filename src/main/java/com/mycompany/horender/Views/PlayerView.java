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
    private AnimationChannel animATTACK;
    private boolean isFlipped = false;
    private String currentAnim = "idle";
    private int frameCount = 0; // apenas para debug de onUpdate

    public PlayerView() {
        try {
            Image idleSheet = loadImage("/assets/textures/IDLE.png");
            Image walkSheet = loadImage("/assets/textures/WALK.png");

            animIDLE = new AnimationChannel(idleSheet, 7, 384, 336, Duration.seconds(1.5), 0, 6);
            animWALK = new AnimationChannel(walkSheet, 8, 384, 336, Duration.seconds(0.8), 0, 7);

            // Fallback seguro para ataque
            Image attackSheet;
            try {
                attackSheet = loadImage("/assets/textures/ATTACK.png");
            } catch (RuntimeException e) {
                System.err.println("AVISO: ATTACK.png não encontrado. Usando WALK.png como placeholder.");
                attackSheet = walkSheet;
            }
            // Configuração para 6 frames
            animATTACK = new AnimationChannel(attackSheet, 6, 384, 336, Duration.seconds(1.0), 0, 5);

            // Debug inicial (apenas dimensões, sem getFrames)
            System.out.println("=== DEBUG PlayerView ===");
            System.out.println("IDLE sheet: " + idleSheet.getWidth() + "x" + idleSheet.getHeight());
            System.out.println("WALK sheet: " + walkSheet.getWidth() + "x" + walkSheet.getHeight());
            System.out.println("ATTACK sheet: " + attackSheet.getWidth() + "x" + attackSheet.getHeight());

            texture = new AnimatedTexture(animIDLE);
            texture.setSmooth(false);
            texture.loop();
            System.out.println("Texture inicial hashCode: " + texture.hashCode());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao carregar animações", e);
        }
    }

    private Image loadImage(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) throw new IOException("Arquivo não encontrado: " + path);
            return new Image(is);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar imagem: " + path, e);
        }
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        frameCount++;
        if (frameCount % 60 == 0) {
            System.out.println("onUpdate frame=" + frameCount +
                " texture=" + texture.hashCode() +
                " currentAnim=" + currentAnim +
                " isFlipped=" + isFlipped);
        }
        texture.onUpdate(tpf);
    }

    public void playIdle() {
        if (!currentAnim.equals("idle")) {
            currentAnim = "idle";
            playAnimation(animIDLE, true);
        }
    }

    public void playWalk() {
        if (!currentAnim.equals("walk")) {
            currentAnim = "walk";
            playAnimation(animWALK, true);
        }
    }

    public void playAttack() {
        currentAnim = "attack";
        playAnimation(animATTACK, false);   // <-- true em vez de false
        }

    public void setFlipped(boolean flipped) {
        this.isFlipped = flipped;
        if (texture != null) texture.setScaleX(flipped ? -1.0 : 1.0);
    }

    private void playAnimation(AnimationChannel channel, boolean loop) {
        System.out.println("playAnimation() loop=" + loop + " isFlipped=" + isFlipped);
        if (texture != null) {
            System.out.println("  removendo textura anterior: " + texture.hashCode());
            texture.stop();
            entity.getViewComponent().removeChild(texture);
        }
        texture = new AnimatedTexture(channel);
        System.out.println("  nova textura hashCode: " + texture.hashCode());
        if (loop) {
            texture.loop();
        }
        texture.setScaleX(isFlipped ? -1.0 : 1.0);
        entity.getViewComponent().addChild(texture);
        System.out.println("  textura adicionada, parent: " + texture.getParent());
    }
}