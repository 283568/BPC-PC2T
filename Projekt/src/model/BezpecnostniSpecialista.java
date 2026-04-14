package model;

import java.util.*;


public class BezpecnostniSpecialista extends Zamestnanec {
    private static final long serialVersionUID = 1L;
    
    public BezpecnostniSpecialista(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }
    
    
    public double vypocitatRizikoveSkore() {
        int pocet = getPocetSpolupracovniku();
        double prumernaUroven = getPrumernaUrovenSpoluprace();
        
        if (pocet == 0) {
            return 0;
        }
        
        
        return pocet * (4 - prumernaUroven);
    }
    

    @Override
    public String spustitDovednost(Map<Integer, Zamestnanec> vsichniZamestnanci) {
        double skore = vypocitatRizikoveSkore();
        int pocet = getPocetSpolupracovniku();
        double prumernaUroven = getPrumernaUrovenSpoluprace();
        
        String rizikovost;
        if (skore <= 2) {
            rizikovost = "NÍZKÉ riziko";
        } else if (skore <= 6) {
            rizikovost = "STŘEDNÍ riziko";
        } else {
            rizikovost = "VYSOKÉ riziko";
        }
        
        return String.format(
            "Bezpečnostní analýza zaměstnance %s %s:\n" +
            "  Počet spolupracovníků: %d\n" +
            "  Průměrná kvalita spolupráce: %.2f\n" +
            "  Rizikové skóre: %.2f\n" +
            "  Hodnocení: %s",
            getJmeno(), getPrijmeni(), pocet, prumernaUroven, skore, rizikovost
        );
    }
    
    @Override
    public String toString() {
        return "[Bezpečnostní specialista] " + super.toString();
    }
}