package pp.imdg.tools

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

import pp.imdg.tools.CloseableResources._

class StandardSerializer[T <: Serializable](classType: Class[T]) extends Serializer[T] {
  override def serialize(obj: T): Array[Byte] = {
    val bout = new ByteArrayOutputStream()
    new ObjectOutputStream(bout).auto { out =>
      out.writeObject(obj)
      out.flush()
    }
    bout.toByteArray
  }

  override def deserialize(bytes: Array[Byte]): T = {
    new ObjectInputStream(new ByteArrayInputStream(bytes)).auto { is =>
      is.readObject().asInstanceOf[T]
    }
  }
}
