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
            aikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 17));
            aika = new TextField("Syötä aika");
            aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 17));
            aika.setPrefWidth(150);

            viiveLabel = new Label("Viive:");
            viiveLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 17));
            viive = new TextField("Syötä viive");
            viive.setFont(Font.font("Tahoma", FontWeight.NORMAL, 17));
            viive.setPrefWidth(150);

            tulosLabel = new Label("Kokonaisaika:");
            tulosLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            tulos = new Label();
            tulos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            tulos.setPrefWidth(150);

            ikaKeskPalveluaika = new Label("Keskim. palveluaika iän mukaan:");
            ikaKeskPalveluaika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
            ikaKeskPalveluaika.setPrefWidth(150);

            ikaNuori = new Label("18 - 40v: ");
            ikaNuori.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            tulosIkaNuori = new Label();
            tulosIkaNuori.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            tulosIkaNuori.setPrefWidth(150);

            ikaKeski = new Label("41 - 60v: ");
            ikaKeski.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            tulosIkaKeski = new Label();
            tulosIkaKeski.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            tulosIkaKeski.setPrefWidth(150);

            ikaVanha = new Label("61v +: ");
            ikaVanha.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            tulosIkaVanha = new Label();
            tulosIkaVanha.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            tulosIkaVanha.setPrefWidth(150);


            palvellutLabel = new Label("Palveltu: ");
            palvellutLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            palvellutAsiakasMaara = new Label();
            palvellutAsiakasMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            palvellutAsiakasMaara.setPrefWidth(150);

            palvelunValintaLabel = new Label("PAKETTIAUTOMAATTI:");
            palvelunValintaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
            palvelunValintaLabel.setPrefWidth(150);

            jonossaLabel = new Label("Jonossa: ");
            jonossaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            jonossa = new Label();
            jonossa.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            jonossa.setPrefWidth(150);

            palveluMaaraLabel = new Label("Palveltu: ");
            palveluMaaraLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            palveluMaara = new Label();
            palveluMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            palveluMaara.setPrefWidth(150);

            keskimJonoAikaLabel = new Label("Keskim. jonotusaika: ");
            keskimJonoAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            keskimJonoAika = new Label();
            keskimJonoAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            keskimJonoAika.setPrefWidth(150);

            keskimPalveluAikaLabel = new Label("Keskim. palveluaika: ");
            keskimPalveluAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            keskimPalveluAika = new Label();
            keskimPalveluAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            keskimPalveluAika.setPrefWidth(150);

            kokonaisAikaLabel = new Label("Kokonaisaika: ");
            kokonaisAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            kokonaisAika = new Label();
            kokonaisAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            kokonaisAika.setPrefWidth(150);




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
    public void paivitaAsiakasMaara(int servedCustomers) {
        this.palvellutAsiakasMaara.setText(Integer.toString(servedCustomers));
    }





    @Override
    public IVisualisointi getVisualisointi() {
        return naytto;
    }
}




