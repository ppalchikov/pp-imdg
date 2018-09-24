package pp.imdg

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey._
import java.nio.channels.{SelectionKey, Selector, ServerSocketChannel, SocketChannel}

import scala.collection.JavaConverters._

object NioServer extends App {
  val serverChannel = ServerSocketChannel.open()
  val port = 45001
  serverChannel.socket().bind(new InetSocketAddress(port))
  serverChannel.configureBlocking(false)

  val selector = Selector.open()
  serverChannel.register(selector, OP_ACCEPT)

  println(s"Server started at port $port. Waiting for connections...")

  while (true) {
    selector.select()
    selector.selectedKeys().asScala.filter(_.isValid).foreach {
      case x: SelectionKey if x.isAcceptable =>
        val clientChannel = serverChannel.accept()
        clientChannel.configureBlocking(false)
        println(s"Connected ${clientChannel.getRemoteAddress}")
        clientChannel.register(selector, OP_READ, ByteBuffer.allocate(1024))
      case x: SelectionKey if x.isReadable =>
        val client = x.channel().asInstanceOf[SocketChannel]
        val buffer = x.attachment().asInstanceOf[ByteBuffer]
        val r = client.read(buffer)
        println(s"Reading from ${client.getRemoteAddress}, bytes = $r")
        if (r == -1) {
          println(s"Connection closed ${client.getRemoteAddress}")
          client.close()
        }

        if (r > 0 && buffer.get(buffer.position() - 1) == '\n') {
          client.register(selector, OP_WRITE, buffer)
        }
      case x: SelectionKey if x.isWritable =>
        val client = x.channel().asInstanceOf[SocketChannel]
        val buffer = x.attachment().asInstanceOf[ByteBuffer]
        // reading from buffer
        buffer.flip()
        val message = new String(buffer.array(), buffer.position(), buffer.limit())
        val response = message.replace('\n', '\0')
          .replace('\r', '0') + s", server time  ${System.currentTimeMillis()}${System.lineSeparator()}"
        buffer.clear()
        buffer.put(response.getBytes)
        buffer.flip()
        val w = client.write(buffer)
        println(s"Writing to ${client.getRemoteAddress}, bytes = $w")
        if (!buffer.hasRemaining) {
          buffer.compact()
          client.register(selector, OP_READ, buffer)
        }
    }
    selector.selectedKeys().clear()
  }
}
