<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Register page</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>

<body>
<jsp:include page="header.jsp"/>

<div class="container col-md-6 col-md-offset-3">
    <h2>User Register Form</h2>

    <c:if test="${not empty sessionScope.ERROR}">
        <div class="alert alert-danger" role="alert">
            <p>${sessionScope.ERROR}</p>
        </div>
        <c:remove var="ERROR" scope="session"/>
    </c:if>

    <form id="registerForm">
        <div id="errorMessage" class="alert alert-danger" style="display:none;"></div>

        <div class="form-group">
            <input type="text" class="form-control" id="firstName" placeholder="First Name" name="firstName" required>
        </div>

        <div class="form-group">
            <input type="text" class="form-control" id="lastName" placeholder="Last Name" name="lastName" required>
        </div>

        <div class="form-group">
            <input type="text" class="form-control" id="username" placeholder="User Name" name="username" required>
        </div>

        <div class="form-group">
            <input type="password" class="form-control" id="password" placeholder="Password" name="password" required>
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

<jsp:include page="footer.jsp"/>

<script>
    document.getElementById('registerForm').addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            username: document.getElementById('username').value,
            password: document.getElementById('password').value
        };

        fetch('<%=request.getContextPath()%>/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = '<%=request.getContextPath()%>/login';
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