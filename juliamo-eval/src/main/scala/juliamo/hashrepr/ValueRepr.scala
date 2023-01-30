package juliamo.hashrepr

sealed trait ValueRepr

final case class RuntimeType(typ: TypeRepr) extends ValueRepr

final case class Closure(args: Vector[AstID], body: ClosureBody) extends ValueRepr with HasID {

}