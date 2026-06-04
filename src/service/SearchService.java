package service;

import model.Equipment;
import model.Hero;
import model.MatchRecord;
import model.MatchResult;
import model.Player;
import model.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchService {
    private final GameDataManager manager;

    public SearchService(GameDataManager manager) {
        this.manager = manager;
    }

    public String buildPlayerLookupReport(String query) {
        Optional<Player> playerOptional = manager.findPlayer(query);
        if (playerOptional.isEmpty()) {
            throw new IllegalArgumentException("Player not found: " + query);
        }
        Player player = playerOptional.get();
        StringBuilder builder = new StringBuilder();
        builder.append("=== Player Lookup ===\n");
        builder.append("ID: ").append(player.getId()).append('\n');
        builder.append("Name: ").append(player.getName()).append('\n');
        Team team = manager.findTeam(player.getTeamId()).orElse(null);
        builder.append("Team: ").append(team != null ? team.getName() : "Unknown").append('\n');
        builder.append("Level: ").append(player.getLevel()).append('\n');
        builder.append(String.format("Win Rate: %.1f%% (%d wins / %d matches)%n",
                player.getWinRate(), player.getWins(), player.getMatches()));
        builder.append("Owned Heroes:\n");
        for (String heroId : player.getOwnedHeroIds()) {
            Hero hero = manager.findHero(heroId).orElse(null);
            if (hero == null) {
                continue;
            }
            builder.append("  - ").append(hero.getName()).append(" (").append(hero.getType()).append(")\n");
            builder.append("    Equipped: ");
            List<String> equippedNames = new ArrayList<>();
            for (String equipmentId : player.getEquipmentForHero(heroId)) {
                manager.findEquipment(equipmentId).ifPresent(item -> equippedNames.add(item.getName()));
            }
            builder.append(equippedNames.isEmpty() ? "None" : String.join(", ", equippedNames)).append('\n');
        }
        return builder.toString();
    }

    public String buildTeamOverviewReport(String query) {
        Team team = manager.findTeam(query)
                .orElseThrow(() -> new IllegalArgumentException("Team not found: " + query));
        List<Player> members = team.getPlayerIds().stream()
                .map(id -> manager.findPlayer(id).orElse(null))
                .filter(player -> player != null)
                .collect(Collectors.toList());
        double averageLevel = members.stream().mapToInt(Player::getLevel).average().orElse(0.0);
        List<MatchRecord> teamMatches = manager.getMatchesForTeam(team.getId());
        int totalMatches = teamMatches.size();
        long wins = teamMatches.stream().filter(match -> match.getResult() == MatchResult.WIN).count();
        double winRate = totalMatches == 0 ? 0.0 : wins * 100.0 / totalMatches;
        Player topPlayer = members.stream()
                .sorted((a, b) -> Double.compare(b.getWinRate(), a.getWinRate()))
                .findFirst()
                .orElse(null);

        StringBuilder builder = new StringBuilder();
        builder.append("=== Team Overview ===\n");
        builder.append("Team: ").append(team.getName()).append(" (").append(team.getId()).append(")\n");
        builder.append("Members:\n");
        for (Player member : members) {
            builder.append(String.format("  - %s (%s) | Level %d | Win Rate %.1f%%%n",
                    member.getName(), member.getId(), member.getLevel(), member.getWinRate()));
        }
        builder.append(String.format("Average Level: %.1f%n", averageLevel));
        builder.append("Total Matches: ").append(totalMatches).append('\n');
        builder.append(String.format("Team Win Rate: %.1f%%%n", winRate));
        if (topPlayer != null) {
            builder.append(String.format("Top Player: %s (%.1f%% win rate)%n", topPlayer.getName(), topPlayer.getWinRate()));
        }
        return builder.toString();
    }

    public String buildHeroDetailsReport(String query) {
        Hero hero = manager.findHero(query)
                .orElseThrow(() -> new IllegalArgumentException("Hero not found: " + query));
        List<Player> owners = manager.getPlayers().stream()
                .filter(player -> player.getOwnedHeroIds().contains(hero.getId()))
                .collect(Collectors.toList());
        List<String> compatibleEquipment = hero.getCompatibleEquipmentIds().stream()
                .map(id -> manager.findEquipment(id).map(Equipment::getName).orElse(id))
                .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        builder.append("=== Hero Details ===\n");
        builder.append("Name: ").append(hero.getName()).append('\n');
        builder.append("Type: ").append(hero.getType()).append('\n');
        builder.append(String.format("Base Stats: ATK %d | DEF %d | HP %d%n",
                hero.getAttack(), hero.getDefense(), hero.getHp()));
        builder.append("Compatible Equipment: ").append(String.join(", ", compatibleEquipment)).append('\n');
        builder.append("Owners: ");
        builder.append(owners.stream().map(Player::getName).collect(Collectors.joining(", "))).append('\n');
        builder.append("Recommended Equipment: ")
                .append(recommendEquipment(hero))
                .append('\n');
        return builder.toString();
    }

    private String recommendEquipment(Hero hero) {
        return hero.getCompatibleEquipmentIds().stream()
                .map(id -> manager.findEquipment(id).orElse(null))
                .filter(item -> item != null)
                .sorted((a, b) -> Double.compare(b.getRating(), a.getRating()))
                .limit(2)
                .map(Equipment::getName)
                .collect(Collectors.joining(", "));
    }

    public String buildMatchHistoryReportForPlayer(String playerQuery, int count) {
        Player player = manager.findPlayer(playerQuery)
                .orElseThrow(() -> new IllegalArgumentException("Player not found: " + playerQuery));
        return buildMatchHistoryReport(manager.getMatchesForPlayer(player.getId()), count, player.getName());
    }

    public String buildMatchHistoryReportForTeam(String teamQuery, int count) {
        Team team = manager.findTeam(teamQuery)
                .orElseThrow(() -> new IllegalArgumentException("Team not found: " + teamQuery));
        return buildMatchHistoryReport(manager.getMatchesForTeam(team.getId()), count, team.getName());
    }

    private String buildMatchHistoryReport(List<MatchRecord> matches, int count, String subjectName) {
        int wins = 0;
        int losses = 0;
        Map<String, Integer> heroPickCounts = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        builder.append("=== Match History: ").append(subjectName).append(" ===\n");
        int limit = Math.min(count, matches.size());
        for (int i = 0; i < limit; i++) {
            MatchRecord match = matches.get(i);
            if (match.getResult() == MatchResult.WIN) {
                wins++;
            } else {
                losses++;
            }
            builder.append(String.format("%s | Opponent: %s | Result: %s | Heroes: %s%n",
                    match.getDate(), match.getOpponentTeamName(), match.getResult(),
                    String.join(", ", match.getPlayerHeroPicks().values())));
            for (String heroId : match.getPlayerHeroPicks().values()) {
                heroPickCounts.merge(heroId, 1, Integer::sum);
            }
        }
        builder.append(String.format("Record in last %d matches: %d wins, %d losses%n", limit, wins, losses));
        builder.append("Hero Pick Rate:\n");
        for (Map.Entry<String, Integer> entry : heroPickCounts.entrySet()) {
            String heroName = manager.findHero(entry.getKey()).map(Hero::getName).orElse(entry.getKey());
            double rate = limit == 0 ? 0.0 : entry.getValue() * 100.0 / limit;
            builder.append(String.format("  - %s: %.1f%%%n", heroName, rate));
        }
        return builder.toString();
    }
}
