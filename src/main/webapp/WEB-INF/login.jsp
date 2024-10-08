<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Login page</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>

<jsp:include page="header.jsp"/>

<div class="container col-md-6 offset-md-3 mt-5">
    <h1 class="text-center mb-4">Log in to Task-Tracker</h1>

    <c:if test="${not empty sessionScope.NOTIFICATION}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${sessionScope.NOTIFICATION}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="NOTIFICATION" scope="session"/>
    </c:if>

    <div id="errorMessage" class="alert alert-danger" style="display:none;"></div>

    <form id="loginForm">
        <div class="mb-3">
            <label for="username" class="form-label">User Name:</label>
            <input type="text" class="form-control" id="username" placeholder="Enter your username" name="username"
                   required>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input type="password" class="form-control" id="password" placeholder="Enter your password" name="password"
                   required>
        </div>

        <button type="submit" class="btn-custom">Submit</button>
    </form>
</div>

<jsp:include page="footer.jsp"/>

<script>
    document.getElementById('loginForm').addEventListener('submit', function (event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        const loginData = {
            username: username,
            password: password
        };

        fetch('/task-tracker/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: document.getElementById('username').value,
                password: document.getElementById('password').value
            })
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = '<%=request.getContextPath()%>/list';
                } else if (response.status === 400) {
                    return response.json();
                }
            })
            .then(data => {
                if (data && data.error) {
                    const errorMessageElement = document.getElementById('errorMessage');
                    errorMessageElement.textContent = data.error;
                    errorMessageElement.style.display = 'block';
                }
            })
            .catch(error => console.error('Error:', error));
    });
</script>
</body>
</html>