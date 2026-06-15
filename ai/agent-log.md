Project Plan — Honor of Kings IMS [25722091]
1. Project Goal
I need to build a console-based information management system for Honor of Kings. Admins should be able to manage all the game data, and regular players can only view public info plus their own profile and match history. The system has to show that I understand OOP, collections, authentication, search and ranking features, file saving, and also document how I used AI responsibly.

2. Requirement Analysis
Here's how I plan to implement each required feature:

Requirement	How I'll do it
Player lookup	Use SearchService.buildPlayerLookupReport() to search by ID or name, then show team, level, win rate, heroes, and equipment
Team overview	SearchService.buildTeamOverviewReport() shows members, average level, total matches, win rate, and top player
Hero details		SearchService.buildHeroDetailsReport() shows type, stats, compatible equipment, owners, and some recommendations
Equipment statistics	RankingService.buildEquipmentStatisticsReport() ranks items using a formula I'll document
Match history	SearchService will have methods to show last N matches for a player or team, including hero pick rates
Leaderboard		RankingService.buildLeaderboardReport() will support sorting by win rate, level, matches, and a custom score
Data management	Admin menu options in Main.java that call GameDataManager methods for CRUD
Authentication	AuthenticationService will handle login and tell apart Admin vs Player roles
File persistence	FileStorageService will save and load from data/game_data.txt
Initial dataset	DataInitializer.loadSampleData() will create 3 teams, 15 players, 15 heroes, 20 equipment items, and 12 matches
3. Java Concepts I Plan to Use
Inheritance: Player and Admin will both extend an abstract Person class

Interfaces: I'll make Searchable, Reportable, Persistable, and Authenticatable

Polymorphism: The logged-in user will be stored as a Person, but I'll check the actual type to show different menus

Association: A Player will own heroes and equipment mappings

Aggregation: A Team will contain a list of player IDs

Collections: Mostly HashMap and ArrayList, plus streams for filtering and sorting

Enums: HeroType, EquipmentType, MatchResult, Role

Exception handling: Need to handle duplicate IDs, missing records, invalid input, and file errors

File I/O: Simple pipe-delimited text format for saving

4. Class Design
Class	What it does
Person	Abstract class for common user fields and authentication
Player	Player stats, owned heroes, equipped items, can generate a report
Admin	Just a marker class, extends Person, has full permissions
Hero	Hero stats, type, and compatible equipment list
Equipment	Item stats and rating
Team	Team ID, name, and list of member IDs
MatchRecord	Match info like date, opponent, result, and which hero each player picked
GameDataManager	Stores all data in memory and handles CRUD operations
AuthenticationService	Login, logout, role checking
SearchService	All the lookup and reporting methods
RankingService	Equipment ranking and leaderboard
FileStorageService	Save and load from text file
DataInitializer	Loads sample data if no saved file exists
InputHelper	Helper for reading user input safely
Main	The main menu and app loop
5. UML Draft
Here's a text version of my UML (I also have a diagram in docs/uml.txt):

text
Person (abstract)
  - id, name, password, role
  + authenticate()
  + describeRole()

Player extends Person
Admin extends Person

Team contains Player (by ID)
Player owns Hero (by ID)
Hero has compatible Equipment (by ID)
MatchRecord belongs to Team and has hero picks per Player

GameDataManager holds all the data
AuthenticationService, SearchService, RankingService, FileStorageService all use GameDataManager
Main uses all the services
6. Data Design
For the initial sample data, I'll create:

3 teams: Dragon Warriors, Storm Legends, Jade Guardians

15 players, each with 3 heroes and at least one equipped item

15 heroes covering assassin, mage, marksman, warrior, tank, and support types

20 equipment items (weapon, armor, accessory)

12 match records with hero picks and win/loss results

For saving, each line in data/game_data.txt will start with a prefix like TEAM|, EQUIP|, HERO|, PLAYER|, or MATCH| so I know what type of record it is.

7. How I'll Use AI
I'll use different "agent roles" for different tasks:

Agent Role	What I'll ask them to help with
Architect Agent	Class structure, relationships, package layout, UML ideas
Implementation Agent	Specific service methods, menu wiring, persistence format
Testing/Reviewer Agent	Help draft test cases, review for null/duplicate issues, check deletion consistency
I'll be responsible for reading the requirements, making final design decisions, testing everything manually, doing Git commits, and writing the reflection myself.

8. My Prompt Strategy
I won't just ask AI to "write my project". I'll try to write specific prompts like:

Ask for design only, no full code yet

Ask for implementation of just one method or class at a time

Ask AI to review my code for bugs or design issues

After AI gives me code, I'll compile it, run it, and check if it actually does what I need before accepting it.

9. Development Timeline
Stage	What I'll do
Stage 1	Read the PDF requirements, create Git repo, write plan.md
Stage 2	Ask Architect Agent for feedback on my class design
Stage 3	Implement the model classes and sample data
Stage 4	Implement search and report menus
Stage 5	Add authentication and separate admin/player menus
Stage 6	Add file persistence and ranking features
Stage 7	Use Testing Agent to find bugs and fix them
Stage 8	Finish documentation, write reflection, export git history
10. Testing Plan
I'll test all the main features: player lookup, team overview, hero details, equipment ranking, match history, leaderboard, admin login, player login, invalid login, adding duplicate IDs, searching for missing records, and making sure file save/load works. I'll record everything in docs/test-cases.md.

11. Risks and How I'll Avoid Them
Risk	What I'll do about it
Putting too much code in Main	Split logic into service classes from the beginning
Deleting something but leaving broken references	Make sure remove methods clean up everywhere (hero from players, player from team, etc.)
User enters invalid input	Use InputHelper to loop until they give a valid number or yes/no
File format gets messed up	Use typed prefixes and load in a specific order
Fake AI evidence	Actually record my real prompts, Git commits, and what I decided to accept or change
12. Reflection Placeholder
I'll write my final reflection in ai/reflection.md after I finish coding and testing. I'll answer all 10 questions honestly about what worked, what didn't, and what I learned from using AI.

