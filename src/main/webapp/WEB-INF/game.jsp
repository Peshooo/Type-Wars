<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<% String gameMode = (String) request.getAttribute("gameMode"); %>
<% String proxyUrl = "\"" + "/game/" + gameMode + "/" + "\""; %>
<% List<String> notifications = (List) request.getAttribute("notifications"); %>
<% String notificationsAll = (String) request.getAttribute("notificationsAll"); %>
<!doctype html>
<html>
<head>
    <title>Type Wars</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Petar Nyagolov">
    <meta name="keywords" content="typing,test,crazy,typer,game">

  <link href="favicon.ico" rel="icon" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="styles/resetsheet.css">
    <link rel="stylesheet" type="text/css" href="styles/main.css">


    <script>
        let allNotifications = 0;
        let currentNotification = 0;
        let notificationsJs = new Array(<%=notificationsAll%>);

        function updateNotifications() {
            document.getElementById("notifications-pages").innerHTML =
                '<a style="cursor: pointer" onclick="previousPage();">⬅</a> ' + currentNotification + ' / ' + allNotifications + ' <a style="cursor: pointer" onclick="nextPage();">➡</a>';

            if (currentNotification != 0) {
                document.getElementById("notifications-text").innerHTML = notificationsJs[currentNotification - 1];
            }
        }

        function previousPage() {
            if (currentNotification <= 1) {
                return;
            }

            --currentNotification;
            updateNotifications()
        }

        function nextPage() {
            if (currentNotification >= allNotifications) {
                return;
            }

            ++currentNotification;
            updateNotifications()
        }

        function focusUserInput() {
            document.getElementById("userInput").focus();
        }

        function windowOnload() {
            allNotifications = <%=notifications.size()%>;
            currentNotification = allNotifications == 0 ? 0 : 1;
            updateNotifications();
            focusUserInput();
        }

        window.onload = windowOnload;
    </script>
    <script src="scripts/word_t.js"></script>
</head>

<body style="background: url(images/background.jpg) 100% 100%; overflow: hidden;">
    <header>
        <div style = "width: 25vw;">
            <p>Nickname: <%= request.getSession().getAttribute("nickname") %></p>
            <p><a href="/nickname" onclick="window.location.href = '/'">Edit nickname</a></p>
        </div>

        <div style="width: 50vw; cursor: default">
            <h1 style="cursor: pointer" onclick="window.location.href = '/'"><strong>Type Wars</strong></h1>
        </div>

        <div style="width: 23vw;">
            <div class="notifications-div">
                <div class="notifications-div-header">
                    <p style="text-align: center;">Notifications</p>
                </div>
                <div class="notifications-div-text">
                    <p id="notifications-text" style="text-align: center;">No notifications.</p>
                </div>
                <div class="notifications-div-pages">
                    <p id="notifications-pages" style="text-align: center;"></p>
                </div>
            </div>
        </div>
    </header>

    <script>
        var gameId = "";

        function generateGameId() {
            let http = new XMLHttpRequest();
            http.open("POST", <%=proxyUrl%>, true);
            http.responseType = "json";
            http.onload = function() {
                gameId = http.response.gameId;
            }
            http.send();
        }

        var lastTypedWord = "";
        generateGameId();
    </script>

    <script>
        function resetGame() {
            clearInterval(getGameStateInterval);
            generateGameId();
            startIntervals();
            focusUserInput();
            timer.style.backgroundColor = "rgba(70, 70, 70, 0.7)";
            score.style.backgroundColor = "rgba(70, 70, 70, 0.7)";
        }
    </script>

    <iframe width="0" height="0
        <div style="width: 25vw;">
        </div>
    </header>" border="0" name="dummyframe" id="dummyframe"></iframe>

    <div class = "game-wrapper">
        <canvas class = "game-canvas" id = "canvas" width = "1200" height = "600"></canvas>

        <div class = "user-controls">
            <input class = "user-input" type = "text" id = "userInput" placeholder = "Type words here">
            <div class = "time-left" id = "timer">60</div>
            <button class = "reset-button" id = "resetButton" onclick="resetGame();">Reset</button>
            <div class = "score-display" id = "score">Score: 0</div>
        </div>

        <script>
            var canvas = document.getElementById("canvas");
            var ctx = canvas.getContext("2d");
            var activeWords = [];
            var userInput = document.getElementById("userInput");
            var timer = document.getElementById("timer");
            var score = document.getElementById("score");
            var getGameStateInterval;

            userInput.onkeydown = function(event) {
              if(event.keyCode==13 || event.keyCode==32 || event.which==13 || event.which==32) {
                event.preventDefault();

                let word = userInput.value;
                lastTypedWord = word;
                userInput.value = "";

                let request = new XMLHttpRequest();
                request.open('PUT', <%=proxyUrl%> + gameId + "?word=" + word, true);
                request.send();

                lastTypedWord = "";
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
                if (word == lastTypedWord) {
                    drawStrokedRectangle(word.x, word.y, word.width, word.height, word.color, "green");
                } else {
                    drawStrokedRectangle(word.x, word.y, word.width, word.height, word.color, "white");
                }

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

            var rememberedScore;

            function render() {
                drawRectangle(0, 0, canvas.width, canvas.height, "black");

                for(var i=0;i<activeWords.length;i++) {
                    drawWord(activeWords[i]);
                }
            }

            function getGameState() {
                if (gameId.length != 36) {
                    return;
                }
                let request = new XMLHttpRequest();
                request.open('GET', <%=proxyUrl%> + gameId, true);
                request.responseType = 'json';
                request.onreadystatechange = function() {
                    if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                        let gameState = request.response;

                        if (gameState == null) {
                            drawScore(rememberedScore);
                            clearInterval(getGameStateInterval);
                            return;
                            //document.getElementById("resetButton").click();
                            //return;
                        }

                        if (gameState.status == 'FINISHED') {
                            rememberedScore = gameState.score
                            drawScore(rememberedScore);
                            clearInterval(getGameStateInterval);
                            return;
                        } else {
                            timer.style.backgroundColor = "rgba(70, 70, 70, 0.7)";
                            score.style.backgroundColor = "rgba(70, 70, 70, 0.7)";
                        }

                        let wordsList = gameState.words;

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
                                    wordsList[i].velocity.x, wordsList[i].velocity.y,
                                    "white"));
                        }
                    }

                    render();
                }

                request.send(gameId);
            }

            function startIntervals() {
                getGameStateInterval = setInterval(getGameState, 32);
            }

            startIntervals();
        </script>
    </div>
</body>

</html>