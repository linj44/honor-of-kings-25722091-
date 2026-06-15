# Manual Test Cases

## Test 01: Admin Login
- Function tested: Authentication
- Input: username `红糖guoy10`, password `123456`
- Expected: Admin menu displayed, welcome message shows name `郭怡婷`
- Actual: Admin menu displayed
- Result: Pass

## Test 01b: Admin Login (Second Account)
- Function tested: Authentication
- Input: username `饭团linj44`, password `070530`
- Expected: Admin menu displayed
- Actual: Admin menu displayed
- Result: Pass

## Test 02: Invalid Login
- Function tested: Authentication
- Input: username `红糖guoy10`, password `wrong`
- Expected: Login rejected
- Actual: "Invalid username or password."
- Result: Pass

## Test 03: Player Login
- Function tested: Authentication
- Input: username `P001`, password `player123`
- Expected: Player menu displayed
- Actual: Player menu displayed
- Result: Pass

## Test 04: Player Lookup by Name
- Function tested: Player lookup
- Input: search player `P001` or `Chen Wei`
- Expected: Player profile shows owned heroes, equipped items with ATK/DEF/HP/SPD stats, proficiency, and compatible equipment list
- Actual: Detailed hero and equipment report displayed
- Result: Pass

## Test 05: Team Overview
- Function tested: Team overview
- Input: team `T001`
- Expected: Members, average level, win rate, top player shown
- Actual: Team report displayed correctly
- Result: Pass

## Test 06: Hero Details
- Function tested: Hero details
- Input: hero `H001` or name `Li Bai`
- Expected: Hero type, stats, compatible equipment, owners shown
- Actual: Hero report displayed correctly
- Result: Pass

## Test 07: Equipment Statistics
- Function tested: Equipment ranking
- Input: Admin menu option 4
- Expected: Ranked equipment list with score formula explanation
- Actual: Sorted equipment report displayed
- Result: Pass

## Test 08: Match History by Player
- Function tested: Match history
- Input: player `P001`, last 5 matches
- Expected: Opponent, date, result, hero picks, record summary
- Actual: Match history report displayed
- Result: Pass

## Test 09: Leaderboard by Win Rate
- Function tested: Leaderboard
- Input: metric `winrate`, top 5
- Expected: Top 5 players sorted by win rate with tie rules
- Actual: Leaderboard displayed
- Result: Pass

## Test 10: Duplicate Player ID
- Function tested: Admin add player validation
- Input: add player with existing ID `P001`
- Expected: Error about duplicate ID
- Actual: "Duplicate player ID: P001"
- Result: Pass

## Test 11: Missing Hero Search
- Function tested: Hero details error handling
- Input: hero name `Unknown Hero`
- Expected: Not found error
- Actual: "Hero not found: Unknown Hero"
- Result: Pass

## Test 12: Save and Reload Data
- Function tested: File persistence
- Input: admin save data, restart program
- Expected: Saved players/heroes/teams reload from `data/game_data.txt`
- Actual: Data restored after restart
- Result: Pass

## Test 13: Player Profile Edit
- Function tested: Player limited edit
- Input: player `P002` changes email
- Expected: Profile updated while admin-only fields remain protected
- Actual: Email updated successfully
- Result: Pass

## Test 14: Delete Hero Consistency
- Function tested: Admin delete hero
- Input: delete a hero owned by players
- Expected: Hero removed from ownership and match references
- Actual: Hero deleted without crash; lookup no longer finds hero
- Result: Pass
- Bug found: Initial AI suggestion did not mention match-pick cleanup; fixed in `GameDataManager.removeHero()`
