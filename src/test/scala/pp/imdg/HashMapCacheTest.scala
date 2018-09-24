package pp.imdg

import org.scalatest.FunSuite

class HashMapCacheTest extends FunSuite {

  val cache = new HashMapCache[String, String]()

  test("test put") {
    cache.put("1", "1")
    assert(cache.contains("1"))
    assert(cache.get("1").contains("1"))
    cache.put("1", "x")
    assert(cache.get("1").contains("x"))
  }

  test("test contains") {
    assert(cache.contains("1"))
    cache.put("2", "2")
    assert(cache.contains("2"))
  }

  test("test get") {
    assert(cache.get("1").contains("x"))
    assert(cache.get("3").isEmpty)
  }

  test("test evict") {
    cache.evict("2")
    assert(!cache.contains("2"))
  }

  test("test clear") {
    cache.put("3", "3")
    assert(cache.contains("1"))
    assert(cache.contains("3"))
    cache.clear()
    assert(!cache.contains("1"))
    assert(!cache.contains("3"))
  }

}
