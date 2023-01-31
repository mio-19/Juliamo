package juliamo.ast

import juliamo.utils.prim

sealed trait Stmt extends HasPos

final case class Let(override val pos: SourcePos, id: String, expr: Expr) extends Stmt

final case class QualifiedName(qualify: Vector[String], name: String)

final case class Arg(name: String, typeExpr: Expr)

sealed trait Expr extends Stmt
object Expr {
  final case class Return(override val pos: SourcePos, expr: Expr) extends Expr
  final case class Resume(override val pos: SourcePos, expr: Expr) extends Expr
  final case class Block(override val pos: SourcePos, body: Vector[Stmt]) extends Expr
  final case class If(override val pos: SourcePos, condition: Expr, whenTrue: Option[Block], whenFalse: Option[Block]) extends Expr
  final case class Ref(override val pos: SourcePos, name: QualifiedName) extends Expr
  final case class Call(override val pos: SourcePos, f: Expr, args: Vector[Expr]) extends Expr
  final case class Lambda(override val pos: SourcePos, args: Vector[Arg], body: Expr.Block) extends Expr
  final case class Lit(override val pos: SourcePos, x: Literal) extends Expr
}


sealed trait Universe extends Expr

enum Literal:
  case Integer(x: prim.Integer) extends Literal
  case Str(x: prim.Str) extends Literal
  case Character(x: prim.Character) extends Literal