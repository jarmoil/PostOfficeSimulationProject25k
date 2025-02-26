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
    public void paivitaAsiakasMaara(int totalServedCustomers);

    // PAKETTIAUTOMAATTI
    public void paivitaJonoPituus(int queueLength);
    public void paivitaPalveltuMaara(int servedCustomers);
    public void paivitaKeskimJonoAika(double averageWaitingTime);
    public void paivitaKeskimPalveluAika(double averageServiceTime);
    public void paivitaKokonaisAika(double totalTime);

    // PALVELUPISTE
    public void PVpaivitaJonoPituus(int queueLength);
    public void PVpaivitaPalveltuMaara(int servedCustomers);
    public void PVpaivitaKeskimJonoAika(double averageWaitingTime);
    public void PVpaivitaKeskimPalveluAika(double averageServiceTime);
    public void PVpaivitaKokonaisAika(double totalTime);

    // NOUTOLÄHETÄ
    public void NTpaivitaJonoPituus(int queueLength);
    public void NTpaivitaPalveltuMaara(int servedCustomers);
    public void NTpaivitaKeskimJonoAika(double averageWaitingTime);
    public void NTpaivitaKeskimPalveluAika(double averageServiceTime);
    public void NTpaivitaKokonaisAika(double totalTime);

    // ERIKOISTAPAUS
    public void ETpaivitaJonoPituus(int queueLength);
    public void ETpaivitaPalveltuMaara(int servedCustomers);
    public void ETpaivitaKeskimJonoAika(double averageWaitingTime);
    public void ETpaivitaKeskimPalveluAika(double averageServiceTime);
    public void ETpaivitaKokonaisAika(double totalTime);


    // Kontrolleri tarvitsee
    public IVisualisointi getVisualisointi();

    // Animaatio hommelit
    public void drawCustomer(int id, double x, double y);
    public void exitCustomer(int id, double toX, double toY);
    public void moveCustomer(int id, double toX, double toY, Runnable onFinished);
}
