package service;

import model.Admin;
import model.Equipment;
import model.Hero;
import model.MatchRecord;
import model.Person;
import model.Player;
import model.Searchable;
import model.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameDataManager {
    private final Map<String, Person> users = new HashMap<>();
    private final Map<String, Player> players = new HashMap<>();
    private final Map<String, Hero> heroes = new HashMap<>();
    private final Map<String, Equipment> equipment = new HashMap<>();
    private final Map<String, Team> teams = new HashMap<>();
    private final List<MatchRecord> matches = new ArrayList<>();

    public void registerUser(Person person) {
        users.put(person.getId(), person);
    }

    public Optional<Person> findUser(String id) {
        return Optional.ofNullable(users.get(id));
    }

    public Collection<Person> getUsers() {
        return users.values();
    }

    public void clearAllData() {
        users.clear();
        players.clear();
        heroes.clear();
        equipment.clear();
        teams.clear();
        matches.clear();
    }

    public void addPlayer(Player player) {
        if (players.containsKey(player.getId())) {
            throw new IllegalArgumentException("Duplicate player ID: " + player.getId());
        }
        players.put(player.getId(), player);
        registerUser(player);
    }

    public Optional<Player> findPlayer(String query) {
        return findByQuery(players.values(), query);
    }

    public Collection<Player> getPlayers() {
        return players.values();
    }

    public void removePlayer(String playerId) {
        Player player = players.remove(playerId);
        if (player == null) {
            throw new IllegalArgumentException("Player not found: " + playerId);
        }
        users.remove(playerId);
        Team team = teams.get(player.getTeamId());
        if (team != null) {
            team.removePlayer(playerId);
        }
        for (MatchRecord match : matches) {
            match.getPlayerHeroPicks().remove(playerId);
        }
    }

    public void addHero(Hero hero) {
        if (heroes.containsKey(hero.getId())) {
            throw new IllegalArgumentException("Duplicate hero ID: " + hero.getId());
        }
        heroes.put(hero.getId(), hero);
    }

    public Optional<Hero> findHero(String query) {
        return findByQuery(heroes.values(), query);
    }

    public Collection<Hero> getHeroes() {
        return heroes.values();
    }

    public void removeHero(String heroId) {
        if (heroes.remove(heroId) == null) {
            throw new IllegalArgumentException("Hero not found: " + heroId);
        }
        for (Player player : players.values()) {
            player.getOwnedHeroIds().remove(heroId);
            player.getHeroEquipment().remove(heroId);
        }
        for (MatchRecord match : matches) {
            match.getPlayerHeroPicks().entrySet().removeIf(entry -> entry.getValue().equals(heroId));
        }
    }

    public void addEquipment(Equipment item) {
        if (equipment.containsKey(item.getId())) {
            throw new IllegalArgumentException("Duplicate equipment ID: " + item.getId());
        }
        equipment.put(item.getId(), item);
    }

    public Optional<Equipment> findEquipment(String query) {
        return findByQuery(equipment.values(), query);
    }

    public Collection<Equipment> getEquipmentItems() {
        return equipment.values();
    }

    public void removeEquipment(String equipmentId) {
        if (equipment.remove(equipmentId) == null) {
            throw new IllegalArgumentException("Equipment not found: " + equipmentId);
        }
        for (Hero hero : heroes.values()) {
            hero.getCompatibleEquipmentIds().remove(equipmentId);
        }
        for (Player player : players.values()) {
            for (List<String> items : player.getHeroEquipment().values()) {
                items.remove(equipmentId);
            }
        }
    }

    public void addTeam(Team team) {
        if (teams.containsKey(team.getId())) {
            throw new IllegalArgumentException("Duplicate team ID: " + team.getId());
        }
        teams.put(team.getId(), team);
    }

    public Optional<Team> findTeam(String query) {
        return findByQuery(teams.values(), query);
    }

    public Team getTeam(String teamId) {
        Team team = teams.get(teamId);
        if (team == null) {
            throw new IllegalArgumentException("Team not found: " + teamId);
        }
        return team;
    }

    public Collection<Team> getTeams() {
        return teams.values();
    }

    public void removeTeam(String teamId) {
        Team team = teams.remove(teamId);
        if (team == null) {
            throw new IllegalArgumentException("Team not found: " + teamId);
        }
        for (String playerId : team.getPlayerIds()) {
            Player player = players.get(playerId);
            if (player != null) {
                player.setTeamId("");
            }
        }
        matches.removeIf(match -> match.getTeamId().equals(teamId));
    }

    public void addMatch(MatchRecord match) {
        if (matches.stream().anyMatch(existing -> existing.getId().equals(match.getId()))) {
            throw new IllegalArgumentException("Duplicate match ID: " + match.getId());
        }
        matches.add(match);
    }

    public List<MatchRecord> getMatches() {
        return new ArrayList<>(matches);
    }

    public void removeMatch(String matchId) {
        boolean removed = matches.removeIf(match -> match.getId().equals(matchId));
        if (!removed) {
            throw new IllegalArgumentException("Match not found: " + matchId);
        }
    }

    public List<MatchRecord> getMatchesForTeam(String teamId) {
        List<MatchRecord> result = new ArrayList<>();
        for (MatchRecord match : matches) {
            if (match.getTeamId().equals(teamId)) {
                result.add(match);
            }
        }
        result.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        return result;
    }

    public List<MatchRecord> getMatchesForPlayer(String playerId) {
        Player player = players.get(playerId);
        if (player == null) {
            return new ArrayList<>();
        }
        List<MatchRecord> result = new ArrayList<>();
        for (MatchRecord match : getMatchesForTeam(player.getTeamId())) {
            if (match.getPlayerHeroPicks().containsKey(playerId)) {
                result.add(match);
            }
        }
        return result;
    }

    private <T extends Searchable> Optional<T> findByQuery(Collection<T> items, String query) {
        if (query == null || query.isBlank()) {
            return Optional.empty();
        }
        for (T item : items) {
            if (item.matchesQuery(query)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
}
