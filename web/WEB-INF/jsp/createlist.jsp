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
    <div style="margin-top: 35px;"></div>
    <main role="main" class="container">
        <form method="post">
            <c:choose>
                <c:when test="${!empty toDoLists}">
                    <div class="col-md-6 col-xs-9">
                        <h2>Create from History</h2>
                        <div style="margin-top: 30px;"></div>  
                        <label class="control-label">History</label>
                        <select class="form-control" id="toDoLists" name="todolistid" >
                            <c:forEach var="toDoList" items="${toDoLists}">
                                <option value="${toDoList.id}">${toDoList.todolistname}: ${toDoList.createdate}</option>
                            </c:forEach>
                        </select>
                        <div style="margin-top: 20px;"></div>
                        <input type="submit" class="btn btn-success" value="Create from History" formaction="${pageContext.request.contextPath}/createfromhistory" />
                    </div>
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="todolistid" value="0"/>
                </c:otherwise>
            </c:choose>
            <div class="col-md-3 col-xs-8">
                <h2>Create New List</h2>
                <div style="margin-top: 35px;"></div>
                <div class="form-group">
                    <label class="control-label">List Name</label>
                    <input class="form-control" type="text" name="todolistname" />
                    <div style="margin-top: 20px;"></div>
                    <input type="submit" formaction="${pageContext.request.contextPath}/proceedlistcreation" class="btn btn-primary" value="Create New" />
                </div>
            </div>
        </form>
    </main>
</body>
</html>
