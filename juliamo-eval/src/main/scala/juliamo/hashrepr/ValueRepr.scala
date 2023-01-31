package juliamo.hashrepr

sealed trait ValueRepr

final case class RuntimeType(typ: TypeRepr) extends ValueRepr

final case class ArgRepr(id: ReprID, typ: TypeRepr)

final case class Closure(args: Vector[ArgRepr], body: ClosureBody) extends ValueRepr with HasID {

}