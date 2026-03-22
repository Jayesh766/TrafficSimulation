package com.example.trafficsimulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class car {
    private ImageView imageView;
    private double currentSpeed;
    private double baseSpeed;
    private String direction;

    // UPDATED: Now accepts imagePath so we can spawn sport-cars or motorcycles
    public car(double x, double y, String direction, String imagePath) {
        this.direction = direction;

        // Load image from the provided path
        Image img = new Image(getClass().getResourceAsStream(imagePath));
        this.imageView = new ImageView(img);

        this.imageView.setX(x);
        this.imageView.setY(y);

        // Adjust size: motorcycles are usually smaller than cars
        if (imagePath.contains("motorcycle")) {
            this.imageView.setFitWidth(35);
            this.baseSpeed = 3.5; // Motorcycles are faster!
        } else {
            this.imageView.setFitWidth(50);
            this.baseSpeed = 2.0; // Standard car speed
        }

        this.imageView.setPreserveRatio(true);
        this.currentSpeed = baseSpeed;

        // Rotate car based on direction
        switch (direction) {
            case "DOWN" -> imageView.setRotate(90);
            case "UP"   -> imageView.setRotate(270);
            case "LEFT" -> imageView.setRotate(180);
            default     -> imageView.setRotate(0); // RIGHT
        }
    }

    public void move(TrafficLights light) {
        // Define stop coordinates for the 4-way intersection
        double stopLineX_Right = 290;
        double stopLineX_Left = 460;
        double stopLineY_Down = 90;
        double stopLineY_Up = 260;

        boolean shouldStop = false;

        // Only check for stopping if the light is RED
        if (light.getState().equals("RED")) {
            if (direction.equals("RIGHT") && imageView.getX() > stopLineX_Right - 60 && imageView.getX() < stopLineX_Right) shouldStop = true;
            if (direction.equals("LEFT") && imageView.getX() < stopLineX_Left + 60 && imageView.getX() > stopLineX_Left) shouldStop = true;
            if (direction.equals("DOWN") && imageView.getY() > stopLineY_Down - 60 && imageView.getY() < stopLineY_Down) shouldStop = true;
            if (direction.equals("UP") && imageView.getY() < stopLineY_Up + 60 && imageView.getY() > stopLineY_Up) shouldStop = true;
        }

        // Apply speed based on light status
        currentSpeed = shouldStop ? 0 : baseSpeed;

        // Execute movement
        switch (direction) {
            case "RIGHT" -> imageView.setX(imageView.getX() + currentSpeed);
            case "LEFT"  -> imageView.setX(imageView.getX() - currentSpeed);
            case "DOWN"  -> imageView.setY(imageView.getY() + currentSpeed);
            case "UP"    -> imageView.setY(imageView.getY() - currentSpeed);
        }

        // Screen Wrap-around (Infinite loop)
        if (imageView.getX() > 850) imageView.setX(-60);
        if (imageView.getX() < -60) imageView.setX(850);
        if (imageView.getY() > 450) imageView.setY(-60);
        if (imageView.getY() < -60) imageView.setY(450);
    }

    public ImageView getShape() { return imageView; }
}