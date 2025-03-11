package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "palvelunvalinta")
public class PalvelunvalintaTulos implements IServicePointTulos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "jonossa")
    private int jonossa;

    @Column(name = "palveltu")
    private int palveltu;

    @Column(name = "keskimjonoaika")
    private double keskimjonoaika;

    @Column(name = "keskimpalveluaika")
    private double keskimpalveluaika;

    @Column(name = "kokonaisaika")
    private double kokonaisaika;

    @Column(name = "distribuutio")
    private String distribuutio;

    @Column(name = "mean")
    private double mean;

    @Column(name = "var")
    private double var;

    public PalvelunvalintaTulos() {}

    public PalvelunvalintaTulos(int jonossa, int palveltu, double keskimjonoaika, double keskimpalveluaika, double kokonaisaika,
                                String distribuutio, double mean, double var) {
        this.jonossa = jonossa;
        this.palveltu = palveltu;
        this.keskimjonoaika = keskimjonoaika;
        this.keskimpalveluaika = keskimpalveluaika;
        this.kokonaisaika = kokonaisaika;
        this.distribuutio = distribuutio;
        this.mean = mean;
        this.var = var;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJonossa() {
        return jonossa;
    }

    public void setJonossa(int jonossa) {
        this.jonossa = jonossa;
    }

    public int getPalveltu() {
        return palveltu;
    }

    public void setPalveltu(int palveltu) {
        this.palveltu = palveltu;
    }

    public double getKeskimjonoaika() {
        return keskimjonoaika;
    }

    public void setKeskimjonoaika(double keskimjonoaika) {
        this.keskimjonoaika = keskimjonoaika;
    }

    public double getKeskimpalveluaika() {
        return keskimpalveluaika;
    }

    public void setKeskimpalveluaika(double keskimpalveluaika) {
        this.keskimpalveluaika = keskimpalveluaika;
    }

    public double getKokonaisaika() {
        return kokonaisaika;
    }

    public void setKokonaisaika(double kokonaisaika) {
        this.kokonaisaika = kokonaisaika;
    }

    public String getDistribuutio() {
        return distribuutio;
    }

    public void setDistribuutio(String distribuutio) {
        this.distribuutio = distribuutio;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getVar() {
        return var;
    }

    public void setVar(double var) {
        this.var = var;
    }
}