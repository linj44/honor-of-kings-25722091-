你还不会用吗？
应用图标我都改了你还不会用？







AI-Assisted Honor of Kings Information Management System
Student folder: honor of kings [25722091]

1. What This Project Does
This is a console app that manages Honor of Kings data — players, heroes, equipment, teams, and match records. Admins can do everything, regular players can only view public info and edit their own profile. It also saves data to a file so you don't lose everything when you close it.

2. How to Run It
Important note: If you change the code, you have to delete the out folder and recompile. Just running java -cp out Main will use old compiled files and things will break.

Option A — Double-click (easiest)
Find this file in the project folder and double-click it:

C:\Users\linjl\Desktop\honor of kings [25722091]\compile-and-run.bat

Option B — PowerShell (I recommend this)
powershell
Set-Location -LiteralPath "C:\Users\linjl\Desktop\honor of kings [25722091]"
powershell -ExecutionPolicy Bypass -File .\compile-and-run.ps1
Option C — Do it manually
powershell
Set-Location -LiteralPath "C:\Users\linjl\Desktop\honor of kings [25722091]"

# Step 1: delete old compiled files
Remove-Item -LiteralPath out -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Path out | Out-Null

# Step 2: compile all java files (don't forget UTF-8)
$files = Get-ChildItem -LiteralPath src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out @files

# Step 3: check that the new admin class exists
Test-Path -LiteralPath "out\util\AdminInitializer.class"

# Step 4: run
chcp 65001
java -Dfile.encoding=UTF-8 -cp out Main
How to tell if you're running the new version (not the old one)
Old version (wrong)	New version (correct)
Login says Username (admin or player ID):	Login says Username (admin account or player ID):
Only admin / admin123 works	饭团linj44 or 红糖guoy10 works
No AdminInitializer.class in out\util\	out\util\AdminInitializer.class exists
3. Default Login Accounts
Role	Name	Username	Password
Admin		饭团linj44	饭团linj44	lsfllrljl070530 (I know, it's long)
Admin		郭怡婷		红糖guoy10	123456
Player	—	P001 to P015	player123
Example admin login: username 红糖guoy10, password 123456.
Example player login: username P001, password player123.

4. Features I Implemented
Look up players by ID or name, see their team, level, win rate, heroes, and equipped items

Team overview showing members, average level, total matches, win rate, and best player

Hero details including type, stats, compatible equipment, and who owns them

Equipment ranking with a formula I made up (explained in the design doc)

Match history for a player or team (last N matches, win/loss record, hero pick rates)

Leaderboard sorted by win rate, level, matches, or a custom score

Admins can add, edit, or delete players, heroes, equipment, teams, and matches

Players can view their own profile, edit email/password, and see their match history

Saves everything to data/game_data.txt so data persists between runs

5. Java Concepts I Used
Inheritance: Player and Admin both extend Person

Interfaces: Searchable, Reportable, Persistable, Authenticatable

Polymorphism: I store logged-in users as Person, then check the actual type to show different menus

Collections: HashMap and ArrayList mostly, plus some streams for filtering

Enums: HeroType, EquipmentType, MatchResult, Role

Exception handling: Catching invalid input, missing records, duplicate IDs, file errors

File I/O: Saved everything to a pipe-delimited text file

6. How I Used AI
I used AI (Cursor) to help with design suggestions, writing some of the code, and reviewing for bugs. I recorded all my prompts, which agent role I was using, and what I decided to accept or change. Everything is in the ai/ folder.

7. Testing
I wrote manual test cases and put them in docs/test-cases.md. I tested all the menu options during development to make sure they worked.

8. Known Issues (Things I didn't fix)
Console only — no GUI

When you rename a team, it only changes the name. Player membership editing is pretty basic

When you add a match, it doesn't automatically check if the hero picks are valid — you have to enter them carefully