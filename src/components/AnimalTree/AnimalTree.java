package components.AnimalTree;

import javafx.beans.property.StringProperty;
import javafx.util.Pair;
import jdk.jshell.spi.ExecutionControl;
import util.JSONLogger;

import java.net.URL;
import java.util.*;

public class AnimalTree {
    private AnimalNode root;
    private String lastQueriedAnimal;
    private String lastQuery;

    public AnimalTree() {

    }

    public void addAnimal(String name, List<URL> imageURL, String parent) {
        if (root == null) {
            this.root = new AnimalNode(name, imageURL);
            return;
        }

        AnimalNode nodeParent = breathFirstSearch(parent);
        if (nodeParent == null) {
            JSONLogger.err("Animal category does not exist.", new Pair<>("Category", parent));
            throw new IllegalArgumentException();
        }
        nodeParent.addChild(new AnimalNode(name, imageURL, nodeParent));
    }

    public void removeAnimal(String name) {
        AnimalNode query = breathFirstSearch(name);
        if (query == null) {
            JSONLogger.warn(
                "Trying to remove an animal that does not exist.",
                new Pair<>("Animal", name), new Pair<>("Class",
                this.getClass().getName())
            );
        } else if (query == this.root) {
            this.root = null;
        } else {
            query.getParent().getChildren().remove(query);
            if (query.getParent().getChildren().size() == 0) {
                removeAnimal(query.getParent().getName());
            }
        }
    }

    public List<URL> getAnimalImages(String name) {
        AnimalNode queryNode = breathFirstSearch(name);
        lastQuery = name;
        return getAnimalImages(queryNode);
    }

    private List<URL> getAnimalImages(AnimalNode queryNode) {
        if (queryNode == null) {
            return null;
        }
        if (queryNode.getImages().isEmpty() && !queryNode.getChildren().isEmpty()) {
            Collections.shuffle(queryNode.getChildren());
            return getAnimalImages(queryNode.getChildren().get(0));
        }
        lastQueriedAnimal = queryNode.getName();
        Collections.shuffle(queryNode.getImages());
        return queryNode.getImages();
    }

    public String getLastQuery() {
        return lastQuery;
    }

    public String getLastQueriedAnimal() {
        return lastQueriedAnimal;
    }

    public String getGenericName(String animal, List<StringProperty> testAnimals) {
        List<StringProperty> temp = new ArrayList<>(testAnimals);
        for (StringProperty s : temp) {
            if (s.get().equals(animal)) {
                testAnimals.remove(s);
            }
        }
        AnimalNode qAnimal = depthFirstSearch(animal);
        if (qAnimal == null) {
            return null;
        }
        int qAnimalDepth = depth(qAnimal);
        List<AnimalNode> qTestAnimals = new ArrayList<>();
        Map<String, Integer> qTestAnimalsDepths = new TreeMap<>();
        for (StringProperty s: testAnimals) {
            AnimalNode node = depthFirstSearch(s.get());
            if (node != null) {
                int depth = depth(node);
                if (depth > qAnimalDepth) {
                    for (int i = 0; i < depth - qAnimalDepth; i++) {
                        node = node.getParent();
                    }
                    qTestAnimalsDepths.put(node.getName(), qAnimalDepth);
                } else {
                    qTestAnimalsDepths.put(node.getName(), depth);
                }
                qTestAnimals.add(node);
            }
        }
        while (qAnimal.getParent() != null) {
            List<AnimalNode> qTestAnimalsTemp = new ArrayList<>();
            for (AnimalNode n : qTestAnimals) {
                if (qTestAnimalsDepths.get(n.getName()) == qAnimalDepth) {
                    int newDepth = qTestAnimalsDepths.get(n.getName()) - 1;
                    n = n.getParent();
                    qTestAnimalsDepths.put(n.getName(), newDepth);
                    if (n == qAnimal.getParent()) {
                        return qAnimal.getName();
                    }
                }
                qTestAnimalsTemp.add(n);
            }
            qAnimalDepth--;
            qTestAnimals = qTestAnimalsTemp;
            qAnimal = qAnimal.getParent();
        }
        return null;
    }

    private AnimalNode depthFirstSearch(String name) {
        if (this.root == null) {
            return null;
        }
        Stack<AnimalNode> stack = new Stack<>() {{
           add(root);
        }};

        while (!stack.isEmpty()) {
            if (stack.peek().getName().equals(name)) {
                return stack.pop();
            }
            AnimalNode node = stack.pop();
            for (AnimalNode n : node.getChildren()) {
                if (n != null) {
                    stack.push(n);
                }
            }

        }
        return null;
    }

    private int depth(AnimalNode node) {
        if (node.getParent() == null) {
            return 0;
        }
        return 1 + depth(node.getParent());
    }

    private AnimalNode breathFirstSearch(String name) {
        if (this.root == null) {
            return null;
        }
        Queue<AnimalNode> queue = new LinkedList<>() {{
            add(root);
        }};

        while (!queue.isEmpty()) {
            if (queue.peek().getName().equals(name)) {
                return queue.remove();
            }
            AnimalNode node = queue.remove();
            queue.addAll(node.getChildren());
        }
        return null;
    }
}
