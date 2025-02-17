//package com.validator;
//
//import java.util.*;
//import java.util.regex.*;
//
//public class SQLParser {
//
//    // SQL grammar patterns
//    private static final Pattern SELECT_PATTERN = Pattern.compile("(?i)^SELECT\\s+.+\\s+FROM\\s+\\w+.*;$");
//    private static final Pattern INSERT_PATTERN = Pattern.compile("(?i)^INSERT\\s+INTO\\s+\\w+\\s*\\(.+\\)\\s*VALUES\\s*\\(.+\\);$");
//    private static final Pattern UPDATE_PATTERN = Pattern.compile("(?i)^UPDATE\\s+\\w+\\s+SET\\s+.+(\\s+WHERE\\s+.+)?;$");
//    private static final Pattern DELETE_PATTERN = Pattern.compile("(?i)^DELETE\\s+FROM\\s+\\w+(\\s+WHERE\\s+.+)?;$");
//    private static final Pattern CREATE_TABLE_PATTERN = Pattern.compile("(?i)^CREATE\\s+TABLE\\s+\\w+\\s*\\(.+\\);$");
//
//    public static String validateSQL(String sqlQuery) {
//        sqlQuery = sqlQuery.trim();
//
//        // Tokenize SQL Query
//        List<String> tokens = tokenize(sqlQuery);
//        if (tokens.isEmpty()) return "❌ Empty Query!";
//
//        // Validate Query using patterns
//        if (SELECT_PATTERN.matcher(sqlQuery).matches()) {
//            return validateSelectQuery(sqlQuery, tokens);
//        } else if (INSERT_PATTERN.matcher(sqlQuery).matches()) {
//            return "✅ Valid INSERT query!";
//        } else if (UPDATE_PATTERN.matcher(sqlQuery).matches()) {
//            return "✅ Valid UPDATE query!";
//        } else if (DELETE_PATTERN.matcher(sqlQuery).matches()) {
//            return "✅ Valid DELETE query!";
//        } else if (CREATE_TABLE_PATTERN.matcher(sqlQuery).matches()) {
//            return "✅ Valid CREATE TABLE query!";
//        } else {
//            return detectError(sqlQuery, tokens);
//        }
//    }
//
//    // Tokenization function (splits by space & punctuation)
//    private static List<String> tokenize(String sqlQuery) {
//        return Arrays.asList(sqlQuery.split("\\s+"));
//    }
//
//    // Validates SELECT queries with GROUP BY, ORDER BY, and JOINs
//    private static String validateSelectQuery(String sqlQuery, List<String> tokens) {
//        if (!sqlQuery.endsWith(";")) {
//            return "❌ Error: Missing semicolon at end of query. 💡 Suggestion: Add ';' at the end.";
//        }
//        if (!tokens.contains("FROM")) {
//            return "❌ Error: Missing 'FROM' clause in SELECT statement. 💡 Suggestion: Add 'FROM table_name'.";
//        }
//        if (tokens.get(0).equalsIgnoreCase("SELECT") && tokens.indexOf("FROM") == 1) {
//            return "❌ Error: Missing column names in SELECT statement. 💡 Suggestion: Specify at least one column name before 'FROM'.";
//        }
//        if (tokens.contains("GROUP") && tokens.contains("BY")) {
//            int groupByIndex = tokens.indexOf("BY");
//            if (groupByIndex + 1 >= tokens.size()) {
//                return "❌ Error: Missing column after 'GROUP BY'. 💡 Suggestion: Add a column (e.g., 'GROUP BY category').";
//            }
//        }
//        if (tokens.contains("ORDER") && tokens.contains("BY")) {
//            int orderByIndex = tokens.indexOf("BY");
//            if (orderByIndex + 1 >= tokens.size()) {
//                return "❌ Error: Missing column after 'ORDER BY'. 💡 Suggestion: Add a column (e.g., 'ORDER BY id ASC').";
//            }
//        }
//        if (tokens.contains("JOIN")) {
//            if (!tokens.contains("ON")) {
//                return "❌ Error: Missing 'ON' clause in JOIN statement. 💡 Suggestion: Use 'ON table1.id = table2.id'.";
//            }
//        }
//        return "✅ Valid SELECT query!";
//    }
//
//    // Detects syntax errors and gives suggestions
//    private static String detectError(String sqlQuery, List<String> tokens) {
//        if (tokens.contains("WHERE") && tokens.indexOf("WHERE") == tokens.size() - 1) {
//            return "❌ Error: WHERE condition is missing. 💡 Suggestion: Add a valid condition (e.g., 'WHERE id = 5').";
//        }
//        if (tokens.get(0).equalsIgnoreCase("UPDATE") && !tokens.contains("SET")) {
//            return "❌ Error: Missing 'SET' clause in UPDATE statement. 💡 Suggestion: Use 'SET column=value'.";
//        }
//        return "❌ Syntax Error: Please check your SQL statement!";
//    }
//}
