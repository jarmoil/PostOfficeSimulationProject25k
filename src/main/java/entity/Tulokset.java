package entity;

import jakarta.persistence.*;

/**
 * Entity class representing the overall simulation results.
 * Maps to the "simuloinnit" table in the database using JPA/Hibernate annotations.
 * Contains aggregate results from different service points and simulation parameters.
 */
@Entity
@Table(name = "simuloinnit")
public class Tulokset {

    /**
     * Unique identifier for the simulation results.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Total time of the simulation.
     */
    @Column(name = "kokonaisaika")
    private double kokonaisaika;

    /**
     * Total number of customers served across all service points.
     */
    @Column(name = "palveltu")
    private int palveltu;

    /**
     * Bernoulli probability for customer arrivals.
     */
    @Column(name = "bernoulliArrival")
    private double bernoulliArrival;

    /**
     * Bernoulli probability for customer redirections.
     */
    @Column(name = "bernoulliRedirect")
    private double bernoulliRedirect;

    /**
     * Input time parameter for the simulation.
     */
    @Column(name = "inputAika")
    private double inputAika;

    /**
     * Input delay parameter for the simulation.
     */
    @Column(name = "inputViive")
    private long inputViive;

    /**
     * Results from the pick-up and send service point.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private NoutolahetaTulos noutolahetaTulos;

    /**
     * Results from the service selection point.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private PalvelunvalintaTulos palvelunvalintaTulos;

    /**
     * Results from the parcel locker service point.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private PakettiautomaattiTulos pakettiautomaattiTulos;

    /**
     * Results from the special cases service point.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private ErityistapauksetTulos erityistapauksetTulos;

    /**
     * Results of service times by age groups.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private PalveluaikaIkaTulos palveluaikaIkaTulos;

    /**
     * Default constructor required by JPA.
     */
    public Tulokset() {}

    /**
     * Constructs a new Tulokset with all fields.
     *
     * @param kokonaisaika Total simulation time
     * @param palveltu Total customers served
     * @param bernoulliArrival Arrival probability
     * @param bernoulliRedirect Redirection probability
     * @param inputAika Input time
     * @param inputViive Input delay
     * @param noutolahetaTulos Pick-up and send results
     * @param palvelunvalintaTulos Service selection results
     * @param pakettiautomaattiTulos Parcel locker results
     * @param erityistapauksetTulos Special cases results
     * @param palveluaikaIkaTulos Age-based service time results
     */
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

    /**
     * Gets the unique identifier of the simulation results.
     *
     * @return the ID of these results
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the simulation results.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the total time of the simulation.
     *
     * @return the total simulation time
     */
    public double getKokonaisaika() {
        return kokonaisaika;
    }

    /**
     * Sets the total time of the simulation.
     *
     * @param kokonaisaika the total time to set
     */
    public void setKokonaisaika(double kokonaisaika) {
        this.kokonaisaika = kokonaisaika;
    }

    /**
     * Gets the total number of customers served.
     *
     * @return the total number of customers served
     */
    public int getPalveltu() {
        return palveltu;
    }

    /**
     * Sets the total number of customers served.
     *
     * @param palveltu the number of customers to set
     */
    public void setPalveltu(int palveltu) {
        this.palveltu = palveltu;
    }

    /**
     * Gets the Bernoulli probability for customer arrivals.
     *
     * @return the arrival probability
     */
    public double getBernoulliArrival() {
        return bernoulliArrival;
    }

    /**
     * Sets the Bernoulli probability for customer arrivals.
     *
     * @param bernoulliArrival the arrival probability to set
     */
    public void setBernoulliArrival(double bernoulliArrival) {
        this.bernoulliArrival = bernoulliArrival;
    }

    /**
     * Gets the Bernoulli probability for customer redirections.
     *
     * @return the redirection probability
     */
    public double getBernoulliRedirect() {
        return bernoulliRedirect;
    }

    /**
     * Sets the Bernoulli probability for customer redirections.
     *
     * @param bernoulliRedirect the redirection probability to set
     */
    public void setBernoulliRedirect(double bernoulliRedirect) {
        this.bernoulliRedirect = bernoulliRedirect;
    }

    /**
     * Gets the input time parameter.
     *
     * @return the input time value
     */
    public double getInputAika() {
        return inputAika;
    }

    /**
     * Sets the input time parameter.
     *
     * @param inputAika the input time to set
     */
    public void setInputAika(double inputAika) {
        this.inputAika = inputAika;
    }

    /**
     * Gets the input delay parameter.
     *
     * @return the input delay value
     */
    public double getInputViive() {
        return inputViive;
    }

    /**
     * Sets the input delay parameter.
     *
     * @param inputViive the input delay to set
     */
    public void setInputViive(long inputViive) {
        this.inputViive = inputViive;
    }

    /**
     * Gets the pick-up and send service results.
     *
     * @return the NoutolahetaTulos object
     */
    public NoutolahetaTulos getNoutolahetaTulos() {
        return noutolahetaTulos;
    }

    /**
     * Sets the pick-up and send service results.
     *
     * @param noutolahetaTulos the NoutolahetaTulos object to set
     */
    public void setNoutolahetaTulos(NoutolahetaTulos noutolahetaTulos) {
        this.noutolahetaTulos = noutolahetaTulos;
    }

    /**
     * Gets the service selection results.
     *
     * @return the PalvelunvalintaTulos object
     */
    public PalvelunvalintaTulos getPalvelunvalintaTulos() {
        return palvelunvalintaTulos;
    }

    /**
     * Sets the service selection results.
     *
     * @param palvelunvalintaTulos the PalvelunvalintaTulos object to set
     */
    public void setPalvelunvalintaTulos(PalvelunvalintaTulos palvelunvalintaTulos) {
        this.palvelunvalintaTulos = palvelunvalintaTulos;
    }

    /**
     * Gets the age-based service time results.
     *
     * @return the PalveluaikaIkaTulos object
     */
    public PalveluaikaIkaTulos getPalveluaikaIkaTulos() {
        return palveluaikaIkaTulos;
    }

    /**
     * Sets the age-based service time results.
     *
     * @param palveluaikaIkaTulos the PalveluaikaIkaTulos object to set
     */
    public void setPalveluaikaIkaTulos(PalveluaikaIkaTulos palveluaikaIkaTulos) {
        this.palveluaikaIkaTulos = palveluaikaIkaTulos;
    }

    /**
     * Gets the parcel locker service results.
     *
     * @return the PakettiautomaattiTulos object
     */
    public PakettiautomaattiTulos getPakettiautomaattiTulos() {
        return pakettiautomaattiTulos;
    }

    /**
     * Sets the parcel locker service results.
     *
     * @param pakettiautomaattiTulos the PakettiautomaattiTulos object to set
     */
    public void setPakettiautomaattiTulos(PakettiautomaattiTulos pakettiautomaattiTulos) {
        this.pakettiautomaattiTulos = pakettiautomaattiTulos;
    }

    /**
     * Gets the special cases service results.
     *
     * @return the ErityistapauksetTulos object
     */
    public ErityistapauksetTulos getErityistapauksetTulos() {
        return erityistapauksetTulos;
    }

    /**
     * Sets the special cases service results.
     *
     * @param erityistapauksetTulos the ErityistapauksetTulos object to set
     */
    public void setErityistapauksetTulos(ErityistapauksetTulos erityistapauksetTulos) {
        this.erityistapauksetTulos = erityistapauksetTulos;
    }
}