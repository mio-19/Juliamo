package juliamo.ast

sealed trait Stmt extends HasPos

final case class Return(override val pos: SourcePos, expr: Expr) extends Stmt

final case class Let(override val pos: SourcePos, id: String, expr: Expr) extends Stmt

sealed trait Expr extends Stmt

final case class Block(override val pos: SourcePos, stmts: Vector[Stmt], lastYield: Option[Expr]) extends Expr