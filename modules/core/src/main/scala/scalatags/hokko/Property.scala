package scalatags.hokko

import scala.scalajs.js

case class Property[T <: js.Any](t: T)

object Property {
  val defaultToProperty = Set(
    "value"
  )
}
