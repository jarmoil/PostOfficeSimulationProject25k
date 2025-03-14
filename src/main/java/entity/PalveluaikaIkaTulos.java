package entity;

import jakarta.persistence.*;

/**
 * Entity class representing service time results based on age groups.
 * Maps to the "palveluaika_ika" table in the database using JPA/Hibernate annotations.
 */
@Entity
@Table(name = "palveluaika_ika")
public class PalveluaikaIkaTulos {

    /**
     * Unique identifier for the service time result.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Service time for the low age group.
     */
    @Column(name = "lowAge")
    private double lowAge;

    /**
     * Service time for the middle age group.
     */
    @Column(name = "middleAge")
    private double middleAge;

    /**
     * Service time for the high age group.
     */
    @Column(name = "highAge")
    private double highAge;

    /**
     * Default constructor required by JPA.
     */
    public PalveluaikaIkaTulos() {}

    /**
     * Constructs a new PalveluaikaIkaTulos with service times for each age group.
     *
     * @param lowAge Service time for low age group
     * @param middleAge Service time for middle age group
     * @param highAge Service time for high age group
     */
    public PalveluaikaIkaTulos(double lowAge, double middleAge, double highAge) {
        this.lowAge = lowAge;
        this.middleAge = middleAge;
        this.highAge = highAge;
    }

    /**
     * Gets the unique identifier.
     *
     * @return the ID of this result
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the service time for low age group.
     *
     * @return the service time for low age group
     */
    public double getLowAge() {
        return lowAge;
    }

    /**
     * Sets the service time for low age group.
     *
     * @param lowAge the service time to set
     */
    public void setLowAge(double lowAge) {
        this.lowAge = lowAge;
    }

    /**
     * Gets the service time for middle age group.
     *
     * @return the service time for middle age group
     */
    public double getMiddleAge() {
        return middleAge;
    }

    /**
     * Sets the service time for middle age group.
     *
     * @param middleAge the service time to set
     */
    public void setMiddleAge(double middleAge) {
        this.middleAge = middleAge;
    }

    /**
     * Gets the service time for high age group.
     *
     * @return the service time for high age group
     */
    public double getHighAge() {
        return highAge;
    }

    /**
     * Sets the service time for high age group.
     *
     * @param highAge the service time to set
     */
    public void setHighAge(double highAge) {
        this.highAge = highAge;
    }
}