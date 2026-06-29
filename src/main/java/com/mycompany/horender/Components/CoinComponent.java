package com.mycompany.horender.Components;

import com.almasb.fxgl.entity.component.Component;

public class CoinComponent extends Component {
    private final int value;

    public CoinComponent(int value) {
        this.value = value;
    }

    public int getValue() { return value; }
}