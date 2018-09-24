package pp.imdg

trait Cache[Key, Value] {
  def get(key: Key): Option[Value]

  def put(key: Key, value: Value): Unit

  def contains(key: Key): Boolean

  def evict(key: Key): Unit

  def clear(): Unit
}
