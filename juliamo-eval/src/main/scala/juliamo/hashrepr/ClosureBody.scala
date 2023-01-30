package juliamo.hashrepr


// String: made by people
// Long: made by compiler
type AstID = String | Long

type ClosureBody = EffectRepr


sealed trait EffectRepr

final case class Return(expr: ASTRepr) extends EffectRepr

final case class Resume(expr: ASTRepr) extends EffectRepr

final case class Bind(x: ASTRepr, id: Option[AstID], more: EffectRepr) extends EffectRepr

final class FunctionCallBind(f: =>Closure, args: Vector[ASTRepr], id: Option[AstID], more: EffectRepr) extends ASTRepr

final class ControlCallBind(control: Control, args: Vector[ASTRepr], id: Option[AstID], more: EffectRepr) extends ASTRepr

sealed trait ASTRepr

final case class Var(id: AstID) extends ASTRepr


// TODO
final case class Control()
