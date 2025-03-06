package view;


public interface IVisualisointi {

    public void tyhjennaNaytto();

    // Animaatio hommelit
    public void drawCustomer(int id, double x, double y);
    public void exitCustomer(int id, double toX, double toY);
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished);
    public void pauseAnimation();
    public void resumeAnimation();
    boolean isAnimating();
    void onAllAnimationsComplete(Runnable callback);

    //public void uusiPalvelupiste(int x, int y);
}

