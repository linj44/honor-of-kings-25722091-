# Design Document — Honor of Kings IMS

## Architecture

The application uses a layered console architecture:

- **Model layer:** domain entities and enums
- **Service layer:** business logic, authentication, search, ranking, persistence
- **Util layer:** sample data and input helpers
- **Presentation layer:** `Main` menu system

## Ranking Formulas

### Equipment score

```
score = usageCount * 1.5 + averageRating * 2 + heroesUsing + winContribution
```

Where:

- `usageCount` = number of equipped instances across all players
- `averageRating` = equipment rating field (0-5 scale)
- `heroesUsing` = number of equipped usages counted per item
- `winContribution` = number of wins in matches where the item was equipped through a picked hero

### Player custom score

```
customScore = level * 1.2 + winRate * 0.5 + matches * 0.3
```

### Leaderboard tie handling

Primary metric descending, then secondary keys:

- win rate: win rate → matches → name
- level: level → name
- matches: matches → name
- score: custom score → name

## Authentication Rules

- Admin account: `admin / admin123`
- Player accounts: player ID / `player123`
- Admin can manage all records
- Player can view own profile, edit email/password, and access public reports

## Persistence

File: `data/game_data.txt`

Load order:

1. teams
2. equipment
3. heroes
4. players
5. matches

After loading from file, the default admin account is re-registered.

## Deletion Consistency

- Deleting a hero removes it from player ownership, equipment mappings, and match picks
- Deleting a player removes user registration and team membership
- Deleting equipment removes compatibility links and equipped references
- Deleting a team clears player team references indirectly through team removal logic
