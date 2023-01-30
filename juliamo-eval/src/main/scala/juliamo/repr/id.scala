package juliamo.repr

final case class RefID(mostSigBits: Long, leastSigBits: Long)

object RefID {
  def constant(x: String): RefID = throw new UnsupportedOperationException("TODO")
}

trait HasID {
  def id: RefID = throw new UnsupportedOperationException("TODO")
}

final case class Ref[T <: HasID](id: RefID, x: T) {
  if (x.id != id) throw new IllegalArgumentException("id doesn't match")
}

object Ref {
  def apply[T <: HasID](x: T): Ref[T] = Ref(x.id, x)
}
