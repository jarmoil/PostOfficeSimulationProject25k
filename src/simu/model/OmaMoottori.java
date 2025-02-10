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

	public OmaMoottori() {
		palvelupisteet = new Palvelupiste[4];

		// Palvelupisteet: Pakettiautomaatti, palvelunvalinta, palvelutiski 1, palvelutiski 2
		palvelupisteet[0] = new Palvelupiste(new Normal(5, 2), tapahtumalista, TapahtumanTyyppi.PAKETTIAUTOMAATTI);
		palvelupisteet[1] = new Palvelupiste(new Normal(3, 1), tapahtumalista, TapahtumanTyyppi.PALVELUNVALINTA);
		palvelupisteet[2] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.TISKI1);
		palvelupisteet[3] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.TISKI2);

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
				if (random.nextBoolean()) {
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
				a = (Asiakas) palvelupisteet[0].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				System.out.println("Asiakas " + a.getId() + " valmis pakettiautomaatilta.");
				a.raportti();
				break;
			case PALVELUNVALINTA:
				a = (Asiakas) palvelupisteet[1].otaJonosta();
				if (random.nextBoolean()) {
					palvelupisteet[2].lisaaJonoon(a); // Palvelutiski 1
					System.out.println("Asiakas " + a.getId() + " ohjattu palvelutiskille 1.");
				} else {
					palvelupisteet[3].lisaaJonoon(a); // Palvelutiski 2
					System.out.println("Asiakas " + a.getId() + " ohjattu palvelutiskille 2.");
				}
				break;
			case TISKI1:
				asiakasLkm++;
				a = (Asiakas) palvelupisteet[2].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				System.out.println("Asiakas " + a.getId() + " valmis palvelutiskiltä 1.");
				a.raportti();
				break;
			case TISKI2:
				asiakasLkm++;
				a = (Asiakas) palvelupisteet[3].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				System.out.println("Asiakas " + a.getId() + " valmis palvelutiskiltä 2.");
				a.raportti();
				break;
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
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());


		System.out.println("Asiakkaita käsitelty yhteensä: " + asiakasLkm);
	}
}
