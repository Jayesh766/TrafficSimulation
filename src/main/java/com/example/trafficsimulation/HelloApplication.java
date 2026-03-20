package com.example.trafficsimulation;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {

    private long lastUpdate = 0;

    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();

        // 1. Draw the 4-Way Road (The Square/Cross)
        Rectangle roadH = new Rectangle(0, 150, 800, 100); // Horizontal
        roadH.setFill(Color.web("#333333")); // Dark grey

        Rectangle roadV = new Rectangle(350, 0, 100, 400); // Vertical
        roadV.setFill(Color.web("#333333"));

        root.getChildren().addAll(roadH, roadV);

        // 2. Create Two Synchronized Traffic Lights
        // Horizontal controller (East-West)
        TrafficLights lightH = new TrafficLights(320, 130);

        // Vertical controller (North-South) - Start this one as GREEN
        TrafficLights lightV = new TrafficLights(470, 270);
        lightV.changeColor(); // Flip it so one is Red and one is Green

        root.getChildren().addAll(lightH.getShape(), lightV.getShape());

        // 3. Create Cars in different directions
        car carRight = new car(0, 190, "RIGHT");
        car carDown = new car(390, 0, "DOWN");

        root.getChildren().addAll(carRight.getShape(), carDown.getShape());

        // 4. Create the Scene
        Scene scene = new Scene(root, 800, 400, Color.DARKGREEN);

        // 5. The "Heartbeat" (Synchronized Animation Loop)
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) lastUpdate = now;

                // Change BOTH lights every 4 seconds to swap flow
                if (now - lastUpdate >= 4_000_000_000L) {
                    lightH.changeColor();
                    lightV.changeColor();
                    lastUpdate = now;
                }

                // Move cars based on their specific light group
                carRight.move(lightH);
                carDown.move(lightV);
            }
        };
        timer.start();

        stage.setTitle("Advanced Traffic Simulation - 4 Way Square");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}