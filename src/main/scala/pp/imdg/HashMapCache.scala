package pp.imdg

import java.util.concurrent.ConcurrentHashMap

class HashMapCache[Key, Value] extends Cache[Key, Value] {

  private val map = new ConcurrentHashMap[Key, Value]()

  override def get(key: Key): Option[Value] = Option(map.get(key))

  override def put(key: Key, value: Value): Unit = map.put(key, value)

  override def evict(key: Key): Unit = map.remove(key)

  override def contains(key: Key): Boolean = map.containsKey(key)

  override def clear(): Unit = map.clear()
}
