import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LRUCacheTest {
    @Test
    void testGetAndPut() {
        LRUCache<Integer, Integer> lruCache = new LRUCacheImpl<>(10);

        assertNull(lruCache.get(1));

        IntStream.range(0, 10).forEach(v -> lruCache.put(v, v * v));
        IntStream.range(0, 10).forEach(v -> assertEquals(v * v, lruCache.get(v)));
    }

    @Test
    void testRewrite() {
        LRUCache<Integer, Integer> lruCache = new LRUCacheImpl<>(10);

        IntStream.range(0, 10).forEach(v -> lruCache.put(v, v * v));
        IntStream.range(5, 10).forEach(v -> lruCache.put(v, v));

        IntStream.range(0, 5).forEach(v -> assertEquals(v * v, lruCache.get(v)));
        IntStream.range(5, 10).forEach(v -> assertEquals(v, lruCache.get(v)));
    }

    @Test
    void testPutMoreThanCapacity() {
        LRUCache<Integer, Integer> lruCache = new LRUCacheImpl<>(10);

        IntStream.range(0, 50).forEach(v -> lruCache.put(v, v + 1));
        IntStream.range(0, 40).forEach(v -> assertNull(lruCache.get(v)));
        IntStream.range(40, 50).forEach(v -> assertEquals(v + 1, v + 1));
    }

    @Test
    void testIncreaseCapacity() {
        LRUCache<Integer, Integer> lruCache = new LRUCacheImpl<>(10);

        IntStream.range(0, 20).forEach(v -> lruCache.put(v, v));
        IntStream.range(10, 20).forEach(v -> assertEquals(v, lruCache.get(v)));

        lruCache.increaseCapacityTo(20);
        IntStream.range(0, 10).forEach(v -> lruCache.put(v, v));

        IntStream.range(0, 10).forEach(v -> assertEquals(v, lruCache.get(v)));
    }

    @Test
    void testSize() {
        LRUCache<Integer, Integer> lruCache = new LRUCacheImpl<>(10);

        assertEquals(0, lruCache.size());

        IntStream.range(0, 5).forEach(v -> lruCache.put(v, v));
        assertEquals(5, lruCache.size());

        IntStream.range(0, 20).forEach(v -> lruCache.put(v, v));
        assertEquals(10, lruCache.size());
    }
}
