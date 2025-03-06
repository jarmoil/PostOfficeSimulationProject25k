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

    public PalvelunvalintaTulos() {}

    public PalvelunvalintaTulos(int jonossa, int palveltu, double keskimjonoaika, double keskimpalveluaika, double kokonaisaika) {
        this.jonossa = jonossa;
        this.palveltu = palveltu;
        this.keskimjonoaika = keskimjonoaika;
        this.keskimpalveluaika = keskimpalveluaika;
        this.kokonaisaika = kokonaisaika;
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
}