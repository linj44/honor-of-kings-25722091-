package model;

import java.util.ArrayList;
import java.util.List;

public class Team implements Searchable, Reportable {
    private String id;
    private String name;
    private final List<String> playerIds = new ArrayList<>();

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPlayerIds() {
        return playerIds;
    }

    public void addPlayer(String playerId) {
        if (!playerIds.contains(playerId)) {
            playerIds.add(playerId);
        }
    }

    public void removePlayer(String playerId) {
        playerIds.remove(playerId);
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
        return "Team: " + name + " (" + id + ") | Members: " + playerIds.size();
    }
}
