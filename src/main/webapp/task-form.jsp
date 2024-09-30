<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Task Tracker - Task Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <script>
        function deleteTask(taskId) {
            fetch('<%=request.getContextPath()%>/delete', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({id: taskId})
            })
                .then(response => {
                    if (response.ok) {
                        window.location.href = '<%=request.getContextPath()%>/list';
                    } else {
                        alert("Failed to delete the task.");
                    }
                })
                .catch(error => {
                    alert("An error occurred: " + error.message);
                });

        }
    </script>

</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark">
        <div>
            <a href="https://github.com/AriiSib/task-tracker" class="navbar-brand">Task-tracker App</a>
        </div>
        <ul class="navbar-nav navbar-collapse justify-content-end">
            <li><a href="<%=request.getContextPath()%>/logout" class="nav-link">Logout</a></li>
        </ul>
    </nav>
</header>

<div class="container mt-5">
    <h3 class="text-center">Task Form</h3>
    <form id="taskForm" onsubmit="return false;">
        <div class="mb-3">
            <label for="title" class="form-label">Title</label>
            <input type="text" id="title" name="title" class="form-control" required
                   value="${task.title != null ? task.title : ''}">
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea id="description" name="description"
                      class="form-control">${task.description != null ? task.description : ''}</textarea>
        </div>

        <div class="mb-3">
            <label for="targetDate" class="form-label">Target Date</label>
            <input type="date" id="targetDate" name="targetDate" class="form-control" required
                   value="${task.targetDate != null ? task.targetDate : ''}">
        </div>

        <div class="mb-3">
            <label for="status" class="form-label">Status</label>
            <select id="status" name="status" class="form-select">
                <option value="NOT_STARTED" <c:if test="${task.status.getName() eq 'NOT_STARTED'}">selected</c:if>>Not
                    Started
                </option>
                <option value="IN_PROGRESS" <c:if test="${task.status.getName() eq 'IN_PROGRESS'}">selected</c:if>>In
                    Progress
                </option>
                <option value="DONE" <c:if test="${task.status.getName() eq 'DONE'}">selected</c:if>>Done</option>
            </select>
        </div>

        <input type="hidden" name="id" id="taskId" value="${task.id != null ? task.id : ''}"/>

        <button type="button" class="btn btn-primary" id="saveTaskButton">
            ${task.id != null ? 'Update Task' : 'Save Task'}
        </button>

        <c:if test="${task.id != null}">
            <button type="button" class="btn btn-danger" onclick="deleteTask(${task.id})">Delete Task</button>
        </c:if>

    </form>
</div>

<jsp:include page="footer.jsp"/>

<script>
    document.getElementById('saveTaskButton').addEventListener('click', function () {
        const taskId = document.getElementById('taskId').value;
        const isUpdating = taskId !== '';

        const taskData = {
            id: taskId,
            title: document.getElementById('title').value,
            description: document.getElementById('description').value,
            targetDate: document.getElementById('targetDate').value,
            status: document.getElementById('status').value
        };

        const method = isUpdating ? 'PUT' : 'POST';
        const url = isUpdating ? '' : '<%=request.getContextPath()%>/save';

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(taskData)
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = '<%=request.getContextPath()%>/list';
                } else {
                    return response.json().then(err => {
                        throw new Error(err.errorMessage || 'Unknown error');
                    });
                }
            })
            .catch(error => {
                const errorMessage = document.createElement('div');
                errorMessage.className = 'alert alert-danger mt-3';
                errorMessage.textContent = error.message;
                document.querySelector('.container').appendChild(errorMessage);
            });
    });
</script>

</body>
</html>