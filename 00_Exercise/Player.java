import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Player extends Person {
    private int shirtNumber;
    private Role role;
    private Nationality nationality;
    private boolean captain;
    private String fiscalCode;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Player(String name, String surname, int shirtNumber, Role role, Nationality nationality, boolean captain, String birthDateStr, Gender gender) {
        super(name, surname, gender, parseDate(birthDateStr));

        setShirtNumber(shirtNumber);
        this.role = (role == null) ? Role.CM : role;
        this.nationality = (nationality == null) ? Nationality.IT : nationality;
        this.captain = captain;
        this.fiscalCode = calculateFiscalCode();
    }

    private static LocalDate parseDate(String dateStr) {
        try {
            if (dateStr == null || dateStr.isEmpty() || dateStr.equals("dd/mm/yyyy")) {
                return LocalDate.of(2000, 1, 1);
            }
            return LocalDate.parse(dateStr, FORMATTER);
        } catch (Exception e) {
            return LocalDate.of(2000, 1, 1);
        }
    }

    private String calculateFiscalCode() {
        String cfSurname = (getSurname().replace(" ", "") + "XXX").substring(0, 3).toUpperCase();
        String cfName = (getName().replace(" ", "") + "XXX").substring(0, 3).toUpperCase();

        int yearShort = getBirthDate().getYear() % 100;
        int dayInt = (getGender() == Gender.F) ? getBirthDate().getDayOfMonth() + 40 : getBirthDate().getDayOfMonth();

        return String.format("%s%s%02dX%02dX000Y", cfSurname, cfName, yearShort, dayInt);
    }

    public void setShirtNumber(int shirtNumber) {
        this.shirtNumber = (shirtNumber >= 1 && shirtNumber <= 99) ? shirtNumber : 0;
    }

    @Override
    public String getFullInfo() {
        return super.getFullInfo() + " (Giocatore, n. " + shirtNumber + ")";
    }

    public int getShirtNumber() { return shirtNumber; }
    public String getFiscalCode() { return fiscalCode; }
    public Role getRole() { return role; }
    public Nationality getNationality() { return nationality; }
    public boolean isCaptain() { return captain; }
}