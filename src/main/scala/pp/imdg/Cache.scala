package pp.imdg

/**
  * Created by pavel on 08.07.17.
  */
trait Cache {
  def get[Key](key: Key)

  def put[Key, Value](key: Key, value: Value)

  def evict[Key](key: Key)
}
