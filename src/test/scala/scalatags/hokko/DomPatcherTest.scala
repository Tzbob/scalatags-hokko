package scalatags.hokko

import org.scalajs.dom
import org.scalajs.dom.raw.Element
import utest._

import scalatags.generic.Bundle

class Container[B, Output <: FragT, FragT](
    val bundle: Bundle[B, Output, FragT]) {
  import bundle.all._

  val divHeadingP = div(
    h1("hello"),
    p("this is a test")
  )

  val divHeadingNoP = div(
    h1("hello")
  )
}

object DomPatcherTests extends TestSuite {

  def tests = TestSuite {
    "add and remove appropriate tags when new state is applied" - {
      import scalatags.Hokko.all._

      def checkInitial(element: Element): Unit = {
        assert(element.tagName.toLowerCase == "div")
        assert(!element.hasChildNodes())
        ()
      }

      val init = div.render.compile().now()

      val patcher = new DomPatcher(init)
      val element = patcher.renderedElement
      dom.document.body.appendChild(element)

      checkInitial(element)

      patcher.applyNewState(div(h1("Hello")).render.compile().now())

      assert(element.tagName.toLowerCase() == "div")
      assert(element.children.length == 1)
      assert(element.children(0).tagName.toLowerCase() == "h1")
    }

    "add and remove appropriate tags starting from an initial element" - {
      def checkInitial(element: Element): Unit = {
        assert(element.tagName.toLowerCase() == "div")
        assert(element.childElementCount == 2)
        assert(element.children(0).tagName.toLowerCase() == "h1")
        assert(element.children(1).textContent == "this is a test")
        ()
      }

      val jsContainer = new Container(scalatags.JsDom)
      val element     = jsContainer.divHeadingP.render
      checkInitial(element)

      val vdomContainer = new Container(scalatags.Hokko)
      val patcher =
        new DomPatcher(vdomContainer.divHeadingP.render.compile().now(),
                       Some(element))

      patcher.applyNewState(vdomContainer.divHeadingNoP.render.compile().now())
      assert(element.childElementCount == 1)
    }

  }
}
