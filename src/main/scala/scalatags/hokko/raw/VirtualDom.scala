package scalatags.hokko.raw

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("virtualDom")
@js.native
object VirtualDom extends js.Object {
  def create[T](node: VTreeChild[T],
                opts: js.UndefOr[js.Object] = js.undefined): T =
    js.native

  def diff[T](lhs: VTreeChild[T], rhs: VTreeChild[T]): Patch = js.native

  def patch(rootNode: dom.raw.Node, patches: Patch): dom.raw.Node =
    js.native

  @js.native
  trait VTreeChild[+T] extends js.Any

  @js.native
  trait Patch extends js.Object
}
