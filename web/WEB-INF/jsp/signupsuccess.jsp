<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ToDoApp</title>
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/custom.css" />">
        <script src="<c:url value="/resources/js/jQuery.js" />"></script>
        <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
    </head>
    <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/">ToDoApp</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
                        <c:if test="${!empty username}">
                        <li><a href="${pageContext.request.contextPath}/addList">Create a List</a></li>
                        </c:if>
                </ul>
            </div>
        </div>
    </nav>
    <div style="margin-top: 30px;"></div>
    <main role="main" class="container">
        <div class="jumbotron">
            <h1 class="display-3 text-center">To Do List App</h1>
        </div>
        <div style="margin-top: 30px;"></div>
        <div class="col-md-3 col-md-offset-4 col-sm-3 col-sm-offset-4">
            <div class="form-grup">
                <h3>Account Registered</h3>
                <table class="table">
                    <tr>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Password</th>
                    </tr>
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.password}</td>
                    </tr>
                </table>
                <div style="margin-top: 30px;"></div>
                <label class="control-label"><a href="${pageContext.request.contextPath}/">Sign in</a></label><br>
            </div>
        </div>
    </main>
</body>
</html>
