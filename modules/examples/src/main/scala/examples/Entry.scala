package examples

import CalendarTodo.htmldsl._
import hokko.core.EventSource

case class Entry(content: String, date: String, done: Boolean = false)

object Entry {
  def view(e: Entry, extraAttr: Modifier*) = {
    val className = if (e.done) "finished" else "unfinished"
    tr(cls := className,
       extraAttr,
       td(s"${e.content} due by ${e.date}"),
       td(button(attr("pa") := 2, "Remove")))
  }

  def viewSeq(es: Seq[Entry], deletions: EventSource[Int]) =
    if (es.isEmpty) p("All done!")
    else
      table(es.zipWithIndex.map {
        case (e, i) =>
          view(e, id := i.toString, onclick.listen(deletions, _ => i))
      })
}
