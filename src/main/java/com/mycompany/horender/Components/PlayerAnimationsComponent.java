/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.horender.Components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image; // Import correto da Image do JavaFX
import javafx.util.Duration;
import com.almasb.fxgl.dsl.FXGL;
/**
 *
 * @author Priscila
 */

public class PlayerAnimationsComponent extends Component {

   private AnimationChannel animIDLE;

    public PlayerAnimationsComponent() {
        
    try {
        // 1. Obtém o InputStream do recurso
        java.io.InputStream is = getClass().getResourceAsStream("/assets/textures/IDLE.png");
        if (is == null) {
            throw new java.io.IOException("Arquivo não encontrado: /assets/textures/IDLE.png");
        }

        // 2. Cria a Image a partir do InputStream
        javafx.scene.image.Image IDLE = new javafx.scene.image.Image(is);
        System.out.println("Sprite carregado manualmente: " + IDLE.getWidth() + "x" + IDLE.getHeight());

        // 3. Cria o AnimationChannel com as dimensões corretas
        animIDLE = new AnimationChannel(IDLE, 7, 384, 336, Duration.seconds(1.5), 0, 6);

    } catch (Exception e) {
        e.printStackTrace();
        // Fallback: criar uma animação vazia ou lançar exceção
        throw new RuntimeException("Falha ao carregar sprite do jogador", e);
    }
}
        // Carrega 'IDLE.png', que tem 7 frames dispostos horizontalmente, por segundo.
       // animIDLE = new AnimationChannel(IDLE, 7, 384, 336, Duration.seconds(1.5), 0, 6);
        

    public AnimationChannel getAnimIDLE() {
        return animIDLE;
    }
}
