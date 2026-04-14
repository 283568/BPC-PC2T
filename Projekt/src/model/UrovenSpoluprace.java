package model;

public enum UrovenSpoluprace {
    
    SPATNA(1, "špatná"),
    PRUMERNA(2, "průměrná"),
    DOBRA(3, "dobrá");
    
    private final int hodnota;
    private final String popis;
    
    UrovenSpoluprace(int hodnota, String popis) {
        this.hodnota = hodnota;
        this.popis = popis;
    }
    
    public int getHodnota() {
        return hodnota;
    }
    
    public String getPopis() {
        return popis;
    }
    
    public static UrovenSpoluprace zHodnoty(int hodnota) {
        for (UrovenSpoluprace u : values()) {
            if (u.hodnota == hodnota) {
                return u;
            }
        }
        throw new IllegalArgumentException("Neplatná hodnota: " + hodnota);
    }
    
    @Override
    public String toString() {
        return popis;
    }
}