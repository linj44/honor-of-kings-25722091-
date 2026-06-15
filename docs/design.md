Design Document — Honor of Kings IMS
Overall Structure
I went with a simple layered design because it's easier to test and debug. Nothing too fancy:

Model layer: just the data classes (Hero, Player, Equipment, etc.) and enums

Service layer: where the actual work happens — search, ranking, login, file saving

Util layer: helper stuff like loading sample data and getting user input

Presentation layer: basically just Main.java with all the menus

How Ranking Works
Equipment Score Formula
Here's what I came up with for ranking equipment:

text
score = usageCount * 1.5 + averageRating * 2 + heroesUsing + winContribution
Breaking it down:

usageCount → how many times this item is equipped across all players

averageRating → the item's rating (0–5 marks)

heroesUsing → how many heroes have this item equipped (counts each hero separately)

winContribution → number of match wins where this item was used on a picked hero

I tweaked the weights a bit so rating matters more (multiplied by 2), since I think item quality should be more important than just how many people use it.

Player Custom Score
For the leaderboard custom metric:

text
customScore = level * 1.2 + winRate * 0.5 + matches * 0.3
Level is weighted highest because I feel like experienced players should rank higher, but win rate and match count still contribute.

Tie-Breaking Rules
When two players have the same score, I sort by:

Win rate leaderboard: win rate → matches played → name alphabetically

Level leaderboard: level → name

Matches leaderboard: matches → name

Custom score leaderboard: custom score → name

Nothing crazy, just makes sure there's always a clear order.

Login & Permissions
I have two hardcoded admin accounts (from the sample data):

Name	      	Username	       Password
饭团linj44	饭团linj44		lsfllrljl070530
郭怡婷	      	红糖guoy10	       123456
Regular players log in with their player ID (like P001) and the default password player123. Admins can do everything (add, edit, delete). Players can only view public info and edit their own email/password.

File Saving (Persistence)
I save everything to data/game_data.txt. The format is pipe-delimited (|), which is simple and worked fine for this project.

Load order matters because of dependencies:

Teams (players need team IDs later)

Equipment (heroes need equipment compatibility)

Heroes (players need hero IDs)

Players (matches need player IDs)

Matches (depends on everything else)

After loading from file, I re-register the default admin accounts just in case the file doesn't have them.

Delete Consistency
This was a pain point. I made sure deleting something cleans up everywhere:

Delete a hero → remove it from players' owned lists, remove equipment mappings, and take it out of match picks

Delete a player → remove from user registration and kick them out of their team

Delete equipment → remove from hero compatibility lists and unequip from all players

Delete a team → clear team reference from players on that team

I caught some of these issues during testing (Test 14 in test-cases.md). The AI gave me a basic delete method but didn't handle all the references, so I had to add the cleanup logic myself.

Final Notes
Nothing too complicated design-wise. I tried to keep it organized so I could find things easily. If I had more time, I'd probably refactor the menu code in Main.java — it's getting a bit long.


