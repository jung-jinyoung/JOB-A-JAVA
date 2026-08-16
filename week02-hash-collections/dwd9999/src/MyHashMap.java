import java.util.Objects;

public class MyHashMap<K, V> {
    private Node<K, V>[] table;
    private int size;
    private int capacity;
    private final float loadFactor;

    private static final int CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public MyHashMap() {
        this.capacity = CAPACITY;
        this.loadFactor = LOAD_FACTOR;
        this.table = new Node[capacity];
        this.size = 0;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7FFFFFFF) % capacity;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Node<K, V>[] newTable = new Node[newCapacity];

        Node<K, V> currentNode, nextNode;
        for (int i = 0; i < capacity; i++) {
            currentNode = table[i];
            while (currentNode != null) {
                nextNode = currentNode.next;

                int newIndex = (currentNode.key == null ? 0 : (currentNode.key.hashCode() & 0x7FFFFFFF) % newCapacity);

                currentNode.next = newTable[newIndex];
                newTable[newIndex] = currentNode;

                currentNode = nextNode;
            }
        }

        table = newTable;
        capacity = newCapacity;
    }

    public void put(K key, V value) {
        int index = hash(key);
        Node<K, V> current = table[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        Node<K, V> newNode = new Node<>(key, value, table[index]);
        table[index] = newNode;
        size++;

        if (size >= capacity * loadFactor) {
            resize();
        }
    }

    public V get(K key) {
        int index = hash(key);
        Node<K, V> currentNode = table[index];

        while (currentNode != null) {
            if (Objects.equals(currentNode.key, key)) {
                return currentNode.value;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        Node<K, V> currentNode = table[index];
        Node<K, V> prev = null;

        while (currentNode != null) {
            if (Objects.equals(currentNode.key, key)) {
                if (prev == null) {
                    table[index] = currentNode.next;
                } else {
                    prev.next = currentNode.next;
                }
                size--;
                return currentNode.value;
            }
            prev = currentNode;
            currentNode = currentNode.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }
}