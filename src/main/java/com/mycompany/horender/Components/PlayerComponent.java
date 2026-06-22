/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.horender.Components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.input.KeyCode;
import View.PlayerView;

import com.almasb.fxgl.entity.component.Component;
/**
 * 
 *
 * @author Priscila
 */
public class PlayerComponent extends Component {


    // NÃO tenha um campo view aqui!

    // Método para acessar a view quando precisar
    private PlayerView getView() {
        return entity.getComponent(PlayerView.class);
    }

    @Override
    public void onUpdate(double tpf) {
        // Exemplo: controlar animação com base no movimento
      //  PlayerView view = getView();
       // if (view != null) {
            // view.playRunAnimation();  // Futuro
        }
    }
