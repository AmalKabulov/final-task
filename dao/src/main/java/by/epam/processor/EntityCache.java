package by.epam.processor;

import by.epam.entity.BaseEntity;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class EntityCache/*<K extends BaseEntity>*/ /*implements Cache1<EntityCache.Key<ID, K>, K>*/ {

    private final long timeToLive;
    private ConcurrentHashMap<EntityCache.Key, CacheObject> values;

    public EntityCache(final long timeToLive, final long idleInterval, final int maxItems) {
        values = new ConcurrentHashMap<>(maxItems);
        this.timeToLive = timeToLive;

        if (timeToLive > 0 && idleInterval > 0) {
            startDaemonThread(idleInterval);
        }
    }

    private void startDaemonThread(final long idleInterval) {
        final Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(idleInterval);
                } catch (InterruptedException e) {
                    //TODO
                }
                //cleanup();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private class Key {
        private Class<? extends BaseEntity> clazz;
        private Serializable id;

        private Key(final Serializable id, final Class<? extends BaseEntity> clazz) {
            Objects.requireNonNull(id);
            Objects.requireNonNull(clazz);
            this.clazz = clazz;
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key key = (Key) o;
            return Objects.equals(clazz, key.clazz) &&
                    Objects.equals(id, key.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(clazz, id);
        }
    }

    @SuppressWarnings("unchecked")
    public <K extends BaseEntity> K get(final Serializable id, final Class<K> clazz) {
        final Key key = new Key(id, clazz);
        CacheObject cacheObject = getCacheObject(key);
        if (cacheObject != null) {
            return (K) cacheObject.value;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void put(final BaseEntity entity) {
        final Key key = new Key(entity.getId(), entity.getClass());
        this.values.put(key, new CacheObject(entity));
    }


    public void remove(final Serializable id, final Class<? extends BaseEntity> clazz) {
        final Key key = new Key(id, clazz);
        if (getCacheObject(key) != null) {
            this.values.remove(key);
        }
    }

    private CacheObject getCacheObject(Key key) {
        final CacheObject cacheObject = this.values.get(key);
        if (cacheObject == null) {
            return null;
        } else if (this.timeToLive > 0 && System.currentTimeMillis() > this.timeToLive + cacheObject.createdTime) {
            this.values.remove(key);
            return null;
        }
        return cacheObject;
    }

    private class CacheObject {
        private final long createdTime = System.currentTimeMillis();
        private final BaseEntity value;

        private CacheObject(BaseEntity value) {
            this.value = value;
        }
    }
}