package scalatags.hokko

import _root_.hokko.core._
import hokko.control.Description
import org.scalajs.dom
import scalatags.Hokko.all._
import utest._

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

    'adveventsource {
      class Counter(inc: Int, dec: Int) {
        private[this] val incInput = Event.source[Int]
        private[this] val decInput = Event.source[Int]

        private def mkButton(src: EventSource[Int], txt: String, v: Int) =
          button(onclick.listen(src, { (_: dom.Event) =>
            v
          }), txt)

        val incButton = mkButton(incInput, "Increment", inc)
        val decButton = mkButton(decInput, "Decrement", dec)

        val state =
          incInput.unionWith(decInput)(_ + _).fold(0)(_ + _).toDBehavior

        val ui = state.map(
          v =>
            div(
              div("Current count: ", span(v)),
              div(incButton, decButton)
          ))
      }

      val c = new Counter(1, -1)

      val desc    = Description.read(c.state.toCBehavior)
      val network = desc.compile()
      val patcher = new DomPatcher(c.ui.init.render(network.engine))
      val el      = patcher.parent.firstElementChild

      val before = network.now()
      assert(before == 0)

      val buttons = el.querySelectorAll("button")
      buttons(0).asInstanceOf[dom.html.Element].click()
      buttons(0).asInstanceOf[dom.html.Element].click()
      val afterIncs = network.now()
      assert(afterIncs == 2)

      buttons(1).asInstanceOf[dom.html.Element].click()
      val afterDec = network.now()
      assert(afterDec == 1)
    }

    'behaviorsink {
      val src = CBehavior.source("default value")

      val description = Description.read(src)
      val network     = description.compile()

      new DomPatcher(
        textarea.read(src, (_: dom.Element).tagName).render(network.engine)
      )
      assert(network.now() == "TEXTAREA")
    }
  }
}
