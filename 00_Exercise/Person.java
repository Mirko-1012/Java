import java.time.LocalDate;

public class Person {
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate birthDate;

    public Person(String name, String surname, Gender gender, LocalDate birthDate) {
        this.name = sanitize(input(name), "N/A");
        this.surname = sanitize(input(surname), "Unknown");
        this.gender = (gender == null) ? Gender.M : gender;
        this.birthDate = (birthDate == null) ? LocalDate.of(2000, 1, 1) : birthDate;
    }

    private String input(String s) {
        return (s == null) ? "" : s.trim();
    }

    private String sanitize(String input, String defaultValue) {
        if (input.isEmpty()) return defaultValue;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Gender getGender() { return gender; }
    public LocalDate getBirthDate() { return birthDate; }

    public String getFullInfo() {
        return name + " " + surname;
    }
}