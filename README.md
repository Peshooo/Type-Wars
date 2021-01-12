A game inspired by typing tests. You will see 10 words on a canvas, each moving in its own direction with its own speed. The moment a word hits the canvas' border, it disappears and another word replaces it on a random place moving in a random direction with random speed. The moment you type a word currently on the canvas (and hit space or enter), the same thing happens but the number of letters in that word is added to your score. There are two modes - Standard and Survival. In Standard mode you simply have 60 seconds to get the maximum score possible. In Survival mode you start with 10 seconds and for each correct word you receive a bonus second but you will never have more than 10 seconds.

Running it locally is simple:

First, start a postgres instance:

`docker run -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root postgres`

Then from the project directory run:

`mvn spring-boot:run -Dspring-boot.run.profiles=dev`

Then open `http://localhost:5000/` and enjoy.