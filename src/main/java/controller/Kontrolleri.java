package controller;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import simu.framework.IDao;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import view.ISimulaattorinUI;
import entity.*;

import java.util.List;


public class Kontrolleri implements IKontrolleriForM, IKontrolleriForV{   // UUSI

    private void runOnFXThread(Runnable action) {
        Platform.runLater(action);
    }

    private IMoottori moottori;
    private ISimulaattorinUI ui;
    private IDao tuloksetDao;

    public Kontrolleri(ISimulaattorinUI ui, IDao dao) {
        this.ui = ui;
        this.tuloksetDao = dao;

    }


    // Moottorin ohjausta:

    @Override
    public void kaynnistaSimulointi() {
        moottori = new OmaMoottori(this, this.tuloksetDao,
                 getDistributionTypes(), getDistributionMeans(), getDistributionVariances()); // luodaan uusi moottorisäie jokaista simulointia varten
        moottori.setSimulointiaika(ui.getAika());
        moottori.setViive(ui.getViive());
        ui.getVisualisointi().tyhjennaNaytto();
        ui.getVisualisointi().visualisoiPalvelupisteet();
        ((Thread)moottori).start();
        //((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?
    }

    @Override
    public void hidasta() { // hidastetaan moottorisäiettä
        moottori.setViive((long)(moottori.getViive()*1.10));
    }

    @Override
    public void nopeuta() { // nopeutetaan moottorisäiettä
        moottori.setViive((long)(moottori.getViive()*0.9));
    }

    @Override
    public void jatka(){
        if (moottori != null){
            moottori.jatkaSimulaatio();
            ui.getVisualisointi().resumeAnimation();
        }
    }

    @Override
    public void pysayta(){
        if(moottori != null){
            moottori.pysaytaSimulaatio();
            ui.getVisualisointi().pauseAnimation();
        }
    }
    //public void updateTotalServedCustomers(int totalServedCustomers) {Platform.runLater(()->ui.paivitaAsiakasMaara(totalServedCustomers));}
    @Override
    public void set(){
        if (moottori != null){
            Platform.runLater(()-> {moottori.set();});
        }
    }

    // Simulointitulosten välittämistä käyttöliittymään.
    // Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:

    @Override
    public void naytaLoppuaika(double aika) {
        runOnFXThread(() -> ui.setLoppuaika(aika));
    }
    @Override
    public void naytaLoppuaikaNuori(double aika) {
        Platform.runLater(()->ui.setLoppuaikaNuori(aika));
    }
    @Override
    public void naytaLoppuaikaKeski(double aika) {
        Platform.runLater(()->ui.setLoppuaikaKeski(aika));
    }
    @Override
    public void naytaLoppuaikaVanha(double aika) {
        Platform.runLater(()->ui.setLoppuaikaVanha(aika));
    }
    @Override
    public void updateTotalServedCustomers(int totalServedCustomers) {Platform.runLater(()->ui.paivitaAsiakasMaara(totalServedCustomers));}

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

    @Override
    public void drawCustomer(int id, double x, double y) {
        Platform.runLater(() -> ui.getVisualisointi().drawCustomer(id, x, y));
    }

    @Override
    public void exitCustomer(int id, double toX, double toY) {
        Platform.runLater(() -> ui.getVisualisointi().exitCustomer(id, toX, toY));
    }

    @Override
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished) {
        runOnFXThread(() -> ui.getVisualisointi().moveCustomer(id, toX, toY, onFinished));
    }

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

    @Override
    public Point2D AloitusCoord() {
        return ui.getVisualisointi().AloitusCoord();
    }
    @Override
    public Point2D PACoord() {
        return ui.getVisualisointi().PACoord();
    }
    @Override
    public Point2D PVCoord() {
        return ui.getVisualisointi().PVCoord();
    }
    @Override
    public Point2D NTCoord() {
        return ui.getVisualisointi().NTCoord();
    }

    @Override
    public Point2D ETCoord() {
        return ui.getVisualisointi().ETCoord();
    }
    @Override
    public Point2D ExitCoord() {
        return ui.getVisualisointi().ExitCoord();
    }


    @Override
    public void naytaHistoriaData() {
        List<Tulokset> historyData = tuloksetDao.lataaKaikki();
        Platform.runLater(() -> ui.naytaHistoriaData(historyData));
    }

    @Override
    public void paivitaHistoriaYksityiskohdat(Tulokset tulos) {
        Platform.runLater(() -> ui.paivitaHistoriaYksityiskohdat(tulos));
    }

    // Distribution related methods
    @Override
    public String[] getDistributionTypes() {
        String[] types = new String[4];
        for (int i = 0; i < 4; i++) {
            types[i] = ui.getDistributionType(i);
        }
        return types;
    }

    @Override
    public double[] getDistributionMeans() {
        double[] means = new double[4];
        for (int i = 0; i < 4; i++) {
            means[i] = ui.getDistributionMean(i);
        }
        return means;
    }

    @Override
    public double[] getDistributionVariances() {
        double[] variances = new double[4];
        for (int i = 0; i < 4; i++) {
            variances[i] = ui.getDistributionVariance(i);
        }
        return variances;
    }

    @Override
    public double getArrivalProbability() {
        return ui.getArrivalProbability();
    }

    @Override
    public double getRedirectProbability() {
        return ui.getRedirectProbability();
    }

    @Override
    public double getAika() {
        return ui.getAika();
    }

    @Override
    public long getViive() {
        return ui.getViive();
    }

    @Override
    public void clearHistory() {
        tuloksetDao.truncateAll();
    }
}
