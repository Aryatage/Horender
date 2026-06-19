/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.horender.Components;

import com.almasb.fxgl.entity.component.Component;
/**
 * 
 *
 * @author Priscila
 */
public class PlayerComponent extends Component {

    // O PlayerComponent segura a peça de animação
    private AnimationsComponent animationsComponent;

    public PlayerComponent(AnimationsComponent animationComponent) {
        this.animationsComponent = animationComponent;
    }

    // No futuro, o onUpdate daqui controlará quando trocar de uma animação para outra
    @Override
    public void onUpdate(double tpf) {
        // Lógica de estado futura entrará aqui
    }
}
