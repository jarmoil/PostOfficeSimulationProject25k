package controller;

import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import view.ISimulaattorinUI;

public class Kontrolleri implements IKontrolleriForM, IKontrolleriForV{   // UUSI

    private IMoottori moottori;
    private ISimulaattorinUI ui;

    public Kontrolleri(ISimulaattorinUI ui) {
        this.ui = ui;

    }


    // Moottorin ohjausta:

    @Override
    public void kaynnistaSimulointi() {
        moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
        moottori.setSimulointiaika(ui.getAika());
        moottori.setViive(ui.getViive());
        ui.getVisualisointi().tyhjennaNaytto();
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
        }
    }

    @Override
    public void pysayta(){
        if(moottori != null){
            moottori.pysaytaSimulaatio();
        }
    }

    // Simulointitulosten välittämistä käyttöliittymään.
    // Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:

    @Override
    public void naytaLoppuaika(double aika) {
        Platform.runLater(()->ui.setLoppuaika(aika));
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

    // PAKETTIAUTOMAATTI
    @Override
    public void updateQueueLength(int queueLength) {Platform.runLater(()->ui.paivitaJonoPituus(queueLength));}
    @Override
    public void updateServedCustomers(int servedCustomers) {Platform.runLater(()->ui.paivitaPalveltuMaara(servedCustomers));}
    @Override
    public void updateAverageWaitingTime(double averageWaitingTime) {Platform.runLater(()->ui.paivitaKeskimJonoAika(averageWaitingTime));}
    @Override
    public void updateAverageSerciceTime(double averageServiceTime) {Platform.runLater(()->ui.paivitaKeskimPalveluAika(averageServiceTime));}
    @Override
    public void updateTotalTime(double totalTime) {Platform.runLater(()->ui.paivitaKokonaisAika(totalTime));}

    // PALVELUNVALINTA
    @Override
    public void PVupdateQueueLength(int queueLength) {Platform.runLater(()->ui.PVpaivitaJonoPituus(queueLength));}
    @Override
    public void PVupdateServedCustomers(int servedCustomers) {Platform.runLater(()->ui.PVpaivitaPalveltuMaara(servedCustomers));}
    @Override
    public void PVupdateAverageWaitingTime(double averageWaitingTime) {Platform.runLater(()->ui.PVpaivitaKeskimJonoAika(averageWaitingTime));}
    @Override
    public void PVupdateAverageSerciceTime(double averageServiceTime) {Platform.runLater(()->ui.PVpaivitaKeskimPalveluAika(averageServiceTime));}
    @Override
    public void PVupdateTotalTime(double totalTime) {Platform.runLater(()->ui.PVpaivitaKokonaisAika(totalTime));}

    // NOUTOLÄHETÄ
    @Override
    public void NTupdateQueueLength(int queueLength) {Platform.runLater(()->ui.NTpaivitaJonoPituus(queueLength));}
    @Override
    public void NTupdateServedCustomers(int servedCustomers) {Platform.runLater(()->ui.NTpaivitaPalveltuMaara(servedCustomers));}
    @Override
    public void NTupdateAverageWaitingTime(double averageWaitingTime) {Platform.runLater(()->ui.NTpaivitaKeskimJonoAika(averageWaitingTime));}
    @Override
    public void NTupdateAverageSerciceTime(double averageServiceTime) {Platform.runLater(()->ui.NTpaivitaKeskimPalveluAika(averageServiceTime));}
    @Override
    public void NTupdateTotalTime(double totalTime) {Platform.runLater(()->ui.NTpaivitaKokonaisAika(totalTime));}

    // ERIKOISTAPAUS
    @Override
    public void ETupdateQueueLength(int queueLength) {Platform.runLater(()->ui.ETpaivitaJonoPituus(queueLength));}
    @Override
    public void ETupdateServedCustomers(int servedCustomers) {Platform.runLater(()->ui.ETpaivitaPalveltuMaara(servedCustomers));}
    @Override
    public void ETupdateAverageWaitingTime(double averageWaitingTime) {Platform.runLater(()->ui.ETpaivitaKeskimJonoAika(averageWaitingTime));}
    @Override
    public void ETupdateAverageSerciceTime(double averageServiceTime) {Platform.runLater(()->ui.ETpaivitaKeskimPalveluAika(averageServiceTime));}
    @Override
    public void ETupdateTotalTime(double totalTime) {Platform.runLater(()->ui.ETpaivitaKokonaisAika(totalTime));}


    // Animaatio hommelit

    @Override
    public void drawCustomer(int id, double x, double y) {
        Platform.runLater(() -> ui.drawCustomer(id, x, y));
    }

    @Override
    public void exitCustomer(int id, double toX, double toY) {
        Platform.runLater(() -> ui.exitCustomer(id, toX, toY));
    }

    @Override
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished) {
        Platform.runLater(() -> ui.moveCustomer(id, toX, toY, onFinished));
    }





    /*public void visualisoiPalvelupiste() {
        Platform.runLater(new Runnable(){
            public void run(){
                ui.getVisualisointi().uusiPalvelupiste(100, 200);
            }
        });
    }*/
}
