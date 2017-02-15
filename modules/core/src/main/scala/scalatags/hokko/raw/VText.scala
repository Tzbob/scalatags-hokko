package scalatags.hokko.raw

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scalatags.hokko.raw.VirtualDom.VTreeChild

@JSName("virtualDom.VText")
@js.native
class VText(str: String) extends js.Object with VTreeChild[dom.raw.Text]
