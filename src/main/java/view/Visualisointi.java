package view;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;


import java.util.*;

public class Visualisointi extends Canvas implements IVisualisointi{

    private final GraphicsContext gc;
    private ISimulaattorinUI ui;
    private Image entryImage;
    private Image ntImage;
    private Image etImage;
    private Image pvImage;
    private Image paImage;
    private Image exitImage;


    // Heitin root borderpanen tänne, käytän sitä useammassa metodissa
    private BorderPane root;

    // Lista varatuista sijainneista
    private final Object animationLock = new Object();

    // Asiakkaan koko, minimi välimatka kun luodaan asiakkaita, että ei mene päällekkäin jne.
    private static final double CUSTOMER_RADIUS = 30;
    private static final double areaWidth = 100;
    private static final double areaHeight = 100;
    private List<Timeline> activeAnimations = new ArrayList<>();  // Keep this one, remove the int version
    private Timeline currentTimeline;  // For pause/resume functionality
    private Runnable completionCallback;


    // Siirretty arvot tänne niin helppo muuttaa halutessaan
    private static final int ANIMATION_FPS = 60;
    private static final double FADE_DURATION = 400;

    private static final Image CUSTOMER_IMAGE = new Image("/customer.png",
            CUSTOMER_RADIUS * 2, CUSTOMER_RADIUS * 2, true, true);

    public Visualisointi(int w, int h, BorderPane root, ISimulaattorinUI ui) {
        super(w, h);
        if (root == null) {
            throw new IllegalArgumentException("Root cannot be null");
        }
        if (ui == null) {
            throw new IllegalArgumentException("UI cannot be null");
        }
        this.root = root;
        this.ui = ui;
        this.gc = this.getGraphicsContext2D();
        loadImages();

        initializeCanvas();
    }
    private void loadImages() {
        entryImage = new Image(getClass().getResourceAsStream("/entry.png"));
        ntImage = new Image(getClass().getResourceAsStream("/serviceDesk.png"));
        etImage = new Image(getClass().getResourceAsStream("/serviceDesk.png"));
        pvImage = new Image(getClass().getResourceAsStream("/palvelunvalintanaytto.png"));
        paImage = new Image(getClass().getResourceAsStream("/pakettiautomaatti.png"));
        exitImage = new Image(getClass().getResourceAsStream("/exit.png"));
    }

    // Metodi joka alustaa canvaksen
    private void initializeCanvas() {
        widthProperty().bind(root.widthProperty());
        heightProperty().bind(root.heightProperty());

        root.widthProperty().addListener((obs, oldVal, newVal) -> updateCanvas());
        root.heightProperty().addListener((obs, oldVal, newVal) -> updateCanvas());

        updateCanvas();
    }

    private double getAnimationDuration() {
        double currentViive = ui.getViive();
        if (currentViive > 100) {
            return 1.0; // Normal speed for viive > 200
        }
        // Scale down duration only for very fast speeds
        return Math.max(0.2, currentViive / 200.0);
    }


    // Canvaksen päivitys kun ikkunan kokoa muutetaan
    public void updateCanvas() {
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
        gc.drawImage(entryImage, 0,this.getHeight()/2, 100, 100);
    }
    public Point2D AloitusCoord() {
        return new Point2D((root.getWidth()-this.getWidth()), ((root.getHeight()-this.getHeight()) + (this.getHeight()/2)-30));
        // vähennetään rootin leveys canvaksen leveydestä, jotta saadaan aloitus canvaksen vasempaan reunaan.
    }

    public void visualisoiPA() {
        gc.drawImage(paImage, (this.getWidth()/4), (this.getHeight()/3 + this.getHeight()/3), 100, 130);
        gc.fillText("Pakettiautomaatti", (this.getWidth()/4), (this.getHeight()/3 + this.getHeight()/3) - 10);
    }

    public Point2D PACoord() {
        return new Point2D((((root.getWidth()-this.getWidth()) + (this.getWidth())/4)) - CUSTOMER_RADIUS, ((root.getHeight()-this.getHeight()) +(this.getHeight()/3 + this.getHeight()/3)) - 25 );
    }

    public void visualisoiPV() {
        gc.drawImage(pvImage, (this.getWidth()/4), (this.getHeight()/3), 100, 130);
        gc.fillText("Palvelunvalinta", (this.getWidth()/4), (this.getHeight()/3) - 10);
    }
    public Point2D PVCoord() {
        return new Point2D((((root.getWidth()-this.getWidth()) + (this.getWidth())/4)) - CUSTOMER_RADIUS, ((root.getHeight() - this.getHeight()) + this.getHeight()/3) - 25);
    }

    public void visualisoiNT() {
        gc.drawImage(ntImage, (this.getWidth()/3), 0, 130, 80);
        gc.fillText("Nouto/Lähetä", (this.getWidth()/3), 90);
    }
    public Point2D NTCoord() {
        return new Point2D((((root.getWidth()-this.getWidth()) + (this.getWidth())/3)) + 50, (root.getHeight() - this.getHeight()));
    }

    public void visualisoiET() {
        gc.drawImage(etImage, (this.getWidth()/3 + this.getWidth()/3), 0, 130, 80);
        gc.fillText("Erikoistapaukset", (this.getWidth()/3 + this.getWidth()/3), 90);
    }
    public Point2D ETCoord() {
        return new Point2D((((root.getWidth()-this.getWidth()) + (this.getWidth())/3) + (this.getWidth()/3)) + 50, (root.getHeight() - this.getHeight()));
    }

    public void visualisoiExit() {
        gc.drawImage(exitImage, this.getWidth()-100, (this.getHeight()/2), 100, 80);
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
    public void cleanUp() {
        Platform.runLater(() -> {
            synchronized (animationLock) {
                for (Timeline timeline : activeAnimations) {
                    timeline.stop();
                }
                activeAnimations.clear();
                root.getChildren().removeIf(node -> node instanceof ImageView);
                currentTimeline = null;
                completionCallback = null;

                tyhjennaNaytto();
                visualisoiPalvelupisteet();
            }
        });
    }

    @Override
    public void drawCustomer(int id, double areaX, double areaY) {
        double x = areaX + (areaWidth - CUSTOMER_RADIUS * 2) / 2;
        double y = areaY + Math.random() * (areaHeight - CUSTOMER_RADIUS * 2);

        ImageView customer = new ImageView(CUSTOMER_IMAGE);
        customer.setId("customer-" + id);
        customer.setX(x - CUSTOMER_RADIUS);
        customer.setY(y - CUSTOMER_RADIUS);
        customer.setStyle("-fx-background-color: transparent;");
        customer.setOpacity(0); // Start fully transparent

        root.getChildren().add(customer);

        // Create and play fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.millis(FADE_DURATION), customer);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    // Pikku tälläne handleri jos asiakasta ei löydy (ei pitäisi tapahtua mutta kuiteski)
    private void handleCustomerNotFound(int id) {
        System.err.println("Customer with ID " + id + " not found");
    }

    // Animaatio asiakkaan liikuttamiseen siirretty omaan metodiin, koska paljon eri logiikkaa siinä
    private void animateCustomer(ImageView customer, double toX, double toY, boolean shouldFadeOut, Runnable onFinished) {

        double deltaX = (toX - CUSTOMER_RADIUS) - customer.getX();
        double deltaY = (toY - CUSTOMER_RADIUS) - customer.getY();
        int frames = (int) (getAnimationDuration() * ANIMATION_FPS);
        double stepX = deltaX / frames;
        double stepY = deltaY / frames;

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), event -> {
            customer.setX(customer.getX() + stepX);
            customer.setY(customer.getY() + stepY);
        }));
        timeline.setCycleCount(frames);

        currentTimeline = timeline;
        activeAnimations.add(timeline);

        timeline.setOnFinished(event -> {
            activeAnimations.remove(timeline);
            if (shouldFadeOut) {
                fadeOutAndRemove(customer, onFinished);
            } else {
                finishAnimation(onFinished);
            }
        });
        timeline.play();
    }

    // Fade animaatiolla ja asiakkaan poistamiselle oma metodi
    private void fadeOutAndRemove(ImageView customer, Runnable onFinished) {
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
        if (activeAnimations.isEmpty() && completionCallback != null) {
            completionCallback.run();
            completionCallback = null;
        }
    }

    @Override
    public boolean isAnimating() {
        synchronized (animationLock) {
            return !activeAnimations.isEmpty();
        }
    }

    @Override
    public void onAllAnimationsComplete(Runnable callback) {
        this.completionCallback = callback;
    }


    // Movecustomer ja exitcustomer hyödyntää uusia metodeja animaatiolle

    @Override
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished) {
        Platform.runLater(() -> {
            ImageView customer = (ImageView) root.lookup("#customer-" + id);
            if (customer != null) {
                synchronized (animationLock) {
                    animateCustomer(customer, toX, toY, false, onFinished);
                }
            } else {
                handleCustomerNotFound(id);
            }
        });
    }

    @Override
    public void exitCustomer(int id, double toX, double toY) {
        ImageView customer = (ImageView) root.lookup("#customer-" + id);
        if (customer != null) {
            animateCustomer(customer, toX, toY, true, null);
        } else {
            handleCustomerNotFound(id);
        }
    }

    @Override
    public void pauseAnimation() {
        Platform.runLater(() -> {
            synchronized (animationLock) {
                for (Timeline timeline : activeAnimations) {
                    timeline.pause();
                }
            }
        });
    }

    @Override
    public void resumeAnimation() {
        Platform.runLater(() -> {
            synchronized (animationLock) {
                for (Timeline timeline : activeAnimations) {
                    timeline.play();
                }
            }
        });
    }
}
