package pp.imdg.tools

import scala.util.Try

object CloseableResources {

  implicit class AutoCloseableOps[T <: AutoCloseable](closable: T) {

    def auto[B](code: T => B): B = {
      try {
        code(closable)
      } finally {
        Try(closable.close())
      }
    }
  }

}
