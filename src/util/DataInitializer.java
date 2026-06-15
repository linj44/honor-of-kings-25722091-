package util;

import model.Equipment;
import model.EquipmentType;
import model.Hero;
import model.HeroType;
import model.MatchRecord;
import model.MatchResult;
import model.Player;
import model.Team;
import service.GameDataManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataInitializer {
    private DataInitializer() {
    }

    public static void loadSampleData(GameDataManager manager) {
        createTeams(manager);
        createEquipment(manager);
        createHeroes(manager);
        createPlayers(manager);
        createMatches(manager);
    }

    private static void createTeams(GameDataManager manager) {
        Team dragon = new Team("T001", "Dragon Warriors");
        Team storm = new Team("T002", "Storm Legends");
        Team jade = new Team("T003", "Jade Guardians");
        manager.addTeam(dragon);
        manager.addTeam(storm);
        manager.addTeam(jade);
    }

    private static void createEquipment(GameDataManager manager) {
        addEquipment(manager, "E001", "Endless Edge", EquipmentType.WEAPON, 4.8, 130, 0, 0, 0);
        addEquipment(manager, "E002", "Storm Bow", EquipmentType.WEAPON, 4.4, 70, 0, 0, 0);
        addEquipment(manager, "E003", "Arcane Staff", EquipmentType.WEAPON, 4.5, 90, 0, 0, 0);
        addEquipment(manager, "E004", "Guardian Shield", EquipmentType.ARMOR, 4.2, 0, 110, 0, 0);
        addEquipment(manager, "E005", "Dragon Armor", EquipmentType.ARMOR, 4.7, 0, 90, 500, 0);
        addEquipment(manager, "E006", "Swift Boots", EquipmentType.SHOES, 4.1, 0, 0, 0, 60);
        addEquipment(manager, "E007", "Power Ring", EquipmentType.ACCESSORY, 4.3, 40, 10, 0, 0);
        addEquipment(manager, "E008", "Life Pendant", EquipmentType.ACCESSORY, 4.0, 0, 40, 200, 0);
        addEquipment(manager, "E009", "Shadow Axe", EquipmentType.WEAPON, 4.6, 85, 0, 500, 0);
        addEquipment(manager, "E010", "Sun Spear", EquipmentType.WEAPON, 4.5, 75, 15, 0, 0);
        addEquipment(manager, "E011", "Moon Cloak", EquipmentType.ARMOR, 4.4, 0, 70, 300, 0);
        addEquipment(manager, "E012", "Wind Amulet", EquipmentType.ACCESSORY, 4.2, 30, 30, 0, 20);
        addEquipment(manager, "E013", "Crusher", EquipmentType.WEAPON, 4.7, 200, 0, 0, 0);
        addEquipment(manager, "E014", "Crystal Wand", EquipmentType.WEAPON, 4.7, 100, 0, 0, 0);
        addEquipment(manager, "E015", "Iron Helm", EquipmentType.ARMOR, 4.1, 0, 55, 150, 0);
        addEquipment(manager, "E016", "Phoenix Plate", EquipmentType.ARMOR, 4.9, 10, 100, 400, 0);
        addEquipment(manager, "E017", "Lucky Charm", EquipmentType.ACCESSORY, 3.9, 15, 15, 100, 0);
        addEquipment(manager, "E018", "Blood Blade", EquipmentType.WEAPON, 4.5, 100, 0, 0, 0);
        addEquipment(manager, "E019", "Spirit Mask", EquipmentType.ARMOR, 4.3, 0, 65, 250, 0);
        addEquipment(manager, "E020", "Oracle Lens", EquipmentType.ACCESSORY, 4.6, 25, 25, 0, 0);
        addEquipment(manager, "E021", "Rapid Boots", EquipmentType.SHOES, 4.2, 0, 0, 0, 80);
    }

    private static void addEquipment(GameDataManager manager, String id, String name, EquipmentType type,
                                     double rating, int attackBonus, int defenseBonus,
                                     int hpBonus, int speedBonus) {
        manager.addEquipment(new Equipment(id, name, type, rating, attackBonus, defenseBonus, hpBonus, speedBonus));
    }

    private static void createHeroes(GameDataManager manager) {
        addHero(manager, "H001", "Li Bai", HeroType.ASSASSIN, 320, 180, 2800,
                List.of("E001", "E013", "E009", "E018", "E006"));
        addHero(manager, "H002", "Han Xin", HeroType.ASSASSIN, 310, 170, 2700,
                List.of("E001", "E009", "E018", "E021"));
        addHero(manager, "H003", "Daji", HeroType.MAGE, 340, 160, 2500, List.of("E003", "E014", "E012"));
        addHero(manager, "H004", "Luban No.7", HeroType.MARKSMAN, 330, 150, 2400, List.of("E002", "E007", "E020"));
        addHero(manager, "H005", "Arthur", HeroType.WARRIOR, 280, 220, 3200, List.of("E004", "E005", "E010"));
        addHero(manager, "H006", "Wang Zhaojun", HeroType.MAGE, 350, 150, 2600, List.of("E003", "E014", "E011"));
        addHero(manager, "H007", "Zhao Yun", HeroType.WARRIOR, 300, 210, 3100, List.of("E010", "E005", "E004"));
        addHero(manager, "H008", "Diao Chan", HeroType.MAGE, 330, 140, 2500, List.of("E003", "E012", "E014"));
        addHero(manager, "H009", "Zhang Fei", HeroType.TANK, 260, 240, 3600, List.of("E004", "E016", "E015"));
        addHero(manager, "H010", "Sun Shangxiang", HeroType.MARKSMAN, 325, 160, 2450, List.of("E002", "E006", "E020"));
        addHero(manager, "H011", "Zhen Ji", HeroType.MAGE, 335, 155, 2550, List.of("E003", "E011", "E012"));
        addHero(manager, "H012", "Yu Ji", HeroType.MARKSMAN, 340, 150, 2400, List.of("E002", "E007", "E018"));
        addHero(manager, "H013", "Cheng Yaojin", HeroType.TANK, 250, 230, 3500, List.of("E004", "E016", "E008"));
        addHero(manager, "H014", "Angela", HeroType.MAGE, 345, 145, 2480, List.of("E014", "E003", "E017"));
        addHero(manager, "H015", "Lu Bu", HeroType.WARRIOR, 360, 200, 3300, List.of("E013", "E005", "E009"));
    }

    private static void addHero(GameDataManager manager, String id, String name, HeroType type,
                                int attack, int defense, int hp, List<String> equipmentIds) {
        Hero hero = new Hero(id, name, type, attack, defense, hp);
        for (String equipmentId : equipmentIds) {
            hero.addCompatibleEquipment(equipmentId);
        }
        manager.addHero(hero);
    }

    private static void createPlayers(GameDataManager manager) {
        createPlayer(manager, "P001", "Chen Wei", "T001", 18, 42, 60, "chen@example.com", List.of("H001", "H005", "H007"), List.of("E001", "E004", "E010"));
        createPlayer(manager, "P002", "Liu Yang", "T001", 16, 35, 55, "liu@example.com", List.of("H002", "H009", "H013"), List.of("E013", "E016", "E004"));
        createPlayer(manager, "P003", "Zhao Min", "T001", 20, 48, 70, "zhao@example.com", List.of("H003", "H006", "H011"), List.of("E003", "E014", "E011"));
        createPlayer(manager, "P004", "Sun Hao", "T001", 15, 30, 50, "sun@example.com", List.of("H004", "H010", "H012"), List.of("E002", "E007", "E018"));
        createPlayer(manager, "P005", "Wu Jing", "T001", 17, 38, 58, "wu@example.com", List.of("H005", "H007", "H015"), List.of("E005", "E010", "E013"));

        createPlayer(manager, "P006", "Zhou Li", "T002", 19, 45, 68, "zhou@example.com", List.of("H001", "H003", "H008"), List.of("E001", "E003", "E012"));
        createPlayer(manager, "P007", "Xu Fang", "T002", 14, 28, 48, "xu@example.com", List.of("H004", "H010", "H012"), List.of("E002", "E020", "E006"));
        createPlayer(manager, "P008", "Ma Lei", "T002", 21, 52, 75, "ma@example.com", List.of("H006", "H011", "H014"), List.of("E014", "E011", "E017"));
        createPlayer(manager, "P009", "Huang Tao", "T002", 16, 33, 54, "huang@example.com", List.of("H002", "H009", "H013"), List.of("E013", "E004", "E008"));
        createPlayer(manager, "P010", "Qian Bo", "T002", 18, 40, 62, "qian@example.com", List.of("H007", "H015", "H005"), List.of("E010", "E005", "E009"));

        createPlayer(manager, "P011", "Feng Yu", "T003", 17, 36, 57, "feng@example.com", List.of("H003", "H008", "H014"), List.of("E003", "E012", "E014"));
        createPlayer(manager, "P012", "Guo Xin", "T003", 22, 55, 80, "guo@example.com", List.of("H001", "H002", "H015"), List.of("E001", "E013", "E009"));
        createPlayer(manager, "P013", "He Ping", "T003", 15, 31, 52, "he@example.com", List.of("H004", "H012", "H010"), List.of("E002", "E018", "E007"));
        createPlayer(manager, "P014", "Jiang Shan", "T003", 19, 44, 66, "jiang@example.com", List.of("H005", "H009", "H013"), List.of("E004", "E016", "E015"));
        createPlayer(manager, "P015", "Kong Ming", "T003", 20, 47, 72, "kong@example.com", List.of("H006", "H011", "H003"), List.of("E014", "E011", "E003"));

        for (Player player : manager.getPlayers()) {
            manager.getTeam(player.getTeamId()).addPlayer(player.getId());
        }

        equipMultiple(manager, "P001", "H001", "E001", "E013", "E009");
        equipMultiple(manager, "P001", "H005", "E004", "E005");
        equipMultiple(manager, "P001", "H007", "E010", "E005");
    }

    private static void equipMultiple(GameDataManager manager, String playerId, String heroId, String... equipmentIds) {
        manager.findPlayer(playerId).ifPresent(player -> {
            for (String equipmentId : equipmentIds) {
                player.equipItem(heroId, equipmentId);
            }
        });
    }

    private static void createPlayer(GameDataManager manager, String id, String name, String teamId, int level,
                                     int wins, int matches, String email, List<String> heroIds, List<String> equipmentIds) {
        Player player = new Player(id, name, "player123", teamId, level, wins, matches, email);
        for (int i = 0; i < heroIds.size(); i++) {
            String heroId = heroIds.get(i);
            player.addHero(heroId);
            if (i < equipmentIds.size()) {
                player.equipItem(heroId, equipmentIds.get(i));
            }
        }
        manager.addPlayer(player);
    }

    private static void createMatches(GameDataManager manager) {
        addMatch(manager, "M001", LocalDate.of(2026, 5, 1), "T001", "Storm Legends", MatchResult.WIN,
                picks("P001", "H001", "P002", "H009", "P003", "H006"));
        addMatch(manager, "M002", LocalDate.of(2026, 5, 3), "T002", "Dragon Warriors", MatchResult.LOSS,
                picks("P006", "H001", "P007", "H004", "P008", "H014"));
        addMatch(manager, "M003", LocalDate.of(2026, 5, 5), "T003", "Dragon Warriors", MatchResult.WIN,
                picks("P011", "H003", "P012", "H015", "P013", "H012"));
        addMatch(manager, "M004", LocalDate.of(2026, 5, 7), "T001", "Jade Guardians", MatchResult.LOSS,
                picks("P004", "H010", "P005", "H007", "P001", "H005"));
        addMatch(manager, "M005", LocalDate.of(2026, 5, 9), "T002", "Jade Guardians", MatchResult.WIN,
                picks("P009", "H013", "P010", "H015", "P006", "H008"));
        addMatch(manager, "M006", LocalDate.of(2026, 5, 11), "T003", "Storm Legends", MatchResult.WIN,
                picks("P014", "H009", "P015", "H006", "P011", "H014"));
        addMatch(manager, "M007", LocalDate.of(2026, 5, 13), "T001", "Storm Legends", MatchResult.WIN,
                picks("P002", "H002", "P003", "H011", "P005", "H015"));
        addMatch(manager, "M008", LocalDate.of(2026, 5, 15), "T002", "Dragon Warriors", MatchResult.LOSS,
                picks("P007", "H012", "P008", "H006", "P009", "H009"));
        addMatch(manager, "M009", LocalDate.of(2026, 5, 17), "T003", "Dragon Warriors", MatchResult.LOSS,
                picks("P012", "H001", "P013", "H004", "P014", "H005"));
        addMatch(manager, "M010", LocalDate.of(2026, 5, 19), "T001", "Jade Guardians", MatchResult.WIN,
                picks("P001", "H001", "P004", "H012", "P003", "H003"));
        addMatch(manager, "M011", LocalDate.of(2026, 5, 21), "T002", "Jade Guardians", MatchResult.WIN,
                picks("P010", "H007", "P006", "H003", "P008", "H011"));
        addMatch(manager, "M012", LocalDate.of(2026, 5, 23), "T003", "Storm Legends", MatchResult.LOSS,
                picks("P015", "H006", "P011", "H008", "P012", "H002"));
    }

    private static List<String> picks(String... values) {
        List<String> list = new ArrayList<>();
        for (String value : values) {
            list.add(value);
        }
        return list;
    }

    private static void addMatch(GameDataManager manager, String id, LocalDate date, String teamId,
                                 String opponent, MatchResult result, List<String> pickValues) {
        MatchRecord record = new MatchRecord(id, date, teamId, opponent, result);
        for (int i = 0; i < pickValues.size(); i += 2) {
            record.addPick(pickValues.get(i), pickValues.get(i + 1));
        }
        manager.addMatch(record);
    }
}
