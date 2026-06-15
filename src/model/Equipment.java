package model;

public class Equipment implements Searchable {
    private String id;
    private String name;
    private EquipmentType type;
    private double rating;
    private int attackBonus;
    private int defenseBonus;
    private int hpBonus;
    private int speedBonus;

    public Equipment(String id, String name, EquipmentType type, double rating,
                     int attackBonus, int defenseBonus) {
        this(id, name, type, rating, attackBonus, defenseBonus, 0, 0);
    }

    public Equipment(String id, String name, EquipmentType type, double rating,
                     int attackBonus, int defenseBonus, int hpBonus, int speedBonus) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.rating = rating;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;
        this.hpBonus = hpBonus;
        this.speedBonus = speedBonus;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EquipmentType getType() {
        return type;
    }

    public double getRating() {
        return rating;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    public int getHpBonus() {
        return hpBonus;
    }

    public int getSpeedBonus() {
        return speedBonus;
    }

    public String getTypeLabel() {
        return switch (type) {
            case WEAPON -> "Weapon";
            case ARMOR -> "Armor";
            case SHOES -> "Boots";
            case ACCESSORY -> "Accessory";
        };
    }

    public String formatDetailLine() {
        return String.format("    - %s (%s) ATK+%d DEF+%d HP+%d SPD+%d | Proficiency: %.1f/5.0",
                name, getTypeLabel(), attackBonus, defenseBonus, hpBonus, speedBonus, rating);
    }

    @Override
    public String getSearchId() {
        return id;
    }

    @Override
    public String getSearchName() {
        return name;
    }

    @Override
    public boolean matchesQuery(String query) {
        if (query == null) {
            return false;
        }
        String normalized = query.trim().toLowerCase();
        return id.equalsIgnoreCase(normalized) || name.toLowerCase().contains(normalized);
    }
}
