package eg.edu.alexu.csd.filestructure.btree;

import java.util.List;

public class BTreeNode<K extends Comparable<K>, V> implements IBTreeNode<K, V> {

    private int numOfKeys;
    private List<K> keys;
    private List<V> values;
    private List<IBTreeNode<K, V>> children;

    @Override
    public int getNumOfKeys() {
        return this.numOfKeys;
    }

    @Override
    public void setNumOfKeys(int numOfKeys) {
        this.numOfKeys = numOfKeys;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public void setLeaf(boolean isLeaf) {
        if (isLeaf){
            children.clear();
        }
    }

    @Override
    public List<K> getKeys() {
        return this.keys;
    }

    @Override
    public void setKeys(List<K> keys) {
        this.keys = keys;
    }

    @Override
    public List<V> getValues() {
        return this.values;
    }

    @Override
    public void setValues(List<V> values) {
        this.values = values;
    }

    @Override
    public List<IBTreeNode<K, V>> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List<IBTreeNode<K, V>> children) {
        this.children = children;
    }
}