package scalatags.hokko

import hokko.core.Engine
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
    import scalatags.Hokko.all._
    "render anchor" - {
      val url    = "https://www.google.com/"
      val anchor = a(href := url, "Google").render(Engine.compile())

      val parent     = new DomPatcher(anchor).parent
      val htmlString = parent.innerHTML
      assert(htmlString == """<a href="https://www.google.com/">Google</a>""")
    }

    "add and remove appropriate tags when new state is applied" - {

      def checkInitial(element: Element): Unit = {
        assert(element.tagName.toLowerCase == "div")
        assert(!element.hasChildNodes())
        ()
      }

      val init = div.render(Engine.compile())

      val patcher = new DomPatcher(init)
      val element = patcher.parent.firstElementChild

      checkInitial(element)

      patcher.applyNewState(div(h1("Hello")).render(Engine.compile()))

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

      val parent = dom.document.createElement("div")

      val jsContainer = new Container(scalatags.JsDom)
      val element     = jsContainer.divHeadingP.render
      parent.appendChild(element)
      checkInitial(element)

      val vdomContainer = new Container(scalatags.Hokko)
      val patcher =
        new DomPatcher(vdomContainer.divHeadingP.render(Engine.compile()),
                       Some(element))

      patcher.applyNewState(
        vdomContainer.divHeadingNoP.render(Engine.compile()))

      assert(element.childElementCount == 1)
    }

  }
}
