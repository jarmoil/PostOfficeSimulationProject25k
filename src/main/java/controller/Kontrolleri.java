package controller;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import simu.framework.IDao;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.model.OmaMoottori;
import view.ISimulaattorinUI;
import entity.*;

import java.util.Comparator;
import java.util.List;


/**
 * The Kontrolleri class implements the IKontrolleriForM and IKontrolleriForV interfaces.
 * It serves as the controller for the simulation, managing the interaction between the UI (visualisointi and simulaattorinGUI) and the simulation engine.
 */
public class Kontrolleri implements IKontrolleriForM, IKontrolleriForV{   // UUSI

    /**
     * Runs the specified action on the JavaFX application thread.
     * @param action The action to be run.
     */
    private void runOnFXThread(Runnable action) {
        Platform.runLater(action);
    }

    private IMoottori moottori;
    private ISimulaattorinUI ui;
    private IDao tuloksetDao;

    /**
     * Constructs a new Kontrolleri instance.
     * @param ui The UI interface for the simulation.
     * @param dao The data access object for simulation results.
     */
    public Kontrolleri(ISimulaattorinUI ui, IDao dao) {
        this.ui = ui;
        this.tuloksetDao = dao;

    }


    // Moottorin ohjausta:

    /**
     * Starts the simulation by creating a new simulation engine thread and initializing it with parameters from the UI.
     */
    @Override
    public void kaynnistaSimulointi() {
        moottori = new OmaMoottori(this, this.tuloksetDao,
                 getDistributionTypes(), getDistributionMeans(), getDistributionVariances()); // luodaan uusi moottorisäie jokaista simulointia varten
        moottori.setSimulointiaika(ui.getAika());
        moottori.setViive(ui.getViive());
        ui.getVisualisointi().tyhjennaNaytto();
        ui.getVisualisointi().visualisoiPalvelupisteet();
        ui.disableInputFields();
        ((Thread)moottori).start();
        //((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?
    }

    /**
     * Slows down the simulation engine thread by increasing its delay.
     */
    @Override
    public void hidasta() { // hidastetaan moottorisäiettä
        moottori.setViive((long)(moottori.getViive()*1.10));
    }

    /**
     * Speeds up the simulation engine thread by decreasing its delay.
     */
    @Override
    public void nopeuta() { // nopeutetaan moottorisäiettä
        moottori.setViive((long)(moottori.getViive()*0.9));
    }

    /**
     * Resumes the simulation if it is paused.
     */
    @Override
    public void jatka(){
        if (moottori != null){
            moottori.jatkaSimulaatio();
            ui.getVisualisointi().resumeAnimation();
        }
    }

    /**
     * Pauses the simulation.
     */
    @Override
    public void pysayta(){
        if(moottori != null){
            moottori.pysaytaSimulaatio();
            ui.getVisualisointi().pauseAnimation();
        }
    }

    /**
     * Stops the simulation and resets the simulation engine with parameters from the UI.
     */
    @Override
    public void stopSim(){
        if(moottori != null){
            moottori.stopSimulaatio();
            moottori.setSimulointiaika(ui.getAika());
            moottori.setViive(ui.getViive());
            ui.getVisualisointi().tyhjennaNaytto();
            ui.getVisualisointi().visualisoiPalvelupisteet();
        }
    }

    //public void updateTotalServedCustomers(int totalServedCustomers) {Platform.runLater(()->ui.paivitaAsiakasMaara(totalServedCustomers));}


    /**
     * Jumps 0.5 seconds forward if the step (set) button is pressed.
     */
    @Override
    public void set(){
        if (moottori != null){
            Platform.runLater(()-> {moottori.set();});
        }
    }

    /**
     * Deletes the specified simulation result from the database.
     * @param tulos The simulation result to be deleted.
     */
    @Override
    public void poistaHistoria(Tulokset tulos){
        boolean success = tuloksetDao.deleteTulos(tulos);
        if (success){
            System.out.println("Tulokset has been deleted");
        }else {
            System.out.println("Tulokset couldn't be deleted");
        }
    }


    // Simulointitulosten välittämistä käyttöliittymään.
    // Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:

    /**
     * Displays the end time of the simulation in the UI.
     * @param aika The end time of the simulation.
     */
    @Override
    public void naytaLoppuaika(double aika) {
        runOnFXThread(() -> ui.setLoppuaika(aika));
    }

    /**
     * Displays the end time of the simulation for young customers in the UI.
     * @param aika The end time for young customers.
     */
    @Override
    public void naytaLoppuaikaNuori(double aika) {
        Platform.runLater(()->ui.setLoppuaikaNuori(aika));
    }

    /**
     * Displays the end time of the simulation for middle-aged customers in the UI.
     * @param aika The end time for middle-aged customers.
     */
    @Override
    public void naytaLoppuaikaKeski(double aika) {
        Platform.runLater(()->ui.setLoppuaikaKeski(aika));
    }

    /**
     * Displays the end time of the simulation for elderly customers in the UI.
     * @param aika The end time for elderly customers.
     */
    @Override
    public void naytaLoppuaikaVanha(double aika) {
        Platform.runLater(()->ui.setLoppuaikaVanha(aika));
    }

    /**
     * Updates the total number of served customers in the UI.
     * @param totalServedCustomers The total number of served customers.
     */
    @Override
    public void updateTotalServedCustomers(int totalServedCustomers) {Platform.runLater(()->ui.paivitaAsiakasMaara(totalServedCustomers));}

    /**
     * Updates the service point statistics in the UI based on the event type.
     * @param type The event type.
     * @param queueLength The length of the queue.
     * @param servedCustomers The number of served customers.
     * @param avgWaitTime The average wait time.
     * @param avgServiceTime The average service time.
     * @param totalTime The total time.
     */
    @Override
    public void updateServicePointStats(simu.model.TapahtumanTyyppi type, int queueLength,
                                        int servedCustomers, double avgWaitTime, double avgServiceTime, double totalTime) {
        Platform.runLater(() -> {
            switch (type) {
                case PAKETTIAUTOMAATTI -> {
                    ui.paivitaJonoPituus(queueLength);
                    ui.paivitaPalveltuMaara(servedCustomers);
                    ui.paivitaKeskimJonoAika(avgWaitTime);
                    ui.paivitaKeskimPalveluAika(avgServiceTime);
                    ui.paivitaKokonaisAika(totalTime);
                }
                case PALVELUNVALINTA -> {
                    ui.PVpaivitaJonoPituus(queueLength);
                    ui.PVpaivitaPalveltuMaara(servedCustomers);
                    ui.PVpaivitaKeskimJonoAika(avgWaitTime);
                    ui.PVpaivitaKeskimPalveluAika(avgServiceTime);
                    ui.PVpaivitaKokonaisAika(totalTime);
                }
                case NOUTOLAHETA -> {
                    ui.NTpaivitaJonoPituus(queueLength);
                    ui.NTpaivitaPalveltuMaara(servedCustomers);
                    ui.NTpaivitaKeskimJonoAika(avgWaitTime);
                    ui.NTpaivitaKeskimPalveluAika(avgServiceTime);
                    ui.NTpaivitaKokonaisAika(totalTime);
                }
                case ERITYISTAPAUKSET -> {
                    ui.ETpaivitaJonoPituus(queueLength);
                    ui.ETpaivitaPalveltuMaara(servedCustomers);
                    ui.ETpaivitaKeskimJonoAika(avgWaitTime);
                    ui.ETpaivitaKeskimPalveluAika(avgServiceTime);
                    ui.ETpaivitaKokonaisAika(totalTime);
                }
                case ARR1 -> {
                    // No UI updates needed for arrivals
                }
            }
        });
    }
    // Animaatio hommelit

    /**
     * Draws a customer at the specified coordinates in the UI.
     * @param id The customer ID.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    @Override
    public void drawCustomer(int id, double x, double y) {
        Platform.runLater(() -> ui.getVisualisointi().drawCustomer(id, x, y));
    }

    /**
     * Moves the customer to the specified coordinates and fades out the customer upon reaching the destination.
     * @param id The customer ID.
     * @param toX The target x-coordinate.
     * @param toY The target y-coordinate.
     */
    @Override
    public void exitCustomer(int id, double toX, double toY) {
        Platform.runLater(() -> ui.getVisualisointi().exitCustomer(id, toX, toY));
    }

    /**
     * Moves the customer to the specified coordinates and executes the specified action upon completion.
     * @param id The customer ID.
     * @param toX The target x-coordinate.
     * @param toY The target y-coordinate.
     * @param onFinished The action to be executed upon completion.
     */
    @Override
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished) {
        runOnFXThread(() -> ui.getVisualisointi().moveCustomer(id, toX, toY, onFinished));
    }

    /**
     * Waits for all animations to complete before executing the specified callback.
     * @param callback The callback to be executed upon completion.
     */
    @Override
    public void waitForAnimations(Runnable callback) {
        runOnFXThread(() -> {
            if (ui.getVisualisointi().isAnimating()) {
                ui.getVisualisointi().onAllAnimationsComplete(callback);
            } else {
                callback.run();
            }
        });
    }

    /**
     * Returns the coordinates of the starting point.
     * @return The coordinates of the starting point.
     */
    @Override
    public Point2D AloitusCoord() {
        return ui.getVisualisointi().AloitusCoord();
    }

    /**
     * Returns the coordinates of the package machine (Pakettiautomaatti).
     * @return The coordinates of the package machine.
     */
    @Override
    public Point2D PACoord() {
        return ui.getVisualisointi().PACoord();
    }

    /**
     * Returns the coordinates of the service selection (Palvelunvalinta).
     * @return The coordinates of the service selection.
     */
    @Override
    public Point2D PVCoord() {
        return ui.getVisualisointi().PVCoord();
    }

    /**
     * Returns the coordinates of the pickup/drop-off point (Nouto/Lähetä).
     * @return The coordinates of the pickup/drop-off point.
     */
    @Override
    public Point2D NTCoord() {
        return ui.getVisualisointi().NTCoord();
    }

    /**
     * Returns the coordinates of the special cases (Erikoistapaukset).
     * @return The coordinates of the special cases.
     */
    @Override
    public Point2D ETCoord() {
        return ui.getVisualisointi().ETCoord();
    }

    /**
     * Returns the coordinates of the exit point.
     * @return The coordinates of the exit point.
     */
    @Override
    public Point2D ExitCoord() {
        return ui.getVisualisointi().ExitCoord();
    }


    /**
     * Displays the historical simulation data in the UI.
     */
    @Override
    public void naytaHistoriaData() {
        List<Tulokset> historyData = tuloksetDao.lataaKaikki();
        Platform.runLater(() -> ui.naytaHistoriaData(historyData));
    }

    /**
     * Updates the historical simulation details in the UI.
     * @param tulos The simulation result to be displayed
     */
    @Override
    public void paivitaHistoriaYksityiskohdat(Tulokset tulos) {
        Platform.runLater(() -> ui.paivitaHistoriaYksityiskohdat(tulos));
    }

    /**
     * Returns the distribution types for the simulation
     * @return An array of distribution types.
     */
    // Distribution related methods
    @Override
    public String[] getDistributionTypes() {
        String[] types = new String[4];
        for (int i = 0; i < 4; i++) {
            types[i] = ui.getDistributionType(i);
        }
        return types;
    }

    /**
     * Returns the distribution means for the simulation
     * @return An array of distribution means.
     */
    @Override
    public double[] getDistributionMeans() {
        double[] means = new double[4];
        for (int i = 0; i < 4; i++) {
            means[i] = ui.getDistributionMean(i);
        }
        return means;
    }

    /**
     * Returns the distribution variances for the simulation
     * @return An array of distribution variances
     */
    @Override
    public double[] getDistributionVariances() {
        double[] variances = new double[4];
        for (int i = 0; i < 4; i++) {
            variances[i] = ui.getDistributionVariance(i);
        }
        return variances;
    }

    /**
     * Returns the arrival probability for the simulation.
     * @return The arrival probability.
     */
    @Override
    public double getArrivalProbability() {
        return ui.getArrivalProbability();
    }

    /**
     * Returns the redirect probability for the simulation
     * @return The redirect probability.
     */
    @Override
    public double getRedirectProbability() {
        return ui.getRedirectProbability();
    }

    /**
     * Returns the simulation time.
     * @return The simulation time
     */
    @Override
    public double getAika() {
        return ui.getAika();
    }

    /**
     * Returns the delay (viive) for the simulation
     * @return The delay
     */
    @Override
    public long getViive() {
        return ui.getViive();
    }

    /**
     * Clears the historical simulation data from the database
     */
    @Override
    public void clearHistory() {
        tuloksetDao.truncateAll();
    }

    /**
     * Resets the simulation clock to zero
     */
    // In Kontrolleri.java
    @Override
    public void resetClock() {
        Kello.getInstance().setAika(0);
    }
}
