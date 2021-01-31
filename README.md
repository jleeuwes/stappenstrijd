Stappenstrijd
=============

A toy project to get to know Spring Boot and Kafka a bit.

Usage
-----

	curl -H 'Content-Type: application/json' --data-raw '{"dateOfActivity": "2021-01-31", "numberOfSteps": 42}' http://localhost:8080/1234/stappen

Roadmap
-------

1. `{user}/stappen` endpoint to POST steps to.
   Generates BunchOfStepsTaken events.
2. Service that picks up those events
   and maintains totals per user (sqlite? in memory?)
3. `scorebord` endpoint to get a ranking.

These are purely JSON endpoints. No UI.
Just document the relevant `curl` calls here.

Stretch goals
-------------

- Ranking per day
- An actual little web-UI
- An actual app to add steps

