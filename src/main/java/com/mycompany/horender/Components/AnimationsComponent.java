/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.horender.Components;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
/**
 *
 * @author Priscila
 */
public class AnimationsComponent extends Component {

    private AnimatedTexture texture;
    private PlayerAnimationsComponent playerAnims;

    public AnimationsComponent() {
        playerAnims = new PlayerAnimationsComponent();
        // Inicializa a textura animada com a animação padrão (IDLE)
        texture = new AnimatedTexture(playerAnims.getAnimIDLE());
        texture.setSmooth(false);
        texture.loop(); // Define para rodar em loop infinito
    }

    @Override
    public void onAdded() {
        // Adiciona a textura animada diretamente à View da Entidade no FXGL
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        // O FXGL precisa atualizar a textura a cada frame para os frames passarem
        texture.onUpdate(tpf);
    }
}

