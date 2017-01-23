package scalatags.hokko

import org.scalajs.dom

import scalatags.hokko.raw.VirtualDom
import scalatags.hokko.raw.VirtualDom.VTreeChild

/**
  * A utility class to patch the DOM with Virtual Elements
  * @param initialVDom the initial virtual dom element used to compute
  *                    consequent diffs
  * @param preRenderedElement an optional pre-rendered element, if supplied
  *                           this element should be the identical DOM
  *                           representation of the generated 'initialVDom'
  */
class DomPatcher(initialVDom: VTreeChild[dom.Element],
                 preRenderedElement: Option[dom.Element] = None) {
  val renderedElement =
    preRenderedElement.getOrElse(VirtualDom.create(initialVDom))
  private[this] var previousState = initialVDom

  private[this] def diffAndSwap(
      vdom: VTreeChild[dom.Element]): VirtualDom.Patch = {
    val patch = VirtualDom.diff(previousState, vdom)
    previousState = vdom
    patch
  }

  def applyNewState(vdom: VTreeChild[dom.Element]): dom.Element = {
    val patch = diffAndSwap(vdom)
    VirtualDom.patch(renderedElement, patch)
  }
}
