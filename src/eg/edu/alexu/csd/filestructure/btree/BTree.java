package eg.edu.alexu.csd.filestructure.btree;

import javax.management.RuntimeErrorException;
import java.util.ArrayList;
import java.util.List;

public class BTree<K extends Comparable<K>, V> implements IBTree<K, V> {

    private int minimumDegree;
    private IBTreeNode<K, V> root;
    private final int MIN_KEYS;
    private final int MAX_KEYS;

    public BTree(int minimumDegree) {
        if (minimumDegree == 0 || minimumDegree == 1){
            throw new RuntimeErrorException(new Error());
        }
        this.minimumDegree = minimumDegree;
        MIN_KEYS = minimumDegree - 1;
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
        if (key == null || value == null){
            throw new RuntimeErrorException(new Error());
        }
        if (search(key) != null){
            return;
        }
        if (this.root == null){
            IBTreeNode<K, V> newRoot = new BTreeNode<>();
            List<K> keys = new ArrayList<>();
            List<V> values = new ArrayList<>();
            keys.add(key);
            values.add(value);
            List<IBTreeNode<K, V>> children = new ArrayList<>();
            newRoot.setKeys(keys);
            newRoot.setValues(values);
            newRoot.setNumOfKeys(keys.size());
            newRoot.setChildren(children);
            newRoot.setLeaf(true);
            this.root = newRoot;
            return;
        }
        IBTreeNode<K, V> insertInto = findNodeInsert(this.root, key);
        List<K> keys = insertInto.getKeys();
        int i;
        for (i = 0 ; i < insertInto.getNumOfKeys(); i++){
            if (key.compareTo(keys.get(i)) < 0){
                break;
            }
        }
        keys.add(i, key);
        insertInto.getValues().add(i, value);
        insertInto.setNumOfKeys(keys.size());
    }

    @Override
    public V search(K key) {
        if (key == null) {
            throw new RuntimeErrorException(new Error());
        }
        IBTreeNode<K, V> found = findNodeSearch(this.root, key); //find the node that contains the key
        if (found == null) {
            return null;
        }
        if (found.getKeys().contains(key)){
            return found.getValues().get(found.getKeys().indexOf(key)); //return suitable value according to the key
        }
        return null;
    }

    @Override
    public boolean delete(K key) {
        return false;
    }

    private IBTreeNode<K, V> findNodeSearch(IBTreeNode<K, V> node, K key) {
        while (node != null && !node.isLeaf()) {
            List<K> keys = node.getKeys();
            for (int i = 0; i < keys.size(); i++) {
                K k = keys.get(i);
                if (key.compareTo(k) == 0) { //key found
                    return node;
                } else if (key.compareTo(k) < 0) { //search in the left child
                    List<IBTreeNode<K, V>> children = node.getChildren();
                    node = children.get(i);
                    break;
                } else if (i == keys.size() - 1) { //search in the right child
                    List<IBTreeNode<K, V>> children = node.getChildren();
                    node = children.get(i + 1);
                    break;
                }
            }
        }
        return node;
    }

    private IBTreeNode<K, V> findNodeInsert(IBTreeNode<K, V> node, K key) {
        if (node.getNumOfKeys() == MAX_KEYS){
            splitRoot(node);
            node = this.root;
        }
        while (!node.isLeaf()) {
            List<K> keys = node.getKeys();
            for (int i = 0; i < keys.size(); i++) {
                K k = keys.get(i);
                if (key.compareTo(k) < 0) {
                    List<IBTreeNode<K, V>> children = node.getChildren();
                    IBTreeNode<K, V> child = children.get(i);
                    if (child.getNumOfKeys() == MAX_KEYS){ //split if left child is full
                        child = split(node, child, key);
                    }
                    node = child; //traverse through left child
                    break;
                } else if (i == keys.size() - 1) {
                    List<IBTreeNode<K, V>> children = node.getChildren();
                    IBTreeNode<K, V> child = children.get(i+1);
                    if (child.getNumOfKeys() == MAX_KEYS){ //split if right child is full
                       child = split(node, child, key);
                    }
                    node = child; //traverse through right child
                    break;
                }
            }
        }
        return node;
    }

    private void splitRoot (IBTreeNode<K, V> node){
        List<K> keys = node.getKeys();
        List<V> values = node.getValues();
        IBTreeNode<K, V> newRoot = new BTreeNode<>();
        IBTreeNode<K, V> leftSplit = new BTreeNode<>();
        IBTreeNode<K, V> rightSplit = new BTreeNode<>();
        List<K> newKeys = new ArrayList<>();
        List<V> newValues = new ArrayList<>();
        List<IBTreeNode<K, V>> children = node.getChildren();
        List<IBTreeNode<K, V>> newChildren = new ArrayList<>();
        if(node.isLeaf()){
            leftSplit.setLeaf(true);
            rightSplit.setLeaf(true);
        }
        int i;
        for ( i = 0; i < node.getNumOfKeys() / 2; i++){ //left side of the split
            newKeys.add(keys.get(i));
            newValues.add(values.get(i));
            if(!node.isLeaf()){
                newChildren.add(children.get(i));
            }
        }
        if(!node.isLeaf()){
            newChildren.add(children.get(i));
        }
        leftSplit.setKeys(newKeys);
        leftSplit.setValues(newValues);
        leftSplit.setChildren(newChildren);
        leftSplit.setNumOfKeys(newKeys.size());
        newKeys = new ArrayList<>();
        newValues = new ArrayList<>();
        newChildren = new ArrayList<>();
        newKeys.add(keys.get(i));  //the middle item
        newValues.add(values.get(i));
        newChildren.add(leftSplit);
        newChildren.add(rightSplit);
        newRoot.setKeys(newKeys);
        newRoot.setValues(newValues);
        newRoot.setChildren(newChildren);
        newRoot.setNumOfKeys(newKeys.size());
        newKeys = new ArrayList<>();
        newValues = new ArrayList<>();
        newChildren = new ArrayList<>();
        i++;
        for (; i < node.getNumOfKeys(); i++){ //the right side
            newKeys.add(keys.get(i));
            newValues.add(values.get(i));
            if(!node.isLeaf()){
                newChildren.add(children.get(i));
            }
        }
        if(!node.isLeaf()){
            newChildren.add(children.get(i));
        }
        rightSplit.setKeys(newKeys);
        rightSplit.setValues(newValues);
        rightSplit.setChildren(newChildren);
        rightSplit.setNumOfKeys(newKeys.size());
        this.root = newRoot;
    }

    private IBTreeNode<K, V> split (IBTreeNode<K, V> node, IBTreeNode<K, V> child, K key){
        IBTreeNode<K, V> leftSplit = new BTreeNode<>();
        IBTreeNode<K, V> rightSplit = new BTreeNode<>();
        List<K> keys = child.getKeys();
        List<V> values = child.getValues();
        List<IBTreeNode<K, V>> children = child.getChildren();
        List<K> newKeys = new ArrayList<>();
        List<V> newValues = new ArrayList<>();
        List<IBTreeNode<K, V>> newChildren = new ArrayList<>();
        if (child.isLeaf()){
            leftSplit.setLeaf(true);
            rightSplit.setLeaf(true);
        }
        int i;
        for ( i = 0; i < child.getNumOfKeys() / 2; i++){ //left side of the split
            newKeys.add(keys.get(i));
            newValues.add(values.get(i));
            if(!child.isLeaf()){
                newChildren.add(children.get(i));
            }
        }
        if(!child.isLeaf()){
            newChildren.add(children.get(i));
        }
        leftSplit.setKeys(newKeys);
        leftSplit.setValues(newValues);
        leftSplit.setChildren(newChildren);
        leftSplit.setNumOfKeys(newKeys.size());

        newKeys = node.getKeys();
        newValues = node.getValues();
        newChildren = node.getChildren();
        int j;
        K midKey = keys.get(i);
        for ( j = 0 ; j < newKeys.size(); j++){
            if (keys.get(i).compareTo(newKeys.get(j)) < 0){
                break;
            }
        }
        newKeys.add(j,midKey);
        newValues.add(j,values.get(i));
        newChildren.remove(j);
        newChildren.add(j,leftSplit);
        newChildren.add(j+1,rightSplit);
        node.setKeys(newKeys);
        node.setValues(newValues);
        node.setChildren(newChildren);
        node.setNumOfKeys(newKeys.size());

        newKeys = new ArrayList<>();
        newValues = new ArrayList<>();
        newChildren = new ArrayList<>();
        i++;
        for (; i < child.getNumOfKeys() ; i++){ //left side of the split
            newKeys.add(keys.get(i));
            newValues.add(values.get(i));
            if(!child.isLeaf()){
                newChildren.add(children.get(i));
            }
        }
        if(!child.isLeaf()){
            newChildren.add(children.get(i));
        }
        rightSplit.setKeys(newKeys);
        rightSplit.setValues(newValues);
        rightSplit.setChildren(newChildren);
        rightSplit.setNumOfKeys(newKeys.size());

        if (key.compareTo(midKey) > 0){
            return rightSplit;
        }
        return leftSplit;
    }
}
