import java.util.HashMap;

public class LRUCacheImpl<K, V> extends AbstractLRUCache<K, V>{
    private final HashMap<K, Node<K, V>> hashMap;
    private int size;
    private Node<K, V> head;
    private Node<K, V> tail;

    public LRUCacheImpl(int capacity) {
        super(capacity);
        this.size = 0;
        this.head = null;
        this.tail = null;
        this.hashMap = new HashMap<>();
        this.capacity = capacity;
    }

    @Override
    public V doGet(K key) {
        if (!hashMap.containsKey(key)) {
            return null;
        }

        Node<K, V> node = hashMap.get(key);

        if (node == head) {
            return node.getValue();
        }

        if (node != tail) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        } else {
            tail = tail.prev;
            tail.next = null;
        }

        node.prev = null;
        node.next = head;
        head.prev = node;
        head = node;

        return node.getValue();
    }

    @Override
    protected int doSize() {
        return size;
    }

    @Override
    public void doPut(K key, V value) {
        if (hashMap.containsKey(key)) {
            hashMap.get(key).setValue(value);
            get(key);

            return;
        }

        Node<K, V> node = new Node<>(key, value);

        if (hashMap.size() < capacity) {
            hashMap.put(key, node);

            if (head == null) {
                head = node;
                tail = node;
            } else {
                node.next = head;
                head.prev = node;
                head = node;
            }

            size++;

            return;
        }

        hashMap.remove(tail.getKey());
        hashMap.put(key, node);

        if (head == tail) {
            head = node;
            tail = node;
        } else {
            Node<K, V> lastNode = tail;
            tail = tail.prev;
            tail.next = null;
            lastNode.prev = null;
            node.next = head;
            head.prev = node;
            head = node;
        }
    }
}
