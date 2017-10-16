package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.util.Pair;

import java.util.Calendar;
import java.util.Date;

/**
 * Represents a logger in JSON format.
 */
public class JSONLogger {
    /**
     * Logs an error message.
     * @param message the error message
     * @param args additional parameters that should be logged.
     */
    @SafeVarargs
    public static void err(String message, Pair<String, Object>... args) {
        System.out.printf("[%02d:%02d:%02d:%02d] ERROR: %s\n", Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND),
            Calendar.getInstance().get(Calendar.MILLISECOND) , message);
        JSONLogger.log(args);
    }

    /**
     * Logs an info message.
     * @param message the error message
     * @param args additional parameters that should be logged.
     */
    @SafeVarargs
    public static void info(String message, Pair<String, Object>... args) {
        System.out.printf("[%02d:%02d:%02d:%02d] INFO: %s\n", Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND),
            Calendar.getInstance().get(Calendar.MILLISECOND) , message);
        JSONLogger.log(args);
    }

    /**
     * Logs a warning message.
     * @param message the error message
     * @param args additional parameters that should be logged.
     */
    @SafeVarargs
    public static void warn(String message, Pair<String, Object>... args) {
        System.out.printf("[%02d:%02d:%02d:%02d] WARNING: %s\n", Calendar.getInstance().get(Calendar.HOUR_OF_DAY    ),
            Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND),
            Calendar.getInstance().get(Calendar.MILLISECOND) , message);
        JSONLogger.log(args);
    }

    /**
     * Logs a debug message.
     * @param message the error message
     * @param args additional parameters that should be logged.
     */
    @SafeVarargs
    public static void debug(String message, Pair<String, Object>... args) {
        System.out.printf("[%02d:%02d:%02d:%02d] DEBUG: %s\n", Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND),
            Calendar.getInstance().get(Calendar.MILLISECOND) , message);
        JSONLogger.log(args);
    }

    /**
     * Logs an set of parameters in JSON format.
     * @param args parameters that should be logged.
     */
    @SafeVarargs
    private static void log(Pair<String, Object>... args) {
        if (args.length == 0) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();

        for (Pair p : args) {
            ((ObjectNode) rootNode).put(p.getKey().toString(), p.getValue().toString());
        }

        String jsonString = "";
        try {
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            System.out.println("Error processing log.");
        }
        System.out.println(jsonString);
    }
}

