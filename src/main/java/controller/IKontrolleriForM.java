package controller;

import view.Visualisointi;

public interface IKontrolleriForM {

    // Rajapinta, joka tarjotaan moottorille:

    public void naytaLoppuaika(double aika);
    public void visualisoiAsiakas();
    public void naytaLoppuaikaNuori(double aika);
    public void naytaLoppuaikaKeski(double aika);
    public void naytaLoppuaikaVanha(double aika);
//    public void updateTotalServedCustomers(int TotalServedCustomers);

    public void updateQueueLength(int queueLength);
    public void updateServedCustomers(int servedCustomers);
    public void updateAverageWaitingTime(double averageWaitingTime);
    public void updateAverageSerciceTime(double averageServiceTime);
    public void updateTotalTime(double totalTime);

    public void PVupdateQueueLength(int queueLength);
    public void PVupdateServedCustomers(int servedCustomers);
    public void PVupdateAverageWaitingTime(double averageWaitingTime);
    public void PVupdateAverageSerciceTime(double averageServiceTime);
    public void PVupdateTotalTime(double totalTime);

    public void NTupdateQueueLength(int queueLength);
    public void NTupdateServedCustomers(int servedCustomers);
    public void NTupdateAverageWaitingTime(double averageWaitingTime);
    public void NTupdateAverageSerciceTime(double averageServiceTime);
    public void NTupdateTotalTime(double totalTime);

    public void ETupdateQueueLength(int queueLength);
    public void ETupdateServedCustomers(int servedCustomers);
    public void ETupdateAverageWaitingTime(double averageWaitingTime);
    public void ETupdateAverageSerciceTime(double averageServiceTime);
    public void ETupdateTotalTime(double totalTime);

    //public void visualisoiPalvelupiste();
}
