# Project Plan — Honor of Kings IMS [25722091]

## 1. Project Goal

Build a Java console Information Management System for Honor of Kings that lets **Admin** users manage all game data and **Player** users view public information plus their own profile and match history. The system must demonstrate OOP design, collections, authentication, search/report features, ranking, file persistence, and responsible AI-assisted development evidence.

## 2. Requirement Analysis

| Requirement | Implementation |
|-------------|----------------|
| Player lookup | `SearchService.buildPlayerLookupReport()` searches by ID/name and shows team, level, win rate, heroes, equipment |
| Team overview | `SearchService.buildTeamOverviewReport()` shows members, average level, total matches, win rate, top player |
| Hero details | `SearchService.buildHeroDetailsReport()` shows type, stats, compatible equipment, owners, recommendations |
| Equipment statistics | `RankingService.buildEquipmentStatisticsReport()` ranks items using a documented score formula |
| Match history | `SearchService.buildMatchHistoryReportForPlayer/Team()` shows last N matches, record, hero pick rate |
| Leaderboard | `RankingService.buildLeaderboardReport()` supports win rate, level, matches, and custom score |
| Data management | Admin menu CRUD in `Main.java` backed by `GameDataManager` |
| Authentication | `AuthenticationService` with Admin and Player roles |
| File persistence | `FileStorageService` saves/loads `data/game_data.txt` |
| Initial dataset | `DataInitializer.loadSampleData()` creates 3 teams, 15 players, 15 heroes, 20 equipment, 12 matches |

## 3. Java Concepts Used

- **Inheritance:** `Player`, `Admin` extend abstract `Person`
- **Interfaces:** `Searchable`, `Reportable`, `Persistable`, `Authenticatable`
- **Polymorphism:** login stores current user as `Person`; runtime type checks for role menus
- **Association:** `Player` owns heroes and hero equipment mappings
- **Aggregation:** `Team` contains player IDs
- **Collections:** `HashMap`, `ArrayList`, streams, sorting comparators
- **Enums:** `HeroType`, `EquipmentType`, `MatchResult`, `Role`
- **Exception handling:** duplicate IDs, missing records, invalid menu input, file load/save failures
- **File I/O:** pipe-delimited text persistence

## 4. Class Design

| Class | Responsibility |
|-------|------------------|
| `Person` | Shared user fields and authentication contract |
| `Player` | Player stats, owned heroes, equipped items, report generation |
| `Admin` | Admin role marker with full permissions |
| `Hero` | Hero stats, type, compatible equipment |
| `Equipment` | Item stats and rating |
| `Team` | Team identity and member list |
| `MatchRecord` | Match metadata and hero picks |
| `GameDataManager` | Central in-memory data store and CRUD |
| `AuthenticationService` | Login/logout and role checks |
| `SearchService` | Lookup and reporting features |
| `RankingService` | Equipment ranking and leaderboard |
| `FileStorageService` | Save/load data file |
| `DataInitializer` | Sample dataset bootstrap |
| `InputHelper` | Safe console input parsing |
| `Main` | Menu-driven application entry point |

## 5. UML Draft

Text-based UML:

```
<<abstract>> Person
  - id, name, password, role
  + authenticate()
  + describeRole()

Player --|> Person
Admin --|> Person

Team o-- Player : member IDs
Player *-- Hero : ownedHeroIds
Hero o-- Equipment : compatibleEquipmentIds
MatchRecord --> Team
MatchRecord --> Player : hero picks

GameDataManager --> Person, Player, Hero, Equipment, Team, MatchRecord
AuthenticationService --> GameDataManager
SearchService --> GameDataManager
RankingService --> GameDataManager
FileStorageService --> GameDataManager
Main --> all services
```

See also `docs/uml.txt`.

## 6. Data Design

Initial sample data:

- Teams: Dragon Warriors, Storm Legends, Jade Guardians
- 15 players, each with 3 heroes and at least one equipped item
- 15 heroes across assassin, mage, marksman, warrior, tank, support types
- 20 equipment items across weapon, armor, accessory types
- 12 match records with hero picks and win/loss results

Persistent storage format: one record per line in `data/game_data.txt` using prefixes `TEAM|`, `EQUIP|`, `HERO|`, `PLAYER|`, `MATCH|`.

## 7. AI Usage Plan

| Agent Role | Allowed Help |
|------------|--------------|
| Architect Agent | Class structure, relationships, package layout, UML suggestions |
| Implementation Agent | Selected service methods, menu wiring, persistence format |
| Testing/Reviewer Agent | Test case drafting, null/duplicate ID review, deletion consistency checks |

Human responsibilities: requirement interpretation, final design approval, manual verification, Git commits, reflection writing.

## 8. Prompt Strategy

Prompts will be specific and scoped, for example:

- design-only prompts without full code generation
- implementation prompts limited to one class or method
- review prompts asking for bugs, encapsulation issues, and edge cases

Every AI output will be compiled, run, and checked against coursework requirements before acceptance.

## 9. Development Timeline

| Stage | Work |
|-------|------|
| Stage 1 | Read PDF requirements, create repository, write plan.md |
| Stage 2 | Architect Agent feedback on OOP structure |
| Stage 3 | Implement model classes and sample data |
| Stage 4 | Implement search/report menus |
| Stage 5 | Implement authentication and admin/player permissions |
| Stage 6 | Implement persistence and ranking |
| Stage 7 | Testing/Reviewer Agent feedback and bug fixes |
| Stage 8 | Final docs, reflection, git-history export |

## 10. Testing Plan

Test player lookup, team overview, hero details, equipment ranking, match history, leaderboard, admin login, player login, invalid login, duplicate ID add, missing record search, and file reload after save. Details recorded in `docs/test-cases.md`.

## 11. Risk Analysis

| Risk | Mitigation |
|------|------------|
| Over-reliance on one giant Main class | Split logic into services and model classes |
| Inconsistent deletes | `GameDataManager.removeHero/removePlayer/removeEquipment` clean related references |
| Invalid console input | `InputHelper` loops until valid values |
| File format errors | Typed prefixes and ordered load sequence |
| Fake AI evidence | Record real prompts, commits, and human decisions |

## 12. Final Reflection Placeholder

Final reflection answers will be completed in `ai/reflection.md` after implementation and manual testing.
