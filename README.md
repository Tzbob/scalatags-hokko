# scalatags-vdom

This project is a [Scalatags](https://github.com/lihaoyi/scalatags/) backend for [virtual-dom](https://github.com/Matt-Esch/virtual-dom).

It includes a barebones Scala.js mapping to the virtual-dom project in ```scalatags.vdom.raw.VirtualDom```. 

## Usage

It's usage is identical to the other Scalatags backends:

```scala
// import scalatags.Text.all._
// OR
// import scalatags.JsDom.all._
// OR
// import scalatags.VDom.all._
html(
  head(
    script(src:="..."),
    script(
      "alert('Hello World')"
    )
  ),
  body(
    div(
      h1(id:="title", "This is a title"),
      p("This is a big paragraph of text")
    )
  )
)
```
