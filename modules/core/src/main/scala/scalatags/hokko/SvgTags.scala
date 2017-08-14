package scalatags
package hokko

import _root_.hokko.core.Engine
import snabbdom.VNode
import org.scalajs.dom

import scalatags.generic.Namespace

trait SvgTags
    extends generic.SvgTags[Builder, Engine => VNode, Engine => VNode]
    with TagFactory {
  implicit lazy val svgNamespaceConfig = Namespace.svgNamespaceConfig

  lazy val altGlyph         = treeTypedTag[dom.svg.Element]("altGlyph")
  lazy val altGlyphDef      = treeTypedTag[dom.svg.Element]("altGlyphDef")
  lazy val altGlyphItem     = treeTypedTag[dom.svg.Element]("altGlyphItem")
  lazy val animate          = treeTypedTag[dom.svg.Element]("animate")
  lazy val animateMotion    = treeTypedTag[dom.svg.Element]("animateMotion")
  lazy val animateTransform = treeTypedTag[dom.svg.Element]("animateTransform")
  lazy val circle           = treeTypedTag[dom.svg.Circle]("circle")
  lazy val clipPath         = treeTypedTag[dom.svg.ClipPath]("clipPath")
  lazy val `color-profile`  = treeTypedTag[dom.svg.Element]("color-profile")
  lazy val cursor           = treeTypedTag[dom.svg.Element]("cursor")
  lazy val defs             = treeTypedTag[dom.svg.Defs]("defs")
  lazy val desc             = treeTypedTag[dom.svg.Desc]("desc")
  lazy val ellipse          = treeTypedTag[dom.svg.Ellipse]("ellipse")
  lazy val feBlend          = treeTypedTag[dom.svg.FEBlend]("feBlend")
  lazy val feColorMatrix    = treeTypedTag[dom.svg.FEColorMatrix]("feColorMatrix")
  lazy val feComponentTransfer =
    treeTypedTag[dom.svg.ComponentTransferFunction]("feComponentTransfer")
  lazy val feComposite = treeTypedTag[dom.svg.FEComposite]("feComposite")
  lazy val feConvolveMatrix =
    treeTypedTag[dom.svg.FEConvolveMatrix]("feConvolveMatrix")
  lazy val feDiffuseLighting =
    treeTypedTag[dom.svg.FEDiffuseLighting]("feDiffuseLighting")
  lazy val feDisplacementMap =
    treeTypedTag[dom.svg.FEDisplacementMap]("feDisplacementMap")
  lazy val feDistantLighting =
    treeTypedTag[dom.svg.FEDistantLight]("feDistantLighting")
  lazy val feFlood = treeTypedTag[dom.svg.FEFlood]("feFlood")
  lazy val feFuncA = treeTypedTag[dom.svg.FEFuncA]("feFuncA")
  lazy val feFuncB = treeTypedTag[dom.svg.FEFuncB]("feFuncB")
  lazy val feFuncG = treeTypedTag[dom.svg.FEFuncG]("feFuncG")
  lazy val feFuncR = treeTypedTag[dom.svg.FEFuncR]("feFuncR")
  lazy val feGaussianBlur =
    treeTypedTag[dom.svg.FEGaussianBlur]("feGaussianBlur")
  lazy val feImage      = treeTypedTag[dom.svg.FEImage]("feImage")
  lazy val feMerge      = treeTypedTag[dom.svg.FEMerge]("feMerge")
  lazy val feMergeNode  = treeTypedTag[dom.svg.FEMergeNode]("feMergeNode")
  lazy val feMorphology = treeTypedTag[dom.svg.FEMorphology]("feMorphology")
  lazy val feOffset     = treeTypedTag[dom.svg.FEOffset]("feOffset")
  lazy val fePointLight = treeTypedTag[dom.svg.FEPointLight]("fePointLight")
  lazy val feSpecularLighting =
    treeTypedTag[dom.svg.FESpecularLighting]("feSpecularLighting")
  lazy val feSpotlight  = treeTypedTag[dom.svg.FESpotLight]("feSpotlight")
  lazy val feTile       = treeTypedTag[dom.svg.FETile]("feTile")
  lazy val feTurbulance = treeTypedTag[dom.svg.FETurbulence]("feTurbulance")
  lazy val filter       = treeTypedTag[dom.svg.Filter]("filter")
  lazy val font         = treeTypedTag[dom.svg.Element]("font")
  lazy val `font-face`  = treeTypedTag[dom.svg.Element]("font-face")
  lazy val `font-face-format` =
    treeTypedTag[dom.svg.Element]("font-face-format")
  lazy val `font-face-name` = treeTypedTag[dom.svg.Element]("font-face-name")
  lazy val `font-face-src`  = treeTypedTag[dom.svg.Element]("font-face-src")
  lazy val `font-face-uri`  = treeTypedTag[dom.svg.Element]("font-face-uri")
  lazy val foreignObject    = treeTypedTag[dom.svg.Element]("foreignObject")
  lazy val g                = treeTypedTag[dom.svg.G]("g")
  lazy val glyph            = treeTypedTag[dom.svg.Element]("glyph")
  lazy val glyphRef         = treeTypedTag[dom.svg.Element]("glyphRef")
  lazy val hkern            = treeTypedTag[dom.svg.Element]("hkern")
  lazy val image            = treeTypedTag[dom.svg.Image]("image")
  lazy val line             = treeTypedTag[dom.svg.Line]("line")
  lazy val linearGradient =
    treeTypedTag[dom.svg.LinearGradient]("linearGradient")
  lazy val marker          = treeTypedTag[dom.svg.Marker]("marker")
  lazy val mask            = treeTypedTag[dom.svg.Mask]("mask")
  lazy val metadata        = treeTypedTag[dom.svg.Metadata]("metadata")
  lazy val `missing-glyph` = treeTypedTag[dom.svg.Element]("missing-glyph")
  lazy val mpath           = treeTypedTag[dom.svg.Element]("mpath")
  lazy val path            = treeTypedTag[dom.svg.Path]("path")
  lazy val pattern         = treeTypedTag[dom.svg.Pattern]("pattern")
  lazy val polygon         = treeTypedTag[dom.svg.Polygon]("polygon")
  lazy val polyline        = treeTypedTag[dom.svg.Polyline]("polyline")
  lazy val radialGradient =
    treeTypedTag[dom.svg.RadialGradient]("radialGradient")
  lazy val rect     = treeTypedTag[dom.svg.RectElement]("rect")
  lazy val set      = treeTypedTag[dom.svg.Element]("set")
  lazy val stop     = treeTypedTag[dom.svg.Stop]("stop")
  lazy val svg      = treeTypedTag[dom.svg.SVG]("svg")
  lazy val switch   = treeTypedTag[dom.svg.Switch]("switch")
  lazy val symbol   = treeTypedTag[dom.svg.Symbol]("symbol")
  lazy val text     = treeTypedTag[dom.svg.Text]("text")
  lazy val textPath = treeTypedTag[dom.svg.TextPath]("textPath")
  lazy val tref     = treeTypedTag[dom.svg.Element]("tref")
  lazy val tspan    = treeTypedTag[dom.svg.TSpan]("tspan")
  lazy val use      = treeTypedTag[dom.svg.Use]("use")
  lazy val view     = treeTypedTag[dom.svg.View]("view")
  lazy val vkern    = treeTypedTag[dom.svg.Element]("vkern")
}
