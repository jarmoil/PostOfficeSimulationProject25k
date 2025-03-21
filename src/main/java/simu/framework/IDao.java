package simu.framework;

import entity.*;

import java.util.List;

public interface IDao {
    void tallenna(Tulokset tulokset);
    List<Tulokset> lataaKaikki();
    void truncateAll();
    boolean deleteTulos(Tulokset tulos);
}
