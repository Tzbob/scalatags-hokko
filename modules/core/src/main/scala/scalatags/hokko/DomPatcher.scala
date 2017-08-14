package scalatags.hokko

import org.scalajs.dom
import snabbdom.{Snabbdom, VNode}
import org.scalajs.dom.{Element, Node}

import scala.scalajs.js

class DomPatcher(initialVDom: VNode, targetOpt: Option[Element] = None) {
  private[this] val target =
    targetOpt
      .map { x =>
        x.innerHTML = ""; x
      }
      .getOrElse(DomPatcher
        .createParentedTarget())

  val parent: Element = {
    val dynamic = target.asInstanceOf[js.Dynamic]
    dynamic.parentElement.asInstanceOf[dom.Element]
  }

  private[this] var oldVDom: VNode = DomPatcher.patch(target, initialVDom)

  def applyNewState(vdom: VNode): Unit =
    oldVDom = DomPatcher.patch(oldVDom, vdom)
}

object DomPatcher {
  def createParentedTarget(): Element = {
    def mkDiv  = dom.document.createElement("div")
    val parent = mkDiv
    val target = mkDiv
    parent.appendChild(target)
    target
  }

  val patch =
    Snabbdom.init(
      js.Array(snabbdom.`class`,
               snabbdom.props,
               snabbdom.attributes,
               snabbdom.style,
               snabbdom.eventlisteners))
}
