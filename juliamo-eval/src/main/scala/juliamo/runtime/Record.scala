package juliamo.runtime

sealed trait RuntimeType extends HasID

final case class Record(module: String, name: String, fields: Vector[Ref[RuntimeType]]) extends RuntimeType
