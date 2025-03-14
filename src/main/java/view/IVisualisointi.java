package view;

import javafx.geometry.Point2D;

/**
 * Interface for visualizing the simulation on a canvas.
 */
public interface IVisualisointi {

    /**
     * Clears the canvas.
     */
    public void tyhjennaNaytto();

    /**
     * Visualizes all service points on the canvas.
     */
    public void visualisoiPalvelupisteet();

    /**
     * Gets the coordinates for the starting point.
     *
     * @return the coordinates for the starting point
     */
    public Point2D AloitusCoord();

    /**
     * Gets the coordinates for the package machine.
     *
     * @return the coordinates for the package machine
     */
    public Point2D PACoord();

    /**
     * Gets the coordinates for the service selection.
     *
     * @return the coordinates for the service selection
     */
    public Point2D PVCoord();

    /**
     * Gets the coordinates for the pickup/send service.
     *
     * @return the coordinates for the pickup/send service
     */
    public Point2D NTCoord();

    /**
     * Gets the coordinates for the special cases service.
     *
     * @return the coordinates for the special cases service
     */
    public Point2D ETCoord();

    /**
     * Gets the coordinates for the exit point.
     *
     * @return the coordinates for the exit point
     */
    public Point2D ExitCoord();

    /**
     * Draws a customer on the canvas.
     *
     * @param id the customer ID
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public void drawCustomer(int id, double x, double y);

    /**
     * Exits a customer from the canvas.
     *
     * @param id the customer ID
     * @param toX the target X coordinate
     * @param toY the target Y coordinate
     */
    public void exitCustomer(int id, double toX, double toY);

    /**
     * Moves a customer to a new position.
     *
     * @param id the customer ID
     * @param toX the target X coordinate
     * @param toY the target Y coordinate
     * @param onFinished the callback to run when the move is finished
     */
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished);

    /**
     * Cleans up the canvas and stops all animations.
     */
    void cleanUp();

    /**
     * Pauses all active animations.
     */
    public void pauseAnimation();

    /**
     * Resumes all paused animations.
     */
    public void resumeAnimation();

    /**
     * Checks if there are any active animations.
     *
     * @return true if there are active animations, false otherwise
     */
    public boolean isAnimating();

    /**
     * Sets a callback to be run when all animations are complete.
     *
     * @param callback the callback to run
     */
    public void onAllAnimationsComplete(Runnable callback);

    /**
     * Updates the canvas when the window size changes.
     */
    void updateCanvas();
}