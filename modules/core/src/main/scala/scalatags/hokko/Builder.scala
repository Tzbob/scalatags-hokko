package scalatags.hokko

import _root_.hokko.core.Engine
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import scalatags.generic
import scalatags.hokko.raw.VirtualDom.VTreeChild

class Builder {

  val handlers   = js.Dictionary.empty[Engine => js.Function1[dom.Event, Unit]]
  val attributes = js.Dictionary.empty[js.Any]
  val properties = js.Dictionary.empty[js.Any]
  val style      = js.Dictionary.empty[String]

  val sinkSetter = js.Array[dom.Node => Unit]()
  val classNames = js.Array[String]()
  val children   = js.Array[generic.Frag[_, Engine => VTreeChild[dom.Node]]]()

  def addChild(f: generic.Frag[_, Engine => VTreeChild[dom.Node]]): Unit = {
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

  def updateAttribute(key: String, value: js.Any): Unit =
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

  def make(tag: String): Engine => raw.VNode[dom.Node] = {
    import cats.instances.all._
    import cats.syntax.all._

    import js.JSConverters._

    val rendered          = children.map(_.render).toList
    val sequencedChildren = rendered.sequenceU.map(x => Option(x).orUndefined)

    makeClassNameString.foreach(this.updateAttribute("class", _))

    engine =>
      val children = sequencedChildren(engine)

      if (properties.contains("value"))
        properties.update("value", new SetHook(properties("value").toString))

      properties.update("attributes", attributes)

      if (!style.isEmpty)
        properties.update("style", style)

      handlers.foreach {
        case (name, f) =>
          properties.update(name, f(engine))
      }

      if (!sinkSetter.isEmpty)
        properties
          .update("hokko-hook", new Hook(n => sinkSetter.foreach(_(n))))

      new raw.VNode(tag,
                    Option(properties).orUndefined,
                    children.map(_.toJSArray))
  }

  @ScalaJSDefined
  class Hook(f: dom.Node => Unit)
      extends js.Object {
    def hook(node: dom.Node,
             propertyName: String,
             previousValue: js.UndefOr[js.Object]): Unit =
      f(node)
  }

  @ScalaJSDefined
  class SetHook(v: String)
      extends Hook(node => {
        val dynamicNode = node.asInstanceOf[js.Dynamic]
        if (dynamicNode.value != v) dynamicNode.value = v
      })
}
