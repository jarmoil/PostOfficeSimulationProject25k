package controller;

import entity.Tulokset;

import java.util.List;

public interface IKontrolleriForV {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void kaynnistaSimulointi();
    public void nopeuta();
    public void hidasta();
    public void jatka();
    public void pysayta();
    // History view methods
    void naytaHistoriaData();
    void paivitaHistoriaYksityiskohdat(Tulokset tulos);
}
