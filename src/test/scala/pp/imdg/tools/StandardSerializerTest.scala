package pp.imdg.tools

import org.scalatest.FunSuite

case class Simple(id: Int, name: String) extends Serializable

class StandardSerializerTest extends FunSuite {
  val simple = Simple(1, "name")
  val ser = new StandardSerializer(classOf[Simple])

  test("serialize") {
    assert(ser.serialize(simple).nonEmpty)
  }

  test("deserialize") {
    assert(simple == ser.deserialize(ser.serialize(simple)))
  }
}
