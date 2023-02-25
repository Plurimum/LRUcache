public abstract class AbstractLRUCache<K, V> implements LRUCache<K, V> {
    protected int capacity;

    public AbstractLRUCache(int capacity) {
        assert capacity > 0;

        this.capacity = capacity;
    }

    @Override
    public final V get(K key) {
        assert key != null;

        final int startSize = size();
        final V value = doGet(key);

        assert startSize == size();

        return value;
    }

    @Override
    public final void put(K key, V value) {
        assert key != null && value != null;

        doPut(key, value);

        assert doSize() <= capacity;
        assert doGet(key) == value;
    }

    @Override
    public final int size() {
        final int size = doSize();

        assert size >= 0 && size <= capacity;

        return size;
    }

    @Override
    public final void increaseCapacityTo(int capacity) {
        assert capacity >= this.capacity;

        this.capacity = capacity;
    }

    protected abstract V doGet(K key);

    protected abstract int doSize();

    protected abstract void doPut(K key, V value);
}
