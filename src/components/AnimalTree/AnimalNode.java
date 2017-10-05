package components.AnimalTree;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalNode {
    private String name;
    private AnimalNode parent;
    private List<AnimalNode> children;
    private List<String> imageURLs;

    protected AnimalNode(String name, String imageURL) {
        this(name, imageURL, null);
    }

    protected AnimalNode(String name, String imageURL, AnimalNode parent) {
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.imageURLs = new ArrayList<>(1);
        if (imageURL != null) {
            this.imageURLs.add(imageURL);
        }
    }

    public String getName() {
        return this.name;
    }

    public AnimalNode getParent() {
        return this.parent;
    }

    public void addChild(AnimalNode node) {
        children.add(node);
    }

    public List<AnimalNode> getChildren() {
        return this.children;
    }

    public List<String> getImages() {
        return this.imageURLs;
    }

}
