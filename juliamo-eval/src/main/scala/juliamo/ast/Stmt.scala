package juliamo.ast


sealed trait Stmt extends HasPos

final case class Let(override val pos: SourcePos, id: String, expr: Expr) extends Stmt

final case class QualifiedName(qualify: Vector[String], name: String)

final case class Arg(name: String, typeExpr: Expr)

enum Expr extends Stmt:
  case Return(override val pos: SourcePos, expr: Expr) extends Expr
  case Resume(override val pos: SourcePos, expr: Expr) extends Expr
  case Block(override val pos: SourcePos, body: Vector[Stmt]) extends Expr
  case If(override val pos: SourcePos, condition: Expr, whenTrue: Option[Block], whenFalse: Option[Block]) extends Expr
  case Ref(override val pos: SourcePos, name: QualifiedName) extends Expr
  case Call(override val pos: SourcePos, f: Expr, args: Vector[Expr]) extends Expr
  case Lambda(override val pos: SourcePos, args: Vector[Arg], body: Expr.Block) extends Expr
  case Lit(override val pos: SourcePos, x: Literal) extends Expr


enum Literal:
  case Integer(x: BigInt) extends Literal
  case Str(x: String) extends Literal
  case Char(codepoint: Int) extends Literal