Stappenstrijd
=============

A toy project to get to know Spring Boot and Kafka a bit.
Please do not take any of this code as best (or even good) practice 🙂️

Usage
-----

At first, the scoreboard is empty:

	$ curl http://localhost:8081/scorebord
	[]

Now, we add some steps:

	$ curl -H 'Content-Type: application/json' --data-raw '{"username": "jeroen", "dateOfActivity": "2021-01-31", "numberOfSteps": 42}' http://localhost:8080/stappen
	$ curl -H 'Content-Type: application/json' --data-raw '{"username": "sebbe", "dateOfActivity": "2021-01-31", "numberOfSteps": 3}' http://localhost:8080/stappen

At this time, `jeroen` is in the lead:

	$ curl http://localhost:8081/scorebord
	[{"username":"jeroen","totalNumberOfSteps":42},{"username":"sebbe","totalNumberOfSteps":3}]

But this was just a fluke. Some more steps are added:

	$ curl -H 'Content-Type: application/json' --data-raw '{"username": "jeroen", "dateOfActivity": "2021-01-31", "numberOfSteps": 12}' http://localhost:8080/stappen
	$ curl -H 'Content-Type: application/json' --data-raw '{"username": "sebbe", "dateOfActivity": "2021-01-31", "numberOfSteps": 839}' http://localhost:8080/stappen

Now, the scoreboard is more realistic:

	$ curl http://localhost:8081/scorebord
	[{"username":"sebbe","totalNumberOfSteps":842},{"username":"jeroen","totalNumberOfSteps":54}]

Roadmap
-------

1. ✓ `{user}/stappen` endpoint to POST steps to.
   Generates BunchOfStepsTaken events.
2. ✓ Service that picks up those events
   and maintains totals per user (in memory).
3. ✓ `scorebord` endpoint to get a ranking.

These are purely JSON endpoints. No UI.
Just document the relevant `curl` calls here.

Stretch goals
-------------

- Persist scores in a database
- Ranking per day
- An actual little web-UI
- An actual app to add steps

