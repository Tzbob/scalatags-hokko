package scalatags.hokko

import hokko.control.Description
import org.scalajs.dom

import scalatags.generic
import scalatags.hokko.raw.VirtualDom
import scalatags.hokko.raw.VirtualDom.{Patch, VTreeChild}

trait Frag extends generic.Frag[Builder, Description[VTreeChild[dom.Node]]] {
  def render: Description[VTreeChild[dom.Node]]
  def diff(
      nodeDescription: Description[raw.VNode[dom.Node]]): Description[Patch] =
    this.render.map2(nodeDescription) { (thisNode, otherNode) =>
      VirtualDom.diff(thisNode, otherNode)
    }
  def create(): Description[dom.Node] =
    this.render.map(x => VirtualDom.create(x))

  override def applyTo(t: Builder): Unit = t.addChild(this)
}
