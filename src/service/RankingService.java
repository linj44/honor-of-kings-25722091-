package service;

import model.Equipment;
import model.Hero;
import model.MatchRecord;
import model.MatchResult;
import model.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingService {
    private final GameDataManager manager;

    public RankingService(GameDataManager manager) {
        this.manager = manager;
    }

    public String buildEquipmentStatisticsReport() {
        Map<String, Integer> usageCount = new HashMap<>();
        Map<String, Integer> heroUsage = new HashMap<>();
        Map<String, Double> winContribution = new HashMap<>();

        for (Player player : manager.getPlayers()) {
            for (List<String> items : player.getHeroEquipment().values()) {
                for (String equipmentId : items) {
                    usageCount.merge(equipmentId, 1, Integer::sum);
                    heroUsage.merge(equipmentId, 1, Integer::sum);
                }
            }
        }

        for (MatchRecord match : manager.getMatches()) {
            double contribution = match.getResult() == MatchResult.WIN ? 1.0 : 0.0;
            for (String heroId : match.getPlayerHeroPicks().values()) {
                for (Player player : manager.getPlayers()) {
                    for (Map.Entry<String, List<String>> entry : player.getHeroEquipment().entrySet()) {
                        if (entry.getKey().equals(heroId)) {
                            for (String equipmentId : entry.getValue()) {
                                winContribution.merge(equipmentId, contribution, Double::sum);
                            }
                        }
                    }
                }
            }
        }

        List<EquipmentScore> scores = new ArrayList<>();
        for (Equipment equipment : manager.getEquipmentItems()) {
            int usage = usageCount.getOrDefault(equipment.getId(), 0);
            int heroesUsing = heroUsage.getOrDefault(equipment.getId(), 0);
            double wins = winContribution.getOrDefault(equipment.getId(), 0.0);
            double score = usage * 1.5 + equipment.getRating() * 2.0 + heroesUsing + wins;
            scores.add(new EquipmentScore(equipment, usage, heroesUsing, wins, score));
        }

        scores.sort(Comparator.comparingDouble(EquipmentScore::score).reversed());

        StringBuilder builder = new StringBuilder();
        builder.append("=== Equipment Statistics ===\n");
        builder.append("Ranking formula: score = usageCount * 1.5 + averageRating * 2 + heroesUsing + winContribution\n\n");
        int rank = 1;
        for (EquipmentScore item : scores) {
            builder.append(String.format("%d. %s | usage=%d | heroes=%d | rating=%.1f | winContribution=%.0f | score=%.2f%n",
                    rank++, item.equipment().getName(), item.usage(), item.heroesUsing(),
                    item.equipment().getRating(), item.winContribution(), item.score()));
        }
        return builder.toString();
    }

    public String buildLeaderboardReport(int topX, String metric) {
        List<Player> players = new ArrayList<>(manager.getPlayers());
        Comparator<Player> comparator;
        switch (metric.toLowerCase()) {
            case "level":
                comparator = Comparator.comparingInt(Player::getLevel)
                        .thenComparing(Player::getName);
                break;
            case "matches":
                comparator = Comparator.comparingInt(Player::getMatches)
                        .thenComparing(Player::getName);
                break;
            case "score":
                comparator = Comparator.comparingDouble(this::customScore)
                        .thenComparing(Player::getName);
                break;
            case "winrate":
            default:
                comparator = Comparator.comparingDouble(Player::getWinRate)
                        .thenComparingInt(Player::getMatches)
                        .thenComparing(Player::getName);
                break;
        }

        players.sort(comparator.reversed());
        StringBuilder builder = new StringBuilder();
        builder.append("=== Leaderboard (Top ").append(topX).append(" by ").append(metric).append(") ===\n");
        builder.append("Tie handling: sort by primary metric, then matches/level/name as secondary keys.\n\n");
        int limit = Math.min(topX, players.size());
        for (int i = 0; i < limit; i++) {
            Player player = players.get(i);
            builder.append(String.format("%d. %s | Level %d | Matches %d | Win Rate %.1f%% | Score %.1f%n",
                    i + 1, player.getName(), player.getLevel(), player.getMatches(),
                    player.getWinRate(), customScore(player)));
        }
        return builder.toString();
    }

    private double customScore(Player player) {
        return player.getLevel() * 1.2 + player.getWinRate() * 0.5 + player.getMatches() * 0.3;
    }

    private record EquipmentScore(Equipment equipment, int usage, int heroesUsing,
                                  double winContribution, double score) {
    }
}
