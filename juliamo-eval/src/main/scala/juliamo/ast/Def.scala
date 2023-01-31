package juliamo.ast

final case class FieldDef(override val pos: SourcePos, name: String, typeExpr: Expr) extends HasPos

sealed trait Def extends HasPos {
  def name: String
}

final case class Val(override val pos: SourcePos, override val name: String, typeExpr: Option[Expr], expr: Expr) extends Def

final case class Record(override val pos: SourcePos, override val name: String, fields: Vector[FieldDef]) extends Def