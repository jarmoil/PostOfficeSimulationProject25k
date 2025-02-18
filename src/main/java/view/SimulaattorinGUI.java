package view;


import java.text.DecimalFormat;
import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;



public class SimulaattorinGUI extends Application implements ISimulaattorinUI {

    //Kontrollerin esittely (tarvitaan käyttöliittymässä)
    private IKontrolleriForV kontrolleri;

    // Käyttöliittymäkomponentit:
    private TextField aika;
    private TextField viive;
    private Label tulos;
    private Label palvellutAsiakasMaara;
    private Label aikaLabel;
    private Label viiveLabel;
    private Label tulosLabel;
    private Label palvellutLabel;
    private Label ikaKeskPalveluaika;
    private Label ikaNuori;
    private Label ikaKeski;
    private Label ikaVanha;
    private Label tulosIkaNuori;
    private Label tulosIkaKeski;
    private Label tulosIkaVanha;

    private Label palvelunValintaLabel;
    private Label jonossaLabel;
    private Label jonossa;
    private Label palveluMaaraLabel;
    private Label palveluMaara;
    private Label keskimJonoAikaLabel;
    private Label keskimJonoAika;
    private Label keskimPalveluAikaLabel;
    private Label keskimPalveluAika;
    private Label kokonaisAikaLabel;
    private Label kokonaisAika;

    private Label PVpalvelunValintaLabel;
    private Label PVjonossaLabel;
    private Label PVjonossa;
    private Label PVpalveluMaaraLabel;
    private Label PVpalveluMaara;
    private Label PVkeskimJonoAikaLabel;
    private Label PVkeskimJonoAika;
    private Label PVkeskimPalveluAikaLabel;
    private Label PVkeskimPalveluAika;
    private Label PVkokonaisAikaLabel;
    private Label PVkokonaisAika;

    private Label NTpalvelunValintaLabel;
    private Label NTjonossaLabel;
    private Label NTjonossa;
    private Label NTpalveluMaaraLabel;
    private Label NTpalveluMaara;
    private Label NTkeskimJonoAikaLabel;
    private Label NTkeskimJonoAika;
    private Label NTkeskimPalveluAikaLabel;
    private Label NTkeskimPalveluAika;
    private Label NTkokonaisAikaLabel;
    private Label NTkokonaisAika;

    private Label ETpalvelunValintaLabel;
    private Label ETjonossaLabel;
    private Label ETjonossa;
    private Label ETpalveluMaaraLabel;
    private Label ETpalveluMaara;
    private Label ETkeskimJonoAikaLabel;
    private Label ETkeskimJonoAika;
    private Label ETkeskimPalveluAikaLabel;
    private Label ETkeskimPalveluAika;
    private Label ETkokonaisAikaLabel;
    private Label ETkokonaisAika;

    private Button kaynnistaButton;
    private Button hidastaButton;
    private Button nopeutaButton;
    private Button pysaytaButton;
    private Button jatkaButton;


    private IVisualisointi naytto;


    @Override
    public void init() {

        Trace.setTraceLevel(Level.INFO);

        kontrolleri = new Kontrolleri(this);
    }

    @Override
    public void start(Stage primaryStage) {
        // Käyttöliittymän rakentaminen
        try {

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });


            primaryStage.setTitle("Posti simulaattori");

            kaynnistaButton = new Button();
            kaynnistaButton.setText("Käynnistä simulointi");
            kaynnistaButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    kontrolleri.kaynnistaSimulointi();
                    kaynnistaButton.setDisable(true);
                }
            });

            hidastaButton = new Button();
            hidastaButton.setText("Hidasta");
            hidastaButton.setOnAction(e -> kontrolleri.hidasta());

            nopeutaButton = new Button();
            nopeutaButton.setText("Nopeuta");
            nopeutaButton.setOnAction(e -> kontrolleri.nopeuta());

            jatkaButton = new Button();
            jatkaButton.setText("Jatka");
            //Pitää tehdä sisältö tälle nappulalle
            //jatkaButton.setOnAction(e -> kontrolleri.jatka());

            pysaytaButton = new Button();
            pysaytaButton.setText("Pysäytä");
            //Pitää tehdä sisältö tälle nappulalle
            //pysaytaButton.setOnAction(e -> kontrolleri.pysayta());

            aikaLabel = new Label("Simulointiaika:");
            aikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            aika = new TextField("Syötä aika");
            aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            aika.setPrefWidth(150);

            viiveLabel = new Label("Viive:");
            viiveLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            viive = new TextField("Syötä viive");
            viive.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            viive.setPrefWidth(150);

            tulosLabel = new Label("Kokonaisaika:");
            tulosLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            tulos = new Label();
            tulos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            tulos.setPrefWidth(150);

            ikaKeskPalveluaika = new Label("Keskim. palveluaika iän mukaan:");
            ikaKeskPalveluaika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            ikaKeskPalveluaika.setPrefWidth(150);

            ikaNuori = new Label("18 - 40v: ");
            ikaNuori.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            tulosIkaNuori = new Label();
            tulosIkaNuori.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            tulosIkaNuori.setPrefWidth(150);

            ikaKeski = new Label("41 - 60v: ");
            ikaKeski.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            tulosIkaKeski = new Label();
            tulosIkaKeski.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            tulosIkaKeski.setPrefWidth(150);

            ikaVanha = new Label("61v +: ");
            ikaVanha.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            tulosIkaVanha = new Label();
            tulosIkaVanha.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            tulosIkaVanha.setPrefWidth(150);


            palvellutLabel = new Label("Palveltu: ");
            palvellutLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            palvellutAsiakasMaara = new Label();
            palvellutAsiakasMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            palvellutAsiakasMaara.setPrefWidth(150);



            palvelunValintaLabel = new Label("PAKETTIAUTOMAATTI:");
            palvelunValintaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            palvelunValintaLabel.setPrefWidth(150);

            jonossaLabel = new Label("Jonossa: ");
            jonossaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            jonossa = new Label();
            jonossa.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            jonossa.setPrefWidth(150);

            palveluMaaraLabel = new Label("Palveltu: ");
            palveluMaaraLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            palveluMaara = new Label();
            palveluMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            palveluMaara.setPrefWidth(150);

            keskimJonoAikaLabel = new Label("Keskim. jonotusaika: ");
            keskimJonoAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            keskimJonoAika = new Label();
            keskimJonoAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            keskimJonoAika.setPrefWidth(150);

            keskimPalveluAikaLabel = new Label("Keskim. palveluaika: ");
            keskimPalveluAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            keskimPalveluAika = new Label();
            keskimPalveluAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            keskimPalveluAika.setPrefWidth(150);

            kokonaisAikaLabel = new Label("Kokonaisaika: ");
            kokonaisAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            kokonaisAika = new Label();
            kokonaisAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            kokonaisAika.setPrefWidth(150);



            PVpalvelunValintaLabel = new Label("PALVELUNVALINTA: ");
            PVpalvelunValintaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            PVpalvelunValintaLabel.setPrefWidth(150);

            PVjonossaLabel = new Label("Jonossa: ");
            PVjonossaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            PVjonossa = new Label();
            PVjonossa.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            PVjonossa.setPrefWidth(150);

            PVpalveluMaaraLabel = new Label("Palveltu: ");
            PVpalveluMaaraLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            PVpalveluMaara = new Label();
            PVpalveluMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            PVpalveluMaara.setPrefWidth(150);

            PVkeskimJonoAikaLabel = new Label("Keskim. jonotusaika: ");
            PVkeskimJonoAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            PVkeskimJonoAika = new Label();
            PVkeskimJonoAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            PVkeskimJonoAika.setPrefWidth(150);

            PVkeskimPalveluAikaLabel = new Label("Keskim. palveluaika: ");
            PVkeskimPalveluAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            PVkeskimPalveluAika = new Label();
            PVkeskimPalveluAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            PVkeskimPalveluAika.setPrefWidth(150);

            PVkokonaisAikaLabel = new Label("Kokonaisaika: ");
            PVkokonaisAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            PVkokonaisAika = new Label();
            PVkokonaisAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            PVkokonaisAika.setPrefWidth(150);



            NTpalvelunValintaLabel = new Label("NOUTOLÄHETÄ: ");
            NTpalvelunValintaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            NTpalvelunValintaLabel.setPrefWidth(150);

            NTjonossaLabel = new Label("Jonossa: ");
            NTjonossaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            NTjonossa = new Label();
            NTjonossa.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            NTjonossa.setPrefWidth(150);

            NTpalveluMaaraLabel = new Label("Palveltu: ");
            NTpalveluMaaraLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            NTpalveluMaara = new Label();
            NTpalveluMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            NTpalveluMaara.setPrefWidth(150);

            NTkeskimJonoAikaLabel = new Label("Keskim. jonotusaika: ");
            NTkeskimJonoAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            NTkeskimJonoAika = new Label();
            NTkeskimJonoAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            NTkeskimJonoAika.setPrefWidth(150);

            NTkeskimPalveluAikaLabel = new Label("Keskim. palveluaika: ");
            NTkeskimPalveluAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            NTkeskimPalveluAika = new Label();
            NTkeskimPalveluAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            NTkeskimPalveluAika.setPrefWidth(150);

            NTkokonaisAikaLabel = new Label("Kokonaisaika: ");
            NTkokonaisAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            NTkokonaisAika = new Label();
            NTkokonaisAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            NTkokonaisAika.setPrefWidth(150);



            ETpalvelunValintaLabel = new Label("ERITYISTAPAUKSET: ");
            ETpalvelunValintaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            ETpalvelunValintaLabel.setPrefWidth(150);

            ETjonossaLabel = new Label("Jonossa: ");
            ETjonossaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            ETjonossa = new Label();
            ETjonossa.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            ETjonossa.setPrefWidth(150);

            ETpalveluMaaraLabel = new Label("Palveltu: ");
            ETpalveluMaaraLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            ETpalveluMaara = new Label();
            ETpalveluMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            ETpalveluMaara.setPrefWidth(150);

            ETkeskimJonoAikaLabel = new Label("Keskim. jonotusaika: ");
            ETkeskimJonoAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            ETkeskimJonoAika = new Label();
            ETkeskimJonoAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            ETkeskimJonoAika.setPrefWidth(150);

            ETkeskimPalveluAikaLabel = new Label("Keskim. palveluaika: ");
            ETkeskimPalveluAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            ETkeskimPalveluAika = new Label();
            ETkeskimPalveluAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            ETkeskimPalveluAika.setPrefWidth(150);

            ETkokonaisAikaLabel = new Label("Kokonaisaika: ");
            ETkokonaisAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            ETkokonaisAika = new Label();
            ETkokonaisAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            ETkokonaisAika.setPrefWidth(150);


            // Pohjan luonti pitää vielä modifioida, aika maanantai malli atm.

            //Taustakuva
            Image backgroundImage = new Image(getClass().getResourceAsStream("/Backgroundimg.jpg"));
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

            // BorderPane pääroska minkä sisällä kaikki muu
            BorderPane root = new BorderPane();
            root.setBackground(new Background(background));
            root.setPadding(new Insets(15, 1, 15, 1)); // margins top, right, bottom, left

            VBox labelBox = new VBox();
            labelBox.setSpacing(10);   // spacing between nodes 10 pixels

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(15, 12, 15, 12)); // margins top, right, bottom, left
            grid.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
            grid.setAlignment(Pos.TOP_LEFT);
            grid.setOpacity(0.8);
            grid.setVgap(10);
            grid.setHgap(5);


            // Column constraints
            ColumnConstraints column1 = new ColumnConstraints();
            ColumnConstraints column2 = new ColumnConstraints();
            grid.getColumnConstraints().addAll(column1, column2);
            column1.setPercentWidth(50);
            column2.setPercentWidth(50);

            // Horizontal alignment
            //grid.setGridLinesVisible(true);

            grid.add(aikaLabel, 0, 0);   // sarake, rivi
            grid.add(aika, 1, 0);
            grid.add(viiveLabel, 0, 1);
            grid.add(viive, 1, 1);
            grid.add(tulosLabel, 0, 2);
            grid.add(tulos, 1, 2);
            grid.add(palvellutLabel, 0, 3);
            grid.add(palvellutAsiakasMaara, 1, 3);

            ikaKeskPalveluaika.setPrefWidth(300);
            grid.add(ikaKeskPalveluaika, 0, 4,2,1);
            grid.add(ikaNuori, 0, 5);
            grid.add(tulosIkaNuori, 1, 5);
            grid.add(ikaKeski, 0, 6);
            grid.add(tulosIkaKeski, 1, 6);
            grid.add(ikaVanha, 0, 7);
            grid.add(tulosIkaVanha, 1, 7);

            // PAKETTIAUTOMAATTI
            grid.add(palvelunValintaLabel,0,8,2,1);
            palvelunValintaLabel.setPrefWidth(300);
            grid.add(jonossaLabel,0,9);
            grid.add(jonossa,1,9);
            grid.add(palveluMaaraLabel,0,10);
            grid.add(palveluMaara,1,10);
            grid.add(keskimJonoAikaLabel,0,11);
            grid.add(keskimJonoAika,1,11);
            grid.add(keskimPalveluAikaLabel,0,12);
            grid.add(keskimPalveluAika,1,12);
            grid.add(kokonaisAikaLabel,0,13);
            grid.add(kokonaisAika,1,13);

            // PALVELUNVALINTA
            grid.add(PVpalvelunValintaLabel,0,14,2,1);
            PVpalvelunValintaLabel.setPrefWidth(300);
            grid.add(PVjonossaLabel,0,15);
            grid.add(PVjonossa,1,15);
            grid.add(PVpalveluMaaraLabel,0,16);
            grid.add(PVpalveluMaara,1,16);
            grid.add(PVkeskimJonoAikaLabel,0,17);
            grid.add(PVkeskimJonoAika,1,17);
            grid.add(PVkeskimPalveluAikaLabel,0,18);
            grid.add(PVkeskimPalveluAika,1,18);
            grid.add(PVkokonaisAikaLabel,0,19);
            grid.add(PVkokonaisAika,1,19);

            // NOUTOLÄHETÄ
            grid.add(NTpalvelunValintaLabel,0,20,2,1);
            NTpalvelunValintaLabel.setPrefWidth(300);
            grid.add(NTjonossaLabel,0,21);
            grid.add(NTjonossa,1,21);
            grid.add(NTpalveluMaaraLabel,0,22);
            grid.add(NTpalveluMaara,1,22);
            grid.add(NTkeskimJonoAikaLabel,0,23);
            grid.add(NTkeskimJonoAika,1,23);
            grid.add(NTkeskimPalveluAikaLabel,0,24);
            grid.add(NTkeskimPalveluAika,1,24);
            grid.add(NTkokonaisAikaLabel,0,25);
            grid.add(NTkokonaisAika,1,25);

            // ERITYISTAPAUKSET
            grid.add(ETpalvelunValintaLabel,0,26,2,1);
            ETpalvelunValintaLabel.setPrefWidth(300);
            grid.add(ETjonossaLabel,0,27);
            grid.add(ETjonossa,1,27);
            grid.add(ETpalveluMaaraLabel,0,28);
            grid.add(ETpalveluMaara,1,28);
            grid.add(ETkeskimJonoAikaLabel,0,29);
            grid.add(ETkeskimJonoAika,1,29);
            grid.add(ETkeskimPalveluAikaLabel,0,30);
            grid.add(ETkeskimPalveluAika,1,30);
            grid.add(ETkokonaisAikaLabel,0,31);
            grid.add(ETkokonaisAika,1,31);


            labelBox.getChildren().add(grid);

            naytto = new Visualisointi(309, 200);

            // Canvas skaalaus ei toimi vielä kunnolla !!
            Canvas canvas = (Canvas) naytto;
            canvas.widthProperty().bind(root.widthProperty().subtract(labelBox.widthProperty()).subtract(20));
            canvas.heightProperty().bind(root.heightProperty().subtract(70)); // Adjusted height binding


            // HBox painikkeille
            HBox buttonBox = new HBox();
            buttonBox.setAlignment(Pos.BOTTOM_CENTER);
            buttonBox.setSpacing(10); // spacing between buttons
            buttonBox.setPadding(new Insets(15, 12, 15, 12)); // margins top, right, bottom, left
            buttonBox.getChildren().addAll(kaynnistaButton, nopeutaButton, hidastaButton, jatkaButton, pysaytaButton);
            buttonBox.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
            buttonBox.setOpacity(0.8);

            // BorderPanen asettelu
            root.setLeft(labelBox);
            root.setCenter(canvas);
            root.setBottom(buttonBox);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

    @Override
    public double getAika() {
        return Double.parseDouble(aika.getText());
    }

    @Override
    public long getViive() {
        return Long.parseLong(viive.getText());
    }

    @Override
    public void setLoppuaika(double aika) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.tulos.setText(formatter.format(aika));
    }

    @Override
    public void setLoppuaikaNuori(double aika) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.tulosIkaNuori.setText(formatter.format(aika));
    }
    @Override
    public void setLoppuaikaKeski(double aika) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.tulosIkaKeski.setText(formatter.format(aika));
    }
    @Override
    public void setLoppuaikaVanha(double aika) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.tulosIkaVanha.setText(formatter.format(aika));
    }

    @Override
    public void paivitaAsiakasMaara(int TotalServedCustomers) {
        this.palvellutAsiakasMaara.setText(Integer.toString(TotalServedCustomers));
    }

    // PAKETTIAUTOMAATTI
    @Override
    public void paivitaJonoPituus(int queueLength) {
        this.jonossa.setText(Integer.toString(queueLength));
    }
    @Override
    public void paivitaPalveltuMaara(int servedCustomers) {
        this.palveluMaara.setText(Integer.toString(servedCustomers));
    }

    @Override
    public void paivitaKeskimJonoAika(double averageWaitingTime) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.keskimJonoAika.setText(formatter.format(averageWaitingTime));
    }
    @Override
    public void paivitaKeskimPalveluAika(double averageServiceTime) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.keskimPalveluAika.setText(formatter.format(averageServiceTime));
    }
    @Override
    public void paivitaKokonaisAika(double totalTime) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.kokonaisAika.setText(formatter.format(totalTime));
    }

    // PALVELUNVALINTA
    @Override
    public void PVpaivitaJonoPituus(int queueLength) {
        this.PVjonossa.setText(Integer.toString(queueLength));
    }
    @Override
    public void PVpaivitaPalveltuMaara(int servedCustomers) {
        this.PVpalveluMaara.setText(Integer.toString(servedCustomers));
    }
    @Override
    public void PVpaivitaKeskimJonoAika(double averageWaitingTime) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.PVkeskimJonoAika.setText(formatter.format(averageWaitingTime));
    }
    @Override
    public void PVpaivitaKeskimPalveluAika(double averageServiceTime) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.PVkeskimPalveluAika.setText(formatter.format(averageServiceTime));
    }
    @Override
    public void PVpaivitaKokonaisAika(double totalTime) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.PVkokonaisAika.setText(formatter.format(totalTime));
    }

    // NOUTOLÄHETÄ
    @Override
    public void NTpaivitaJonoPituus(int queueLength) {
        this.NTjonossa.setText(Integer.toString(queueLength));
    }
    @Override
    public void NTpaivitaPalveltuMaara(int servedCustomers) {
        this.NTpalveluMaara.setText(Integer.toString(servedCustomers));
    }
    @Override
    public void NTpaivitaKeskimJonoAika(double averageWaitingTime) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.NTkeskimJonoAika.setText(formatter.format(averageWaitingTime));
    }
    @Override
    public void NTpaivitaKeskimPalveluAika(double averageServiceTime) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.NTkeskimPalveluAika.setText(formatter.format(averageServiceTime));
    }
    @Override
    public void NTpaivitaKokonaisAika(double totalTime) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.NTkokonaisAika.setText(formatter.format(totalTime));
    }

    // ERITYISTAPAUKSET
    @Override
    public void ETpaivitaJonoPituus(int queueLength) {
        this.ETjonossa.setText(Integer.toString(queueLength));
    }
    @Override
    public void ETpaivitaPalveltuMaara(int servedCustomers) {
        this.ETpalveluMaara.setText(Integer.toString(servedCustomers));
    }
    @Override
    public void ETpaivitaKeskimJonoAika(double averageWaitingTime) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.ETkeskimJonoAika.setText(formatter.format(averageWaitingTime));
    }
    @Override
    public void ETpaivitaKeskimPalveluAika(double averageServiceTime) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.ETkeskimPalveluAika.setText(formatter.format(averageServiceTime));
    }
    @Override
    public void ETpaivitaKokonaisAika(double totalTime) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.ETkokonaisAika.setText(formatter.format(totalTime));
    }





    @Override
    public IVisualisointi getVisualisointi() {
        return naytto;
    }
}




