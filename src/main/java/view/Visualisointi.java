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
    private List<Point2D> occupiedPositions;

    // Asiakkaan koko, minimi välimatka kun luodaan asiakkaita, että ei mene päällekkäin jne.
    private static final double CUSTOMER_RADIUS = 10;
    private static final double MIN_DISTANCE = 3 * CUSTOMER_RADIUS;
    private static final double areaWidth = 100;
    private static final double areaHeight = 100;
    private Timeline timeline;

    // Siirretty arvot tänne niin helppo muuttaa halutessaan
    private static final double ANIMATION_DURATION = 1.0;
    private static final int ANIMATION_FPS = 60;
    private static final double FADE_DURATION = 400;

    // Katsotaan onko animaatioita käynnissä
    private int activeAnimations = 0;
    private Runnable completionCallback;

    public Visualisointi(int w, int h, BorderPane root) {
        super(w, h);
        if (root == null) {
            throw new IllegalArgumentException("Root cannot be null");
        }
        this.root = root;
        this.gc = this.getGraphicsContext2D();
        this.occupiedPositions = new ArrayList<>();

        initializeCanvas();
    }

    // Metodi joka alustaa canvaksen
    private void initializeCanvas() {
        widthProperty().bind(root.widthProperty());
        heightProperty().bind(root.heightProperty());

        root.widthProperty().addListener((obs, oldVal, newVal) -> updateCanvas());
        root.heightProperty().addListener((obs, oldVal, newVal) -> updateCanvas());

        updateCanvas();
    }

    // Metodi jota voi hyödyntää ehkä sitten kun tehdään joku nappi STOP/START yms
    public void cleanup() {
        if (timeline != null) {
            timeline.stop();
        }
        occupiedPositions.clear();
        completionCallback = null;
        activeAnimations = 0;
    }

    // Canvaksen päivitys kun ikkunan kokoa muutetaan
    private void updateCanvas() {
        tyhjennaNaytto();
        visualisoiPalvelupisteet();
    }
    // Alustetaan näyttö
    public void tyhjennaNaytto() {
        gc.setFill(Color.SANDYBROWN);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void visualisoiAloitus() {
        gc.setFill(Color.BROWN);
        gc.fillText("Aloitus", 0, (this.getHeight()/2) - 10);
        gc.fillRect(0, (this.getHeight()/2), 40, 60);
    }
    public Point2D AloitusCoord() {
        return new Point2D((root.getWidth()-this.getWidth()), ((root.getHeight()-this.getHeight()) + (this.getHeight()/2)-30));
        // vähennetään rootin leveys canvaksen leveydestä, jotta saadaan aloitus canvaksen vasempaan reunaan.
    }

    public void visualisoiPA() {
        gc.setFill(Color.BLACK);
        gc.fillText("Pakettiautomaatti", (this.getWidth()/4), (this.getHeight()/3 + this.getHeight()/3) - 10);
        gc.fillRect((this.getWidth()/4), (this.getHeight()/3 + this.getHeight()/3), 50, 50);
    }

    public Point2D PACoord() {
        return new Point2D((((root.getWidth()-this.getWidth()) + (this.getWidth())/4)) - CUSTOMER_RADIUS, ((root.getHeight()-this.getHeight()) +(this.getHeight()/3 + this.getHeight()/3)) - 25 );
    }

    public void visualisoiPV() {
        gc.setFill(Color.BLACK);
        gc.fillText("Palvelunvalinta", (this.getWidth()/4), (this.getHeight()/3) - 10);
        gc.fillRect((this.getWidth()/4), (this.getHeight()/3), 50, 50);
    }
    public Point2D PVCoord() {
        return new Point2D((((root.getWidth()-this.getWidth()) + (this.getWidth())/4)) - CUSTOMER_RADIUS, ((root.getHeight() - this.getHeight()) + this.getHeight()/3) - 25);
    }

    public void visualisoiNT() {
        gc.setFill(Color.BLACK);
        gc.fillText("Nouto/Lähetä", (this.getWidth()/3), 60);
        gc.fillRect((this.getWidth()/3), 0, 100, 50);
    }
    public Point2D NTCoord() {
        return new Point2D((((root.getWidth()-this.getWidth()) + (this.getWidth())/3)) + 50, (root.getHeight() - this.getHeight()));
    }

    public void visualisoiET() {
        gc.setFill(Color.BLACK);
        gc.fillText("Erikoistapaukset", (this.getWidth()/3 + this.getWidth()/3), 60);
        gc.fillRect((this.getWidth()/3 + this.getWidth()/3), 0, 100, 50);
    }
    public Point2D ETCoord() {
        return new Point2D((((root.getWidth()-this.getWidth()) + (this.getWidth())/3) + (this.getWidth()/3)) + 50, (root.getHeight() - this.getHeight()));
    }

    public void visualisoiExit() {
        gc.setFill(Color.RED);
        gc.fillText("Exit", this.getWidth()-40, (this.getHeight()/2) - 10);
        gc.fillRect(this.getWidth()-40, this.getHeight()/2, 40, 60);
    }
    public Point2D ExitCoord() {
        return new Point2D((root.getWidth()-40), ((root.getHeight() - this.getHeight()) + (this.getHeight()/2)-30));
    }

    public void visualisoiPalvelupisteet() {
        visualisoiAloitus();
        visualisoiPA();
        visualisoiPV();
        visualisoiNT();
        visualisoiET();
        visualisoiExit();
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

    // Pikku tälläne handleri jos asiakasta ei löydy (ei pitäisi tapahtua mutta kuiteski)
    private void handleCustomerNotFound(int id) {
        System.err.println("Customer with ID " + id + " not found");
        if (activeAnimations > 0) {
            activeAnimations--;
        }
    }

    // Animaatio asiakkaan liikuttamiseen siirretty omaan metodiin, koska paljon eri logiikkaa siinä
    private void animateCustomer(Circle customer, double toX, double toY, boolean shouldFadeOut, Runnable onFinished) {
        activeAnimations++;
        Point2D oldPosition = new Point2D(customer.getCenterX(), customer.getCenterY());
        occupiedPositions.remove(oldPosition);

        double deltaX = toX - customer.getCenterX();
        double deltaY = toY - customer.getCenterY();
        int frames = (int) (ANIMATION_DURATION * ANIMATION_FPS);
        double stepX = deltaX / frames;
        double stepY = deltaY / frames;

        timeline = new Timeline(new KeyFrame(Duration.millis(16), event -> {
            customer.setCenterX(customer.getCenterX() + stepX);
            customer.setCenterY(customer.getCenterY() + stepY);
        }));
        timeline.setCycleCount(frames);
        timeline.setOnFinished(event -> {
            if (shouldFadeOut) {
                fadeOutAndRemove(customer, onFinished);
            } else {
                finishAnimation(onFinished);
            }
        });
        timeline.play();
    }

    // Fade animaatiolla ja asiakkaan poistamiselle oma metodi
    private void fadeOutAndRemove(Circle customer, Runnable onFinished) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(FADE_DURATION), customer);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(e -> {
            root.getChildren().remove(customer);
            finishAnimation(onFinished);
        });
        fadeTransition.play();
    }

    // Animaation lopetus
    private void finishAnimation(Runnable onFinished) {
        if (onFinished != null) {
            onFinished.run();
        }
        activeAnimations--;
        if (activeAnimations == 0 && completionCallback != null) {
            completionCallback.run();
            completionCallback = null;
        }
    }

    @Override
    public boolean isAnimating() {
        return activeAnimations > 0;
    }

    @Override
    public void onAllAnimationsComplete(Runnable callback) {
        this.completionCallback = callback;
    }


    // Movecustomer ja exitcustomer hyödyntää uusia metodeja animaatiolle

    @Override
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished) {
        Circle customer = (Circle) root.lookup("#customer-" + id);
        if (customer != null) {
            animateCustomer(customer, toX, toY, false, onFinished);
        } else {
            handleCustomerNotFound(id);
        }
    }

    @Override
    public void exitCustomer(int id, double toX, double toY) {
        Circle customer = (Circle) root.lookup("#customer-" + id);
        if (customer != null) {
            animateCustomer(customer, toX, toY, true, null);
        } else {
            handleCustomerNotFound(id);
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
}
