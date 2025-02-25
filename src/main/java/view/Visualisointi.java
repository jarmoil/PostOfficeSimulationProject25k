package view;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
            Point2D oldPosition = new Point2D(customer.getCenterX(), customer.getCenterY());
            occupiedPositions.remove(oldPosition);

            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), customer);
            transition.setToX(toX - customer.getCenterX());
            transition.setToY(toY - customer.getCenterY());
            transition.setOnFinished(event -> onFinished.run());
            transition.play();
        }
    }

    @Override
    public void exitCustomer(int id, double toX, double toY) {
        Circle customer = (Circle) root.lookup("#customer-" + id);
        if (customer != null) {
            Point2D oldPosition = new Point2D(customer.getCenterX(), customer.getCenterY());
            occupiedPositions.remove(oldPosition);

            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), customer);
            transition.setToX(toX - customer.getCenterX());
            transition.setToY(toY - customer.getCenterY());

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), customer);
            fadeTransition.setToValue(0);

            SequentialTransition sequentialTransition = new SequentialTransition(transition, fadeTransition);
            sequentialTransition.setOnFinished(event -> root.getChildren().remove(customer));
            sequentialTransition.play();
        }
    }

    //jotenkin tällein, pitää varmaan jokaiselle palvelupisteelle
    // olla oma metodi et ne pystyy sijottamaan eri kohtiin, kai?

    /*public void uusiPalvelupiste(int x, int y) {
        gc.setFill(Color.BROWN);
        gc.fillRect(i,j,40,20);
        i = x;
        j = y;
    }*/
}
