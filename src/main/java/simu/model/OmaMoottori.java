package simu.model;

import controller.IKontrolleriForM;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import java.util.Random;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;
	private Palvelupiste[] palvelupisteet;
	private Random random;


	// iän seuranta ja niiden jaottelu
	private double totalServiceTime18to40 = 0;
	private int count18to40 = 0;
	private double totalServiceTime41to60 = 0;
	private int count41to60 = 0;
	private double totalServiceTime60Plus = 0;
	private int count60Plus = 0;

	// Probabilities
	private static final double PROBABILITY_18TO40 = 0.7;
	private static final double PROBABILITY_41TO60 = 0.3;
	private static final double PROBABILITY_60PLUS = 0.1;

	public OmaMoottori(IKontrolleriForM kontrolleri) {
		super (kontrolleri);

		palvelupisteet = new Palvelupiste[4];

		// Initialize service points
		palvelupisteet[0] = new Palvelupiste(new Normal(4, 3), tapahtumalista, TapahtumanTyyppi.PAKETTIAUTOMAATTI);
		palvelupisteet[1] = new Palvelupiste(new Normal(2, 2), tapahtumalista, TapahtumanTyyppi.PALVELUNVALINTA);
		palvelupisteet[2] = new Palvelupiste(new Normal(5, 4), tapahtumalista, TapahtumanTyyppi.NOUTOLAHETA);
		palvelupisteet[3] = new Palvelupiste(new Normal(7, 4), tapahtumalista, TapahtumanTyyppi.ERITYISTAPAUKSET);

		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.ARR1);
		random = new Random();

	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // First arrival in the system

		// Johonkin tää roska
		//kontrolleri.visualisoiPalvelupiste();
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {
		Asiakas a;
		switch ((TapahtumanTyyppi) t.getTyyppi()) {
			case ARR1:
				a = new Asiakas();
				handleArrival(a);
				saapumisprosessi.generoiSeuraava();
				kontrolleri.visualisoiAsiakas(); // UUSI
				break;

			case PAKETTIAUTOMAATTI:
				a = palvelupisteet[0].otaJonosta();
				processCustomer(a, palvelupisteet[0]);
				break;

			case PALVELUNVALINTA:
				a = palvelupisteet[1].otaJonosta();
				redirectToService(a);
				break;

			case NOUTOLAHETA:
				a = palvelupisteet[2].otaJonosta();
				processCustomer(a, palvelupisteet[2]);
				break;

			case ERITYISTAPAUKSET:
				a = palvelupisteet[3].otaJonosta();
				processCustomer(a, palvelupisteet[3]);
				break;
		}
	}

	private void handleArrival(Asiakas a) {
		double probability = getArrivalProbability(a);
		if (random.nextDouble() < probability) {
			palvelupisteet[0].lisaaJonoon(a); // Pakettiautomaatti
			printArrival(a, "pakettiautomaatille");
		} else {
			palvelupisteet[1].lisaaJonoon(a); // Palvelun valinta
			printArrival(a, "palvelunvalintaan");
		}
	}

	private double getArrivalProbability(Asiakas a) {
		if (a.getAge() <= 40) return PROBABILITY_18TO40;
		if (a.getAge() <= 60) return PROBABILITY_41TO60;
		return PROBABILITY_60PLUS;
	}

	private void printArrival(Asiakas a, String servicePoint) {
		System.out.println("Asiakas " + a.getId() + " saapui " + servicePoint + ".");
	}

	private void redirectToService(Asiakas a) {
		if (random.nextBoolean()) {
			palvelupisteet[2].lisaaJonoon(a); // Nouto/Lähetä
			System.out.println("Asiakas " + a.getId() + " ohjattu nouto/lähetä palvelutiskille.");
		} else {
			palvelupisteet[3].lisaaJonoon(a); // Erityistapaus
			System.out.println("Asiakas " + a.getId() + " ohjattu erityistapaus palvelutiskille.");
		}
	}

	private void processCustomer(Asiakas a, Palvelupiste p) {
		a.setPoistumisaika(Kello.getInstance().getAika());
		updateServiceTimeStats(a);
		System.out.println("Asiakas " + a.getId() + " valmis " + p.getType() + " palvelutiskiltä");
		a.raportti();
	}

	// Update service time statistics based on age group
	private void updateServiceTimeStats(Asiakas a) {
		double serviceTime = a.getPoistumisaika() - a.getSaapumisaika();
		if (a.getAge() <= 40) {
			totalServiceTime18to40 += serviceTime;
			count18to40++;
		} else if (a.getAge() <= 60) {
			totalServiceTime41to60 += serviceTime;
			count41to60++;
		} else {
			totalServiceTime60Plus += serviceTime;
			count60Plus++;
		}
	}

	@Override
	protected void yritaCTapahtumat() {
		for (Palvelupiste p : palvelupisteet) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			}
		}
	}

	@Override
	protected void tulokset() {
		System.out.println("\nSimulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Palvellut asiakkaat (Poistumiseen asti): " + Asiakas.palvellut);
		// Print service point statistics
		for (Palvelupiste p : palvelupisteet) {
			printServicePointStats(p);
		}

		// Print average service time by age group
		printAverageServiceTimeByAge();

		// UUTTA graafista
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		kontrolleri.naytaLoppuaikaNuori(count18to40 > 0 ? totalServiceTime18to40 / count18to40 : 0);
		kontrolleri.naytaLoppuaikaKeski(count41to60 > 0 ? totalServiceTime41to60 / count41to60 : 0);
		kontrolleri.naytaLoppuaikaVanha(count60Plus > 0 ? totalServiceTime60Plus / count60Plus : 0);
	}

	private void printServicePointStats(Palvelupiste p) {
		System.out.println("\nPalvelupiste: " + p.getType());
		System.out.println("  Jonossa asiakkaita: " + p.getQueueLength());
		System.out.println("  Palveltujen asiakkaiden määrä: " + p.getServedCustomers());
		System.out.println("  Keskimääräinen jonotusaika: " + p.getAverageWaitingTime());
		System.out.println("  Keskimääräinen palveluaika: " + p.getAverageServiceTime());
		System.out.println("  Kokonaisaika: " + p.getTotalTime());
		System.out.println("--------------------------------");
	}

	private void printAverageServiceTimeByAge() {
		System.out.println("\nAsiakkaiden keskimääräiset palveluajat iän mukaan:");
		System.out.println("Ikä 18-40: " + (count18to40 > 0 ? totalServiceTime18to40 / count18to40 : 0));
		System.out.println("Ikä 41-60: " + (count41to60 > 0 ? totalServiceTime41to60 / count41to60 : 0));
		System.out.println("Ikä 61+: " + (count60Plus > 0 ? totalServiceTime60Plus / count60Plus : 0));
		System.out.println("--------------------------------");
	}
}
