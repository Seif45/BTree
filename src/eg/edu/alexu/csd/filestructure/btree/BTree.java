package eg.edu.alexu.csd.filestructure.btree;

import java.util.List;

public class BTree<K extends Comparable<K>, V> implements IBTree<K, V> {

    private int minimumDegree;
    private IBTreeNode<K, V> root;
    private final int MIN_KEYS;
    private final int MAX_KEYS;

    public BTree (int minimumDegree){
        this.minimumDegree = minimumDegree;
        MIN_KEYS =  minimumDegree - 1;
        MAX_KEYS = minimumDegree * 2 - 1;
    }

    @Override
    public int getMinimumDegree() {
        return this.minimumDegree;
    }

    @Override
    public IBTreeNode<K, V> getRoot() {
        return root;
    }

    @Override
    public void insert(K key, V value) {

    }

    @Override
    public V search(K key) {
        IBTreeNode<K, V> found = findNode(this.root, key); //find the node that contains the key
        if (found == null){
            return null;
        }
        return found.getValues().get(found.getKeys().indexOf(key)); //return suitable value according to the key
    }

    @Override
    public boolean delete(K key) {
        return false;
    }

    private IBTreeNode<K, V> findNode(IBTreeNode<K, V> node, K key){
        if (node == null || key == null){ //condition if the key doesn't exist
            return null;
        }
        List<K> keys = node.getKeys();
        for (int i = 0 ; i < keys.size() ; i++){
            K k = keys.get(i);
            if (key.compareTo(k) == 0){ //key found
                return node;
            }
            else if (key.compareTo(k) < 0){ //search in the left child
                node = findNode(node.getChildren().get(i),key);
            }
            else if (i == keys.size()-1){ //search in the right child
                node = findNode(node.getChildren().get(i+1),key);
            }
        }
        return node;
    }
}
