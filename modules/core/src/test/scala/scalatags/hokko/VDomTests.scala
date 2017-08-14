package scalatags
package hokko

import org.scalajs.dom
import utest._
import _root_.hokko.core._
import org.scalajs.dom.raw.HTMLElement

import scalatags.Hokko.all._

//object VDomTests extends TestSuite {
//  def tests = TestSuite {
//    'basic {
//      'children {
//        val noChildren = div.render(Engine.compile())
//        assert(noChildren.maybeChildren.get.length == 0)
//
//        val children = div(h1, div(h2)).render(Engine.compile())
//        assert(children.maybeChildren.get.length == 2)
//        assert(
//          children.maybeChildren
//            .get(1)
//            .asInstanceOf[VNode]
//            .maybeChildren
//            .get
//            .length
//            == 1)
//      }
//
//      'attributes {
//        val url = "https://www.google.com/"
//
//        val anchor = a(href := url, "Google").render(Engine.compile())
//
//
//        println("@@@@@@@@@@@@@")
//        println(a(href := url, "google"))
//        val elem = new DomPatcher(anchor).renderedElement
//
//        assert(elem.asInstanceOf[dom.html.Anchor].href == url)
//        assert(elem.childNodes.length == 1)
//        val textNode = elem.childNodes(0).asInstanceOf[dom.Text]
//        assert(textNode.textContent == "Google")
//      }
//
//      'styles {
//        val elemV = div(
//          color := "red",
//          float.left,
//          backgroundColor := "yellow"
//        ).render
//
//        val elem = new DomPatcher(elemV(Engine.compile())).renderedElement
//          .asInstanceOf[HTMLElement]
//
//        assert(elem.style.color == "red")
//        assert(elem.style.cssFloat == "left")
//        assert(elem.style.backgroundColor == "yellow")
//        // styles end up being sorted in alphabetical order
//        val styleAttr = elem.getAttribute("style")
//        assert(
//          styleAttr.trim == "color: red; float: left; background-color: yellow;"
//        )
//      }
//    }
//
//    'fancy {
//      'fragSeqsAreFrags {
//        val rendered = Seq(
//          h1("titless"),
//          div("lol")
//        )
//
//        val wrapped =
//          new DomPatcher(div(rendered).render(Engine.compile())).renderedElement
//
//        val outerHTML = wrapped.outerHTML
//        assert(outerHTML == "<div><h1>titless</h1><div>lol</div></div>")
//      } //end
//    }
//  }
//}
