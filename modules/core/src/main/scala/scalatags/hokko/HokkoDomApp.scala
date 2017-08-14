package scalatags.hokko

import hokko.core.{DBehavior, Engine}
import org.scalajs.dom

import scala.scalajs.js.JSApp

trait HokkoDomApp extends JSApp {
  lazy val htmldsl   = scalatags.Hokko.all
  lazy val extraHtml = scalatags.Hokko.tags2

  type Html = htmldsl.Tag

  def initialize(): dom.Element = {
    val engine     = Engine.compile(ui, ui.changes)
    val domPatcher = new DomPatcher(ui.init.render(engine))

    engine.subscribeForPulses { pulses =>
      pulses(ui.changes).foreach { tag =>
        val newVDom = tag.render(engine)
        domPatcher.applyNewState(newVDom)
      }
    }

    domPatcher.parent
  }

  def main(): Unit = {
    println("Starting App")

    def start(): Unit = {
      val element = this.initialize()
      this.container.appendChild(element)
      ()
    }

    if (dom.document.readyState != "loading") start()
    else
      dom.document
        .addEventListener("DOMContentLoaded", (_: dom.Event) => start())
  }

  def container: dom.Element = dom.document.body
  def ui: DBehavior[Html]
}
