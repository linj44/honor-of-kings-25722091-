package model;

public class Equipment implements Searchable {
    private String id;
    private String name;
    private EquipmentType type;
    private double rating;
    private int attackBonus;
    private int defenseBonus;

    public Equipment(String id, String name, EquipmentType type, double rating, int attackBonus, int defenseBonus) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.rating = rating;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;
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
