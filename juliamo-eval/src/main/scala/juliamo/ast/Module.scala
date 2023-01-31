package juliamo.ast

final case class Module(override val pos: SourcePos, name: QualifiedName, defs: Vector[Def]) extends HasPos