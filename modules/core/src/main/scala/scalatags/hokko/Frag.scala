package scalatags.hokko

import snabbdom.VNode
import hokko.core.Engine

import scalatags.generic

trait Frag extends generic.Frag[Builder, Engine => VNode] {
  def render: Engine => VNode
  override def applyTo(t: Builder): Unit = t.addChild(this)
}
