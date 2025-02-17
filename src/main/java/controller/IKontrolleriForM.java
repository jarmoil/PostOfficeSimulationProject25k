package controller;

import view.Visualisointi;

public interface IKontrolleriForM {

    // Rajapinta, joka tarjotaan moottorille:

    public void naytaLoppuaika(double aika);
    public void visualisoiAsiakas();
    public void naytaLoppuaikaNuori(double aika);
    public void naytaLoppuaikaKeski(double aika);
    public void naytaLoppuaikaVanha(double aika);


    //public void visualisoiPalvelupiste();
}
