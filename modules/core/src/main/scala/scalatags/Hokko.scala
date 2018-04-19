package scalatags

import java.util.Objects

import _root_.hokko.core._
import snabbdom.VNode
import org.scalajs.dom

import scala.language.implicitConversions
import scala.scalajs.js
import scalatags.generic._
import scalatags.hokko.{Builder, DomPatcher, Property}
import scalatags.stylesheet.{StyleSheetFrag, StyleTree}

import scala.scalajs.js.JSON

/**
  * A Scalatags module that generates `Description[VNode]`s when the tags are
  * rendered.
  * This provides some additional flexibility over the Text backend, as you
  * can bind structured objects to the attributes of your
  * `Description[VNode]` without serializing them first into strings.
  */
object Hokko
    extends generic.Bundle[hokko.Builder, Engine => VNode, Engine => VNode]
    with Aliases[hokko.Builder, Engine => VNode, Engine => VNode] {

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
    type ConcreteHtmlTag[T <: Engine => VNode] =
      TypedTag[T]
    type BaseTagType =
      TypedTag[Engine => VNode]

    protected[this] implicit def stringAttrX = new GenericAttr[String]

    protected[this] implicit def stringStyleX = new GenericStyle[String]

    protected[this] implicit def stringPixelStyleX =
      new GenericPixelStyle[String](stringStyleX)

    implicit def UnitFrag(u: Unit): Hokko.StringFrag = new Hokko.StringFrag("")

    def makeAbstractTypedTag[T <: Engine => VNode](
        tag: String,
        void: Boolean,
        nameSpaceConfig: Namespace): TypedTag[T] =
      TypedTag(tag, Nil, void)

    implicit class SeqFrag[A](xs: Seq[A])(implicit ev: A => Frag) extends Frag {
      Objects.requireNonNull(xs)

      def applyTo(t: hokko.Builder): Unit = xs.foreach(_.applyTo(t))

      def render: Engine => VNode = {
        val builder = new hokko.Builder
        xs.foreach(x => builder.addChild(ev(x)))
        builder.make("DocumentFragment")
      }
    }

  }

  trait Aggregate
      extends generic.Aggregate[hokko.Builder, Engine => VNode, Engine => VNode] {
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
        c.copy(styles = c.styles ++ b.escapedStyle)
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
    def raw(s: String) = RawFrag(s)

    val StringFrag = Hokko.StringFrag
    type StringFrag = Hokko.StringFrag

    val Tag = Hokko.TypedTag
  }

  object RawFrag extends Companion[RawFrag]
  case class RawFrag(v: String) extends hokko.Frag {
    Objects.requireNonNull(v)
    def render: Engine => VNode =
      _ => ??? // can be implemented with virtualize
  }

  object StringFrag extends Companion[StringFrag]
  case class StringFrag(v: String) extends hokko.Frag {
    Objects.requireNonNull(v)
    def render: Engine => VNode = _ => v.asInstanceOf[VNode]
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

  case class TypedTag[Output <: Engine => VNode](tag: String = "",
                                                 modifiers: List[Seq[Modifier]],
                                                 void: Boolean = false)
      extends generic.TypedTag[hokko.Builder, Output, Engine => VNode]
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
      val node = render(Engine.compile())
      val copiedNode: VNode = JSON
        .parse(JSON.stringify(node))
        .asInstanceOf[VNode]

      new DomPatcher(copiedNode, None).parent.innerHTML
    }

  }

  trait LowPriorityImplicits {

    implicit class AttrExtension(a: Attr) {
      def listen[R >: dom.Event](src: EventSource[R])
        : generic.AttrPair[hokko.Builder, EventSource[R]] =
        this.listen(src, identity)

      def listen[Result](src: EventSource[Result], f: dom.Event => Result)
        : generic.AttrPair[hokko.Builder, EventSource[Result]] =
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

    type TTag = TypedTag[Engine => VNode]

    implicit class TagWithSink(tag: TTag) {
      def read[Result](src: CBehaviorSource[Result],
                       selector: dom.Element => Result): TTag = {
        val sinkSetter = (el: dom.Element) =>
          src.changeSource(Option(selector(el)))
        val sinkUnSetter = (el: dom.Element) => src.unSet()

        tag.apply(new Modifier {
          def applyTo(t: Builder) = {
            t.addSinkSetter { (n: dom.Node) =>
              sinkSetter(n.asInstanceOf[dom.Element])
            }
            t.addSinkUnSetter { (n: dom.Node) =>
              sinkUnSetter(n.asInstanceOf[dom.Element])
            }
          }
        })
      }
    }
  }
}
