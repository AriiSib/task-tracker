<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Welcome - Task Tracker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        body {
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: url('https://www.transparenttextures.com/patterns/black-linen.png'), linear-gradient(135deg, #1f1f1f, #2b2b2b);
            background-size: cover;
            color: #ffffff;
        }

        h1 {
            font-size: 3.5rem;
            margin-bottom: 30px;
            font-weight: bold;
            text-transform: uppercase;
        }

        .start-button {
            background-color: rgba(128, 128, 128, 0.2);
            color: #ffffff;
            border: 2px solid rgba(255, 255, 255, 0.6);
            padding: 15px 40px;
            font-size: 1.5rem;
            font-weight: bold;
            border-radius: 8px;
            transition: background-color 0.3s ease, transform 0.3s ease, box-shadow 0.3s ease;
            box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.5);
            text-decoration: none;
        }

        .start-button:hover {
            background-color: rgba(128, 128, 128, 0.4);
            color: #ffffff;
            transform: translateY(-3px);
            box-shadow: 0px 6px 20px rgba(0, 0, 0, 0.6);
            text-decoration: none;
        }

        .start-button:active {
            background-color: rgba(128, 128, 128, 0.6);
            transform: translateY(1px);
        }
    </style>
</head>

<body>
<div class="text-center">
    <h1>Task Tracker</h1>
    <a href="<%= request.getContextPath() %>/login" class="start-button">Start</a>
</div>
</body>

</html>