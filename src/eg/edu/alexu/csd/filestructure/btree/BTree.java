package eg.edu.alexu.csd.filestructure.btree;

public class BTree<K extends Comparable<K>, V> implements IBTree<K, V> {

    private int minimumDegree;
    private IBTreeNode<K, V> root;

    public BTree (int minimumDegree){
        this.minimumDegree = minimumDegree;
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
        return null;
    }

    @Override
    public boolean delete(K key) {
        return false;
    }
}
