<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>result page</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<form method="post">
    <br>
    <h1>MY<span style="color: #e6b800">SITE</span></h1>
    <h2><%= session.getAttribute("weekend").toString() %> </h2> <br>
</form>
</body>
</html>