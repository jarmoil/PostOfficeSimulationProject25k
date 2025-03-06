package controller;

import entity.Tulokset;

public interface IKontrolleriForV {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void kaynnistaSimulointi();
    public void nopeuta();
    public void hidasta();
    public void jatka();
    public void pysayta();
    void set();
    public void naytaHistoriaData();
    public void paivitaHistoriaYksityiskohdat(Tulokset tulos);
}
