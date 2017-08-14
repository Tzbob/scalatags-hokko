package scalatags.hokko

import snabbdom.VNode
import hokko.core.Engine
import org.scalajs.dom

import scalatags.Escaping
import scalatags.generic.Namespace

trait TagFactory
    extends scalatags.generic.Util[Builder, Engine => VNode, Engine => VNode] {
  def nTag(s: String,
           void: Boolean = false): ConcreteHtmlTag[Engine => VNode] = {
    typedTag(s, void)
  }

  def treeTypedTag[T <: dom.Element](
      s: String,
      void: Boolean = false): ConcreteHtmlTag[Engine => VNode] = {
    typedTag(s, void)
  }

  def typedTag[T <: VNode](
      s: String,
      void: Boolean = false): ConcreteHtmlTag[Engine => T] = {

    if (!Escaping.validTag(s))
      throw new IllegalArgumentException(
        s"Illegal tag name: $s is not a valid XML tag name")
    makeAbstractTypedTag[Engine => T](s, void, Namespace.htmlNamespaceConfig)
  }

  def tag(s: String, void: Boolean = false): ConcreteHtmlTag[Engine => VNode] = {
    typedTag(s, void)
  }
}
