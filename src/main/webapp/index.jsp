<%@ page import="java.util.List" %>
<%@ page import="models.Todo" %>
<!DOCTYPE html>
<html>
<head>
    <title>Todo List (MVC)</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Animated Background */
        body {
            min-height: 100vh;
            margin: 0;
            overflow-x: hidden;
            position: relative;
            background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
            background-size: 400% 400%;
            animation: gradient 15s ease infinite;
        }

        @keyframes gradient {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        /* Floating Particles */
        .background-animations {
            position: fixed;
            width: 100vw;
            height: 100vh;
            z-index: 0;
            overflow: hidden;
        }

        .shape {
            position: absolute;
            opacity: 0.15;
            animation: float 40s infinite linear;
        }

        .shape:nth-child(1) {
            width: 120px;
            height: 120px;
            left: 10%;
            top: 20%;
            background: rgba(255,255,255,0.3);
            clip-path: circle(50% at 50% 50%);
            animation-delay: 0s;
        }

        .shape:nth-child(2) {
            width: 80px;
            height: 80px;
            left: 70%;
            top: 60%;
            background: rgba(255,255,255,0.2);
            clip-path: polygon(50% 0%, 0% 100%, 100% 100%);
            animation-duration: 30s;
        }

        .shape:nth-child(3) {
            width: 150px;
            height: 150px;
            left: 30%;
            top: 80%;
            background: rgba(255,255,255,0.25);
            clip-path: polygon(25% 0%, 100% 0%, 75% 100%, 0% 100%);
            animation-duration: 45s;
        }

        @keyframes float {
            0% { transform: translateY(0) rotate(0deg) scale(1); }
            50% { transform: translateY(-100vh) rotate(360deg) scale(1.5); }
            100% { transform: translateY(-200vh) rotate(720deg) scale(2); }
        }

        /* Main Container */
        .todo-container {
            position: relative;
            z-index: 1;
            max-width: 800px;
            margin: 2rem auto;
            padding: 2rem;
            background: rgba(255, 255, 255, 0.95);
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            backdrop-filter: blur(10px);
            transform: translateY(0);
            transition: transform 0.3s ease;
        }

        .todo-container:hover {
            transform: translateY(-5px);
        }

        /* Todo Items */
        .todo-list {
            list-style: none;
            padding: 0;
        }

        .todo-item {
            padding: 1rem;
            margin: 0.5rem 0;
            background: rgba(248, 249, 250, 0.8);
            border-radius: 8px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            animation: slideIn 0.5s ease forwards;
            opacity: 0;
            transform: translateX(-20px);
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        }

        @keyframes slideIn {
            to { opacity: 1; transform: translateX(0); }
        }

        .todo-item:hover {
            transform: scale(1.02);
            box-shadow: 0 4px 15px rgba(0,0,0,0.05);
        }

        /* Form Styling */
        .form-entrance {
            animation: formEntrance 0.8s cubic-bezier(0.34, 1.56, 0.64, 1) forwards;
        }

        @keyframes formEntrance {
            from { opacity: 0; transform: translateY(30px) scale(0.95); }
            to { opacity: 1; transform: translateY(0) scale(1); }
        }

        /* Success Message */
        .success-message {
            animation: fadeInOut 2.5s ease forwards;
        }

        @keyframes fadeInOut {
            0% { opacity: 0; transform: translateY(-20px); }
            15% { opacity: 1; transform: translateY(0); }
            85% { opacity: 1; transform: translateY(0); }
            100% { opacity: 0; transform: translateY(-20px); }
        }
    </style>
</head>
<body>
    <!-- Animated Background Elements -->
    <div class="background-animations">
        <div class="shape"></div>
        <div class="shape"></div>
        <div class="shape"></div>
    </div>

    <!-- Main Content -->
    <div class="container todo-container">
        <h1 class="mb-4 text-center text-primary">Todo List</h1>

        <!-- Success Message -->
        <% if(request.getParameter("success") != null) { %>
            <div class="alert alert-success success-message" role="alert">
                Todo added successfully! ✅
            </div>
        <% } %>

        <!-- Todo List -->
        <ul class="todo-list">
            <%
                List<Todo> todos = (List<Todo>) request.getAttribute("todos");
                if (todos != null) {
                    for (Todo todo : todos) {
            %>
                <li class="todo-item">
                    <div class="d-flex align-items-center gap-3">
                        <input type="checkbox" class="form-check-input">
                        <span class="h5 mb-0"><%= todo.getTask() %></span>
                    </div>
                 
                </li>
            <%  
                    }
                }
            %>
        </ul>

        <!-- Add Todo Form -->
        <form action="todos" method="POST" class="mt-4 form-entrance">
            <div class="input-group">
                <input type="text" 
                       name="task" 
                       class="form-control form-control-lg" 
                       placeholder="What needs to be done?" 
                       required
                       aria-label="Todo input">
                <button type="submit" class="btn btn-primary btn-lg">
                    Add Task
                </button>
            </div>
        </form>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>