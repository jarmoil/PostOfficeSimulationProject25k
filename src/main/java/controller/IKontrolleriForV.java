package controller;

import entity.Tulokset;
import javafx.scene.control.TableView;

import java.util.List;

public interface IKontrolleriForV {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void kaynnistaSimulointi();
    public void nopeuta();
    public void hidasta();
    public void jatka();
    public void pysayta();
    void set();
    void stopSim();
    void poistaHistoria(Tulokset tulos);

    public void naytaHistoriaData();
    public void paivitaHistoriaYksityiskohdat(Tulokset tulos);
    void clearHistory();
    // In IKontrolleriForV.java
    void resetClock();

    double getAika();
    long getViive();
}
