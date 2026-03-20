package com.example.trafficsimulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class car {
    private ImageView imageView;
    private double speed;
    private String direction;

    public car(double x, double y, String direction) {
        this.direction = direction;

        // Load your image
        Image img = new Image(getClass().getResourceAsStream("/com/example/trafficsimulation/sport-car.png"));
        this.imageView = new ImageView(img);

        this.imageView.setX(x);
        this.imageView.setY(y);
        this.imageView.setFitWidth(50); // Adjust size to look good
        this.imageView.setPreserveRatio(true);
        this.speed = 2.0;

        // Rotate car based on direction
        if (direction.equals("DOWN")) imageView.setRotate(90);
        if (direction.equals("UP")) imageView.setRotate(270);
        if (direction.equals("LEFT")) imageView.setRotate(180);
    }

    public void move(TrafficLights light) {
        double stopLineX_Right = 300;
        double stopLineX_Left = 450;
        double stopLineY_Down = 100;
        double stopLineY_Up = 250;

        boolean shouldStop = false;

        if (light.getState().equals("RED")) {
            if (direction.equals("RIGHT") && imageView.getX() > stopLineX_Right - 50 && imageView.getX() < stopLineX_Right) shouldStop = true;
            if (direction.equals("LEFT") && imageView.getX() < stopLineX_Left + 50 && imageView.getX() > stopLineX_Left) shouldStop = true;
            if (direction.equals("DOWN") && imageView.getY() > stopLineY_Down - 50 && imageView.getY() < stopLineY_Down) shouldStop = true;
            if (direction.equals("UP") && imageView.getY() < stopLineY_Up + 50 && imageView.getY() > stopLineY_Up) shouldStop = true;
        }

        speed = shouldStop ? 0 : 2.0;

        switch (direction) {
            case "RIGHT" -> imageView.setX(imageView.getX() + speed);
            case "LEFT"  -> imageView.setX(imageView.getX() - speed);
            case "DOWN"  -> imageView.setY(imageView.getY() + speed);
            case "UP"    -> imageView.setY(imageView.getY() - speed);
        }

        if (imageView.getX() > 800) imageView.setX(-50);
        if (imageView.getX() < -50) imageView.setX(800);
        if (imageView.getY() > 400) imageView.setY(-50);
        if (imageView.getY() < -50) imageView.setY(400);
    }

    public ImageView getShape() { return imageView; }
}