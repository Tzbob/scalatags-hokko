package scalatags.hokko

import _root_.hokko.core._
import hokko.control.Description
import org.scalajs.dom
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
      val element = domPatcher.renderedElement

      dom.document.body.appendChild(element)

      def click(el: dom.html.Element) = {
        // JSDom does not support .onclick properties, we mimic it by
        // manually calling onclick
        el.onclick(
          js.Dynamic
            .literal(preventDefault = () => ())
            .asInstanceOf[dom.MouseEvent])
      }

      assert(counter == 0)
      click(element.asInstanceOf[dom.html.Element])
      assert(counter == 1)
      click(element.asInstanceOf[dom.html.Element])
      assert(counter == 2)
    }

    'behaviorsink {
      val src = CBehavior.src("default value")

      val description = Description.listen(src)
      val network     = description.compile()

      val domPatcher = new DomPatcher(
        textarea.read(src, _.tagName).render(network.engine)
      )
      val element = domPatcher.renderedElement

      assert(network.now() == "TEXTAREA")
    }
  }
}
