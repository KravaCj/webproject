<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Successful page</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<form action="../index.html" method="post">
    <div class="menu">
        <a href="../index.html">Home</a>
    </div>
    <br>
    <h1>MY<span style="color: #e6b800">SITE</span></h1>
    <h2><%= session.getAttribute("info").toString() %></h2>
        <input id="in" type="submit" name="submit" value="LOGIN AGAIN"> <br><br>
    </h2>
</form>
</body>
</html>