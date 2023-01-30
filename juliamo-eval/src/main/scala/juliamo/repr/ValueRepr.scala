package juliamo.repr

sealed trait ValueRepr

final case class RuntimeType(typ: TypeRepr) extends ValueRepr

final case class Closure(args: Vector[String], body: ClosureBody) extends ValueRepr with HasID {

}