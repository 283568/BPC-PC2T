package module;

public enum CooperationLevel {
    SPATNA("Špatná", 1),
    PRUMERNA("Průměrná", 2),
    DOBRA("Dobrá", 3);

    private final String displayName;
    private final int value;

    CooperationLevel(String displayName, int value) {
        this.displayName = displayName;
        this.value = value;
    }

    public int getValue() { return value; }

    public static int getMaxPossibleValue() {
        int maxValue = 0;
        for (CooperationLevel cl : CooperationLevel.values()) {
            if (cl.getValue() > maxValue) {
                maxValue = cl.getValue();
            }
        }
        return maxValue;
    }
}