package module;

public class SecuritySpecialist extends Employee {

    public SecuritySpecialist(int id, String name, String surname, int year) {
        super(id, name, surname, year);
    }

    @Override
    public String getGroup() {
        return "Bezpečnostní specialista";
    }
}
