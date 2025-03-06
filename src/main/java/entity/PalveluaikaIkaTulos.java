package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "palveluaika_ika")
public class PalveluaikaIkaTulos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "lowAge")
    private double lowAge;

    @Column(name = "middleAge")
    private double middleAge;

    @Column(name = "highAge")
    private double highAge;

    public PalveluaikaIkaTulos() {}

    public PalveluaikaIkaTulos(double lowAge, double middleAge, double highAge) {
        this.lowAge = lowAge;
        this.middleAge = middleAge;
        this.highAge = highAge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLowAge() {
        return lowAge;
    }

    public void setLowAge(double lowAge) {
        this.lowAge = lowAge;
    }

    public double getMiddleAge() {
        return middleAge;
    }

    public void setMiddleAge(double middleAge) {
        this.middleAge = middleAge;
    }

    public double getHighAge() {
        return highAge;
    }

    public void setHighAge(double highAge) {
        this.highAge = highAge;
    }
}