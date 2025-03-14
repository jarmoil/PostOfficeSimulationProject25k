package view;

import entity.Tulokset;

import java.util.List;

/**
 * Interface for the simulator's user interface.
 */
public interface ISimulaattorinUI {

    /**
     * Gets the simulation time.
     *
     * @return the simulation time
     */
    public double getAika();

    /**
     * Gets the delay time.
     *
     * @return the delay time
     */
    public long getViive();

    /**
     * Sets the total simulation time.
     *
     * @param aika the total simulation time
     */
    public void setLoppuaika(double aika);

    /**
     * Sets the total simulation time for young customers.
     *
     * @param aika the total simulation time for young customers
     */
    public void setLoppuaikaNuori(double aika);

    /**
     * Sets the total simulation time for middle-aged customers.
     *
     * @param aika the total simulation time for middle-aged customers
     */
    public void setLoppuaikaKeski(double aika);

    /**
     * Sets the total simulation time for old customers.
     *
     * @param aika the total simulation time for old customers
     */
    public void setLoppuaikaVanha(double aika);

    /**
     * Updates the total number of served customers.
     *
     * @param totalServedCustomers the total number of served customers
     */
    public void paivitaAsiakasMaara(int totalServedCustomers);

    /**
     * Updates the queue length for the package machine.
     *
     * @param queueLength the queue length
     */
    public void paivitaJonoPituus(int queueLength);

    /**
     * Updates the number of served customers for the package machine.
     *
     * @param servedCustomers the number of served customers
     */
    public void paivitaPalveltuMaara(int servedCustomers);

    /**
     * Updates the average waiting time for the package machine.
     *
     * @param averageWaitingTime the average waiting time
     */
    public void paivitaKeskimJonoAika(double averageWaitingTime);

    /**
     * Updates the average service time for the package machine.
     *
     * @param averageServiceTime the average service time
     */
    public void paivitaKeskimPalveluAika(double averageServiceTime);

    /**
     * Updates the total time for the package machine.
     *
     * @param totalTime the total time
     */
    public void paivitaKokonaisAika(double totalTime);

    /**
     * Updates the queue length for the service selection.
     *
     * @param queueLength the queue length
     */
    public void PVpaivitaJonoPituus(int queueLength);

    /**
     * Updates the number of served customers for the service selection.
     *
     * @param servedCustomers the number of served customers
     */
    public void PVpaivitaPalveltuMaara(int servedCustomers);

    /**
     * Updates the average waiting time for the service selection.
     *
     * @param averageWaitingTime the average waiting time
     */
    public void PVpaivitaKeskimJonoAika(double averageWaitingTime);

    /**
     * Updates the average service time for the service selection.
     *
     * @param averageServiceTime the average service time
     */
    public void PVpaivitaKeskimPalveluAika(double averageServiceTime);

    /**
     * Updates the total time for the service selection.
     *
     * @param totalTime the total time
     */
    public void PVpaivitaKokonaisAika(double totalTime);

    /**
     * Updates the queue length for the pickup/send service.
     *
     * @param queueLength the queue length
     */
    public void NTpaivitaJonoPituus(int queueLength);

    /**
     * Updates the number of served customers for the pickup/send service.
     *
     * @param servedCustomers the number of served customers
     */
    public void NTpaivitaPalveltuMaara(int servedCustomers);

    /**
     * Updates the average waiting time for the pickup/send service.
     *
     * @param averageWaitingTime the average waiting time
     */
    public void NTpaivitaKeskimJonoAika(double averageWaitingTime);

    /**
     * Updates the average service time for the pickup/send service.
     *
     * @param averageServiceTime the average service time
     */
    public void NTpaivitaKeskimPalveluAika(double averageServiceTime);

    /**
     * Updates the total time for the pickup/send service.
     *
     * @param totalTime the total time
     */
    public void NTpaivitaKokonaisAika(double totalTime);

    /**
     * Updates the queue length for the special cases service.
     *
     * @param queueLength the queue length
     */
    public void ETpaivitaJonoPituus(int queueLength);

    /**
     * Updates the number of served customers for the special cases service.
     *
     * @param servedCustomers the number of served customers
     */
    public void ETpaivitaPalveltuMaara(int servedCustomers);

    /**
     * Updates the average waiting time for the special cases service.
     *
     * @param averageWaitingTime the average waiting time
     */
    public void ETpaivitaKeskimJonoAika(double averageWaitingTime);

    /**
     * Updates the average service time for the special cases service.
     *
     * @param averageServiceTime the average service time
     */
    public void ETpaivitaKeskimPalveluAika(double averageServiceTime);

    /**
     * Updates the total time for the special cases service.
     *
     * @param totalTime the total time
     */
    public void ETpaivitaKokonaisAika(double totalTime);

    /**
     * Displays the history data in a table.
     *
     * @param data the list of Tulokset to display
     */
    void naytaHistoriaData(List<Tulokset> data);

    /**
     * Updates the history details view with the specified Tulokset.
     *
     * @param tulos the Tulokset to display in the details view
     */
    void paivitaHistoriaYksityiskohdat(Tulokset tulos);

    /**
     * Gets the distribution type for the specified service point.
     *
     * @param servicePoint the index of the service point
     * @return the distribution type
     */
    String getDistributionType(int servicePoint);

    /**
     * Gets the distribution mean for the specified service point.
     *
     * @param servicePoint the index of the service point
     * @return the distribution mean
     */
    double getDistributionMean(int servicePoint);

    /**
     * Gets the distribution variance for the specified service point.
     *
     * @param servicePoint the index of the service point
     * @return the distribution variance
     */
    double getDistributionVariance(int servicePoint);

    /**
     * Gets the arrival probability.
     *
     * @return the arrival probability
     */
    double getArrivalProbability();

    /**
     * Gets the redirect probability.
     *
     * @return the redirect probability
     */
    double getRedirectProbability();

    /**
     * Disables the input fields for simulation time and delay.
     */
    void disableInputFields();

    /**
     * Enables the input fields for simulation time and delay.
     */
    void enableInputFields();

    /**
     * Gets the visualization interface.
     *
     * @return the visualization interface
     */
    public IVisualisointi getVisualisointi();
}