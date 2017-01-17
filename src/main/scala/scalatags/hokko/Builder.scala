package scalatags.hokko

import _root_.hokko.control.Description
import _root_.hokko.core.Engine
import org.scalajs.dom

import scala.collection.mutable
import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import scalatags.hokko.raw.VirtualDom.VTreeChild
import scalatags.generic

class Builder {

  val handlers   = js.Dictionary.empty[Engine => js.Function1[dom.Event, Unit]]
  val attributes = js.Dictionary.empty[js.Any]
  val style      = js.Dictionary.empty[String]

  val sinkSetter = js.Array[dom.Node => Unit]()
  val classNames = js.Array[String]()
  val children   = js.Array[generic.Frag[_, Description[VTreeChild[dom.Node]]]]()

  def addChild(f: generic.Frag[_, Description[VTreeChild[dom.Node]]]): Unit =
    children.push(f)

  def addClassName(name: String): Unit = classNames.push(name)

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

  def addStyle(key: String, value: String): Unit =
    style.update(key, value)

  def addHandler(key: String)(
      f: Engine => js.Function1[dom.Event, Unit]): Unit =
    handlers.update(key, f)

  def addSinkSetter(setter: dom.Node => Unit): Unit =
    sinkSetter.push(setter)

  def make(tag: String): Description[raw.VNode[dom.Node]] = {
    import cats.instances.all._
    import cats.syntax.all._

    import js.JSConverters._

    val rendered          = children.map(_.render).toList
    val sequencedChildren = rendered.sequence.map(x => Option(x).orUndefined)

    makeClassNameString.foreach(this.updateAttribute("class", _))

    sequencedChildren.mapEngine { (engine, children) =>
      val props =
        js.Dictionary[js.Any]("attributes" -> attributes, "style" -> style)

      handlers.foreach {
        case (name, f) =>
          props.update(name, f(engine))
      }

      props.update("hokko-hook", new Hook(n => sinkSetter.foreach(_(n))))

      new raw.VNode(tag, Option(props).orUndefined, children.map(_.toJSArray))
    }
  }

  @ScalaJSDefined
  class Hook(f: dom.Node => Unit) extends js.Object {
    def hook(node: dom.Node,
             propertyName: String,
             previousValue: js.UndefOr[js.Object]): Unit =
      f(node)
  }
}
