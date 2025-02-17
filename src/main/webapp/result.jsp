<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SQL Validation Result</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
    <h2 class="text-center">SQL Validation Result</h2>
    <div class="alert alert-info mt-3">
        <%= request.getAttribute("validationResult") %>
    </div>
    <a href="index.jsp" class="btn btn-secondary">Back</a>
</body>
</html>
