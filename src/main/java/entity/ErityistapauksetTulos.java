package entity;

import jakarta.persistence.*;

/**
 * Entity class representing special cases (Erityistapaukset) results in the simulation.
 * Maps to the "erityistapaukset" table in the database using JPA/Hibernate annotations.
 * Implements IServicePointTulos interface for standardized service point result handling.
 */
@Entity
@Table(name = "erityistapaukset")
public class ErityistapauksetTulos implements IServicePointTulos {
    /**
     * Unique identifier for the special case result.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Number of customers in queue.
     */
    @Column(name = "jonossa")
    private int jonossa;

    /**
     * Number of customers served.
     */
    @Column(name = "palveltu")
    private int palveltu;

    /**
     * Average queuing time.
     */
    @Column(name = "keskimjonoaika")
    private double keskimjonoaika;

    /**
     * Average service time.
     */
    @Column(name = "keskimpalveluaika")
    private double keskimpalveluaika;

    /**
     * Total time spent in the service point.
     */
    @Column(name = "kokonaisaika")
    private double kokonaisaika;

    /**
     * Distribution type used for service time calculation.
     */
    @Column(name = "distribuutio")
    private String distribuutio;

    /**
     * Mean value for the distribution.
     */
    @Column(name = "mean")
    private double mean;

    /**
     * Variance value for the distribution.
     */
    @Column(name = "var")
    private double var;

    /**
     * Default constructor required by JPA.
     */
    public ErityistapauksetTulos() {}

    /**
     * Constructs a new ErityistapauksetTulos with all fields.
     *
     * @param jonossa Number of customers in queue
     * @param palveltu Number of customers served
     * @param keskimjonoaika Average queuing time
     * @param keskimpalveluaika Average service time
     * @param kokonaisaika Total time
     * @param distribuutio Distribution type
     * @param mean Mean value
     * @param var Variance value
     */
    public ErityistapauksetTulos(int jonossa, int palveltu, double keskimjonoaika, double keskimpalveluaika, double kokonaisaika,
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

    /**
     * Gets the unique identifier of the special case result.
     *
     * @return the ID of this result
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the special case result.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the number of customers in queue.
     *
     * @return the number of customers in queue
     */
    public int getJonossa() {
        return jonossa;
    }

    /**
     * Sets the number of customers in queue.
     *
     * @param jonossa the number of customers to set
     */
    public void setJonossa(int jonossa) {
        this.jonossa = jonossa;
    }

    /**
     * Gets the number of customers served.
     *
     * @return the number of customers served
     */
    public int getPalveltu() {
        return palveltu;
    }

    /**
     * Sets the number of customers served.
     *
     * @param palveltu the number of customers served to set
     */
    public void setPalveltu(int palveltu) {
        this.palveltu = palveltu;
    }

    /**
     * Gets the average queuing time.
     *
     * @return the average queuing time
     */
    public double getKeskimjonoaika() {
        return keskimjonoaika;
    }

    /**
     * Sets the average queuing time.
     *
     * @param keskimjonoaika the average queuing time to set
     */
    public void setKeskimjonoaika(double keskimjonoaika) {
        this.keskimjonoaika = keskimjonoaika;
    }

    /**
     * Gets the average service time.
     *
     * @return the average service time
     */
    public double getKeskimpalveluaika() {
        return keskimpalveluaika;
    }

    /**
     * Sets the average service time.
     *
     * @param keskimpalveluaika the average service time to set
     */
    public void setKeskimpalveluaika(double keskimpalveluaika) {
        this.keskimpalveluaika = keskimpalveluaika;
    }

    /**
     * Gets the total time spent in the service point.
     *
     * @return the total time spent
     */
    public double getKokonaisaika() {
        return kokonaisaika;
    }

    /**
     * Sets the total time spent in the service point.
     *
     * @param kokonaisaika the total time to set
     */
    public void setKokonaisaika(double kokonaisaika) {
        this.kokonaisaika = kokonaisaika;
    }

    /**
     * Gets the distribution type used for service time calculation.
     *
     * @return the distribution type
     */
    public String getDistribuutio() {
        return distribuutio;
    }

    /**
     * Sets the distribution type used for service time calculation.
     *
     * @param distribuutio the distribution type to set
     */
    public void setDistribuutio(String distribuutio) {
        this.distribuutio = distribuutio;
    }

    /**
     * Gets the mean value for the distribution.
     *
     * @return the mean value
     */
    public double getMean() {
        return mean;
    }

    /**
     * Sets the mean value for the distribution.
     *
     * @param mean the mean value to set
     */
    public void setMean(double mean) {
        this.mean = mean;
    }

    /**
     * Gets the variance value for the distribution.
     *
     * @return the variance value
     */
    public double getVar() {
        return var;
    }

    /**
     * Sets the variance value for the distribution.
     *
     * @param var the variance value to set
     */
    public void setVar(double var) {
        this.var = var;
    }

}