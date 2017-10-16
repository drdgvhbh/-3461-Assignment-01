package components.AnimalTree;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node containing information about an animal.
 */
class AnimalNode {
    /**
     * The name of the animal.
     */
    private String name;

    /**
     * The parent of this node.
     */
    private AnimalNode parent;

    /**
     * The children of this node.
     */
    private List<AnimalNode> children;

    /**
     * A collection of images <code>URLs</code> representing the animal.
     */
    private List<URL> imageURLs;

    /**
     * Initializes a new instance of the {@link AnimalNode} class.
     * @param name the name of the animal
     * @param imageURLs the collection of image <code>URLs</code> that represent this animal
     */
    AnimalNode(String name, List<URL> imageURLs) {
        this(name, imageURLs, null);
    }

    /**
     * Initializes a new instance of the {@link AnimalNode} class.
     * @param name the name of the animal
     * @param imageURLs the collection of image <code>URLs</code> that represent this animal
     * @param parent The parent of the node
     */
    AnimalNode(String name, List<URL> imageURLs, AnimalNode parent) {
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.imageURLs = new ArrayList<>(1);
        if (imageURLs != null) {
            this.imageURLs = imageURLs;
        }
    }

    /**
     * Gets the name of this animal.
     * @return the name of this animal.
     */
    String getName() {
        return this.name;
    }

    /**
     * Gets the parent of this node.
     * @return the parent of this node
     */
    AnimalNode getParent() {
        return this.parent;
    }

    /**
     * Adds a child to this node.
     * @param node the child of this node
     */
    void addChild(AnimalNode node) {
        children.add(node);
    }

    /**
     * Gets the children of this node.
     * @return the children of this node.
     */
    List<AnimalNode> getChildren() {
        return this.children;
    }

    /**
     * Gets the collection if image <code>URLs</code> that represent this animal.
     * @return
     */
    List<URL> getImages() {
        return this.imageURLs;
    }

}
