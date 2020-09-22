<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.typer.web.recordstore.RecordStoreClient" %>
<%@ page import="com.typer.web.recordstore.SurvivalRecord" %>
<% RecordStoreClient recordStoreClient = (RecordStoreClient) request.getAttribute("recordStoreClient"); %>
<% List<SurvivalRecord> survivalRecords = recordStoreClient.getTopFiveSurvivalRecords(); %>

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
</head>

<body style="background: url(images/background.jpg) 100% 100%;">
    <script>
        console.log("${sessionId}");
    </script>
    <header>
        <div style = "width: 25vw;">
            <p>Nickname: <%= request.getSession().getAttribute("nickname") %></p>
            <p><a href="/nickname">Edit nickname</a></p>
        </div>

        <div style="width: 50vw; cursor: default">
            <h1 style="cursor: pointer" onclick="window.location.href = '/'"><strong>Crazy Typer</strong></h1>
        </div>

        <div style="width: 25vw;">
        </div>
    </header>

    <div id="menuContainer">
        <div class="table-button" id="standardContainer">
            <table class = "results-table" style = "width: 100%" align = "center">
                <caption>TOP 5 STANDARD RESULTS (last 24 hours)</caption>
                <tr>
                    <th>#</th>
                    <th>Nickname</th>
                    <th>Score</th>
                </tr>
                <tr>
                    <td>1</td>
                    <td>Pesho1</td>
                    <td>200</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>Pesho2</td>
                    <td>190</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>Pesho3</td>
                    <td>180</td>
                </tr>
                <tr>
                    <td>4</td>
                    <td>Pesho4</td>
                    <td>170</td>
                </tr>
                <tr>
                    <td>5</td>
                    <td>Pesho5</td>
                    <td>160</td>
                </tr>
            </table>

            <button class="play-button" title="You have 60 seconds to type words you see on the screen. Your score is the total number of letters in the words you type correctly.">Standard</button>
        </div>

        <div class="table-button" id="survivalContainer">
            <table class = "results-table" style = "width: 100%" align = "center">
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