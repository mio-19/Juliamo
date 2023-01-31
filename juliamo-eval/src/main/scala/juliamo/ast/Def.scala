package juliamo.ast

final case class FieldDef(override val pos: SourcePos, name: String, typeName: QualifiedName) extends HasPos

enum Def extends HasPos:
  case Val(override val pos: SourcePos,name: String, expr: Expr) extends Def
  case Record(override val pos: SourcePos, fields: Vector[FieldDef]) extends Def