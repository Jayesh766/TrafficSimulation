package com.example.trafficsimulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TrafficLights {
    private ImageView lightView;
    private Image redImg;
    private Image greenImg;
    private String state;

    public TrafficLights(double x, double y) {
        redImg = new Image(getClass().getResourceAsStream("/com/example/trafficsimulation/red-light.png"));
        greenImg = new Image(getClass().getResourceAsStream("/com/example/trafficsimulation/traffic-light.png"));

        this.lightView = new ImageView(redImg);
        this.lightView.setX(x);
        this.lightView.setY(y);
        this.lightView.setFitHeight(60); // Adjust height for a vertical signal
        this.lightView.setPreserveRatio(true);
        this.state = "RED";
    }

    public void changeColor() {
        if (state.equals("RED")) {
            state = "GREEN";
            lightView.setImage(greenImg);
        } else {
            state = "RED";
            lightView.setImage(redImg);
        }
    }

    public String getState() { return state; }
    public ImageView getShape() { return lightView; }
}