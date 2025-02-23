package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;

public class Visualisointi extends Canvas implements IVisualisointi{

    private final GraphicsContext gc;

    double i = 0;
    double j = 10;


    public Visualisointi(int w, int h) {
        super(w, h);
        gc = this.getGraphicsContext2D();
        tyhjennaNaytto();
    }


    public void tyhjennaNaytto() {
        gc.setFill(Color.SANDYBROWN);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    /*
    Animaatio shitit on tällä hetkellä kaikki SimulaattorinGUI luokassa, ehkä voisi siirtää tänne?
    Varmaan parempi sillein, mut sen voi tehä sit ku on aikaa.
     */

    //jotenkin tällein, pitää varmaan jokaiselle palvelupisteelle
    // olla oma metodi et ne pystyy sijottamaan eri kohtiin, kai?

    /*public void uusiPalvelupiste(int x, int y) {
        gc.setFill(Color.BROWN);
        gc.fillRect(i,j,40,20);
        i = x;
        j = y;
    }*/
}
