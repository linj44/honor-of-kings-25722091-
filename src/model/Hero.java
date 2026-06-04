package model;

import java.util.ArrayList;
import java.util.List;

public class Hero implements Searchable, Reportable {
    private String id;
    private String name;
    private HeroType type;
    private int attack;
    private int defense;
    private int hp;
    private final List<String> compatibleEquipmentIds = new ArrayList<>();

    public Hero(String id, String name, HeroType type, int attack, int defense, int hp) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HeroType getType() {
        return type;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getHp() {
        return hp;
    }

    public List<String> getCompatibleEquipmentIds() {
        return compatibleEquipmentIds;
    }

    public void addCompatibleEquipment(String equipmentId) {
        if (!compatibleEquipmentIds.contains(equipmentId)) {
            compatibleEquipmentIds.add(equipmentId);
        }
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

    @Override
    public String generateReport() {
        return "Hero: " + name + " (" + type + ") | ATK " + attack + " DEF " + defense + " HP " + hp;
    }
}
