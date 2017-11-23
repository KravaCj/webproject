<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="../index.html" method="post">
    <div class="menu">
        <a href="../index.html">Home</a>
    </div>
    <br>
    <h1>MY<span style="color: #e6b800">SITE</span></h1>
   <h2> <%= session.getAttribute("info").toString() %> </h2> <br>
        <input id="in" type="submit" name="submit" value="LOGIN AGAIN"> <br><br>
    </h2>
</form>
</body>
</html>
