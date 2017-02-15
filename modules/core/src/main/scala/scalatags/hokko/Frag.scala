package scalatags.hokko

import hokko.core.Engine
import org.scalajs.dom

import scalatags.generic
import scalatags.hokko.raw.VirtualDom
import scalatags.hokko.raw.VirtualDom.{Patch, VTreeChild}

trait Frag extends generic.Frag[Builder, Engine => VTreeChild[dom.Node]] {
  def render: Engine => VTreeChild[dom.Node]
  override def applyTo(t: Builder): Unit = t.addChild(this)
}
