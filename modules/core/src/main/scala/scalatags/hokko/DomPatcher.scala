package scalatags.hokko

import org.scalajs.dom
import org.scalajs.dom.Element
import slogging.LazyLogging
import snabbdom.{Snabbdom, VNode}

import scala.scalajs.js
import scala.scalajs.js.|

class DomPatcher(initialVDom: VNode, targetOpt: Option[Element] = None)
    extends LazyLogging {
  private[this] val target =
    targetOpt
      .map { x =>
        x.innerHTML = ""; x
      }
      .getOrElse(DomPatcher.createParentedTarget())

  val parent: Element = {
    val dynamic = target.asInstanceOf[js.Dynamic]
    dynamic.parentElement.asInstanceOf[dom.Element]
  }

  private[this] var oldVDom: VNode = {
    logger.debug(s"Initializing $target with $initialVDom")
    DomPatcher.patch(target, initialVDom)
  }

  def applyNewState(vdom: VNode): Unit = {
    logger.debug(s"Patching $target on $initialVDom")
    oldVDom = DomPatcher.patch(oldVDom, vdom)
  }
}

object DomPatcher {
  def createParentedTarget(): Element = {
    def mkDiv  = dom.document.createElement("div")
    val parent = mkDiv
    val target = mkDiv
    parent.appendChild(target)
    target
  }

  val patch: js.Function2[|[VNode, Element], VNode, VNode] =
    Snabbdom.init(
      js.Array(snabbdom.`class`,
               snabbdom.props,
               snabbdom.attributes,
               snabbdom.style,
               snabbdom.eventlisteners))
}
