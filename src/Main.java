import model.Admin;
import model.Equipment;
import model.EquipmentType;
import model.Hero;
import model.HeroType;
import model.MatchRecord;
import model.MatchResult;
import model.Person;
import model.Player;
import model.Team;
import service.AuthenticationService;
import service.FileStorageService;
import service.GameDataManager;
import service.RankingService;
import service.SearchService;
import util.DataInitializer;
import util.InputHelper;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final InputHelper input = new InputHelper(scanner);
    private final GameDataManager manager = new GameDataManager();
    private final AuthenticationService authService = new AuthenticationService();
    private final SearchService searchService = new SearchService(manager);
    private final RankingService rankingService = new RankingService(manager);
    private final FileStorageService storageService = new FileStorageService(Path.of("data", "game_data.txt"));

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        initializeData();
        System.out.println("=== Honor of Kings Information Management System ===");
        System.out.println("Student ID folder: honor of kings [25722091]");
        boolean running = true;
        while (running) {
            if (!authService.isLoggedIn()) {
                running = loginMenu();
            } else {
                running = authService.isAdmin() ? adminMenu() : playerMenu();
            }
        }
        saveDataQuietly();
        System.out.println("Goodbye.");
    }

    private void initializeData() {
        try {
            storageService.load(manager);
            if (manager.getPlayers().isEmpty()) {
                DataInitializer.loadSampleData(manager);
                storageService.save(manager);
            } else {
                manager.registerUser(new Admin("admin", "System Admin", "admin123"));
            }
        } catch (Exception ex) {
            System.out.println("Failed to load saved data, using sample dataset. Reason: " + ex.getMessage());
            manager.clearAllData();
            DataInitializer.loadSampleData(manager);
        }
    }

    private void saveDataQuietly() {
        try {
            storageService.save(manager);
        } catch (Exception ex) {
            System.out.println("Failed to save data: " + ex.getMessage());
        }
    }

    private boolean loginMenu() {
        System.out.println("\n1. Login");
        System.out.println("2. Exit");
        int choice = input.readInt("Choose an option: ", 1, 2);
        if (choice == 2) {
            return false;
        }
        String username = input.readLine("Username (admin or player ID): ");
        String password = input.readLine("Password: ");
        var userOptional = authService.login(manager, username, password);
        if (userOptional.isEmpty()) {
            System.out.println("Invalid username or password.");
            return true;
        }
        Person user = userOptional.get();
        System.out.println("Welcome, " + user.getName() + " (" + user.describeRole() + ")");
        return true;
    }

    // Fixed switch branch return syntax for exit options.
    private boolean adminMenu() {
        printAdminMenu();
        int choice = input.readInt("Choose an option: ", 0, 14);
        try {
            switch (choice) {
                case 1 -> System.out.println(searchService.buildPlayerLookupReport(input.readLine("Player ID or name: ")));
                case 2 -> System.out.println(searchService.buildTeamOverviewReport(input.readLine("Team ID or name: ")));
                case 3 -> System.out.println(searchService.buildHeroDetailsReport(input.readLine("Hero ID or name: ")));
                case 4 -> System.out.println(rankingService.buildEquipmentStatisticsReport());
                case 5 -> printMatchHistory();
                case 6 -> printLeaderboard();
                case 7 -> managePlayers(true);
                case 8 -> manageHeroes();
                case 9 -> manageEquipment();
                case 10 -> manageTeams();
                case 11 -> manageMatches();
                case 12 -> saveDataQuietly();
                case 13 -> authService.logout();
                case 0 -> {
                    return false;
                }
                default -> System.out.println("Invalid option.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Unexpected error: " + ex.getMessage());
        }
        return true;
    }

    private boolean playerMenu() {
        printPlayerMenu();
        int choice = input.readInt("Choose an option: ", 0, 10);
        Player current = authService.getCurrentPlayer();
        try {
            switch (choice) {
                case 1 -> System.out.println(current.generateReport());
                case 2 -> editOwnProfile(current);
                case 3 -> System.out.println(searchService.buildPlayerLookupReport(current.getId()));
                case 4 -> System.out.println(searchService.buildMatchHistoryReportForPlayer(current.getId(),
                        input.readInt("How many recent matches? ", 1, 20)));
                case 5 -> System.out.println(searchService.buildPlayerLookupReport(input.readLine("Player ID or name: ")));
                case 6 -> System.out.println(searchService.buildTeamOverviewReport(input.readLine("Team ID or name: ")));
                case 7 -> System.out.println(searchService.buildHeroDetailsReport(input.readLine("Hero ID or name: ")));
                case 8 -> System.out.println(rankingService.buildEquipmentStatisticsReport());
                case 9 -> printLeaderboard();
                case 10 -> authService.logout();
                case 0 -> {
                    return false;
                }
                default -> System.out.println("Invalid option.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return true;
    }

    private void printAdminMenu() {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. Player Lookup");
        System.out.println("2. Team Overview");
        System.out.println("3. Hero Details");
        System.out.println("4. Equipment Statistics");
        System.out.println("5. Match History");
        System.out.println("6. Leaderboard");
        System.out.println("7. Manage Players");
        System.out.println("8. Manage Heroes");
        System.out.println("9. Manage Equipment");
        System.out.println("10. Manage Teams");
        System.out.println("11. Manage Matches");
        System.out.println("12. Save Data");
        System.out.println("13. Logout");
        System.out.println("0. Exit");
    }

    private void printPlayerMenu() {
        System.out.println("\n--- Player Menu ---");
        System.out.println("1. View My Profile");
        System.out.println("2. Edit My Profile");
        System.out.println("3. View My Heroes");
        System.out.println("4. View My Match History");
        System.out.println("5. Player Lookup");
        System.out.println("6. Team Overview");
        System.out.println("7. Hero Details");
        System.out.println("8. Equipment Statistics");
        System.out.println("9. Leaderboard");
        System.out.println("10. Logout");
        System.out.println("0. Exit");
    }

    private void printMatchHistory() {
        System.out.println("1. By Player");
        System.out.println("2. By Team");
        int choice = input.readInt("Choose: ", 1, 2);
        int count = input.readInt("How many recent matches? ", 1, 20);
        if (choice == 1) {
            System.out.println(searchService.buildMatchHistoryReportForPlayer(
                    input.readLine("Player ID or name: "), count));
        } else {
            System.out.println(searchService.buildMatchHistoryReportForTeam(
                    input.readLine("Team ID or name: "), count));
        }
    }

    private void printLeaderboard() {
        System.out.println("Metric: winrate / level / matches / score");
        String metric = input.readLine("Choose metric: ");
        int topX = input.readInt("Top X players: ", 1, 20);
        System.out.println(rankingService.buildLeaderboardReport(topX, metric));
    }

    private void editOwnProfile(Player player) {
        player.setEmail(input.readLine("New email: "));
        if (input.readYesNo("Change password?")) {
            player.setPassword(input.readLine("New password: "));
        }
        System.out.println("Profile updated.");
    }

    private void managePlayers(boolean adminMode) {
        System.out.println("1. Add  2. Edit  3. Delete");
        int choice = input.readInt("Choose: ", 1, 3);
        if (choice == 1) {
            String id = input.readLine("Player ID: ");
            String name = input.readLine("Name: ");
            String teamId = input.readLine("Team ID: ");
            int level = input.readInt("Level: ", 1, 100);
            int wins = input.readInt("Wins: ", 0, 10000);
            int matches = input.readInt("Matches: ", wins, 10000);
            String email = input.readLine("Email: ");
            Player player = new Player(id, name, "player123", teamId, level, wins, matches, email);
            manager.addPlayer(player);
            manager.getTeam(teamId).addPlayer(id);
            System.out.println("Player added.");
        } else if (choice == 2) {
            Player player = manager.findPlayer(input.readLine("Player ID or name: "))
                    .orElseThrow(() -> new IllegalArgumentException("Player not found"));
            player.setName(input.readLine("New name: "));
            player.setEmail(input.readLine("New email: "));
            player.setLevel(input.readInt("New level: ", 1, 100));
            System.out.println("Player updated.");
        } else {
            manager.removePlayer(input.readLine("Player ID to delete: "));
            System.out.println("Player deleted.");
        }
    }

    private void manageHeroes() {
        System.out.println("1. Add  2. Delete");
        int choice = input.readInt("Choose: ", 1, 2);
        if (choice == 1) {
            Hero hero = new Hero(
                    input.readLine("Hero ID: "),
                    input.readLine("Name: "),
                    HeroType.valueOf(input.readLine("Type (WARRIOR/MAGE/ASSASSIN/MARKSMAN/SUPPORT/TANK): ").toUpperCase()),
                    input.readInt("Attack: ", 0, 1000),
                    input.readInt("Defense: ", 0, 1000),
                    input.readInt("HP: ", 1, 10000));
            manager.addHero(hero);
            System.out.println("Hero added.");
        } else {
            manager.removeHero(input.readLine("Hero ID to delete: "));
            System.out.println("Hero deleted.");
        }
    }

    private void manageEquipment() {
        System.out.println("1. Add  2. Delete");
        int choice = input.readInt("Choose: ", 1, 2);
        if (choice == 1) {
            Equipment item = new Equipment(
                    input.readLine("Equipment ID: "),
                    input.readLine("Name: "),
                    EquipmentType.valueOf(input.readLine("Type (WEAPON/ARMOR/ACCESSORY): ").toUpperCase()),
                    input.readDouble("Rating (0-5): "),
                    input.readInt("Attack bonus: ", 0, 500),
                    input.readInt("Defense bonus: ", 0, 500));
            manager.addEquipment(item);
            System.out.println("Equipment added.");
        } else {
            manager.removeEquipment(input.readLine("Equipment ID to delete: "));
            System.out.println("Equipment deleted.");
        }
    }

    private void manageTeams() {
        System.out.println("1. Add  2. Edit  3. Delete");
        int choice = input.readInt("Choose: ", 1, 3);
        if (choice == 1) {
            Team team = new Team(input.readLine("Team ID: "), input.readLine("Team name: "));
            manager.addTeam(team);
            System.out.println("Team added.");
        } else if (choice == 2) {
            Team team = manager.findTeam(input.readLine("Team ID or name: "))
                    .orElseThrow(() -> new IllegalArgumentException("Team not found"));
            team.setName(input.readLine("New team name: "));
            System.out.println("Team updated.");
        } else {
            manager.removeTeam(input.readLine("Team ID to delete: "));
            System.out.println("Team deleted.");
        }
    }

    private void manageMatches() {
        System.out.println("1. Add  2. Delete");
        int choice = input.readInt("Choose: ", 1, 2);
        if (choice == 1) {
            MatchRecord match = new MatchRecord(
                    input.readLine("Match ID: "),
                    LocalDate.parse(input.readLine("Date (YYYY-MM-DD): ")),
                    input.readLine("Team ID: "),
                    input.readLine("Opponent team name: "),
                    MatchResult.valueOf(input.readLine("Result (WIN/LOSS): ").toUpperCase()));
            manager.addMatch(match);
            System.out.println("Match added.");
        } else {
            manager.removeMatch(input.readLine("Match ID to delete: "));
            System.out.println("Match deleted.");
        }
    }
}
