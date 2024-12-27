<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Task Tracker - Task Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <script>
        let tags = [];

        document.addEventListener("DOMContentLoaded", function () {
            updateTagList();
        });

        function updateTagList() {
            const tagList = document.getElementById('tagList');
            tagList.innerHTML = '';

            tags.forEach((tag, index) => {
                const tagElement = document.createElement('span');
                tagElement.className = 'badge bg-primary me-2';
                tagElement.textContent = tag;

                const removeButton = document.createElement('button');
                removeButton.className = 'btn btn-sm btn-danger ms-2';
                removeButton.textContent = 'x';
                removeButton.onclick = function () {
                    tags.splice(index, 1);
                    updateTagList();
                };

                tagElement.appendChild(removeButton);
                tagList.appendChild(tagElement);
            });
        }

        document.addEventListener("DOMContentLoaded", function () {
            const tagInput = document.getElementById('tagInput');
            tagInput.addEventListener('keypress', function (event) {
                if (event.key === 'Enter') {
                    event.preventDefault();
                    const tag = event.target.value.trim();
                    if (tag && !tags.includes(tag)) {
                        tags.push(tag);
                        updateTagList();
                        event.target.value = '';
                    }
                }
            });
        });
    </script>
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
            <a href="<%=request.getContextPath()%>/start" class="navbar-brand">Task-tracker App</a>
        </div>
        <ul class="navbar-nav navbar-collapse justify-content-end">
            <li><a href="<%=request.getContextPath()%>/logout" class="nav-link">Logout</a></li>
        </ul>
    </nav>
</header>

<div class="container mt-5 mt-5-custom">
    <div class="form-container">
        <h3 class="text-center">Task Form</h3>

        <div id="errorMessages" class="alert alert-danger d-none"></div>

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

            <div class="form-group">
                <label for="status">Status</label>
                <select id="status" name="status" class="form-control">
                    <option value="NOT_STARTED">Not Started</option>
                    <option value="IN_PROGRESS">In Progress</option>
                    <option value="DONE">Done</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="tagList" class="form-label">Tags</label>
                <input type="text" id="tagInput" class="form-control" placeholder="Add a tag and press Enter">
                <div id="tagList" class="mt-2"></div>
            </div>

            <c:forEach var="tag" items="${tagList}">
                <script>
                    tags.push("${tag.name}");
                </script>
            </c:forEach>

            <input type="hidden" name="id" id="taskId" value="${task.id != null ? task.id : ''}"/>

            <button type="button" class="btn btn-primary" id="saveTaskButton">
                ${task.id != null ? 'Update Task' : 'Save Task'}
            </button>

            <c:if test="${task.id != null}">
                <button type="button" class="btn btn-danger" onclick="deleteTask(${task.id})">Delete Task</button>
            </c:if>
        </form>
    </div>
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
            status: document.getElementById('status').value,
            tags: tags
        };

        const method = isUpdating ? 'PUT' : 'POST';
        const url = isUpdating ? '<%=request.getContextPath()%>/update' : '<%=request.getContextPath()%>/save';

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
                        throw new Error(err.error || 'Unknown error');
                    });
                }
            })
            .catch(error => {
                const errorMessages = document.getElementById('errorMessages');
                errorMessages.textContent = error.message;
                errorMessages.classList.remove('d-none');
            });
    });
</script>
</body>
</html>