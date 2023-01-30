package juliamo.repr

import juliamo.utils.Parameter

final case class RefID(mostSigBits: Long, leastSigBits: Long)

object RefID {
  def constant(x: String): RefID = throw new UnsupportedOperationException("TODO")

  val OnRec: RefID = RefID(0, 0)
}

private val RecIDCatcher = new Parameter[Set[HasID]]

trait HasID {
  protected def idImpl: RefID = throw new UnsupportedOperationException("TODO")

  private var idCache: RefID = null

  private def calculateId: RefID = RecIDCatcher.get match {
    case Some(history) if history.contains(this) => RefID.OnRec
    case _ => RecIDCatcher.callWithOrUpdate(Set(this), _.incl(this)) {
      idImpl
    }
  }

  def id: RefID = if (idCache != null) idCache else {
    val result = calculateId
    idCache = result
    result
  }
}

final case class Ref[T <: HasID](id: RefID, x: T) {
  if (x.id != id) throw new IllegalArgumentException("id doesn't match")
}

object Ref {
  def apply[T <: HasID](x: T): Ref[T] = Ref(x.id, x)
}

