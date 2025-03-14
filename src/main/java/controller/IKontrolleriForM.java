package controller;


import javafx.geometry.Point2D;

/**
 * The IKontrolleriForM interface provides methods for controlling the simulation engine.
 * It includes methods for updating simulation statistics, managing animations, and retrieving distribution parameters.
 */
public interface IKontrolleriForM {

    // Rajapinta, joka tarjotaan moottorille:

    /**
     * Displays the end time of the simulation.
     * @param aika The end time of the simulation.
     */
    public void naytaLoppuaika(double aika);

    /**
     * Displays the end time of the simulation for young customers.
     * @param aika The end time for young customers.
     */
    public void naytaLoppuaikaNuori(double aika);

    /**
     * Displays the end time of the simulation for middle-aged customers.
     * @param aika The end time for middle-aged customers.
     */
    public void naytaLoppuaikaKeski(double aika);

    /**
     * Displays the end time of the simulation for elderly customers.
     * @param aika The end time for elderly customers.
     */
    public void naytaLoppuaikaVanha(double aika);

    /**
     * Updates the total number of served customers.
     * @param TotalServedCustomers The total number of served customers.
     */
    public void updateTotalServedCustomers(int TotalServedCustomers);

    /**
     * Updates the statistics for the specified service point.
     * @param type The type of the service point.
     * @param queueLength The length of the queue.
     * @param servedCustomers The number of served customers.
     * @param avgWaitTime The average wait time.
     * @param avgServiceTime The average service time.
     * @param totalTime The total time.
     */
    void updateServicePointStats(simu.model.TapahtumanTyyppi type, int queueLength, int servedCustomers,
                                 double avgWaitTime, double avgServiceTime, double totalTime);

    /**
     * Draws a customer at the specified coordinates
     * @param id The customer ID.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    // Animaatio huommelit
    public void drawCustomer(int id, double x, double y);

    /**
     * Moves the customer to the specified coordinates and fades out the customer upon reaching the destination.
     * @param id The customer ID.
     * @param toX The target x-coordinate.
     * @param toY The target y-coordinate.
     */
    public void exitCustomer(int id, double toX, double toY);

    /**
     * Moves the customer to the specified coordinates and executes the specified action upon completion.
     * @param id The customer ID.
     * @param toX The target x-coordinate.
     * @param toY The target y-coordinate.
     * @param onFinished The action to be executed upon completion.
     */
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished);

    // Distribuutio valitsemis hommelit

    /**
     * Returns the distribution types for the simulation.
     * @return An array of distribution types.
     */
    String[] getDistributionTypes();

    /**
     * Returns the distribution means for the simulation.
     * @return An array of distribution means.
     */
    double[] getDistributionMeans();

    /**
     * Returns the distribution variances for the simulation.
     * @return An array of distribution variances.
     */
    double[] getDistributionVariances();

    /**
     * Returns the arrival probability for the simulation.
     * @return The arrival probability.
     */
    double getArrivalProbability();

    /**
     * Returns the redirect probability for the simulation.
     * @return The redirect probability.
     */
    double getRedirectProbability();

    /**
     * Returns the simulation time.
     * @return The simulation time.
     */
    double getAika();

    /**
     * Returns the delay (viive) for the simulation.
     * @return The delay.
     */
    long getViive();

    /**
     * Waits for all animations to complete before executing the specified callback.
     * @param callback The callback to be executed upon completion.
     */
    void waitForAnimations(Runnable callback);

    /**
     * Returns the coordinates of the starting point.
     * @return The coordinates of the starting point.
     */
    // X , Y koordinaatit palvelupisteille / asiakkaille
    public Point2D AloitusCoord();

    /**
     * Returns the coordinates of the package machine (Pakettiautomaatti).
     * @return The coordinates of the package machine.
     */
    public Point2D PACoord();

    /**
     * Returns the coordinates of the service selection (Palvelunvalinta).
     * @return The coordinates of the service selection.
     */
    public Point2D PVCoord();

    /**
     * Returns the coordinates of the pickup/drop-off point (Nouto/Lähetä).
     * @return The coordinates of the pickup/drop-off point.
     */
    public Point2D NTCoord();

    /**
     * Returns the coordinates of the special cases (Erityistapaukset).
     * @return The coordinates of the special cases.
     */
    public Point2D ETCoord();

    /**
     * Returns the coordinates of the exit point.
     * @return The coordinates of the exit point.
     */
    public Point2D ExitCoord();
}
