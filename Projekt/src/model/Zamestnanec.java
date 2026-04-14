package model;

import java.io.Serializable;
import java.util.*;


public abstract class Zamestnanec implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String jmeno;
    private String prijmeni;
    private int rokNarozeni;
    private Map<Integer, UrovenSpoluprace> spolupracovnici;
    
    public Zamestnanec(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
        this.spolupracovnici = new HashMap<>();
    }
    
    public int getId() { return id; }
    public String getJmeno() { return jmeno; }
    public String getPrijmeni() { return prijmeni; }
    public int getRokNarozeni() { return rokNarozeni; }
    public Map<Integer, UrovenSpoluprace> getSpolupracovnici() { return spolupracovnici; }
    
   
    public void pridatSpolupraci(int idKolegy, UrovenSpoluprace uroven) {
        spolupracovnici.put(idKolegy, uroven);
    }
    
   
    public void odebratSpolupraci(int idKolegy) {
        spolupracovnici.remove(idKolegy);
    }
    
    
    public int getPocetSpolupracovniku() {
        return spolupracovnici.size();
    }
    
    
    public double getPrumernaUrovenSpoluprace() {
        if (spolupracovnici.isEmpty()) {
            return 0;
        }
        int soucet = 0;
        for (UrovenSpoluprace uroven : spolupracovnici.values()) {
            soucet += uroven.getHodnota();
        }
        return (double) soucet / spolupracovnici.size();
    }
    
   
    public abstract String spustitDovednost(Map<Integer, Zamestnanec> vsichniZamestnanci);
    
    @Override
    public String toString() {
        return String.format("ID: %d | %s %s | Rok: %d | Počet spolupracovníků: %d",
                id, jmeno, prijmeni, rokNarozeni, spolupracovnici.size());
    }
}