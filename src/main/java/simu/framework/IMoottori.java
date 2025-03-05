package simu.framework;

public interface IMoottori { // UUSI

    // Kontrolleri käyttää tätä rajapintaa

    public void setSimulointiaika(double aika);
    public void setViive(long aika);
    public long getViive();

    void jatkaSimulaatio();
    void pysaytaSimulaatio();
    void set();
    void history();
}
