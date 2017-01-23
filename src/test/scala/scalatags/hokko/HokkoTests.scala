package scalatags.hokko

import _root_.hokko.core._
import org.scalajs.dom
import utest._

import scalatags.Hokko.all._
import scalatags.hokko.raw.VirtualDom

object HokkoTests extends TestSuite {
  def tests = TestSuite {
    'eventsource {
      val mouseEvents = Event.source[dom.MouseEvent]
      val description = div(onclick := mouseEvents)(div).render

      var counter = 0

      val subscribedDesc = description.subscribe(mouseEvents.toEvent) { _ =>
        counter += 1
      }

      val network = subscribedDesc.compile()
      val vnode   = network.now()
      val element = VirtualDom.create(vnode)

      dom.document.body.appendChild(element)

      def click(el: dom.html.Element) = {
        // JSDom does not support .onclick properties, we mimic it by
        // manually calling onclick
        el.onclick(null)
      }

      assert(counter == 0)
      click(element)
      assert(counter == 1)
      click(element)
      assert(counter == 2)
    }

    'behaviorsink {
      val sink = CBehavior.sink("default value")

      val desc = textarea.read(_.tagName, sink).render

      val network = desc
        .listen(sink) { (n, s) =>
          (n, s)
        }
        .compile()

      val (vnode, _) = network.now

      val element = VirtualDom.create(vnode)

      assert(network.now()._2 == "TEXTAREA")
    }
  }
}
