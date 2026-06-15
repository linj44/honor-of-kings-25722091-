# AI Prompt Records

## Prompt 01
Time: 2026-06-03 10:00
Tool/Model: Cursor / Composer
Agent Role: Architect Agent
Related Commit: pending `[AI-Architect] draft OOP class structure`

### My Prompt
I am building a Java Honor of Kings information management system coursework. Suggest a class structure using inheritance, interfaces, collections, and service classes. Don't write full code .

### AI Response Summary
Suggested `Person`, `Player`, `Admin`, `Hero`, `Equipment`, `Team`, `MatchRecord`, plus services such as `GameDataManager`, `SearchService`, and `FileStorageService`.

### My Decision
Accepted the overall structure and added interfaces `Searchable`, `Reportable`, `Persistable`, and `Authenticatable`.

## Prompt 02
Time: 2026-06-03 11:20
Tool/Model: Cursor / Composer
Agent Role: Implementation Agent
Related Commit: pending `[AI-Implementation] implement search and ranking services`

### My Prompt
Implement player lookup, team overview, hero details, match history, equipment ranking, and leaderboard methods using existing model classes.

### AI Response Summary
Generated `SearchService` and `RankingService` with report strings and documented ranking formulas.

### My Decision
Accepted with modifications to tie-breaking rules and hero recommendation logic.

## Prompt 03
Time: 2026-06-03 12:10
Tool/Model: Cursor / Composer
Agent Role: Implementation Agent
Related Commit: pending `[AI-Implementation] implement file persistence`

### My Prompt
Implement text file save/load for players, heroes, equipment, teams, and matches using a simple documented format.

### AI Response Summary
Suggested pipe-delimited records with typed prefixes.

### My Decision
Accepted and changed load order to teams → equipment → heroes → players → matches.

## Prompt 04
Time: 2026-06-03 13:00
Tool/Model: Cursor / Composer
Agent Role: Testing/Reviewer Agent
Related Commit: pending `[AI-Review] review deletion consistency`

### My Prompt
Review delete operations for heroes, players, and equipment. Check whether related collections are cleaned correctly.

### AI Response Summary
Found that deleting a hero should also remove it from player ownership and match hero picks.

### My Decision
Accepted and updated `GameDataManager.removeHero()` and related delete methods.

## Prompt 05
Time: 2026-06-03 14:00
Tool/Model: Cursor / Composer
Agent Role: Architect Agent
Related Commit: pending `[AI-Architect] design authentication and menu permissions`

### My Prompt
Design login/logout flow and separate admin/player menus according to coursework permissions.

### AI Response Summary
Suggested `AuthenticationService` with role-based menus in `Main`.

### My Decision
Accepted. Added default admin account and player password `player123`.

## Prompt 06
Time: 2026-06-03 15:00
Tool/Model: Cursor / Composer
Agent Role: Testing/Reviewer Agent
Related Commit: pending `[Fix] fix switch return syntax in Main`

### My Prompt
Compile the project and identify Java syntax errors.

### AI Response Summary
Reported invalid arrow-switch `return` statements in `Main.java`.

### My Decision
Accepted and fixed with block syntax `case 0 -> { return false; }`.

## Prompt 07
Time: 2026-06-03 15:30
Tool/Model: Cursor / Composer
Agent Role: Architect Agent
Related Commit: pending `[Docs] write plan and design docs`

### My Prompt
Generate coursework documentation structure for plan.md, design.md, test-cases.md, README, and AI evidence files based on the PDF requirements.

### AI Response Summary
Provided required headings, test case format, and AI evidence templates.

### My Decision
Accepted and customized with student folder name `honor of kings [25722091]`.
