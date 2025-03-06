package view;
import javafx.geometry.Point2D;

public interface IVisualisointi {

    public void tyhjennaNaytto();

    // Palvelupisteiden visualisointi ja koordinaatit
    public void visualisoiPalvelupisteet();
    public Point2D AloitusCoord();
    public Point2D PACoord();
    public Point2D PVCoord();
    public Point2D NTCoord();
    public Point2D ETCoord();
    public Point2D ExitCoord();

    // Animaatio hommelit
    public void drawCustomer(int id, double x, double y);
    public void exitCustomer(int id, double toX, double toY);
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished);
    public void pauseAnimation();
    public void resumeAnimation();
    boolean isAnimating();
    void onAllAnimationsComplete(Runnable callback);
}

