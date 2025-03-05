package view;

import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Visualisointi extends Canvas implements IVisualisointi{

    private final GraphicsContext gc;
    // Heitin root borderpanen tänne, käytän sitä useammassa metodissa
    private BorderPane root;

    // Lista varatuista sijainneista
    private List<Point2D> occupiedPositions = new ArrayList<>();

    // Asiakkaan koko, minimi välimatka kun luodaan asiakkaita, että ei mene päällekkäin jne.
    private static final double CUSTOMER_RADIUS = 10;
    private static final double MIN_DISTANCE = 3 * CUSTOMER_RADIUS;
    private static final double areaWidth = 100;
    private static final double areaHeight = 100;
    private Timeline timeline;

    private int activeAnimations = 0;
    private Runnable completionCallback;

    public Visualisointi(int w, int h, BorderPane root) {
        super(w, h);
        this.root = root;
        gc = this.getGraphicsContext2D();
        tyhjennaNaytto();
    }


    public void tyhjennaNaytto() {
        gc.setFill(Color.SANDYBROWN);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    // Animaatio hommelit siirretty tänne

    @Override
    public boolean isAnimating() {
        return activeAnimations > 0;
    }

    @Override
    public void onAllAnimationsComplete(Runnable callback) {
        this.completionCallback = callback;
    }

    @Override
    public void drawCustomer(int id, double areaX, double areaY) {
        double x = areaX;
        double y = areaY;

        // Check for overlaps and adjust position if necessary
        boolean overlap;
        do {
            overlap = false;
            for (Point2D position : occupiedPositions) {
                if (position.distance(x, y) < MIN_DISTANCE) {
                    overlap = true;
                    x += MIN_DISTANCE; // Adjust x position
                    if (x > areaX + areaWidth - CUSTOMER_RADIUS) { // If x exceeds area width, reset x and adjust y
                        x = areaX;
                        y += MIN_DISTANCE;
                    }
                    if (y > areaY + areaHeight - CUSTOMER_RADIUS) { // If y exceeds area height, reset y
                        y = areaY;
                    }
                    break;
                }
            }
        } while (overlap);

        // Add the new position to the list of occupied positions
        occupiedPositions.add(new Point2D(x, y));

        Circle customer = new Circle(CUSTOMER_RADIUS, Color.BLUE);
        customer.setId("customer-" + id);
        customer.setCenterX(x);
        customer.setCenterY(y);
        root.getChildren().add(customer);
    }

    @Override
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished) {
        Circle customer = (Circle) root.lookup("#customer-" + id);
        if (customer != null) {
            activeAnimations++;
            Point2D oldPosition = new Point2D(customer.getCenterX(), customer.getCenterY());
            occupiedPositions.remove(oldPosition);

            double deltaX = toX - customer.getCenterX();
            double deltaY = toY - customer.getCenterY();
            double duration = 1.0;
            int frames = (int) (duration * 60);
            double stepX = deltaX / frames;
            double stepY = deltaY / frames;

            timeline = new Timeline(new KeyFrame(Duration.millis(16), event -> {
                customer.setCenterX(customer.getCenterX() + stepX);
                customer.setCenterY(customer.getCenterY() + stepY);
            }));
            timeline.setCycleCount(frames);
            timeline.setOnFinished(event -> {
                onFinished.run();
                activeAnimations--;
                if (activeAnimations == 0 && completionCallback != null) {
                    completionCallback.run();
                    completionCallback = null;
                }
            });
            timeline.play();
        }
    }

    @Override
    public void pauseAnimation() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    @Override
    public void resumeAnimation() {
        if (timeline != null) {
            timeline.play();
        }
    }

    @Override
    public void exitCustomer(int id, double toX, double toY) {
        Circle customer = (Circle) root.lookup("#customer-" + id);
        if (customer != null) {
            activeAnimations++;
            Point2D oldPosition = new Point2D(customer.getCenterX(), customer.getCenterY());
            occupiedPositions.remove(oldPosition);

            double deltaX = toX - customer.getCenterX();
            double deltaY = toY - customer.getCenterY();
            double duration = 1.0;
            int frames = (int) (duration * 60);
            double stepX = deltaX / frames;
            double stepY = deltaY / frames;

            timeline = new Timeline(new KeyFrame(Duration.millis(16), event -> {
                customer.setCenterX(customer.getCenterX() + stepX);
                customer.setCenterY(customer.getCenterY() + stepY);
            }));
            timeline.setCycleCount(frames);
            timeline.setOnFinished(event -> {
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), customer);
                fadeTransition.setToValue(0);
                fadeTransition.setOnFinished(e -> {
                    root.getChildren().remove(customer);
                    activeAnimations--;
                    if (activeAnimations == 0 && completionCallback != null) {
                        completionCallback.run();
                        completionCallback = null;
                    }
                });
                fadeTransition.play();
            });
            timeline.play();
        }
    }
}
