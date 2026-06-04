# Agent Log

## Architect Agent
Main contribution:
Suggested the initial OOP structure, service layer split, authentication flow, and documentation outline.

Human decision:
Accepted abstract `Person`, rejected adding a separate `GameAccount` class because authentication could be handled directly by `Person` subclasses.

Related commits:
- `[AI-Architect] draft OOP class structure for Person Hero Team`
- `[AI-Architect] add interfaces enums and service package layout`
- `[AI-Architect] design authentication and menu permissions`

## Implementation Agent
Main contribution:
Implemented model classes, search/report services, ranking logic, file persistence, sample dataset, and menu wiring.

Human decision:
Modified ranking formulas and persistence load order after manual review. Limited AI-generated code to services and menu integration rather than one monolithic file.

Related commits:
- `[AI-Implementation] implement core model classes and sample data`
- `[AI-Implementation] implement search and ranking services`
- `[AI-Implementation] implement console menus and admin CRUD`
- `[AI-Implementation] implement file persistence`

## Testing/Reviewer Agent
Main contribution:
Reviewed deletion consistency, invalid login handling, duplicate ID cases, and compilation issues.

Human decision:
Fixed hero deletion cleanup, switch syntax in `Main`, and package placement for `DataInitializer`.

Related commits:
- `[AI-Review] review deletion consistency`
- `[Fix] fix switch return syntax in Main`
- `[Test] add manual test cases for core features`

## Documentation Agent (optional)
Main contribution:
Drafted README, plan.md, design.md, and test case templates.

Human decision:
Edited content to match final implemented behavior and student submission folder name.

Related commits:
- `[Docs] write plan design README and AI evidence`
