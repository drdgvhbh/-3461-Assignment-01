package components.AnimalTree;

import javafx.scene.image.Image;
import javafx.util.Pair;
import util.JSONLogger;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AnimalTree {
    private AnimalNode root;
    private String lastQueriedAnimal;

    public AnimalTree() {

    }

    public void addAnimal(String name, String imageURL, String parent) {
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

    public List<String> getAnimalImages(String name) {
        AnimalNode queryNode = breathFirstSearch(name);
        return getAnimalImages(queryNode);
    }

    private List<String> getAnimalImages(AnimalNode queryNode) {
        if (queryNode == null) {
            return null;
        }
        if (queryNode.getImages().isEmpty() && !queryNode.getChildren().isEmpty()) {
            Collections.shuffle(queryNode.getChildren());
            return getAnimalImages(queryNode.getChildren().get(0));
        }
        lastQueriedAnimal = queryNode.getName();
        return queryNode.getImages();
    }

    public String getLastQueriedAnimal() {
        return lastQueriedAnimal;
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
