package juliamo.ast

final case class Lambda(val pos: SourcePos, args: Vector[String], body: Block)
