package view;


import java.text.DecimalFormat;
import java.util.List;


import controller.*;

import dao.TuloksetDao;
import entity.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
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

    // Kontrolleri, näyttö, root ja HistoryDetailView
    // (Palvelupisteiden tiedot tietokannasta)

    private IKontrolleriForV kontrolleri;
    private IVisualisointi naytto;
    private BorderPane root;
    private HistoryDetailView currentDetailView;


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
    private Button historyButton;
    private Button setButton;

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final String FONT_FAMILY = "Tahoma";
    private static final double GRID_SPACING = 5;
    private static final double BUTTON_SPACING = 10;
    private static final double OPACITY = 0.8;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");


    @Override
    public void init() {

        Trace.setTraceLevel(Level.INFO);
        TuloksetDao dao = new TuloksetDao();
        kontrolleri = new Kontrolleri(this, dao);
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
            primaryStage.setWidth(WIDTH);
            primaryStage.setHeight(HEIGHT);

            kaynnistaButton = createButton("Käynnistä simulointi", event -> {
                kontrolleri.kaynnistaSimulointi();
                kaynnistaButton.setDisable(true);
            });
            hidastaButton = createButton("Hidasta", e -> kontrolleri.hidasta());
            nopeutaButton = createButton("Nopeuta", e -> kontrolleri.nopeuta());
            jatkaButton = createButton("Jatka", e -> kontrolleri.jatka());
            pysaytaButton = createButton("Pysäytä", e -> kontrolleri.pysayta());
            setButton = createButton("Set", e -> kontrolleri.set());
            historyButton = createButton("History", e -> kontrolleri.naytaHistoriaData());

            aikaLabel = new Label("Simulointiaika:");
            aikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            aika = new TextField("Syötä aika");
            aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            aika.setPrefWidth(150);

            viiveLabel = new Label("Viive:");
            viiveLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            viive = new TextField("Syötä viive");
            viive.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            viive.setPrefWidth(150);

            tulosLabel = new Label("Kokonaisaika:");
            tulosLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            tulos = new Label();
            tulos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            tulos.setPrefWidth(150);

            ikaKeskPalveluaika = new Label("Keskim. palveluaika iän mukaan:");
            ikaKeskPalveluaika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            ikaKeskPalveluaika.setPrefWidth(150);

            ikaNuori = new Label("18 - 40v: ");
            ikaNuori.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            tulosIkaNuori = new Label();
            tulosIkaNuori.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            tulosIkaNuori.setPrefWidth(150);

            ikaKeski = new Label("41 - 60v: ");
            ikaKeski.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            tulosIkaKeski = new Label();
            tulosIkaKeski.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            tulosIkaKeski.setPrefWidth(150);

            ikaVanha = new Label("61v +: ");
            ikaVanha.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            tulosIkaVanha = new Label();
            tulosIkaVanha.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            tulosIkaVanha.setPrefWidth(150);


            palvellutLabel = new Label("Palveltu: ");
            palvellutLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            palvellutAsiakasMaara = new Label();
            palvellutAsiakasMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            palvellutAsiakasMaara.setPrefWidth(150);



            palvelunValintaLabel = new Label("PAKETTIAUTOMAATTI:");
            palvelunValintaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            palvelunValintaLabel.setPrefWidth(150);

            jonossaLabel = new Label("Jonossa: ");
            jonossaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            jonossa = new Label();
            jonossa.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            jonossa.setPrefWidth(150);

            palveluMaaraLabel = new Label("Palveltu: ");
            palveluMaaraLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            palveluMaara = new Label();
            palveluMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            palveluMaara.setPrefWidth(150);

            keskimJonoAikaLabel = new Label("Keskim. jonotusaika: ");
            keskimJonoAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            keskimJonoAika = new Label();
            keskimJonoAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            keskimJonoAika.setPrefWidth(150);

            keskimPalveluAikaLabel = new Label("Keskim. palveluaika: ");
            keskimPalveluAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            keskimPalveluAika = new Label();
            keskimPalveluAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            keskimPalveluAika.setPrefWidth(150);

            kokonaisAikaLabel = new Label("Kokonaisaika: ");
            kokonaisAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            kokonaisAika = new Label();
            kokonaisAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            kokonaisAika.setPrefWidth(150);



            PVpalvelunValintaLabel = new Label("PALVELUNVALINTA: ");
            PVpalvelunValintaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            PVpalvelunValintaLabel.setPrefWidth(150);

            PVjonossaLabel = new Label("Jonossa: ");
            PVjonossaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            PVjonossa = new Label();
            PVjonossa.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            PVjonossa.setPrefWidth(150);

            PVpalveluMaaraLabel = new Label("Palveltu: ");
            PVpalveluMaaraLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            PVpalveluMaara = new Label();
            PVpalveluMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            PVpalveluMaara.setPrefWidth(150);

            PVkeskimJonoAikaLabel = new Label("Keskim. jonotusaika: ");
            PVkeskimJonoAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            PVkeskimJonoAika = new Label();
            PVkeskimJonoAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            PVkeskimJonoAika.setPrefWidth(150);

            PVkeskimPalveluAikaLabel = new Label("Keskim. palveluaika: ");
            PVkeskimPalveluAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            PVkeskimPalveluAika = new Label();
            PVkeskimPalveluAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            PVkeskimPalveluAika.setPrefWidth(150);

            PVkokonaisAikaLabel = new Label("Kokonaisaika: ");
            PVkokonaisAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            PVkokonaisAika = new Label();
            PVkokonaisAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            PVkokonaisAika.setPrefWidth(150);



            NTpalvelunValintaLabel = new Label("NOUTOLÄHETÄ: ");
            NTpalvelunValintaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            NTpalvelunValintaLabel.setPrefWidth(150);

            NTjonossaLabel = new Label("Jonossa: ");
            NTjonossaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            NTjonossa = new Label();
            NTjonossa.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            NTjonossa.setPrefWidth(150);

            NTpalveluMaaraLabel = new Label("Palveltu: ");
            NTpalveluMaaraLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            NTpalveluMaara = new Label();
            NTpalveluMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            NTpalveluMaara.setPrefWidth(150);

            NTkeskimJonoAikaLabel = new Label("Keskim. jonotusaika: ");
            NTkeskimJonoAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            NTkeskimJonoAika = new Label();
            NTkeskimJonoAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            NTkeskimJonoAika.setPrefWidth(150);

            NTkeskimPalveluAikaLabel = new Label("Keskim. palveluaika: ");
            NTkeskimPalveluAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            NTkeskimPalveluAika = new Label();
            NTkeskimPalveluAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            NTkeskimPalveluAika.setPrefWidth(150);

            NTkokonaisAikaLabel = new Label("Kokonaisaika: ");
            NTkokonaisAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            NTkokonaisAika = new Label();
            NTkokonaisAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            NTkokonaisAika.setPrefWidth(150);



            ETpalvelunValintaLabel = new Label("ERITYISTAPAUKSET: ");
            ETpalvelunValintaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
            ETpalvelunValintaLabel.setPrefWidth(150);

            ETjonossaLabel = new Label("Jonossa: ");
            ETjonossaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            ETjonossa = new Label();
            ETjonossa.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            ETjonossa.setPrefWidth(150);

            ETpalveluMaaraLabel = new Label("Palveltu: ");
            ETpalveluMaaraLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            ETpalveluMaara = new Label();
            ETpalveluMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            ETpalveluMaara.setPrefWidth(150);

            ETkeskimJonoAikaLabel = new Label("Keskim. jonotusaika: ");
            ETkeskimJonoAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            ETkeskimJonoAika = new Label();
            ETkeskimJonoAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            ETkeskimJonoAika.setPrefWidth(150);

            ETkeskimPalveluAikaLabel = new Label("Keskim. palveluaika: ");
            ETkeskimPalveluAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            ETkeskimPalveluAika = new Label();
            ETkeskimPalveluAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            ETkeskimPalveluAika.setPrefWidth(150);

            ETkokonaisAikaLabel = new Label("Kokonaisaika: ");
            ETkokonaisAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            ETkokonaisAika = new Label();
            ETkokonaisAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            ETkokonaisAika.setPrefWidth(150);


            // Pohjan luonti pitää vielä modifioida, aika maanantai malli atm.

            //Taustakuva
            Image backgroundImage = new Image(getClass().getResourceAsStream("/Backgroundimg.jpg"));
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

            // BorderPane pääroska minkä sisällä kaikki muu
            root = new BorderPane();
            root.setBackground(new Background(background));
            root.setPadding(new Insets(4,1, 0, 1)); // margins top, right, bottom, left

            VBox labelBox = new VBox();
            labelBox.setSpacing(10);   // spacing between nodes 10 pixels

            GridPane grid = createInfoGrid();
            labelBox.getChildren().add(grid);

            naytto = new Visualisointi(309, 200, root);

            // Canvas skaalaus ei toimi vielä kunnolla !!
            Canvas canvas = (Canvas) naytto;
            canvas.widthProperty().bind(root.widthProperty().subtract(labelBox.widthProperty()).subtract(20));
            canvas.heightProperty().bind(root.heightProperty().subtract(70)); // Adjusted height binding


            // HBox painikkeille
            HBox buttonBox = new HBox();
            buttonBox.setAlignment(Pos.BOTTOM_CENTER);
            buttonBox.setSpacing(10); // spacing between buttons
            buttonBox.setPadding(new Insets(15, 12, 15, 12)); // margins top, right, bottom, left
            buttonBox.getChildren().addAll(kaynnistaButton, nopeutaButton, hidastaButton, jatkaButton, pysaytaButton, setButton, historyButton);
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

    // Tässä on helper metodeja ja historiaikkunan luonti alkuun
    // Ei käytetä rajapinnassa

    // Metodi luo napin ja asettaa sille tapahtumankäsittelijän
    private Button createButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setOnAction(handler);
        return button;
    }

    // Metodi luo historiatietojen näyttöikkunan
    private void showHistoryDialog(TableView<Tulokset> table) {
        try {
            Stage historyStage = new Stage();
            historyStage.initModality(Modality.APPLICATION_MODAL);
            historyStage.setTitle("Simulation History");

            // Details panel
            currentDetailView = new HistoryDetailView();
            ScrollPane scrollPane = new ScrollPane(currentDetailView);
            scrollPane.setFitToWidth(true);

            // Selection listener
            table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    kontrolleri.paivitaHistoriaYksityiskohdat(newVal);
                }
            });

            // Layout
            SplitPane splitPane = new SplitPane(table, scrollPane);
            splitPane.setDividerPositions(0.6);

            Scene scene = new Scene(splitPane, 1200, 600);
            historyStage.setScene(scene);

            // Clear reference when closing
            historyStage.setOnHidden(e -> currentDetailView = null);

            historyStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodi luo historiatietojen taulukon
    private TableView<Tulokset> createHistoryTable() {
        TableView<Tulokset> table = new TableView<>();
        table.setMinWidth(800);

        TableColumn<Tulokset, Integer> idCol = new TableColumn<>("ID");
        TableColumn<Tulokset, Double> timeCol = new TableColumn<>("Kokonaisaika");
        TableColumn<Tulokset, Integer> servedCol = new TableColumn<>("Palveltu");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("kokonaisaika"));
        servedCol.setCellValueFactory(new PropertyValueFactory<>("palveltu"));

        table.getColumns().addAll(idCol, timeCol, servedCol);
        return table;
    }

    // Metodi luo gridin täällä jota kutsutaan start metodissa
    private GridPane createInfoGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setOpacity(OPACITY);
        grid.setVgap(GRID_SPACING);

        // Column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        grid.getColumnConstraints().addAll(column1, column2);
        column1.setPercentWidth(50);
        column2.setPercentWidth(50);

        addGeneralInfo(grid);
        addAgeInfo(grid);
        addServicePointInfo(grid);

        return grid;
    }

    // Metodi jota käytetään start metodissa luomaan perustiedot
    private void addGeneralInfo(GridPane grid) {
        grid.add(aikaLabel, 0, 0);
        grid.add(aika, 1, 0);
        grid.add(viiveLabel, 0, 1);
        grid.add(viive, 1, 1);
        grid.add(tulosLabel, 0, 2);
        grid.add(tulos, 1, 2);
        grid.add(palvellutLabel, 0, 3);
        grid.add(palvellutAsiakasMaara, 1, 3);
    }

    // Metodi jota käytetään start metodissa luomaan ikä tiedot
    private void addAgeInfo(GridPane grid) {
        ikaKeskPalveluaika.setPrefWidth(300);
        grid.add(ikaKeskPalveluaika, 0, 4, 2, 1);
        grid.add(ikaNuori, 0, 5);
        grid.add(tulosIkaNuori, 1, 5);
        grid.add(ikaKeski, 0, 6);
        grid.add(tulosIkaKeski, 1, 6);
        grid.add(ikaVanha, 0, 7);
        grid.add(tulosIkaVanha, 1, 7);
    }

    // Metodi jota käytetään start metodissa palvelupisteiden tietoja varten
    private void addServicePointInfo(GridPane grid) {
        // PAKETTIAUTOMAATTI
        addServicePointSection(grid, 8, palvelunValintaLabel, jonossaLabel, jonossa,
                palveluMaaraLabel, palveluMaara, keskimJonoAikaLabel, keskimJonoAika,
                keskimPalveluAikaLabel, keskimPalveluAika, kokonaisAikaLabel, kokonaisAika);

        // PALVELUNVALINTA
        addServicePointSection(grid, 14, PVpalvelunValintaLabel, PVjonossaLabel, PVjonossa,
                PVpalveluMaaraLabel, PVpalveluMaara, PVkeskimJonoAikaLabel, PVkeskimJonoAika,
                PVkeskimPalveluAikaLabel, PVkeskimPalveluAika, PVkokonaisAikaLabel, PVkokonaisAika);

        // NOUTOLÄHETÄ
        addServicePointSection(grid, 20, NTpalvelunValintaLabel, NTjonossaLabel, NTjonossa,
                NTpalveluMaaraLabel, NTpalveluMaara, NTkeskimJonoAikaLabel, NTkeskimJonoAika,
                NTkeskimPalveluAikaLabel, NTkeskimPalveluAika, NTkokonaisAikaLabel, NTkokonaisAika);

        // ERIKOISTAPAUKSET
        addServicePointSection(grid, 26, ETpalvelunValintaLabel, ETjonossaLabel, ETjonossa,
                ETpalveluMaaraLabel, ETpalveluMaara, ETkeskimJonoAikaLabel, ETkeskimJonoAika,
                ETkeskimPalveluAikaLabel, ETkeskimPalveluAika, ETkokonaisAikaLabel, ETkokonaisAika);
    }

    // Sama homma kun ylempi metodi
    private void addServicePointSection(GridPane grid, int startRow, Label... labels) {
        grid.add(labels[0], 0, startRow, 2, 1);
        labels[0].setPrefWidth(300);

        for (int i = 1; i < labels.length; i += 2) {
            grid.add(labels[i], 0, startRow + (i + 1) / 2);
            grid.add(labels[i + 1], 1, startRow + (i + 1) / 2);
        }
    }


    //Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

    @Override
    public void naytaHistoriaData(List<Tulokset> data) {
        Platform.runLater(() -> {
            if (data != null) {
                TableView<Tulokset> table = createHistoryTable();
                table.getItems().clear();
                table.getItems().addAll(data);
                showHistoryDialog(table);
            }
        });
    }

    @Override
    public void paivitaHistoriaYksityiskohdat(Tulokset tulos) {
        Platform.runLater(() -> {
            if (currentDetailView != null && tulos != null) {
                currentDetailView.updateDetails(tulos);
            }
        });
    }

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




