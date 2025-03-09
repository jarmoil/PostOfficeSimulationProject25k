package simu.model;

import controller.IKontrolleriForM;
import entity.*;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

import java.util.Random;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;
	private Palvelupiste[] palvelupisteet;
	private Random random;
	private int servedCustomers = 0;

	private IDao tuloksetDao;
	private Tulokset tulokset;

	private record ServicePoint(double x, double y, double exitX, double exitY) {}

	private ServicePoint getServiceConfig(TapahtumanTyyppi type) {
		return switch (type) {
			case PAKETTIAUTOMAATTI -> new ServicePoint(kontrolleri.PACoord().getX(), kontrolleri.PACoord().getY(), kontrolleri.ExitCoord().getX(), kontrolleri.ExitCoord().getY());
			case PALVELUNVALINTA -> new ServicePoint(kontrolleri.PVCoord().getX(), kontrolleri.PVCoord().getY(), kontrolleri.ExitCoord().getX(), kontrolleri.ExitCoord().getY());
			case NOUTOLAHETA -> new ServicePoint(kontrolleri.NTCoord().getX(), kontrolleri.NTCoord().getY(), kontrolleri.ExitCoord().getX(), kontrolleri.ExitCoord().getY());
			case ERITYISTAPAUKSET -> new ServicePoint(kontrolleri.ETCoord().getX(), kontrolleri.ETCoord().getY(), kontrolleri.ExitCoord().getX(), kontrolleri.ExitCoord().getY());
			case ARR1 -> throw new IllegalArgumentException("Invalid service point type");
		};
	}

	private void updateStats(Palvelupiste p) {

		kontrolleri.updateServicePointStats(
				p.getType(),
				p.getQueueLength(),
				p.getServedCustomers(),
				p.getAverageWaitingTime(),
				p.getAverageServiceTime(),
				p.getTotalTime()
		);
	}

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



	public OmaMoottori(IKontrolleriForM kontrolleri, IDao dao) {
		super (kontrolleri, dao);

		tuloksetDao = dao;
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

	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {
		Asiakas a;

		TapahtumanTyyppi type = (TapahtumanTyyppi) t.getTyyppi();

		switch (type) {
			case ARR1 -> {
				a = new Asiakas();
				handleArrival(a);
				saapumisprosessi.generoiSeuraava();
				kontrolleri.drawCustomer(a.getId(), kontrolleri.AloitusCoord().getX(), kontrolleri.AloitusCoord().getY());
			}
			case PALVELUNVALINTA -> {
				a = palvelupisteet[1].otaJonosta();
				ServicePoint config = getServiceConfig(type);
				kontrolleri.moveCustomer(a.getId(), kontrolleri.PVCoord().getX(), kontrolleri.PVCoord().getY(), () -> {
					redirectToService(a);
					updateStats(palvelupisteet[1]);
				});
			}
			case PAKETTIAUTOMAATTI, NOUTOLAHETA, ERITYISTAPAUKSET -> {
				int index = type == TapahtumanTyyppi.PAKETTIAUTOMAATTI ? 0 :
						type == TapahtumanTyyppi.NOUTOLAHETA ? 2 : 3;
				a = palvelupisteet[index].otaJonosta();
				ServicePoint config = getServiceConfig(type);
				kontrolleri.moveCustomer(a.getId(), config.x(), config.y(), () -> {
					processCustomer(a, palvelupisteet[index]);
					// Update stats AFTER processing the customer
					updateStats(palvelupisteet[index]);
					kontrolleri.exitCustomer(a.getId(), kontrolleri.ExitCoord().getX(), kontrolleri.ExitCoord().getY());
				});
			}
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
		ServicePoint next;

		if (random.nextBoolean()) {
			next = getServiceConfig(TapahtumanTyyppi.NOUTOLAHETA);
			kontrolleri.moveCustomer(a.getId(), next.x(), next.y(), () -> {
				palvelupisteet[2].lisaaJonoon(a);
				System.out.println("Asiakas " + a.getId() + " ohjattu nouto/lähetä palvelutiskille.");
			});
		} else {
			next = getServiceConfig(TapahtumanTyyppi.ERITYISTAPAUKSET);
			kontrolleri.moveCustomer(a.getId(), next.x(), next.y(), () -> {
				palvelupisteet[3].lisaaJonoon(a);
				System.out.println("Asiakas " + a.getId() + " ohjattu erityistapaus palvelutiskille.");
			});
		}
	}

	private void processCustomer(Asiakas a, Palvelupiste p) {
		a.setPoistumisaika(Kello.getInstance().getAika());
		updateServiceTimeStats(a);
		servedCustomers++;
		kontrolleri.updateTotalServedCustomers(servedCustomers);
		System.out.println("Asiakas " + a.getId() + " valmis " + p.getType() + " palvelutiskiltä");
		a.raportti();
	}

	// Update service time statistics based on age group
	private void updateServiceTimeStats(Asiakas a) {
		double serviceTime = a.getPoistumisaika() - a.getSaapumisaika();
		if (a.getAge() <= 40) {
			totalServiceTime18to40 += serviceTime;
			count18to40++;
			kontrolleri.naytaLoppuaikaNuori(count18to40 > 0 ? totalServiceTime18to40 / count18to40 : 0);
		} else if (a.getAge() <= 60) {
			totalServiceTime41to60 += serviceTime;
			count41to60++;
			kontrolleri.naytaLoppuaikaKeski(count41to60 > 0 ? totalServiceTime41to60 / count41to60 : 0);
		} else {
			totalServiceTime60Plus += serviceTime;
			count60Plus++;
			kontrolleri.naytaLoppuaikaVanha(count60Plus > 0 ? totalServiceTime60Plus / count60Plus : 0);
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

		kontrolleri.waitForAnimations(() -> {
			System.out.println("\nSimulointi päättyi kello " + Kello.getInstance().getAika());

			int[] finalQueueLengths = new int[palvelupisteet.length];
			for (int i = 0; i < palvelupisteet.length; i++) {
				finalQueueLengths[i] = palvelupisteet[i].getQueueLength();
			}

			double finalAvg18to40 = count18to40 > 0 ? totalServiceTime18to40 / count18to40 : 0;
			double finalAvg41to60 = count41to60 > 0 ? totalServiceTime41to60 / count41to60 : 0;
			double finalAvg60Plus = count60Plus > 0 ? totalServiceTime60Plus / count60Plus : 0;

			for (Palvelupiste p : palvelupisteet) {
				if (p != null) {
					updateStats(p);
				}
			}

			PakettiautomaattiTulos paTulos = new PakettiautomaattiTulos(
					finalQueueLengths[0],
					palvelupisteet[0].getServedCustomers(),
					palvelupisteet[0].getAverageWaitingTime(),
					palvelupisteet[0].getAverageServiceTime(),
					palvelupisteet[0].getTotalTime()
			);

			PalvelunvalintaTulos pvTulos = new PalvelunvalintaTulos(
					finalQueueLengths[1],
					palvelupisteet[1].getServedCustomers(),
					palvelupisteet[1].getAverageWaitingTime(),
					palvelupisteet[1].getAverageServiceTime(),
					palvelupisteet[1].getTotalTime()
			);

			NoutolahetaTulos nlTulos = new NoutolahetaTulos(
					finalQueueLengths[2],
					palvelupisteet[2].getServedCustomers(),
					palvelupisteet[2].getAverageWaitingTime(),
					palvelupisteet[2].getAverageServiceTime(),
					palvelupisteet[2].getTotalTime()
			);

			ErityistapauksetTulos etTulos = new ErityistapauksetTulos(
					finalQueueLengths[3],
					palvelupisteet[3].getServedCustomers(),
					palvelupisteet[3].getAverageWaitingTime(),
					palvelupisteet[3].getAverageServiceTime(),
					palvelupisteet[3].getTotalTime()
			);

			PalveluaikaIkaTulos ikaTulos = new PalveluaikaIkaTulos(
					finalAvg18to40,
					finalAvg41to60,
					finalAvg60Plus
			);

			this.tulokset = new Tulokset(
					Kello.getInstance().getAika(),
					servedCustomers,
					nlTulos,
					pvTulos,
					paTulos,
					etTulos,
					ikaTulos
			);

			tallennaTulokset();

			// Print final statistics
			for (Palvelupiste p : palvelupisteet) {
				printServicePointStats(p);
			}
			printAverageServiceTimeByAge();
			kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		});
		}

	public void tallennaTulokset() {
		if (tulokset != null) {
			tuloksetDao.tallenna(tulokset);
			System.out.println("Simulaation tulokset tallennettu tietokantaan.");
		}
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
