public enum Role {
    GK("Goalkeeper", "GK"),
    CB("Centre-Back", "CB"),
    RB("Right-Back", "RB"),
    LB("Left-Back", "LB"),
    RWB("Right Wing-Back", "RWB"),
    LWB("Left Wing-Back", "LWB"),
    CDM("Central Defensive Midfielder", "CDM"),
    CM("Central Midfielder", "CM"),
    RM("Right Midfielder", "RM"),
    LM("Left Midfielder", "LM"),
    CAM("Central Offensive Midfielder", "CAM"),
    RW("Right Winger", "RW"),
    LW("Left Winger", "LW"),
    CF("Second Striker", "CF"),
    ST("Striker", "ST");

    private final String description;
    private final String shortName;

    Role(String description, String shortName) {
        this.description = description;
        this.shortName = shortName;
    }

    public String getShortName() { return shortName; }

    @Override
    public String toString() {
        return shortName + " - " + description;
    }
}