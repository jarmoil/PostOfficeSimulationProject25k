package view;

import entity.IServicePointTulos;
import entity.PalveluaikaIkaTulos;
import entity.Tulokset;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

public class HistoryDetailView extends VBox {

    public HistoryDetailView() {
        setPadding(new Insets(10));
        setStyle("-fx-background-color: #f0f0f0;");
        setPrefWidth(400);
    }

    public void updateDetails(Tulokset result) {
        getChildren().clear();
        if (result == null) return;

        // Add service point details
        addServicePointDetails("Pakettiautomaatti", result.getPakettiautomaattiTulos());
        addServicePointDetails("Palvelunvalinta", result.getPalvelunvalintaTulos());
        addServicePointDetails("Nouto/lähetä", result.getNoutolahetaTulos());
        addServicePointDetails("Erityistapaukset", result.getErityistapauksetTulos());

        // Add age group details
        addSectionHeader("Keskim. Palveluaika iän mukaan:");
        PalveluaikaIkaTulos pit = result.getPalveluaikaIkaTulos();
        addDetailRow("18 - 40v:", pit.getLowAge());
        addDetailRow("41 - 60v:", pit.getMiddleAge());
        addDetailRow("61v +:", pit.getHighAge());
    }

    private void addServicePointDetails(String title, IServicePointTulos tulos) {
        addSectionHeader(title);
        addDetailRow("Jonossa:", tulos.getJonossa());
        addDetailRow("Palveltu:", tulos.getPalveltu());
        addDetailRow("Keskim. jonotusaika:", tulos.getKeskimjonoaika());
        addDetailRow("Keskim. palveluaika:", tulos.getKeskimpalveluaika());
        addDetailRow("Kokonaisaika:", tulos.getKokonaisaika());
        addDetailRow("Syötetty jakauma: ", tulos.getDistribuutio());
        addDetailRow("Syötetty keskiarvo: ", tulos.getMean());
        addDetailRow("Syötetty varianssi: ", tulos.getVar());
        addSeparator();
    }

    private void addSectionHeader(String text) {
        Label header = new Label(text);
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 5 0 5 0;");
        getChildren().add(header);
    }

    private void addDetailRow(String label, Number value) {
        DecimalFormat df = new DecimalFormat("#0.00");
        HBox row = new HBox(10);
        Label labelNode = new Label(label);
        labelNode.setMinWidth(120);
        Label valueNode = new Label(value instanceof Double ? df.format(value) : value.toString());
        row.getChildren().addAll(labelNode, valueNode);
        getChildren().add(row);
    }

    private void addDetailRow(String label, String value) {
        HBox row = new HBox(10);
        Label labelNode = new Label(label);
        labelNode.setMinWidth(120);
        Label valueNode = new Label(value);
        row.getChildren().addAll(labelNode, valueNode);
        getChildren().add(row);
    }

    private void addSeparator() {
        Separator separator = new Separator();
        separator.setPadding(new Insets(5, 0, 5, 0));
        getChildren().add(separator);
    }
}