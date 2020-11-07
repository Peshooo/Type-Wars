<html>

<head>
  <title>Crazy Typer</title>

  <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="author" content="Petar Nyagolov">
  <meta name="keywords" content="typing,test,crazy,typer,game">

  <link href="favicon.ico" rel="icon" type="image/x-icon"/>
  <link rel="stylesheet" type="text/css" href="styles/resetsheet.css">
  <link rel = "stylesheet" type = "text/css" href = "styles/main.css">

  <script>
    window.onload = function() {
      document.getElementById("nicknameInput").focus();
    }
  </script>
</head>

<body style = "background: url(images/background.jpg) 100% 100%;">
  <header>
    <div style = "width: 25vw;">
        <%
            String nickname = (String) request.getSession().getAttribute("nickname");
            if (nickname == null) {
                nickname = "";
            }
        %>
        <p>Nickname: <%= nickname %></p>
        <p><a href="/nickname">Edit nickname</a></p>
    </div>

    <div style="width: 50vw; cursor: default">
        <h1 style="cursor: pointer" onclick="window.location.href = '/'"><strong>Type Wars</strong></h1>
    </div>

          <div style="width: 25vw;">
          </div>
      </header>

  <div id = "nicknameFormContainer">
    <h1>Choose a nickname</h1>
    <form id = "nicknameForm" action = "/nickname" method = "post">
      <input type = "text" name = "nickname" maxlength = "24" id = "nicknameInput">
      <button name = "submitnickname" type = "submit">Play</button>
    </form>
  </div>
</body>

</html>