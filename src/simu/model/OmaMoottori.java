package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import java.util.Random;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;
	private Palvelupiste[] palvelupisteet;
	private Random random;

	// Analyysia varten nyt vaan yksi tulostus
	private int asiakasLkm;

	// iän seuranta ja niiden jaottelu
	private double totalServiceTime18to40 = 0;
	private int count18to40 = 0;
	private double totalServiceTime41to60 = 0;
	private int count41to60 = 0;
	private double totalServiceTime60Plus = 0;
	private int count60Plus = 0;


	public OmaMoottori() {
		palvelupisteet = new Palvelupiste[4];

		// Palvelupisteet: Pakettiautomaatti, palvelunvalinta, palvelutiski 1, palvelutiski 2
		palvelupisteet[0] = new Palvelupiste(new Normal(5, 2), tapahtumalista, TapahtumanTyyppi.PAKETTIAUTOMAATTI);
		palvelupisteet[1] = new Palvelupiste(new Normal(3, 1), tapahtumalista, TapahtumanTyyppi.PALVELUNVALINTA);
		palvelupisteet[2] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.NOUTOLAHETA);
		palvelupisteet[3] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.ERITYISTAPAUKSET);

		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.ARR1);
		random = new Random();

		// Alustetaan analyysi
		asiakasLkm = 0;
	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {  // B-vaiheen tapahtumat
		Asiakas a;
		switch ((TapahtumanTyyppi) t.getTyyppi()) {
			case ARR1:
				a = new Asiakas();
				// annetaan eri todennäköisyydet iän perusteella
				double probability;
				if (a.getAge() <= 40) {
					probability = 0.7; 		// 70% todennäköisyys
				} else if (a.getAge() <= 60) {
					probability = 0.3; 		// 30% todennäköisyys
				} else {
					probability = 0.1; 		// 10% todennäköisyys
				}

				if (random.nextDouble() < probability) {
					palvelupisteet[0].lisaaJonoon(a); // Pakettiautomaatti
					System.out.println("Asiakas " + a.getId() + " saapui pakettiautomaatille.");
				} else {
					palvelupisteet[1].lisaaJonoon(a); // Palvelun valinta
					System.out.println("Asiakas " + a.getId() + " saapui palvelunvalintaan.");
				}
				saapumisprosessi.generoiSeuraava();
				break;
			case PAKETTIAUTOMAATTI:
				asiakasLkm++;
				a = palvelupisteet[0].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				updateServiceTimeStats(a);
				System.out.println("Asiakas " + a.getId() + " valmis pakettiautomaatilta.");
				a.raportti();
				break;
			case PALVELUNVALINTA:
				a = palvelupisteet[1].otaJonosta();
				if (random.nextBoolean()) {
					palvelupisteet[2].lisaaJonoon(a); // Palvelutiski 1
					System.out.println("Asiakas " + a.getId() + " ohjattu nouto/lähetä palvelutiskille.");
				} else {
					palvelupisteet[3].lisaaJonoon(a); // Palvelutiski 2
					System.out.println("Asiakas " + a.getId() + " ohjattu erityistapaus palvelutiskille.");
				}
				break;
			case NOUTOLAHETA:
				asiakasLkm++;
				a = palvelupisteet[2].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				updateServiceTimeStats(a);
				System.out.println("Asiakas " + a.getId() + " valmis nouto/lähetä palvelutiskiltä");
				a.raportti();
				break;
			case ERITYISTAPAUKSET:
				asiakasLkm++;
				a = palvelupisteet[3].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				updateServiceTimeStats(a);
				System.out.println("Asiakas " + a.getId() + " valmis erityistapaus palvelutiskiltä");
				a.raportti();
				break;
		}
	}

	// eritellään iän mukaan palveluajat
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
		System.out.println("Asiakkaita käsitelty (Jonossa tai poistunut): " + asiakasLkm);

		System.out.println("\nPalvelupisteiden tilastot:");
		for (Palvelupiste p : palvelupisteet) {
			System.out.println("Palvelupiste: " + p.getType());
			System.out.println("	Jonossa asiakkaita: " + p.getQueueLength());
			System.out.println("	Palveltujen asiakkaiden määrä: " + p.getServedCustomers());
			System.out.println("	Keskimääräinen jonotusaika: " + p.getAverageWaitingTime());
			System.out.println("	Keskimääräinen palveluaika: " + p.getAverageServiceTime());
			System.out.println("--------------------------------");
		}

		// tulostetaan iän mukaan palveluajat erikseen
		System.out.println("\nAsiakkaiden keskimääräiset palveluajat iän mukaan:");
		System.out.println("Ikä 18-40: " + (count18to40 > 0 ? totalServiceTime18to40 / count18to40 : 0));
		System.out.println("Ikä 41-60: " + (count41to60 > 0 ? totalServiceTime41to60 / count41to60 : 0));
		System.out.println("Ikä 61+: " + (count60Plus > 0 ? totalServiceTime60Plus / count60Plus : 0));
		System.out.println("--------------------------------");
	}
}
