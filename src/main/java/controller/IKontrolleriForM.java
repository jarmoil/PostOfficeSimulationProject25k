package controller;


import javafx.geometry.Point2D;

public interface IKontrolleriForM {

    // Rajapinta, joka tarjotaan moottorille:

    public void naytaLoppuaika(double aika);
    public void naytaLoppuaikaNuori(double aika);
    public void naytaLoppuaikaKeski(double aika);
    public void naytaLoppuaikaVanha(double aika);
    public void updateTotalServedCustomers(int TotalServedCustomers);

    void updateServicePointStats(simu.model.TapahtumanTyyppi type, int queueLength, int servedCustomers,
                                 double avgWaitTime, double avgServiceTime, double totalTime);
    // Animaatio huommelit
    public void drawCustomer(int id, double x, double y);
    public void exitCustomer(int id, double toX, double toY);
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished);

    // Distribuutio valitsemis hommelit
    String[] getDistributionTypes();
    double[] getDistributionMeans();
    double[] getDistributionVariances();
    double getArrivalProbability();
    double getRedirectProbability();

    double getAika();
    long getViive();

    void waitForAnimations(Runnable callback);

    // X , Y koordinaatit palvelupisteille / asiakkaille
    public Point2D AloitusCoord();
    public Point2D PACoord();
    public Point2D PVCoord();
    public Point2D NTCoord();
    public Point2D ETCoord();
    public Point2D ExitCoord();
}
