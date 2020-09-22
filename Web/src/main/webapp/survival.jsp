<%@ page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<html>
<head>
    <title>Crazy Typer</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Petar Nyagolov">
    <meta name="keywords" content="typing,test,crazy,typer,game">

    <link rel="stylesheet" type="text/css" href="styles/resetsheet.css">
    <link rel="stylesheet" type="text/css" href="styles/main.css">

    <script src="scripts/word_t.js"></script>
</head>

<body style="background: url(images/background.jpg) 100% 100%; overflow: hidden;">
    <header>
        <div style = "width: 25vw;">
            <p>Nickname: <%= request.getSession().getAttribute("nickname") %></p>
            <p><a href="/nickname" onclick="window.location.href = '/'">Edit nickname</a></p>
        </div>

        <div style="width: 50vw; cursor: default">
            <h1 style="cursor: pointer"><strong>Crazy Typer</strong></h1>
        </div>

        <div style="width: 25vw;">
        </div>
    </header>

    <script>
        var nickname = "Pesho";
        var gameId;

        let http = new XMLHttpRequest();
        http.open("POST", "http://localhost:8080/proxy", true);
        http.responseType = "json";
        http.onload = function() {
            gameId = http.response.gameId;
            console.log("Game id is " + gameId);
        }
        http.send();
    </script>

    <iframe width="0" height="0
        <div style="width: 25vw;">
        </div>
    </header>" border="0" name="dummyframesurvival" id="dummyframesurvival"></iframe>

    <div class = "game-wrapper">
        <canvas class = "game-canvas" id = "survivalCanvas" width = "1200" height = "600"></canvas>

        <div class = "user-controls">
            <input class = "user-input" type = "text" id = "survivalUserInput" placeholder = "Type words here">
            <div class = "time-left" id = "survivalTimer">60</div>
            <button class = "reset-button" id = "survivalResetButton">Reset</button>
            <div class = "score-display" id = "survivalScore">Score: 0</div>
        </div>

        <script>
            var canvas = document.getElementById("survivalCanvas");
            var ctx = canvas.getContext("2d");
            var activeWords = [];
            var userInput = document.getElementById("survivalUserInput");
            var timer = document.getElementById("survivalTimer");
            var score = document.getElementById("survivalScore");
            var getGameStateInterval;
            var renderInterval;

            userInput.onkeydown = function(event) {
              if(event.keyCode==13 || event.keyCode==32 || event.which==13 || event.which==32) {
                event.preventDefault();

                let word = userInput.value;
                userInput.value = "";

                let request = new XMLHttpRequest();
                request.open('PUT', 'http://localhost:8080/proxy/' + gameId + "?word=" + word, true);
                request.send();
              }
            }

            function drawRectangle(x, y, width, height, color) {
                ctx.fillStyle = color;
                ctx.fillRect(x, y, width, height);
            }

            function drawStrokedRectangle(x, y, width, height, color, strokeColor) {
                ctx.lineWidth = 2;
                ctx.fillStyle = color;
                ctx.strokeStyle = strokeColor;
                ctx.fillRect(x, y, width, height);
                ctx.strokeRect(x, y, width, height);
            }

            function drawWord(word) {
                drawStrokedRectangle(word.x, word.y, word.width, word.height, word.color, "white");

                ctx.fillStyle = "black";
                ctx.font = "50px arial";
                ctx.fillText(word.word, word.x + 5, word.y + 45);
            }

            function drawScore(scoreToDraw) {
                drawRectangle(0, 0, canvas.width, canvas.height, "black");

                ctx.fillStyle = "#029300";
                ctx.font = "100px arial";

                let text = "Score: " + scoreToDraw;

                ctx.fillText(text, canvas.width/2 - ctx.measureText(text).width/2, canvas.height/2);

                timer.style.backgroundColor="#c40000";
                score.style.backgroundColor="#029300";

                score.innerHTML = text;
                timer.innerHTML = 0;
            }

            function render() {
                drawRectangle(0, 0, canvas.width, canvas.height, "black");

                for(var i=0;i<activeWords.length;i++) {
                    drawWord(activeWords[i]);
                }
            }

            function getGameState() {
                let request = new XMLHttpRequest();
                request.open('GET', 'http://localhost:8080/proxy/' + gameId, true);
                request.responseType = 'json';
                request.onreadystatechange = function() {
                    if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                        let gameState = request.response;

                        if (gameState.gameStatus == 'FINISHED') {
                            drawScore(gameState.score);
                            clearInterval(getGameStateInterval);
                            clearInterval(renderInterval);
                            return;
                        }

                        let wordsList = gameState.words;
                        console.log("Game status: " + gameState.gameStatus);

                        score.innerHTML = "Score: " + gameState.score;
                        timer.innerHTML = Math.ceil(gameState.timeLeftMillis / 1000);

                        activeWords = [];
                        for (let i = 0; i < wordsList.length; i++) {
                            activeWords.push(
                                new word_t(
                                    wordsList[i].word,
                                    wordsList[i].color,
                                    wordsList[i].position.x, wordsList[i].position.y,
                                    wordsList[i].size.width, wordsList[i].size.height,
                                    wordsList[i].velocity.x, wordsList[i].velocity.y));
                        }
                    }
                }

                request.send(gameId);
            }

            getGameStateInterval = setInterval(getGameState, 32);
            renderInterval = setInterval(render, 32);
        </script>
    </div>
</body>

</html>