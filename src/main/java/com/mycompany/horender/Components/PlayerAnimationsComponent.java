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
        
     Image IDLE = FXGL.image("IDLE.png");
    System.out.println("Carregando sprite: " + IDLE.getWidth() + "x" + IDLE.getHeight());
    animIDLE = new AnimationChannel(IDLE, 7, 384, 336, Duration.seconds(1.5), 0, 6);
         // No PlayerAnimationsComponent
java.net.URL url = getClass().getResource("/assets/textures/IDLE.png");
System.out.println("URL do recurso: " + url);
        // Carrega 'IDLE.png', que tem 7 frames dispostos horizontalmente, por segundo.
       // animIDLE = new AnimationChannel(IDLE, 7, 384, 336, Duration.seconds(1.5), 0, 6);
        
    }

    public AnimationChannel getAnimIDLE() {
        return animIDLE;
    }
}
