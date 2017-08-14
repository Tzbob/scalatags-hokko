package scalatags.hokko

import _root_.hokko.core._
import hokko.control.Description
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement
import utest._

import scala.scalajs.js
import scalatags.Hokko.all._

object HokkoTests extends TestSuite {
  def tests = TestSuite {
    'eventsource {
      val mouseEvents = Event.source[dom.MouseEvent]
      var counter     = 0

      val description = Description.pure(()).subscribe(mouseEvents) { _ =>
        counter += 1
      }

      val network = description.compile()
      val domPatcher = new DomPatcher(
        div(onclick.listen(mouseEvents))(div).render(network.engine)
      )
      val element = domPatcher.parent.firstElementChild

      def click(el: dom.html.Element) = {
        el.click()
      }

      assert(counter == 0)
      click(element.asInstanceOf[dom.html.Element])
      assert(counter == 1)
      click(element.asInstanceOf[dom.html.Element])
      assert(counter == 2)
    }

    'behaviorsink {
      val src = CBehavior.source("default value")

      val description = Description.read(src)
      val network     = description.compile()

      val domPatcher = new DomPatcher(
        textarea.read(src, (_: HTMLElement).tagName).render(network.engine)
      )
      assert(network.now() == "TEXTAREA")
    }
  }
}
