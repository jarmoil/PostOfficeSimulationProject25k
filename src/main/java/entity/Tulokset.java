package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "simuloinnit")
public class Tulokset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "kokonaisaika")
    private double kokonaisaika;

    @Column(name = "palveltu")
    private int palveltu;

    @Column(name = "bernoulliArrival")
    private double bernoulliArrival;

    @Column(name = "bernoulliRedirect")
    private double bernoulliRedirect;

    @Column(name = "inputAika")
    private double inputAika;

    @Column(name = "inputViive")
    private long inputViive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private NoutolahetaTulos noutolahetaTulos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private PalvelunvalintaTulos palvelunvalintaTulos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private PakettiautomaattiTulos pakettiautomaattiTulos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private ErityistapauksetTulos erityistapauksetTulos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private PalveluaikaIkaTulos palveluaikaIkaTulos;

    public Tulokset() {}

    public Tulokset(double kokonaisaika, int palveltu, double bernoulliArrival, double bernoulliRedirect, double inputAika, long inputViive, NoutolahetaTulos noutolahetaTulos,
                    PalvelunvalintaTulos palvelunvalintaTulos, PakettiautomaattiTulos pakettiautomaattiTulos,
                    ErityistapauksetTulos erityistapauksetTulos, PalveluaikaIkaTulos palveluaikaIkaTulos) {
        this.kokonaisaika = kokonaisaika;
        this.palveltu = palveltu;
        this.bernoulliArrival = bernoulliArrival;
        this.bernoulliRedirect = bernoulliRedirect;
        this.inputAika = inputAika;
        this.inputViive = inputViive;
        this.noutolahetaTulos = noutolahetaTulos;
        this.palvelunvalintaTulos = palvelunvalintaTulos;
        this.pakettiautomaattiTulos = pakettiautomaattiTulos;
        this.erityistapauksetTulos = erityistapauksetTulos;
        this.palveluaikaIkaTulos = palveluaikaIkaTulos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getKokonaisaika() {
        return kokonaisaika;
    }

    public void setKokonaisaika(double kokonaisaika) {
        this.kokonaisaika = kokonaisaika;
    }

    public int getPalveltu() {
        return palveltu;
    }

    public void setPalveltu(int palveltu) {
        this.palveltu = palveltu;
    }

    public double getBernoulliArrival() {
        return bernoulliArrival;
    }

    public void setBernoulliArrival(double bernoulliArrival) {
        this.bernoulliArrival = bernoulliArrival;
    }

    public double getBernoulliRedirect() {
        return bernoulliRedirect;
    }

    public void setBernoulliRedirect(double bernoulliRedirect) {
        this.bernoulliRedirect = bernoulliRedirect;
    }

    public double getInputAika() {
        return inputAika;
    }

    public void setInputAika(double inputAika) {
        this.inputAika = inputAika;
    }

    public double getInputViive() {
        return inputViive;
    }

    public void setInputViive(long inputViive) {
        this.inputViive = inputViive;
    }

    public NoutolahetaTulos getNoutolahetaTulos() {
        return noutolahetaTulos;
    }

    public void setNoutolahetaTulos(NoutolahetaTulos noutolahetaTulos) {
        this.noutolahetaTulos = noutolahetaTulos;
    }

    public PalvelunvalintaTulos getPalvelunvalintaTulos() {
        return palvelunvalintaTulos;
    }

    public void setPalvelunvalintaTulos(PalvelunvalintaTulos palvelunvalintaTulos) {
        this.palvelunvalintaTulos = palvelunvalintaTulos;
    }

    public PalveluaikaIkaTulos getPalveluaikaIkaTulos() {
        return palveluaikaIkaTulos;
    }

    public void setPalveluaikaIkaTulos(PalveluaikaIkaTulos palveluaikaIkaTulos) {
        this.palveluaikaIkaTulos = palveluaikaIkaTulos;
    }

    public PakettiautomaattiTulos getPakettiautomaattiTulos() {
        return pakettiautomaattiTulos;
    }

    public void setPakettiautomaattiTulos(PakettiautomaattiTulos pakettiautomaattiTulos) {
        this.pakettiautomaattiTulos = pakettiautomaattiTulos;
    }

    public ErityistapauksetTulos getErityistapauksetTulos() {
        return erityistapauksetTulos;
    }

    public void setErityistapauksetTulos(ErityistapauksetTulos erityistapauksetTulos) {
        this.erityistapauksetTulos = erityistapauksetTulos;
    }
}