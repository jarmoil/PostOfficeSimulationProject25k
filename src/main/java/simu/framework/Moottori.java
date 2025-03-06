package simu.framework;
import controller.IKontrolleriForM; // UUSI
import dao.TuloksetDao;

public abstract class Moottori extends Thread implements IMoottori{  // UUDET MÄÄRITYKSET
	
	private double simulointiaika = 0;
	private long viive = 0;
	private boolean paused = false;
	private final Object lock = new Object();
	public TuloksetDao tuloksetDao;
	
	private Kello kello;
	
	protected Tapahtumalista tapahtumalista;

	protected IKontrolleriForM kontrolleri; // UUSI

	public Moottori(IKontrolleriForM kontrolleri){ // UUSITTU

		this.kontrolleri = kontrolleri; //UUSI

		this.tuloksetDao = new TuloksetDao(); // UUSI

		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia
		
		tapahtumalista = new Tapahtumalista();
		
		// Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa

	}
	@Override
	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}

	@Override // UUSI
	public void setViive(long viive) {
		this.viive = viive;
	}

	@Override // UUSI
	public long getViive() {
		return viive;
	}

	public void run(){
		alustukset(); // luodaan mm. ensimmäinen tapahtuma
		while (simuloidaan()){
			viive(); // UUSI

			synchronized (lock){
				while(paused){
					try{
						lock.wait();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
			
			Trace.out(Trace.Level.INFO, "\nA-vaihe: kello on " + nykyaika());
			kello.setAika(nykyaika());
			
			Trace.out(Trace.Level.INFO, "\nB-vaihe:" );
			suoritaBTapahtumat();
			
			Trace.out(Trace.Level.INFO, "\nC-vaihe:" );
			yritaCTapahtumat();

		}
		tulokset();
		
	}

	public void pysaytaSimulaatio(){
		synchronized (lock){
			paused = true;
		}
	}

	public void jatkaSimulaatio(){
		synchronized (lock){
			paused = false;
			lock.notify();
		}
	}


	private void suoritaBTapahtumat(){
		while (tapahtumalista.getSeuraavanAika() == kello.getAika()){
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	private double nykyaika(){
		return tapahtumalista.getSeuraavanAika();
	}
	
	private boolean simuloidaan(){
		return kello.getAika() < simulointiaika;
	}

	private void viive() { // UUSI
		Trace.out(Trace.Level.INFO, "Viive " + viive);
		try {
			sleep(viive);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected abstract void suoritaTapahtuma(Tapahtuma t);  // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	protected abstract void yritaCTapahtumat();	// Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void alustukset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void tulokset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
}
