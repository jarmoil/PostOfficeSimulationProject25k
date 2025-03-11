package entity;

public interface IServicePointTulos {
    int getJonossa();
    int getPalveltu();
    double getKeskimpalveluaika();
    double getKeskimjonoaika();
    double getKokonaisaika();
    String getDistribuutio();
    double getMean();
    double getVar();
}