# Worklog Lite Spec

## Goal

Create a mini attendance / work log app for a one-hour demo.

Users can record work sessions with date, start time, end time, task title, category, and memo. The app calculates each session duration and the total work time for a selected day.

## MVP features

Must have:

1. Work log list
   - Show all logs, newest first
   - Show date, time range, task title, category, duration, memo
   - Show daily total duration when filtered by date

2. Create work log
   - Fields:
     - workDate
     - startTime
     - endTime
     - taskTitle
     - category
     - memo
   - Validate required fields
   - Validate that endTime is after startTime

3. Edit work log

4. Delete work log

5. Date filter
   - User can select a date
   - List shows logs for that date
   - Total duration for that date is shown

6. Initial sample data
   - Add 5 sample logs on startup or via data.sql / CommandLineRunner

7. README
   - Include setup, run command, test command, and demo URLs

## Nice-to-have only if time remains

- Category filter
- CSV export
- Today shortcut
- Simple dashboard cards

Do not implement nice-to-have features until all MVP features work.

## Data model

Entity: WorkLog

Fields:
- id: Long
- workDate: LocalDate
- startTime: LocalTime
- endTime: LocalTime
- taskTitle: String
- category: String
- memo: String
- createdAt: LocalDateTime
- updatedAt: LocalDateTime

Derived behavior:
- durationMinutes = endTime - startTime
- Display duration as "X時間Y分"

## Routes

Suggested routes:

- GET `/` redirects to `/logs`
- GET `/logs` list logs, optional `?date=yyyy-MM-dd`
- GET `/logs/new` new form
- POST `/logs` create
- GET `/logs/{id}/edit` edit form
- POST `/logs/{id}` update
- POST `/logs/{id}/delete` delete

## Acceptance criteria

The app is done when:

- `mvn test` or `./mvnw test` passes
- App starts locally
- `/logs` displays sample data
- A user can create a log
- Invalid time range is rejected
- A user can edit a log
- A user can delete a log
- Date filter works
- Daily total duration is displayed
- README explains how to run the app