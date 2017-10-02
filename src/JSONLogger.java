import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.util.Pair;

public class JSONLogger {
    @SafeVarargs
    public static void err(String message, Pair<String, Object>... args) throws JsonProcessingException {
        System.out.println("ERROR: " + message);
        JSONLogger.log(args);
    }

    @SafeVarargs
    public static void info(String message, Pair<String, Object>... args) throws JsonProcessingException {
        System.out.println("INFO: " + message);
        JSONLogger.log(args);
    }

    @SafeVarargs
    public static void warn(String message, Pair<String, Object>... args) throws JsonProcessingException {
        System.out.println("WARNING: " + message);
        JSONLogger.log(args);
    }

    @SafeVarargs
    public static void debug(String message, Pair<String, Object>... args) throws JsonProcessingException {
        System.out.println("DEBUG: " + message);
        JSONLogger.log(args);
    }

    @SafeVarargs
    private static void log(Pair<String, Object>... args) throws JsonProcessingException {
        if (args.length == 0) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();

        for (Pair p : args) {
            ((ObjectNode) rootNode).put(p.getKey().toString(), p.getValue().toString());
        }

        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        System.out.println(jsonString);
    }
}

