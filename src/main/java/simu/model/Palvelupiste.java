package simu.model;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.DiscreteGenerator;
import eduni.distributions.Generator;
import simu.framework.*;
import java.util.LinkedList;
import java.util.Random;


/**
 * The Palvelupiste class represents a service point in the simulation.
 * It manages the queue of customers, service times, and various performance metrics.
 */
// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {
	private ContinuousGenerator continuousGenerator;
	private DiscreteGenerator discreteGenerator;
	private boolean isDiscrete;

	private String distributionType;
	private double distributionMean;
	private double distributionVar;

	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus
	private final Tapahtumalista tapahtumalista;
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	
	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	
	private boolean varattu = false;

	// Suorituskyky mittarit
	private int servedCustomers = 0;
	private double overallWaitingTime = 0;
	private double overallServiceTime = 0;
	private double totalTime = 0;


	/**
	 * Constructs a new Palvelupiste instance with the specified parameters.
	 * @param generator The distribution generator for service times.
	 * @param tapahtumalista The event list for scheduling events.
	 * @param tyyppi The type of event to be scheduled.
	 * @param distributionType The type of distribution used for service times.
	 * @param mean The mean of the distribution.
	 * @param var The variance of the distribution.
	 */
	public Palvelupiste(Generator generator, Tapahtumalista tapahtumalista,
						TapahtumanTyyppi tyyppi, String distributionType,
						double mean, double var) {
		this.tapahtumalista = tapahtumalista;
		if (generator instanceof ContinuousGenerator) {
			this.continuousGenerator = (ContinuousGenerator) generator;
			this.isDiscrete = false;
		} else {
			this.discreteGenerator = (DiscreteGenerator) generator;
			this.isDiscrete = true;
		}
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.distributionType = distributionType;
		this.distributionMean = mean;
		this.distributionVar = var;
	}

	/**
	 * Adds a customer to the queue and sets their arrival time.
	 * @param a The customer to be added to the queue.
	 */
	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		a.setSaapumisaika(Kello.getInstance().getAika());
		jono.add(a);
		
	}

	/**
	 * Removes the customer from the queue and sets their departure time.
	 * @return The customer that was removed from the queue.
	 */
	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		Asiakas poistettava = jono.poll();
		if (poistettava != null) {
			poistettava.setPoistumisaika(Kello.getInstance().getAika());
			servedCustomers++;
		}
		return poistettava;
	}

	/**
	 * Starts the service for the next customer in the queue.
	 * Calculates the service time based on the customer's age and the distribution generator.
	 */
	public void aloitaPalvelu() {
		if(jono.isEmpty()) return;

		Asiakas asiakas = jono.peek();
		double palveluaika;

		if (isDiscrete) {
			palveluaika = discreteGenerator.sample() * checkAgeMultiplier(asiakas);
		} else {
			palveluaika = continuousGenerator.sample() * checkAgeMultiplier(asiakas);
		}

		double randomDelay = new Random().nextDouble() * 10;
		overallWaitingTime += Kello.getInstance().getAika() - asiakas.getSaapumisaika() + randomDelay;
		overallServiceTime += palveluaika;

		varattu = true;
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,
				Kello.getInstance().getAika() + palveluaika + randomDelay));

		totalTime += palveluaika + randomDelay;
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
	}

	/**
	 * Returns the age multiplier for the customer's service time based on their age group.
	 * @param a The customer whose age multiplier is to be determined.
	 * @return The age multiplier for the customer's service time.
	 */
	// ikäluokkien kertoimet palveluajalle
	public double checkAgeMultiplier(Asiakas a){
		if (a.getAge() <= 40) {
			return 1.0;
		} else if (a.getAge() <= 60) {
			return 1.3;
		}else{
			return 1.6;
		}
	}

	/**
	 * Returns whether the service point is currently occupied.
	 * @return True if the service point is occupied, false otherwise.
	 */
	public boolean onVarattu(){
		return varattu;
	}

	/**
	 * Returns whether there are customers in the queue.
	 * @return True if there are customers in the queue, false otherwise.
	 */
	public boolean onJonossa(){
		return jono.size() != 0;
	}

	// Raportointi metodit

	/**
	 * Returns the type of distribution used for service times.
	 * @return The type of distribution.
	 */
	public String getDistributionType() {
		return distributionType;
	}

	/**
	 * Returns the mean of the distribution used for service times.
	 * @return The mean of the distribution.
	 */
	public double getDistributionMean() {
		return distributionMean;
	}

	/**
	 * Returns the variance of the distribution used for service times.
	 * @return The variance of the distribution.
	 */
	public double getDistributionVar() {
		return distributionVar;
	}

	/**
	 * Returns the average waiting time for customers at the service point.
	 * @return The average waiting time.
	 */
	public double getAverageWaitingTime() {
		return servedCustomers > 0 ? overallWaitingTime / servedCustomers : 0;
	}

	/**
	 * Returns the average service time for customers at the service point.
	 * @return The average service time.
	 */
	public double getAverageServiceTime() {
		return servedCustomers > 0 ? overallServiceTime / servedCustomers : 0;
	}

	/**
	 * Returns the total number of customers served at the service point.
	 * @return The total number of served customers.
	 */
	public int getServedCustomers() {
		return servedCustomers;
	}

	/**
	 * Returns the type of event to be scheduled for the service point.
	 * @return The type of event.
	 */
	public TapahtumanTyyppi getType() {
		return skeduloitavanTapahtumanTyyppi;
	}

	/**
	 * Returns the number of customers in the queue.
	 * @return The number of customers in the queue.
	 */
	public int getQueueLength() {
		return jono.size();
	}

	/**
	 * Returns the total time spent on service at the service point.
	 * @return The total service time.
	 */
	public double getTotalTime() { return totalTime;}

}
