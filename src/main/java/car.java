package com.example.trafficsimulation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class car {
    private Rectangle shape;
    private double speed;

    public car(double x, double y) {
        // Create a blue rectangle (the car)
        this.shape = new Rectangle(x, y, 40, 20); // Width 40, Height 20
        this.shape.setFill(Color.BLUE);
        this.speed = 2.0; // How fast it moves
    }

    public void move() {
        // Move the car to the right
        shape.setX(shape.getX() + speed);

        // Loop back to the start if it goes off screen (800 is screen width)
        if (shape.getX() > 800) {
            shape.setX(-40);
        }
    }

    public Rectangle getShape() {
        return shape;
    }
}