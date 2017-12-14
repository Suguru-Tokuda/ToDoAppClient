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
        <div style="margin-top: 30px;"></div>
        <div class="col-md-6">
            <h2>${toDoList.todolistname}</h2>
            <form method="GET">
                <table class="table table-striped table-bordered">
                    <tr>
                        <th class="col-md-1" style="text-align: center;">#</th>
                        <th class="col-md-1" style="text-align: center;">Item</th>
                        <th class="col-md-1" style="text-align: center;">Action</th>
                    </tr>
                    <c:set var="count" value="1" scope="page" />
                    <c:forEach var="item" items="${itemList}">
                        <tr>
                            <td style="text-align: center;">${count}</td>
                            <c:choose>
                                <c:when test="${item.important == true}">
                                    <td style="color: red; font-weight: bold;">${item.itemname}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${item.itemname}</td>
                                </c:otherwise>
                            </c:choose>
                            <td style="text-align: center;"><input type="submit" class="btn btn-primary btn-sm" style="width: 70px;" value="View" formaction="${pageContext.request.contextPath}/viewitem/${item.id}" /></td>
                        </tr>
                        <c:set var="count" value="${count+1}" scope="page" />
                    </c:forEach>
                </table>
            </form>
        </div>
        <div style="margin-top: 68px;"></div>
        <div class="col-md-6">
            <form method="post">
                <div class="form-grup">
                    <label class="control-label">Item</label>
                    <input type="text" class="form-control" name="itemname" value="${itemToView.itemname}" />
                    <label class="control-label">Due</label>
                    <input type="text" class="form-control" name="due" placeholder="YYYY-MM-DD" value="${itemToView.due}" />
                    <label class="form-check-label">
                        <input 
                            <c:choose>
                                <c:when test="${itemToView.important == true}">     
                                    checked="true" 
                                </c:when>
                                <c:otherwise>
                                </c:otherwise>
                            </c:choose>
                            type="checkbox" name="important" class="form-check-input" value="${itemToView.important}">
                        Important                            
                    </label>
                    <label class="form-check-label">
                        <input type="checkbox" name="finished" class="form-check-input" value="${itemToView.important}">
                        Finished
                    </label>
                    <div style="margin-top: 30px;"></div>
                    <div style="margin-top: 30px;" ></div>
                    <input type="submit" class="btn btn-warning" value="Update" formaction="${pageContext.request.contextPath}/update" />
                    <input type="submit" class="btn btn-primary" value="Add New" formaction="${pageContext.request.contextPath}/addnewitem" />
                </div>
            </form>
        </div>
    </main>
</body>
</html>
