# AI-Assisted Honor of Kings Information Management System

Student folder: `honor of kings [25722091]`

## 1. Project Overview

This console application manages Honor of Kings players, heroes, equipment, teams, and match records. It supports authentication for Admin and Player roles, search/report features, ranking, and file persistence.

## 2. How to Run

```powershell
Set-Location -LiteralPath "C:\Users\linjl\Desktop\honor of kings [25722091]"
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
java -cp out Main
```

## 3. Default Login Accounts

| Role | Username | Password |
|------|----------|----------|
| Admin | `admin` | `admin123` |
| Player | `P001` to `P015` | `player123` |

Example player login: username `P001`, password `player123`.

## 4. Implemented Features

- Player lookup by ID or name
- Team overview with average level, win rate, and top player
- Hero details with compatible equipment and owners
- Equipment statistics ranking with documented formula
- Match history for player or team (last N matches)
- Leaderboard by win rate, level, matches, or custom score
- Admin CRUD for players, heroes, equipment, teams, and matches
- Player profile view/edit and personal match history
- Text file persistence in `data/game_data.txt`

## 5. Java Concepts Used

- Inheritance: `Player` and `Admin` extend `Person`
- Interfaces: `Searchable`, `Reportable`, `Persistable`, `Authenticatable`
- Polymorphism: users stored and handled as `Person`
- Collections: `HashMap`, `ArrayList`, `List`, `Map`, `Set`-like uniqueness checks
- Enums: `HeroType`, `EquipmentType`, `MatchResult`, `Role`
- Exception handling: invalid input, missing records, duplicate IDs, file I/O errors
- File I/O: `FileStorageService` saves and loads pipe-delimited records

## 6. AI Usage Summary

AI tools were used for architecture suggestions, selected implementation tasks, and review feedback. All prompts, agent roles, and decisions are recorded in the `ai/` folder.

## 7. Testing Summary

Manual test cases are documented in `docs/test-cases.md`. Core menu paths were verified during development.

## 8. Known Limitations

- Console UI only (no GUI)
- Team rename edits name only; player membership editing is basic
- Match creation does not validate every hero pick relationship automatically
