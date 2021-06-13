<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.typewars.model.GameRecord" %>
<% List<GameRecord> standardRecords = (List) request.getAttribute("standardRecords"); %>
<% List<GameRecord> survivalRecords = (List) request.getAttribute("survivalRecords"); %>
<% Integer notificationsCount = (Integer) request.getAttribute("notificationsCount"); %>
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
</head>

<body style="background: url(images/background.jpg) 100% 100%;">
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
        
        window.onload = function() {
            allNotifications = <%=notificationsCount%>;
            currentNotification = allNotifications == 0 ? 0 : 1;
            updateNotifications();
        }
    </script>
    <header>
        <div style = "width: 25vw;">
            <p>Nickname: <%= request.getSession().getAttribute("nickname") %></p>
            <p><a href="/nickname">Edit nickname</a></p>
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

    <div id="menuContainer">
        <div id="standardContainer">
            <table style = "width: 100%" align = "center">
                <caption>TOP 5 STANDARD RESULTS (last 24 hours)</caption>
                <tr>
                    <th>#</th>
                    <th>Nickname</th>
                    <th>Score</th>
                </tr>
                <tr>
                    <td>1</td>
                    <td><%= standardRecords.size() >= 1 ? standardRecords.get(0).getNickname() : "" %></td>
                    <td><%= standardRecords.size() >= 1 ? standardRecords.get(0).getScore() : "" %></td>
                </tr>
                <tr>
                    <td>2</td>
                    <td><%= standardRecords.size() >= 2 ? standardRecords.get(1).getNickname() : "" %></td>
                    <td><%= standardRecords.size() >= 2 ? standardRecords.get(1).getScore() : "" %></td>
                </tr>
                <tr>
                    <td>3</td>
                    <td><%= standardRecords.size() >= 3 ? standardRecords.get(2).getNickname() : "" %></td>
                    <td><%= standardRecords.size() >= 3 ? standardRecords.get(2).getScore() : "" %></td>
                </tr>
                <tr>
                    <td>4</td>
                    <td><%= standardRecords.size() >= 4 ? standardRecords.get(3).getNickname() : "" %></td>
                    <td><%= standardRecords.size() >= 4 ? standardRecords.get(3).getScore() : "" %></td>
                </tr>
                <tr>
                    <td>5</td>
                    <td><%= standardRecords.size() >= 5 ? standardRecords.get(4).getNickname() : "" %></td>
                    <td><%= standardRecords.size() >= 5 ? standardRecords.get(4).getScore() : "" %></td>
                </tr>
            </table>

            <button class="play-button" onclick="window.location.href='standard'" title="You have 60 seconds to type words you see on the screen. Your score is the total number of letters in the words you type correctly.">Standard</button>
        </div>

        <div id="survivalContainer">
            <table style = "width: 100%" align = "center">
                <caption>TOP 5 SURVIVAL RESULTS (last 24 hours)</caption>
                <tr>
                    <th>#</th>
                    <th>Nickname</th>
                    <th>Score</th>
                </tr>
                <tr>
                    <td>1</td>
                    <td><%= survivalRecords.size() >= 1 ? survivalRecords.get(0).getNickname() : "" %></td>
                    <td><%= survivalRecords.size() >= 1 ? survivalRecords.get(0).getScore() : "" %></td>
                </tr>
                <tr>
                    <td>2</td>
                    <td><%= survivalRecords.size() >= 2 ? survivalRecords.get(1).getNickname() : "" %></td>
                    <td><%= survivalRecords.size() >= 2 ? survivalRecords.get(1).getScore() : "" %></td>
                </tr>
                <tr>
                    <td>3</td>
                    <td><%= survivalRecords.size() >= 3 ? survivalRecords.get(2).getNickname() : "" %></td>
                    <td><%= survivalRecords.size() >= 3 ? survivalRecords.get(2).getScore() : "" %></td>
                </tr>
                <tr>
                    <td>4</td>
                    <td><%= survivalRecords.size() >= 4 ? survivalRecords.get(3).getNickname() : "" %></td>
                    <td><%= survivalRecords.size() >= 4 ? survivalRecords.get(3).getScore() : "" %></td>
                </tr>
                <tr>
                    <td>5</td>
                    <td><%= survivalRecords.size() >= 5 ? survivalRecords.get(4).getNickname() : "" %></td>
                    <td><%= survivalRecords.size() >= 5 ? survivalRecords.get(4).getScore() : "" %></td>
                </tr>
             </table>

            <button class="play-button" onclick="window.location.href='survival'" title="You start with 10 seconds and get an additional second for each word you type correctly but you will never have more than 10 seconds. Your score is the total number of letters in the words you type correctly.">Survival</button>
        </div>
    </div>
</body>
</html>