package scalatags.hokko

import hokko.control.Description
import org.scalajs.dom

import scalatags.Escaping
import scalatags.generic.Namespace
import scalatags.hokko.raw.VNode
import scalatags.hokko.raw.VirtualDom.VTreeChild

trait TagFactory
    extends scalatags.generic.Util[Builder,
                                   Description[VTreeChild[dom.Element]],
                                   Description[VTreeChild[dom.Node]]] {
  def nTag(s: String, void: Boolean = false)
    : ConcreteHtmlTag[Description[raw.VNode[dom.Element]]] = {
    typedTag(s, void)
  }

  def treeTypedTag[T <: dom.Element](
      s: String,
      void: Boolean = false): ConcreteHtmlTag[Description[VNode[T]]] = {
    typedTag(s, void)
  }

  def typedTag[T <: VTreeChild[dom.Element]](
      s: String,
      void: Boolean = false): ConcreteHtmlTag[Description[T]] = {

    if (!Escaping.validTag(s))
      throw new IllegalArgumentException(
        s"Illegal tag name: $s is not a valid XML tag name")
    makeAbstractTypedTag[Description[T]](s,
                                         void,
                                         Namespace.htmlNamespaceConfig)
  }

  def tag(s: String, void: Boolean = false)
    : ConcreteHtmlTag[Description[VTreeChild[dom.Element]]] = {
    typedTag(s, void)
  }
}
