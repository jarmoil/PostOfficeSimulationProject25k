package view;
import javafx.geometry.Point2D;

public interface IVisualisointi {

    // Canvas jutut
    public void tyhjennaNaytto();

    // Palvelupisteiden visualisointi ja koordinaatit
    public void visualisoiPalvelupisteet();
    public Point2D AloitusCoord();
    public Point2D PACoord();
    public Point2D PVCoord();
    public Point2D NTCoord();
    public Point2D ETCoord();
    public Point2D ExitCoord();

    // Animaatio metodit
    public void drawCustomer(int id, double x, double y);
    public void exitCustomer(int id, double toX, double toY);
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished);
    // In IVisualisointi.java, add:
    void cleanUp();

    // Animaation hallinta
    public void pauseAnimation();
    public void resumeAnimation();
    public boolean isAnimating();
    public void onAllAnimationsComplete(Runnable callback);
    void updateCanvas();
}

