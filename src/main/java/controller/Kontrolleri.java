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
    public void updateServedCustomers(int servedCustomers) {
        Platform.runLater(()->ui.paivitaAsiakasMaara(servedCustomers));
    }



    @Override
    public void visualisoiAsiakas() {
        Platform.runLater(new Runnable(){
            public void run(){
                ui.getVisualisointi().uusiAsiakas();
            }
        });
    }

    /*public void visualisoiPalvelupiste() {
        Platform.runLater(new Runnable(){
            public void run(){
                ui.getVisualisointi().uusiPalvelupiste(100, 200);
            }
        });
    }*/
}
