package view;

public interface ISimulaattorinUI {

    // Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
    public double getAika();
    public long getViive();

    //Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa
    public void setLoppuaika(double aika);

    public void setLoppuaikaNuori(double aika);
    public void setLoppuaikaKeski(double aika);
    public void setLoppuaikaVanha(double aika);

    public void paivitaAsiakasMaara(int servedCustomers);

    // Kontrolleri tarvitsee
    public IVisualisointi getVisualisointi();

}
