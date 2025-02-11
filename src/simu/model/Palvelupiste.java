package simu.model;

import simu.framework.*;
import java.util.LinkedList;
import java.util.Random;

import eduni.distributions.ContinuousGenerator;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus
	private final ContinuousGenerator generator;
	private final Tapahtumalista tapahtumalista;
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	
	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	
	private boolean varattu = false;

	// Suorituskyky mittarit
	private int servedCustomers = 0;
	private double overallWaitingTime = 0;
	private double overallServiceTime = 0;


	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
				
	}

	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		a.setSaapumisaika(Kello.getInstance().getAika());
		jono.add(a);
		
	}

	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		Asiakas poistettava = jono.poll();
		if (poistettava != null) {
			poistettava.setPoistumisaika(Kello.getInstance().getAika());
			servedCustomers++;
		}
		return poistettava;
	}

	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		if(jono.isEmpty()) return;


		Asiakas asiakas = jono.peek();
		double palveluaika = generator.sample() * checkAgeMultiplier(asiakas);
		double randomDelay = new Random().nextDouble() * 10;
		overallWaitingTime += Kello.getInstance().getAika() - asiakas.getSaapumisaika() + randomDelay;
		overallServiceTime += palveluaika;
		
		varattu = true;
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika+randomDelay));

		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());

	}

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

	public boolean onVarattu(){
		return varattu;
	}

	public boolean onJonossa(){
		return jono.size() != 0;
	}

	// Raportointi metodit

	public double getAverageWaitingTime() {
		return servedCustomers > 0 ? overallWaitingTime / servedCustomers : 0;
	}

	public double getAverageServiceTime() {
		return servedCustomers > 0 ? overallServiceTime / servedCustomers : 0;
	}

	public int getServedCustomers() {
		return servedCustomers;
	}

	public TapahtumanTyyppi getType() {
		return skeduloitavanTapahtumanTyyppi;
	}

	public int getQueueLength() {
		return jono.size();
	}

}
