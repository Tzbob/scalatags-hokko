package scalatags.hokko.raw

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scalatags.hokko.raw.VirtualDom.VTreeChild

@JSName("virtualDom.VNode")
@js.native
class VNode[T](tagName0: String,
               properties0: js.UndefOr[js.Dictionary[js.Any]] = js.undefined,
               children0: js.UndefOr[js.Array[VTreeChild[_ <: dom.Node]]] =
                 js.undefined)
    extends js.Object
    with VTreeChild[T] {
  def tagName: String                          = js.native
  def key: js.UndefOr[String]                  = js.native
  def namespace: js.UndefOr[String]            = js.native
  def count: Int                               = js.native
  def hasWidgets: Boolean                      = js.native
  def hasThunks: Boolean                       = js.native
  def hooks: js.UndefOr[js.Dictionary[js.Any]] = js.native
  def children: js.Array[VTreeChild[_]]        = js.native
  def properties: js.Dictionary[js.Any]        = js.native
}
