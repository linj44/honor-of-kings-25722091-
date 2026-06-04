package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends Person implements Reportable {
    private String teamId;
    private int level;
    private int wins;
    private int matches;
    private String email;
    private final List<String> ownedHeroIds = new ArrayList<>();
    private final Map<String, List<String>> heroEquipment = new HashMap<>();

    public Player(String id, String name, String password, String teamId, int level, int wins, int matches, String email) {
        super(id, name, password, Role.PLAYER);
        this.teamId = teamId;
        this.level = level;
        this.wins = wins;
        this.matches = matches;
        this.email = email;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getOwnedHeroIds() {
        return ownedHeroIds;
    }

    public Map<String, List<String>> getHeroEquipment() {
        return heroEquipment;
    }

    public double getWinRate() {
        if (matches == 0) {
            return 0.0;
        }
        return (wins * 100.0) / matches;
    }

    public void addHero(String heroId) {
        if (!ownedHeroIds.contains(heroId)) {
            ownedHeroIds.add(heroId);
        }
        heroEquipment.putIfAbsent(heroId, new ArrayList<>());
    }

    public void equipItem(String heroId, String equipmentId) {
        heroEquipment.putIfAbsent(heroId, new ArrayList<>());
        List<String> items = heroEquipment.get(heroId);
        if (!items.contains(equipmentId)) {
            items.add(equipmentId);
        }
    }

    public List<String> getEquipmentForHero(String heroId) {
        return heroEquipment.getOrDefault(heroId, new ArrayList<>());
    }

    @Override
    public String describeRole() {
        return "Player account";
    }

    @Override
    public String generateReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("Player ID: ").append(getId()).append('\n');
        builder.append("Name: ").append(getName()).append('\n');
        builder.append("Team ID: ").append(teamId).append('\n');
        builder.append("Level: ").append(level).append('\n');
        builder.append(String.format("Win Rate: %.1f%% (%d/%d)%n", getWinRate(), wins, matches));
        builder.append("Email: ").append(email).append('\n');
        builder.append("Owned Heroes: ").append(ownedHeroIds).append('\n');
        return builder.toString();
    }
}
