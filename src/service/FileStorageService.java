package service;

import model.Equipment;
import model.EquipmentType;
import model.Hero;
import model.HeroType;
import model.MatchRecord;
import model.MatchResult;
import model.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileStorageService {
    private final Path dataFile;

    public FileStorageService(Path dataFile) {
        this.dataFile = dataFile;
    }

    public void save(GameDataManager manager) throws IOException {
        Files.createDirectories(dataFile.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(dataFile)) {
            for (var team : manager.getTeams()) {
                writer.write("TEAM|" + team.getId() + "|" + team.getName() + "|" + String.join(",", team.getPlayerIds()));
                writer.newLine();
            }
            for (Equipment equipment : manager.getEquipmentItems()) {
                writer.write("EQUIP|" + equipment.getId() + "|" + equipment.getName() + "|" + equipment.getType()
                        + "|" + equipment.getRating() + "|" + equipment.getAttackBonus() + "|"
                        + equipment.getDefenseBonus() + "|" + equipment.getHpBonus() + "|"
                        + equipment.getSpeedBonus());
                writer.newLine();
            }
            for (Hero hero : manager.getHeroes()) {
                writer.write("HERO|" + hero.getId() + "|" + hero.getName() + "|" + hero.getType() + "|"
                        + hero.getAttack() + "|" + hero.getDefense() + "|" + hero.getHp() + "|"
                        + String.join(",", hero.getCompatibleEquipmentIds()));
                writer.newLine();
            }
            for (Player player : manager.getPlayers()) {
                writer.write("PLAYER|" + player.getId() + "|" + player.getName() + "|" + player.getTeamId() + "|"
                        + player.getLevel() + "|" + player.getWins() + "|" + player.getMatches() + "|"
                        + player.getEmail() + "|" + String.join(",", player.getOwnedHeroIds()) + "|"
                        + serializeHeroEquipment(player));
                writer.newLine();
            }
            for (MatchRecord match : manager.getMatches()) {
                writer.write("MATCH|" + match.getId() + "|" + match.getDate() + "|" + match.getTeamId() + "|"
                        + match.getOpponentTeamName() + "|" + match.getResult() + "|"
                        + serializePicks(match));
                writer.newLine();
            }
        }
    }

    public void load(GameDataManager manager) throws IOException {
        if (!Files.exists(dataFile)) {
            return;
        }
        manager.clearAllData();
        List<String[]> teams = new ArrayList<>();
        List<String[]> equipmentRows = new ArrayList<>();
        List<String[]> heroes = new ArrayList<>();
        List<String[]> players = new ArrayList<>();
        List<String[]> matches = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(dataFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split("\\|", -1);
                switch (parts[0]) {
                    case "TEAM" -> teams.add(parts);
                    case "EQUIP" -> equipmentRows.add(parts);
                    case "HERO" -> heroes.add(parts);
                    case "PLAYER" -> players.add(parts);
                    case "MATCH" -> matches.add(parts);
                    default -> throw new IOException("Unknown record type: " + parts[0]);
                }
            }
        }

        for (String[] parts : teams) {
            loadTeam(manager, parts);
        }
        for (String[] parts : equipmentRows) {
            loadEquipment(manager, parts);
        }
        for (String[] parts : heroes) {
            loadHero(manager, parts);
        }
        for (String[] parts : players) {
            loadPlayer(manager, parts);
        }
        for (String[] parts : matches) {
            loadMatch(manager, parts);
        }
    }

    private void loadPlayer(GameDataManager manager, String[] parts) {
        Player player = new Player(parts[1], parts[2], "player123", parts[3],
                Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), parts[7]);
        if (!parts[8].isBlank()) {
            for (String heroId : parts[8].split(",")) {
                player.addHero(heroId);
            }
        }
        if (parts.length > 9 && !parts[9].isBlank()) {
            for (String pair : parts[9].split(";")) {
                String[] values = pair.split(":");
                if (values.length == 2) {
                    for (String equipmentId : values[1].split(",")) {
                        if (!equipmentId.isBlank()) {
                            player.equipItem(values[0], equipmentId);
                        }
                    }
                }
            }
        }
        manager.addPlayer(player);
    }

    private void loadHero(GameDataManager manager, String[] parts) {
        Hero hero = new Hero(parts[1], parts[2], HeroType.valueOf(parts[3]),
                Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
        if (!parts[7].isBlank()) {
            for (String equipmentId : parts[7].split(",")) {
                hero.addCompatibleEquipment(equipmentId);
            }
        }
        manager.addHero(hero);
    }

    private void loadEquipment(GameDataManager manager, String[] parts) {
        int hpBonus = parts.length > 7 ? Integer.parseInt(parts[7]) : 0;
        int speedBonus = parts.length > 8 ? Integer.parseInt(parts[8]) : 0;
        manager.addEquipment(new Equipment(parts[1], parts[2], EquipmentType.valueOf(parts[3]),
                Double.parseDouble(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6]),
                hpBonus, speedBonus));
    }

    private void loadTeam(GameDataManager manager, String[] parts) {
        var team = new model.Team(parts[1], parts[2]);
        if (!parts[3].isBlank()) {
            team.getPlayerIds().addAll(Arrays.asList(parts[3].split(",")));
        }
        manager.addTeam(team);
    }

    private void loadMatch(GameDataManager manager, String[] parts) {
        MatchRecord match = new MatchRecord(parts[1], LocalDate.parse(parts[2]), parts[3], parts[4],
                MatchResult.valueOf(parts[5]));
        if (parts.length > 6 && !parts[6].isBlank()) {
            for (String pair : parts[6].split(";")) {
                String[] values = pair.split(":");
                if (values.length == 2) {
                    match.addPick(values[0], values[1]);
                }
            }
        }
        manager.addMatch(match);
    }

    private String serializeHeroEquipment(Player player) {
        List<String> pairs = new ArrayList<>();
        for (var entry : player.getHeroEquipment().entrySet()) {
            pairs.add(entry.getKey() + ":" + String.join(",", entry.getValue()));
        }
        return String.join(";", pairs);
    }

    private String serializePicks(MatchRecord match) {
        List<String> pairs = new ArrayList<>();
        match.getPlayerHeroPicks().forEach((playerId, heroId) -> pairs.add(playerId + ":" + heroId));
        return String.join(";", pairs);
    }
}
