<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Site page</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<form action="resultWeekend.do" method="post">
    <div class="menu">
        <a href="../index.html">Home</a>
        <br>
        <%= session.getAttribute("name").toString() %>
    </div>
    <br>
    <h1>Welcome to MY<span style="color: #e6b800">SITE</span> </h1>
    <h2>Select the date</h2>
    Previous date: <input type="date" name="beforeDate"
                          max="3999-05-05" min="1-01-01"><br><br>
    Next date: <input type="date" name="afterDate"
                      max="3999-05-05" min="1-01-01"> <br><br>
    <input id="count" type="submit" name="submit" value="COUNT"> <br><br>
</form>
</body>
</html>