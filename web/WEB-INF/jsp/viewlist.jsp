<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ToDoApp</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="<c:url value="/resources/css/custom.css" />">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
                        <c:if test="${!empty userid}">
                        <li><a href="${pageContext.request.contextPath}/createlist">Create a List</a></li>
                        </c:if>
                </ul>
                <c:if test="${!empty userid}">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> ${email}</a>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/mylists"><span class="glyphicon glyphicon-folder-open"></span> My Lists</a></li>
                                <li><a href="${pageContext.request.contextPath}/profile"><span class="glyphicon glyphicon-book"></span> Profile</a></li>
                                <li class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/logout"><span class="glyphicon glyphicon-log-out"></span> Log out</a></li>
                            </ul>
                    </ul>
                </c:if>
            </div>
        </div>
    </nav>               
    <div style="margin-top: 30px;"></div>
    <main role="main" class="container">
        <div class="jumbotron">
            <h1 class="display-3 text-center">To Do List App</h1>
        </div>
        <div style="margin-top: 30px;"></div>
       <div class="form-grup">
            <form method="get">
                <table class="table table-striped table-bordered">
                    <tr>
                        <th class="col-md-4" style="text-align: center;">Name</th>
                        <th class="col-md-1" style="text-align: center;">Date Made</th>
                        <th class="col-md-1" style="text-align: center;">View</th>
                        <th class="col-md-1" style="text-align: center;">Invite</th>
                        <th class="col-md-1" style="text-align: center;">Remove</th>
                    </tr>
                    <c:forEach var="list" items="${lists}">
                        <tr>
                            <td>${list.todolistname}</td>
                            <td>${list.createdate}</td>
                            <td style="text-align: center;"><input type="submit" formaction="${pageContext.request.contextPath}/getlistdetails/${list.id}" class="btn btn-primary btn-sm" style="width: 70px;" value="View" /></td>
                            <td style="text-align: center;"><input type="submit" formaction="${pageContext.request.contextPath}/invite/${list.id}" class="btn btn-success btn-sm" style="width: 70px;" value="Invite" /></td>
                            <td style="text-align: center;"><input type="submit" formaction="${pageContext.request.contextPath}/remove/${list.id}" class="btn btn-danger btn-sm" style="width: 70px;" value="Remove"</td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </div>
    </main>
</body>
</html>
