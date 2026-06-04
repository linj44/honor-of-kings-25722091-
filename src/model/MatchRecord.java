package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MatchRecord implements Searchable {
    private String id;
    private LocalDate date;
    private String teamId;
    private String opponentTeamName;
    private MatchResult result;
    private final Map<String, String> playerHeroPicks = new HashMap<>();

    public MatchRecord(String id, LocalDate date, String teamId, String opponentTeamName, MatchResult result) {
        this.id = id;
        this.date = date;
        this.teamId = teamId;
        this.opponentTeamName = opponentTeamName;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getOpponentTeamName() {
        return opponentTeamName;
    }

    public MatchResult getResult() {
        return result;
    }

    public Map<String, String> getPlayerHeroPicks() {
        return playerHeroPicks;
    }

    public void addPick(String playerId, String heroId) {
        playerHeroPicks.put(playerId, heroId);
    }

    @Override
    public String getSearchId() {
        return id;
    }

    @Override
    public String getSearchName() {
        return id;
    }

    @Override
    public boolean matchesQuery(String query) {
        return id.equalsIgnoreCase(query != null ? query.trim() : "");
    }
}
