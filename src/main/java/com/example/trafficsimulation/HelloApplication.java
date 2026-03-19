package com.example.trafficsimulation;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        Group root = new Group();

        // 1. Draw the Road (A grey rectangle)
        Rectangle road = new Rectangle(0, 150, 800, 100);
        road.setFill(Color.GRAY);
        root.getChildren().add(road);

        // 2. Create our Car object
        car myCar = new car(0, 190);
        root.getChildren().add(myCar.getShape());

        Scene scene = new Scene(root, 800, 400, Color.LIGHTGREEN);

        // 3. The "Heartbeat" (Animation Loop)
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                myCar.move(); // Tell the car to update its position 60 times a second
            }
        };
        timer.start();

        stage.setTitle("Traffic Simulation - Phase 1");
        stage.setScene(scene);
        stage.show();
    }
}