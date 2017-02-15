package scalatags

import java.util.Objects

import _root_.hokko.core._
import org.scalajs.dom

import scala.language.implicitConversions
import scalatags.generic._
import scalatags.hokko.{Builder, Property}
import scalatags.hokko.raw.VirtualDom.VTreeChild
import scalatags.hokko.raw.{VText, VirtualDom}
import scalatags.stylesheet.{StyleSheetFrag, StyleTree}
import cats.syntax.applicative._

import scala.scalajs.js

/**
  * A Scalatags module that generates `Description[VNode]`s when the tags are
  * rendered.
  * This provides some additional flexibility over the Text backend, as you
  * can bind structured objects to the attributes of your
  * `Description[VNode]` without serializing them first into strings.
  */
object Hokko
    extends generic.Bundle[hokko.Builder,
                           Engine => VTreeChild[dom.Element],
                           Engine => VTreeChild[dom.Node]]
    with Aliases[hokko.Builder,
                 Engine => VTreeChild[dom.Element],
                 Engine => VTreeChild[dom.Node]] {

  object attrs extends Hokko.Cap with Attrs

  object tags extends Hokko.Cap with hokko.Tags

  object tags2 extends Hokko.Cap with hokko.Tags2

  object styles extends Hokko.Cap with Styles

  object styles2 extends Hokko.Cap with Styles2

  object svgTags extends Hokko.Cap with hokko.SvgTags

  object svgAttrs extends Hokko.Cap with SvgAttrs

  object implicits extends Aggregate with DataConverters

  object all
      extends Cap
      with Attrs
      with Styles
      with hokko.Tags
      with DataConverters
      with Aggregate
      with LowPriorityImplicits

  object short
      extends Cap
      with hokko.Tags
      with DataConverters
      with Aggregate
      with AbstractShort {

    object * extends Cap with Attrs with Styles

  }

  trait Cap extends Util with hokko.TagFactory { self =>
    type ConcreteHtmlTag[T <: Engine => VTreeChild[dom.Element]] =
      TypedTag[T]
    type BaseTagType =
      TypedTag[Engine => VTreeChild[dom.Element]]

    protected[this] implicit def stringAttrX = new GenericAttr[String]

    protected[this] implicit def stringStyleX = new GenericStyle[String]

    protected[this] implicit def stringPixelStyleX =
      new GenericPixelStyle[String](stringStyleX)

    implicit def UnitFrag(u: Unit): Hokko.StringFrag = new Hokko.StringFrag("")

    def makeAbstractTypedTag[T <: Engine => VTreeChild[dom.Element]](
        tag: String,
        void: Boolean,
        nameSpaceConfig: Namespace): TypedTag[T] =
      TypedTag(tag, Nil, void)

    implicit class SeqFrag[A](xs: Seq[A])(implicit ev: A => Frag)
        extends Frag {
      Objects.requireNonNull(xs)

      def applyTo(t: hokko.Builder): Unit = xs.foreach(_.applyTo(t))

      def render: Engine => VTreeChild[dom.Node] = {
        val builder = new hokko.Builder
        xs.foreach(x => builder.addChild(ev(x)))
        builder.make("DocumentFragment")
      }
    }

  }

  trait Aggregate
      extends generic.Aggregate[hokko.Builder,
                                Engine => VTreeChild[dom.Element],
                                Engine => VTreeChild[dom.Node]] {
    implicit def ClsModifier(s: stylesheet.Cls): Modifier = new Modifier {
      def applyTo(t: hokko.Builder) = {
        t.addClassName(s.name)
      }
    }

    implicit class StyleFrag(s: generic.StylePair[hokko.Builder, _])
        extends StyleSheetFrag {
      def applyTo(c: StyleTree) = {
        val b = new hokko.Builder
        s.applyTo(b)
        val escapedStyles = b.style.mapValues(v => s" $v;")
        c.copy(styles = c.styles ++ escapedStyles)
      }
    }

    def genericAttr[T] = new Hokko.GenericAttr[T]

    def genericStyle[T] = new Hokko.GenericStyle[T]

    def genericPixelStyle[T](implicit ev: StyleValue[T]): PixelStyleValue[T] =
      new Hokko.GenericPixelStyle[T](ev)

    def genericPixelStylePx[T](
        implicit ev: StyleValue[String]): PixelStyleValue[T] =
      new Hokko.GenericPixelStylePx[T](ev)

    implicit def stringFrag(v: String) = new Hokko.StringFrag(v)

    val RawFrag = Hokko.RawFrag
    type RawFrag = Hokko.RawFrag

    val StringFrag = Hokko.StringFrag
    type StringFrag = Hokko.StringFrag

    def raw(s: String) = RawFrag(s)

    type Tag = Hokko.TypedTag[_ <: Engine => VTreeChild[dom.Element]]
    val Tag = Hokko.TypedTag
  }

  object RawFrag extends Companion[RawFrag]
  case class RawFrag(v: String) extends hokko.Frag {
    Objects.requireNonNull(v)
    def render: Engine => VText = _ => new VText(v)
  }

  object StringFrag extends Companion[StringFrag]
  case class StringFrag(v: String) extends hokko.Frag {
    Objects.requireNonNull(v)
    def render: Engine => VText = _ => new VText(v)
  }

  class GenericAttr[T] extends AttrValue[T] {
    def apply(t: hokko.Builder, a: Attr, v: T): Unit = v match {
      case Property(value) =>
        t.updateProperty(a.name, value)
      case _ =>
        if (Property.defaultToProperty(a.name)) {
          t.updateProperty(a.name, v.toString)
        } else t.updateAttribute(a.name, v.toString)
    }
  }

  class GenericStyle[T] extends StyleValue[T] {
    def apply(t: hokko.Builder, s: Style, v: T): Unit =
      t.addStyle(s.cssName, v.toString)
  }

  class GenericPixelStyle[T](ev: StyleValue[T]) extends PixelStyleValue[T] {
    def apply(s: Style, v: T) = StylePair(s, v, ev)
  }

  class GenericPixelStylePx[T](ev: StyleValue[String])
      extends PixelStyleValue[T] {
    def apply(s: Style, v: T) = StylePair(s, v + "px", ev)
  }

  case class TypedTag[Output <: Engine => VTreeChild[dom.Element]](
      tag: String = "",
      modifiers: List[Seq[Modifier]],
      void: Boolean = false)
      extends generic.TypedTag[hokko.Builder,
                               Output,
                               Engine => VTreeChild[dom.Node]]
      with hokko.Frag {

    protected[this] type Self = TypedTag[Output]

    def render: Output = {
      val builder = new hokko.Builder
      this.build(builder)
      builder.make(tag).asInstanceOf[Output]
    }

    def apply(xs: Modifier*): TypedTag[Output] = {
      this.copy(tag = tag, void = void, modifiers = xs :: modifiers)
    }

    override def toString = {
      val node = VirtualDom.create(render(Engine.compile()))
      if (node.nodeType == dom.Node.ELEMENT_NODE)
        node.asInstanceOf[dom.Element].outerHTML
      else node.textContent
    }

  }

}

trait LowPriorityImplicits {

  implicit class AttrExtension(a: Attr) {
    def listen[R >: dom.Event](
        src: EventSource[R]): AttrPair[hokko.Builder, EventSource[R]] =
      this.listen(src, identity)

    def listen[Result](
        src: EventSource[Result],
        f: dom.Event => Result): AttrPair[hokko.Builder, EventSource[Result]] =
      AttrPair(
        a,
        src,
        new generic.AttrValue[hokko.Builder, EventSource[Result]] {
          def apply(t: Builder, a: Attr, v: EventSource[Result]): Unit =
            t.addHandler(a.name) { engine => (event: dom.Event) =>
              event.preventDefault()
              engine.fire(Seq(v -> f(event)))
            }
        }
      )
  }

  implicit def evtSourceFunAttrValue[Ev >: dom.Event, Result] =
    new generic.AttrValue[hokko.Builder, (Ev => Result, EventSource[Result])] {
      def apply(t: Builder,
                a: Attr,
                value: (Ev => Result, EventSource[Result])): Unit =
        t.addHandler(a.name) { engine => (event: dom.Event) =>
          event.preventDefault()
          val (f, src) = value
          engine.fire(Seq(src -> f(event)))
        }
    }

  type TTag[El <: dom.Element] = TypedTag[hokko.Builder,
                                          Engine => VTreeChild[El],
                                          Engine => VTreeChild[dom.Node]]

  implicit class TagWithSink[El <: dom.Element](tag: TTag[El]) {
    def read[Result](src: CBehaviorSource[Result],
                     selector: El => Result): TTag[El] = {
      val sinkSetter = (el: El) => src.changeSource(Option(selector(el)))

      tag.apply(new Modifier[Builder] {
        def applyTo(t: Builder) =
          t.addSinkSetter { (n: dom.Node) =>
            sinkSetter(n.asInstanceOf[El])
          }
      })
    }
  }
}
