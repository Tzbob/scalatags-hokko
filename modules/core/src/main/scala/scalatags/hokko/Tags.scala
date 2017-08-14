package scalatags
package hokko

import _root_.hokko.core.Engine
import snabbdom.VNode
import org.scalajs.dom

trait Tags
    extends generic.Tags[Builder,
                         Engine => VNode,
                         Engine => VNode]
    with TagFactory {
  // Root Element
  lazy val html = treeTypedTag[dom.html.Html]("html")
  // Document Metadata
  lazy val head = treeTypedTag[dom.html.Head]("head")
  lazy val base = treeTypedTag[dom.html.Base]("base", void = true)
  lazy val link = treeTypedTag[dom.html.Link]("link", void = true)
  lazy val meta = treeTypedTag[dom.html.Meta]("meta", void = true)
  // Scripting
  lazy val script = treeTypedTag[dom.html.Script]("script")
  // Sections
  lazy val body   = treeTypedTag[dom.html.Body]("body")
  lazy val h1     = treeTypedTag[dom.html.Heading]("h1")
  lazy val h2     = treeTypedTag[dom.html.Heading]("h2")
  lazy val h3     = treeTypedTag[dom.html.Heading]("h3")
  lazy val h4     = treeTypedTag[dom.html.Heading]("h4")
  lazy val h5     = treeTypedTag[dom.html.Heading]("h5")
  lazy val h6     = treeTypedTag[dom.html.Heading]("h6")
  lazy val header = treeTypedTag[dom.html.Element]("header")
  lazy val footer = treeTypedTag[dom.html.Element]("footer")
  // Grouping content
  lazy val p          = treeTypedTag[dom.html.Paragraph]("p")
  lazy val hr         = treeTypedTag[dom.html.HR]("hr", void = true)
  lazy val pre        = treeTypedTag[dom.html.Pre]("pre")
  lazy val blockquote = treeTypedTag[dom.html.Quote]("blockquote")
  lazy val ol         = treeTypedTag[dom.html.OList]("ol")
  lazy val ul         = treeTypedTag[dom.html.UList]("ul")
  lazy val li         = treeTypedTag[dom.html.LI]("li")
  lazy val dl         = treeTypedTag[dom.html.DList]("dl")
  lazy val dt         = treeTypedTag[dom.html.DT]("dt")
  lazy val dd         = treeTypedTag[dom.html.DD]("dd")
  lazy val figure     = treeTypedTag[dom.html.Element]("figure")
  lazy val figcaption = treeTypedTag[dom.html.Element]("figcaption")
  lazy val div        = treeTypedTag[dom.html.Div]("div")
  // Text-level semantics
  lazy val a      = treeTypedTag[dom.html.Anchor]("a")
  lazy val em     = treeTypedTag[dom.html.Element]("em")
  lazy val strong = treeTypedTag[dom.html.Element]("strong")
  lazy val small  = treeTypedTag[dom.html.Element]("small")
  lazy val s      = treeTypedTag[dom.html.Element]("s")
  lazy val cite   = treeTypedTag[dom.html.Element]("cite")
  lazy val code   = treeTypedTag[dom.html.Element]("code")
  lazy val sub    = treeTypedTag[dom.html.Element]("sub")
  lazy val sup    = treeTypedTag[dom.html.Element]("sup")
  lazy val i      = treeTypedTag[dom.html.Element]("i")
  lazy val b      = treeTypedTag[dom.html.Element]("b")
  lazy val u      = treeTypedTag[dom.html.Element]("u")
  lazy val span   = treeTypedTag[dom.html.Span]("span")
  lazy val br     = treeTypedTag[dom.html.BR]("br", void = true)
  lazy val wbr    = treeTypedTag[dom.html.Element]("wbr", void = true)
  // Edits
  lazy val ins = treeTypedTag[dom.html.Mod]("ins")
  lazy val del = treeTypedTag[dom.html.Mod]("del")
  // Embedded content
  lazy val img      = treeTypedTag[dom.html.Image]("img", void = true)
  lazy val iframe   = treeTypedTag[dom.html.IFrame]("iframe")
  lazy val embed    = treeTypedTag[dom.html.Embed]("embed", void = true)
  lazy val `object` = treeTypedTag[dom.html.Object]("object")
  lazy val param    = treeTypedTag[dom.html.Param]("param", void = true)
  lazy val video    = treeTypedTag[dom.html.Video]("video")
  lazy val audio    = treeTypedTag[dom.html.Audio]("audio")
  lazy val source   = treeTypedTag[dom.html.Source]("source", void = true)
  lazy val track    = treeTypedTag[dom.html.Track]("track", void = true)
  lazy val canvas   = treeTypedTag[dom.html.Canvas]("canvas")
  lazy val map      = treeTypedTag[dom.html.Map]("map")
  lazy val area     = treeTypedTag[dom.html.Area]("area", void = true)
  // Tabular data
  lazy val table    = treeTypedTag[dom.html.Table]("table")
  lazy val caption  = treeTypedTag[dom.html.TableCaption]("caption")
  lazy val colgroup = treeTypedTag[dom.html.TableCol]("colgroup")
  lazy val col      = treeTypedTag[dom.html.TableCol]("col", void = true)
  lazy val tbody    = treeTypedTag[dom.html.TableSection]("tbody")
  lazy val thead    = treeTypedTag[dom.html.TableSection]("thead")
  lazy val tfoot    = treeTypedTag[dom.html.TableSection]("tfoot")
  lazy val tr       = treeTypedTag[dom.html.TableRow]("tr")
  lazy val td       = treeTypedTag[dom.html.TableCell]("td")
  lazy val th       = treeTypedTag[dom.html.TableHeaderCell]("th")
  // Forms
  lazy val form     = treeTypedTag[dom.html.Form]("form")
  lazy val fieldset = treeTypedTag[dom.html.FieldSet]("fieldset")
  lazy val legend   = treeTypedTag[dom.html.Legend]("legend")
  lazy val label    = treeTypedTag[dom.html.Label]("label")
  lazy val input    = treeTypedTag[dom.html.Input]("input", void = true)
  lazy val button   = treeTypedTag[dom.html.Button]("button")
  lazy val select   = treeTypedTag[dom.html.Select]("select")
  lazy val datalist = treeTypedTag[dom.html.DataList]("datalist")
  lazy val optgroup = treeTypedTag[dom.html.OptGroup]("optgroup")
  lazy val option   = treeTypedTag[dom.html.Option]("option")
  lazy val textarea = treeTypedTag[dom.html.TextArea]("textarea")
}
