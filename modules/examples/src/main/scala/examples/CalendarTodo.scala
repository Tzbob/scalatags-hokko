package examples

import hokko.core.{CBehavior, DBehavior, Event}
import org.scalajs.dom.html.Input

import scalatags.hokko.HokkoDomApp

object CalendarTodo extends HokkoDomApp {
  import htmldsl._

  val date    = CBehavior.source("")
  val message = CBehavior.source("")

  val entry = message.map2(date) { (dateString, messageString) =>
    Entry(dateString, messageString)
  }

  val submit = Event.source[Any]
  val delete = Event.source[Int]

  val submissions: Event[Either[Entry, Int]] =
    entry.sampledBy(submit: Event[Any]).map(Left.apply)
  val deletions: Event[Either[Entry, Int]] =
    (delete: Event[Int]).map(Right.apply)

  val todoList = submissions
    .unionLeft(deletions)
    .fold(Vector.empty[Entry]) { (acc, content) =>
      content match {
        case Left(entry) => entry +: acc
        case Right(idx)  => acc.patch(idx, Nil, 1)
      }
    }
    .toDBehavior

  val test: DBehavior[htmldsl.Tag] =
    todoList.map { entries =>
      div(id := "content")(
        extraHtml.section(
          h1("Todo!"),
          form(
            action := "",
            onsubmit.listen(submit),
            input(tpe := "text",
                  placeholder := "Enter your todo content...",
                  value := "").read(message, _.asInstanceOf[Input].value),
            input(
              tpe := "text",
              attr("xx").empty,
              cls := "datepicker-here",
              placeholder := "Select a date...",
              attr("data-language") := "en",
              attr("data-auto-close") := "true",
              value := ""
            ).read(date, _.asInstanceOf[Input].value),
            button(attr("full").empty, tpe := "submit", "Add")
          ),
          br,
          h2("Entries"),
          Entry.viewSeq(entries, delete)
        )
      )
    }

  val ui = test
    .map { i =>
      println(i)
      i
    }
}
