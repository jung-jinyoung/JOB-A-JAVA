public class MyHashMap {
    static class Node{
        String key;
        int value;
        Node next;

        Node(String key, int value){
            this.key = key;
            this.value = value;
        }
    }

    private Node[] table = new Node[16];
    private int size;

    private int indexFor(String key){
        return Math.abs(key.hashCode() % table.length);
    }

    public void put(String key, int value){
        int idx = indexFor(key);
        Node node = table[idx];

        // 같은 키 -> 값만 교체
        if (node != null && node.key.equals(key)){
            node.value = value;
            return;
        }
        // 없으면 추가
        if (node == null){
            size++;
            table[idx] = new Node(key, value);
        }

    }

    public int get(String key){
        Node node = table[indexFor(key)];
        if (node != null && node.key.equals(key)){
            return node.value;
        }
        return -1;
    }

    public boolean containsKey(String key){
        return get(key) != -1;
    }

    public int remove(String key){
        int idx = indexFor(key);
        Node node = table[idx];

        if (node != null && node.key.equals(key)){
            table[idx] = null;
            size--;
            return node.value;
        }
        return -1;
    }

    public int size(){
        return size;
    }



}
