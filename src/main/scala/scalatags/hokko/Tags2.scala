package scalatags
package hokko

import _root_.hokko.control.Description
import org.scalajs.dom
import org.scalajs.dom.html

import scalatags.hokko.raw.VirtualDom.VTreeChild

trait Tags2
    extends generic.Tags2[Builder,
                          Description[VTreeChild[dom.Element]],
                          Description[VTreeChild[dom.Node]]]
    with TagFactory {
  // Document Metadata
  lazy val title = treeTypedTag[html.Title]("title")
  lazy val style = treeTypedTag[html.Style]("style")
  // Scripting
  lazy val noscript = treeTypedTag[html.Element]("noscript")
  // Sections
  lazy val section = treeTypedTag[html.Element]("section")
  lazy val nav     = treeTypedTag[html.Element]("nav")
  lazy val article = treeTypedTag[html.Element]("article")
  lazy val aside   = treeTypedTag[html.Element]("aside")
  lazy val address = treeTypedTag[html.Element]("address")
  lazy val main    = treeTypedTag[html.Element]("main")
  // Text level semantics
  lazy val q     = treeTypedTag[html.Quote]("q")
  lazy val dfn   = treeTypedTag[html.Element]("dfn")
  lazy val abbr  = treeTypedTag[html.Element]("abbr")
  lazy val data  = treeTypedTag[html.Element]("data")
  lazy val time  = treeTypedTag[html.Element]("time")
  lazy val `var` = treeTypedTag[html.Element]("var")
  lazy val samp  = treeTypedTag[html.Element]("samp")
  lazy val kbd   = treeTypedTag[html.Element]("kbd")
  lazy val math  = treeTypedTag[html.Element]("math")
  lazy val mark  = treeTypedTag[html.Element]("mark")
  lazy val ruby  = treeTypedTag[html.Element]("ruby")
  lazy val rt    = treeTypedTag[html.Element]("rt")
  lazy val rp    = treeTypedTag[html.Element]("rp")
  lazy val bdi   = treeTypedTag[html.Element]("bdi")
  lazy val bdo   = treeTypedTag[html.Element]("bdo")
  // Forms
  lazy val keygen   = treeTypedTag[html.Element]("keygen", void = true)
  lazy val output   = treeTypedTag[html.Element]("output")
  lazy val progress = treeTypedTag[html.Progress]("progress")
  lazy val meter    = treeTypedTag[html.Element]("meter")
  // Interactive elements
  lazy val details = treeTypedTag[html.Element]("details")
  lazy val summary = treeTypedTag[html.Element]("summary")
  lazy val command = treeTypedTag[html.Element]("command", void = true)
  lazy val menu    = treeTypedTag[html.Menu]("menu")
}
