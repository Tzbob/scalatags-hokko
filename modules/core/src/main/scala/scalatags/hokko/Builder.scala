package scalatags.hokko

import _root_.hokko.core.Engine
import org.scalajs.dom
import org.scalajs.dom.{Element, Event}
import snabbdom.VNode

import scala.scalajs.js
import scala.scalajs.js.{Array, Function1, UndefOr, WrappedDictionary, |}
import scalatags.generic

class Builder {

  val handlers   = js.Dictionary.empty[Engine => js.Function1[dom.Event, Unit]]
  val attributes = js.Dictionary.empty[Boolean | String]
  val properties = js.Dictionary.empty[Any]
  val style      = js.Dictionary.empty[Any]

  val sinkSetter   = js.Array[dom.Node => Unit]()
  val sinkUnSetter = js.Array[dom.Node => Unit]()
  val classNames   = js.Array[String]()
  val children     = js.Array[generic.Frag[_, Engine => VNode]]()

  def addChild(f: generic.Frag[_, Engine => VNode]): Unit = {
    children.push(f)
    ()
  }

  def addClassName(name: String): Unit = {
    classNames.push(name)
    ()
  }

  def makeClassNameString: Option[String] = {
    val namesOpt =
      if (classNames.isEmpty) None
      else Some(classNames.mkString(" "))

    attributes
      .get("class")
      .asInstanceOf[Option[String]]
      .flatMap { x =>
        namesOpt.map(y => s"$x $y")
      }
      .orElse(namesOpt)
  }

  def updateAttribute(key: String, value: Boolean | String): Unit =
    attributes.update(key, value)

  def updateProperty(key: String, value: js.Any): Unit =
    properties.update(key, value)

  def addStyle(key: String, value: String): Unit =
    style.update(key, value)

  def addHandler(key: String)(
      f: Engine => js.Function1[dom.Event, Unit]): Unit =
    handlers.update(key, f)

  def addSinkSetter(setter: dom.Node => Unit): Unit = {
    sinkSetter.push(setter)
    ()
  }

  def addSinkUnSetter(unSetter: dom.Node => Unit): Unit = {
    sinkUnSetter.push(unSetter)
    ()
  }

  def make(tag: String): Engine => VNode = {
    import cats.instances.all._
    import cats.syntax.all._

    import js.JSConverters._

    makeClassNameString.foreach(this.updateAttribute("class", _))

    val renderedChildren: Array[(Engine) => VNode] = children.map(_.render)

    engine =>
      val jsChildren = renderedChildren.map(_(engine)).toJSArray
      val handlersWithoutOn: js.Dictionary[js.Function1[Event, Unit]] =
        handlers.map {
          case (name, fn) =>
            val nameWithoutOn = name.drop(2)
            nameWithoutOn -> fn(engine)
        }.toJSDictionary

      val hooks = js.Dynamic.literal(
        insert = (vnode: VNode) => {
          sinkSetter.foreach { f =>
            vnode.elm.toOption.foreach(n => f(n.asInstanceOf[dom.Node]))
          }
        },

        destroy = (vnode: VNode) => {
          sinkUnSetter.foreach { f =>
            vnode.elm.toOption.foreach(n => f(n.asInstanceOf[dom.Node]))
          }
        }
      )

      val data = js.Dynamic.literal(
        attrs = attributes,
        props = properties,
        style = this.style,
        on = handlersWithoutOn,
        hook = hooks
      )

      snabbdom.h(tag, data, jsChildren)
  }
}
