package com.validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

@WebServlet("/SQLValidatorServlet")
public class SQLValidatorServlet extends HttpServlet {
    private static final Set<String> SQL_KEYWORDS = Set.of(
        "SELECT", "FROM", "WHERE", "INSERT", "INTO", "VALUES", "CREATE", "TABLE", "DATABASE",
        "ALTER", "DROP", "UPDATE", "SET", "DELETE", "ORDER BY", "GROUP BY", "HAVING", "LIMIT"
    );

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String result = validateSQL(query);
        request.setAttribute("validationResult", result);
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }

    private String validateSQL(String query) {
        if (query.isEmpty()) {
            return "<span class='text-danger'>❌ Empty SQL Query</span>";
        }

        if (!query.trim().endsWith(";")) {
            return "<span class='text-danger'>❌ Missing semicolon (;) at the end</span>";
        }

        // Track position of tokens
        List<TokenPosition> tokensWithPosition = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\b\\w+\\b|;|,|\\(|\\))");
        Matcher matcher = pattern.matcher(query);
        while (matcher.find()) {
            String token = matcher.group();
            tokensWithPosition.add(new TokenPosition(token, matcher.start()));
        }

        // Check for keyword typos with position
        for (TokenPosition tp : tokensWithPosition) {
            String upperToken = tp.token.toUpperCase();
            if (SQL_KEYWORDS.stream().anyMatch(kw -> getLevenshteinDistance(upperToken, kw) <= 2)) {
                if (!SQL_KEYWORDS.contains(upperToken)) {
                    String correction = correctSQLKeyword(upperToken);
                    return String.format("<span class='text-danger'>❌ Possible typo at position %d: \"%s\" <br> Did you mean: <b>%s</b>?</span>",
                            tp.position, tp.token, correction);
                }
            }
        }

        // Enhanced regex patterns with better structure
        String firstToken = tokensWithPosition.isEmpty() ? "" : tokensWithPosition.get(0).token.toUpperCase();

        try {
            if (firstToken.equals("SELECT")) validateSelect(query);
            else if (firstToken.equals("INSERT")) validateInsert(query);
            else if (firstToken.equals("CREATE")) validateCreate(query);
            else throw new SQLValidationException("Unsupported SQL statement");
        } catch (SQLValidationException e) {
            return "<span class='text-danger'>❌ " + e.getMessage() + "</span>";
        }

        return "<span class='text-success'>✅ SQL syntax is correct!</span>";
    }

    // Helper class for token position tracking
    private static class TokenPosition {
        String token;
        int position;

        TokenPosition(String token, int position) {
            this.token = token;
            this.position = position;
        }
    }

    // Enhanced validation methods
    private void validateSelect(String query) throws SQLValidationException {
        Pattern pattern = Pattern.compile("(?i)^SELECT\\s+.+?\\s+FROM\\s+\\w+" +
                "(\\s+(WHERE|GROUP BY|HAVING|ORDER BY|LIMIT)\\s+.+?)*;?$");
        if (!pattern.matcher(query).matches()) {
            throw new SQLValidationException("Invalid SELECT syntax. Example: SELECT col1, col2 FROM table WHERE condition;");
        }
    }

    private void validateInsert(String query) throws SQLValidationException {
        Pattern pattern = Pattern.compile("(?i)^INSERT\\s+INTO\\s+\\w+\\s*\\([^)]+\\)\\s*VALUES\\s*\\([^)]+\\);?$");
        if (!pattern.matcher(query).matches()) {
            throw new SQLValidationException("Invalid INSERT syntax. Example: INSERT INTO table (col1, col2) VALUES (1, 'value');");
        }
    }

    private void validateCreate(String query) throws SQLValidationException {
        Pattern pattern = Pattern.compile("(?i)^CREATE\\s+(TABLE|DATABASE)\\s+\\w+\\s*(\\(.+?\\))?;?$");
        if (!pattern.matcher(query).matches()) {
            throw new SQLValidationException("Invalid CREATE syntax. Example: CREATE TABLE table (col1 INT, col2 TEXT);");
        }
    }

    // Custom exception class
    private static class SQLValidationException extends Exception {
        public SQLValidationException(String message) {
            super(message);
        }
    }

    // Implement Levenshtein Distance algorithm
    private int getLevenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    // Suggest correct SQL keyword
    private String correctSQLKeyword(String input) {
        String closestMatch = "";
        int minDistance = Integer.MAX_VALUE;
        
        for (String keyword : SQL_KEYWORDS) {
            int distance = getLevenshteinDistance(input, keyword);
            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = keyword;
            }
        }
        return closestMatch;
    }
}
