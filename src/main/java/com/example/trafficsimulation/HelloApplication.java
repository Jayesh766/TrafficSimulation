package com.example.trafficsimulation;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class HelloApplication extends Application {

    private long lastUpdate = 0;
    private long lastSpawn = 0;
    private final Random random = new Random();

    // A list to track multiple vehicles on screen
    private final ArrayList<car> vehicles = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();

        // 1. Draw the Roads
        Rectangle roadH = new Rectangle(0, 150, 800, 100);
        roadH.setFill(Color.web("#222222"));

        Rectangle roadV = new Rectangle(350, 0, 100, 400);
        roadV.setFill(Color.web("#222222"));

        // 2. Add Lane Markings
        Line lineH = new Line(0, 200, 800, 200);
        lineH.setStroke(Color.WHITE);
        lineH.setStrokeWidth(2);
        lineH.getStrokeDashArray().addAll(25.0, 15.0);

        Line lineV = new Line(400, 0, 400, 400);
        lineV.setStroke(Color.WHITE);
        lineV.setStrokeWidth(2);
        lineV.getStrokeDashArray().addAll(25.0, 15.0);

        // 3. Add Zebra Crossings
        Group zebraCrossings = createAllZebraCrossings();
        root.getChildren().addAll(roadH, roadV, lineH, lineV, zebraCrossings);

        // 4. Create Synchronized Traffic Lights
        TrafficLights lightH = new TrafficLights(280, 95);
        TrafficLights lightV = new TrafficLights(475, 265);
        lightV.changeColor(); // Start V as GREEN, H as RED

        root.getChildren().addAll(lightH.getShape(), lightV.getShape());

        // 5. Scene Setup
        Scene scene = new Scene(root, 800, 400, Color.web("#1b4d3e"));

        // 6. The Animation Timer (Logic Controller)
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) lastUpdate = now;

                // LIGHT LOGIC: Toggle lights every 5 seconds
                if (now - lastUpdate >= 5_000_000_000L) {
                    lightH.changeColor();
                    lightV.changeColor();
                    lastUpdate = now;
                }

                // SPAWN LOGIC: Try to spawn a new vehicle every 3 seconds
                if (now - lastSpawn >= 3_000_000_000L) {
                    spawnRandomVehicle(root);
                    lastSpawn = now;
                }

                // MOVEMENT LOGIC: Move all active vehicles
                for (car v : vehicles) {
                    // Vehicles check the light relative to their direction
                    if (v.getShape().getRotate() == 90 || v.getShape().getRotate() == 270) {
                        v.move(lightV); // Vertical cars check lightV
                    } else {
                        v.move(lightH); // Horizontal cars check lightH
                    }
                }
            }
        };
        timer.start();

        stage.setTitle("Advanced Traffic Simulation - Dynamic Flow");
        stage.setScene(scene);
        stage.show();
    }

    // Helper to spawn a car or motorcycle in a random lane
    private void spawnRandomVehicle(Group root) {
        boolean isHorizontal = random.nextBoolean();
        String img = random.nextBoolean() ? "/com/example/trafficsimulation/sport-car.png" : "/com/example/trafficsimulation/motorcycle.png";

        car newVehicle;
        if (isHorizontal) {
            newVehicle = new car(-50, 190, "RIGHT", img);
        } else {
            newVehicle = new car(390, -50, "DOWN", img);
        }

        vehicles.add(newVehicle);
        root.getChildren().add(newVehicle.getShape());
    }

    private Group createAllZebraCrossings() {
        Group crossings = new Group();
        for (int i = 0; i < 5; i++) {
            Rectangle stripeW = new Rectangle(320, 155 + (i * 20), 25, 8);
            Rectangle stripeE = new Rectangle(455, 155 + (i * 20), 25, 8);
            stripeW.setFill(Color.WHITE);
            stripeE.setFill(Color.WHITE);
            crossings.getChildren().addAll(stripeW, stripeE);
        }
        for (int i = 0; i < 5; i++) {
            Rectangle stripeN = new Rectangle(355 + (i * 20), 120, 8, 25);
            Rectangle stripeS = new Rectangle(355 + (i * 20), 255, 8, 25);
            stripeN.setFill(Color.WHITE);
            stripeS.setFill(Color.WHITE);
            crossings.getChildren().addAll(stripeN, stripeS);
        }
        return crossings;
    }

    public static void main(String[] args) {
        launch();
    }
}