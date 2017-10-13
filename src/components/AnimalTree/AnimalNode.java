package components.AnimalTree;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AnimalNode {
    private String name;
    private AnimalNode parent;
    private List<AnimalNode> children;
    private List<URL> imageURLs;

    AnimalNode(String name, List<URL> imageURL) {
        this(name, imageURL, null);
    }

    AnimalNode(String name, List<URL> imageURLs, AnimalNode parent) {
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.imageURLs = new ArrayList<>(1);
        if (imageURLs != null) {
            this.imageURLs = imageURLs;
        }
    }

    String getName() {
        return this.name;
    }

    AnimalNode getParent() {
        return this.parent;
    }

    void addChild(AnimalNode node) {
        children.add(node);
    }

    List<AnimalNode> getChildren() {
        return this.children;
    }

    List<URL> getImages() {
        return this.imageURLs;
    }

}
