import java.time.LocalDate;

public class Referee extends Person {
    private String associationSection;
    private int matchesOfficiated;

    public Referee(String name, String surname, Gender gender, LocalDate birthDate, String section) {
        super(name, surname, gender, birthDate);
        this.associationSection = (section == null || section.isEmpty()) ? "Sezione N/A" : section;
        this.matchesOfficiated = 0;
    }

    public void incrementMatches() {
        this.matchesOfficiated++;
    }

    @Override
    public String getFullInfo() {
        return "Arbitro: " + super.getFullInfo() + " [" + associationSection + "]";
    }

    public String getAssociationSection() { return associationSection; }
    public int getMatchesOfficiated() { return matchesOfficiated; }
}