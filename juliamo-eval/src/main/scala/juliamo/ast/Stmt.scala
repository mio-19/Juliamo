package juliamo.ast


sealed trait Stmt extends HasPos

final case class Let(override val pos: SourcePos, id: String, expr: Expr) extends Stmt

enum Expr extends Stmt:
  case Return(override val pos: SourcePos, expr: Expr) extends Expr
  case Resume(override val pos: SourcePos, expr: Expr) extends Expr
  case Block(override val pos: SourcePos, body: Vector[Stmt]) extends Expr
  case If(override val pos: SourcePos, condition: Expr, whenTrue: Option[Block], whenFalse: Option[Block])
  case Ref(override val pos: SourcePos, qualify: Vector[String], name: String) extends Expr
  case Call(override val pos: SourcePos, f: Expr, args: Vector[Expr])
