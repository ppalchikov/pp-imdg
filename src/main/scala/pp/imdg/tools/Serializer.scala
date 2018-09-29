package pp.imdg.tools

trait Serializer[T <: Serializable] {
  def serialize(obj: T): Array[Byte]
  def deserialize(bytes: Array[Byte]): T
}
