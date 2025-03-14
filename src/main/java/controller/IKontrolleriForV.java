package controller;

import entity.Tulokset;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * The IKontrolleriForV interface provides methods for controlling the simulation from the user interface.
 * It includes methods for starting, stopping, and adjusting the simulation, as well as managing historical data.
 */
public interface IKontrolleriForV {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    /**
     * Starts the simulation.
     */
    public void kaynnistaSimulointi();

    /**
     * Speeds up the simulation.
     */
    public void nopeuta();

    /**
     * Slows down the simulation.
     */
    public void hidasta();

    /**
     * Resumes the simulation if it is paused.
     */
    public void jatka();

    /**
     * Pauses the simulation.
     */
    public void pysayta();

    /**
     * Time forward by 0.5 seconds
     */
    void set();

    /**
     * Stops the simulation.
     */
    void stopSim();

    /**
     * Deletes the specified historical result from the database.
     * @param tulos tulos The historical result to be deleted.
     */
    void poistaHistoria(Tulokset tulos);

    /**
     * Displays the historical simulation data in the user interface.
     */
    public void naytaHistoriaData();

    /**
     * Updates the historical simulation details in the user interface.
     * @param tulos The historical result to be displayed.
     */
    public void paivitaHistoriaYksityiskohdat(Tulokset tulos);

    /**
     * Clears all historical simulation data from the database.
     */
    void clearHistory();

    /**
     * Resets the simulation clock to zero.
     */
    // In IKontrolleriForV.java
    void resetClock();

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
}
