<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SQL Validator</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
    <h2 class="text-center">SQL Query Validator</h2>
    <form action="SQLValidatorServlet" method="post" class="mt-4">
        <div class="mb-3">
            <label for="query" class="form-label">Enter your SQL Query:</label>
            <textarea class="form-control" id="query" name="query" rows="3" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Validate Query</button>
    </form>
</body>
</html>
