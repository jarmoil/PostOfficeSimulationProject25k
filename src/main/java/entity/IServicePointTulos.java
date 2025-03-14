package entity;

/**
 * Interface defining standard methods for accessing service point simulation results.
 * This interface provides a contract for classes that represent results from different
 * service points in the simulation system.
 */
public interface IServicePointTulos {

    /**
     * Gets the number of customers in queue.
     *
     * @return the number of customers waiting in queue
     */
    int getJonossa();

    /**
     * Gets the number of customers served.
     *
     * @return the number of customers that have been served
     */
    int getPalveltu();

    /**
     * Gets the average service time.
     *
     * @return the average time spent serving customers
     */
    double getKeskimpalveluaika();

    /**
     * Gets the average queuing time.
     *
     * @return the average time customers spent in queue
     */
    double getKeskimjonoaika();

    /**
     * Gets the total time spent in the service point.
     *
     * @return the total time including both service and queue time
     */
    double getKokonaisaika();

    /**
     * Gets the distribution type used for service time calculation.
     *
     * @return the type of distribution used
     */
    String getDistribuutio();

    /**
     * Gets the mean value for the distribution.
     *
     * @return the mean value used in calculations
     */
    double getMean();

    /**
     * Gets the variance value for the distribution.
     *
     * @return the variance value used in calculations
     */
    double getVar();
}