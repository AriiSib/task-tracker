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

    <div id="error-message" class="alert alert-danger d-none"></div>

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

        <div class="d-grid gap-2">
            <button type="submit" class="btn btn-primary btn-block">Submit</button>
        </div>

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

        fetch('/login', {
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
                    window.location.href = '/log';
                } else if (!response.ok) {
                    return response.json().then(err => {
                        throw new Error(err.errorMessage || 'Unknown error');
                    });
                }
            })
            .catch(error => {
                const errorMessage = document.getElementById('error-message');
                errorMessage.classList.remove('d-none');
                errorMessage.textContent = error.message;
            });
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFZ8fcbZJpDLUbXU0IT+8tDEn24AdU6lok9ZZz8z1F4J8VIFl1lbFVG5F3"
        crossorigin="anonymous"></script>
</body>

</html>