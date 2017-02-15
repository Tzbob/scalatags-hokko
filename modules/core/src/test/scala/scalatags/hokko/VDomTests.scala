package scalatags
package hokko

import org.scalajs.dom
import utest._

import _root_.hokko.core._

import scalatags.Hokko.all._
import scalatags.hokko.raw.{VNode, VirtualDom}

object VDomTests extends TestSuite {
  def tests = TestSuite {
    'basic {
      'children {
        val noChildren = div.render(Engine.compile())
        assert(noChildren.children.length == 0)

        val children = div(h1, div(h2)).render(Engine.compile())
        assert(children.children.length == 2)
        assert(
          children.children(1).asInstanceOf[VNode[_]].children.length == 1)
      }

      'attributes {
        val url = "https://www.google.com/"
        val elem = VirtualDom.create(
          a(
            href := url,
            "Google"
          ).render(Engine.compile()))

        assert(elem.asInstanceOf[dom.html.Anchor].href == url)
        assert(elem.childNodes.length == 1)
        val textNode = elem.childNodes(0).asInstanceOf[dom.Text]
        assert(textNode.textContent == "Google")
      }

      'styles {
        val elemV = div(
          color := "red",
          float.left,
          backgroundColor := "yellow"
        ).render

        val elem = VirtualDom.create(elemV(Engine.compile()))

        assert(elem.style.color == "red")
        assert(elem.style.cssFloat == "left")
        assert(elem.style.backgroundColor == "yellow")
        // styles end up being sorted in alphabetical order
        val styleAttr = elem.getAttribute("style")
        assert(
          styleAttr.trim == "color: red; float: left; background-color: yellow;"
        )
      }
    }

    'fancy {
      'fragSeqsAreFrags {
        val rendered = Seq(
          h1("titless"),
          div("lol")
        )

        val wrapped = VirtualDom.create(div(rendered).render(Engine.compile()))

        val outerHTML = wrapped.outerHTML
        assert(outerHTML == "<div><h1>titless</h1><div>lol</div></div>")
      } //end
    }
  }
}
