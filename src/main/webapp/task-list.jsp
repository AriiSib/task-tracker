<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Task Board</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script>
        function openTask(taskId) {
            window.location.href = "edit?id=" + taskId;
        }
    </script>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: #2b2b2b;">
        <div>
            <a href="<%=request.getContextPath()%>/index.jsp" class="navbar-brand"
               style="font-weight: bold; color: #ffffff;">
                Task-Tracker App
            </a>
        </div>
        <ul class="navbar-nav ms-auto">
            <li class="nav-item">
                <a href="<%=request.getContextPath()%>/logout" class="nav-link" style="color: #ffffff;">
                    Logout
                </a>
            </li>
        </ul>
    </nav>
</header>

<div class="container">
    <h3 class="text-center">Task Board</h3>
    <div class="kanban-board">
        <div class="kanban-column">
            <h4>Not Started</h4>
            <c:forEach var="task" items="${taskList}">
                <c:if test="${task.status.getName() eq 'NOT_STARTED'}">
                    <div class="task-card" onclick="openTask(${task.id})">
                        <h5><c:out value="${task.title}"/></h5>
                        <p>Target Date: <c:out value="${task.targetDate}"/></p>
                    </div>
                </c:if>
            </c:forEach>
            <div class="text-center">
                <form action="<%=request.getContextPath()%>/new" method="POST" style="display: inline-block;">
                    <button type="submit" class="btn btn-success">Add Task</button>
                </form>
            </div>
        </div>

        <div class="kanban-column">
            <h4>In Progress</h4>
            <c:forEach var="task" items="${taskList}">
                <c:if test="${task.status.getName() eq 'IN_PROGRESS'}">
                    <div class="task-card" onclick="openTask(${task.id})">
                        <h5><c:out value="${task.title}"/></h5>
                        <p>Target Date: <c:out value="${task.targetDate}"/></p>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <div class="kanban-column">
            <h4>Done</h4>
            <c:forEach var="task" items="${taskList}">
                <c:if test="${task.status.getName() eq 'DONE'}">
                    <div class="task-card" onclick="openTask(${task.id})">
                        <h5><c:out value="${task.title}"/></h5>
                        <p>Target Date: <c:out value="${task.targetDate}"/></p>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>