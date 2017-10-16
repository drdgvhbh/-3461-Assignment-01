package components.AnimalTree;

import javafx.beans.property.StringProperty;
import javafx.util.Pair;
import jdk.jshell.spi.ExecutionControl;
import util.JSONLogger;

import java.net.URL;
import java.util.*;

/**
 * Represents a tree hierarchy of the animal kingdom.
 */
public class AnimalTree {
    /**
     * The root of the tree.
     */
    private AnimalNode root;

    /**
     * The name of the animal last returned by {@link AnimalTree#getAnimalImages(String)}
     */
    private String lastQueriedAnimal;

    /**
     * The name of the query last passed into the {@link AnimalTree#getAnimalImages(String)} method.
     */
    private String lastQuery;

    /**
     * Initializes a new instance of the {@link AnimalTree} class.
     */
    public AnimalTree() {

    }

    /**
     * Adds an animal to the tree as a child of the <code>parent</code>.
     * @param name the name of the animal
     * @param imageURL the collection of animal image <code>URLs</code>
     * @param parent the animal this animal should be a child of
     * @throws IllegalArgumentException the parent is not in the tree
     */
    public void addAnimal(String name, List<URL> imageURL, String parent) throws IllegalArgumentException {
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

    /**
     * Removes an animal from the tree.
     * @param name the name of the animal
     */
    public void removeAnimal(String name) {
        AnimalNode query = breathFirstSearch(name);
        if (query == null) {
            JSONLogger.warn(
                "Trying to remove an animal that is not in the tree",
                new Pair<>("Animal", name), new Pair<>("Class", this.getClass().getName())
            );
        } else if (query == this.root) {
            this.root = null;
        } else {
            // Any node that is not a leaf has no animal images. So if the leaf is the parent's only child, then
            // the parent is useless after removal.
            query.getParent().getChildren().remove(query);
            if (query.getParent().getChildren().size() == 0) {
                removeAnimal(query.getParent().getName());
            }
        }
    }

    /**
     * Gets the collection of animal images for the specified animal name.
     * @param name the name of the animal
     * @return the collection if animal images if the animal is in the tree; otherwise, <code>null</code>
     */
    public List<URL> getAnimalImages(String name) {
        AnimalNode queryNode = breathFirstSearch(name);
        lastQuery = name;
        return getAnimalImages(queryNode);
    }

    /**
     * Gets the collection of animal images for the specified animal node.
     * @param queryNode the animal node
     * @return the collection if animal images if the animal is in the tree; otherwise, <code>null</code>
     */
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

    /**
     * Gets the name of the query last passed into the {@link AnimalTree#getAnimalImages(String)} method.
     * @return the name of the query if a query has been made; otherwise <code>null</code>
     */
    public String getLastQuery() {
        return lastQuery;
    }


    /**
     * Gets the name of the animal last returned by {@link AnimalTree#getAnimalImages(String)}
     * @return the name of the animal last returned, if an animal has been returned; otherwise <code>null</code>
     */
    public String getLastQueriedAnimal() {
        return lastQueriedAnimal;
    }

    /**
     * Gets the generic name of the specified animal after being compared to group of animals.
     *
     * <p>The method looks for a child of the lowest common ancestor between all the nodes. The child is on the same
     * branch as the specified animal. The group may also contain the specified animal but it will be removed by the
     * method.</p>
     *
     * <p>Example: Given a horse as the specified name and a group of three random reptiles, the algorithm turn the horse
     * name into mammal because the lowest common ancestor of the group is animals.</p>
     *
     * @param animal the animal to get a generic name from
     * @param testAnimals the group of animals
     * @return the generic name of the animal
     */
    public String getGenericName(String animal, List<StringProperty> testAnimals) {
        List<StringProperty> temp = new ArrayList<>(testAnimals);

        // remove the specified animal if it is in the group.
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

        // For any nodes with a greater depth than the specified node, iterate until they are the same depth
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

        // Find lowest common ancestor
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

    /**
     * Performs a depth first search for an animal in the Tree.
     * @param name the name of the animal
     * @return the node of the animal if found; otherwise <code>null</code>.
     */
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

    /**
     * Gets the depth of the node in the tree.
     *
     * <p>Precondition: The node should be in the tree.</p>
     *
     * @param node the node
     * @return the depth of the node
     */
    private int depth(AnimalNode node) {
        if (node.getParent() == null) {
            return 0;
        }
        return 1 + depth(node.getParent());
    }

    /**
     * Performs a breadth first search for an animal in the tree.
     * @param name the name of the animal
     * @return the node of the animal if found; otherwise <code>null</code>.
     */
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
