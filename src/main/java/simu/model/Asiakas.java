package simu.model;

import simu.framework.*;

import java.util.Random;

/**
 * The Asiakas class represents a customer in the simulation.
 * It contains information about the customer's arrival and departure times, ID, and age.
 */
// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	private static int age;
	public static int palvellut = 0;

	//Koordinaatit
	private int x;
	private int y;


	/**
	 * Constructs a new Asiakas instance with a unique ID and random age between 18 and 80.
	 * Sets the arrival time to the current simulation time.
	 */
	public Asiakas(){
		id = i++;

		// Random ikä välillä 18 - 80
		Random random = new Random();
		age = random.nextInt(63) + 18; // 18-80

		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " ikä " + age +" saapui klo "+saapumisaika);


	}

	/**
	 * Returns the age of the customer.
	 * @return The age of the customer.
	 */
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Returns the departure time of the customer.
	 * @return The departure time of the customer.
	 */
	public double getPoistumisaika() {
		return poistumisaika;
	}

	/**
	 * Sets the departure time of the customer.
	 * @param poistumisaika The departure time to set.
	 */
	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	/**
	 * Returns the arrival time of the customer.
	 * @return The arrival time of the customer.
	 */
	public double getSaapumisaika() {
		return saapumisaika;
	}

	/**
	 * Sets the arrival time of the customer.
	 * @param saapumisaika The arrival time to set.
	 */
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}


	/**
	 * Returns the ID of the customer.
	 * @return The ID of the customer.
	 */
	public int getId() {
		return id;
	}


	/**
	 * Generates a report for the customer, including arrival time, departure time, and duration of stay.
	 * Updates the total number of served customers and calculates the average processing time.
	 */
	public void raportti(){
		palvellut++;
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui: " +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui: " +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi: " +(poistumisaika-saapumisaika));
		sum += (poistumisaika-saapumisaika);
		double keskiarvo = sum/id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti "+ keskiarvo);


	}

	public int getPalvellut() { return palvellut; }

}
