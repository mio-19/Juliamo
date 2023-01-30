package juliamo.hashrepr

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

  def id: RefID = if (idCache != null) idCache else RecIDCatcher.get match {
    case Some(history) if history.contains(this) => RefID.OnRec
    case _ => {
      val result = RecIDCatcher.callWithOrUpdate(Set(this), _.incl(this)) {
        idImpl
      }
      if (idCache != null) throw new IllegalStateException("Unexpected State")
      idCache = result
      result
    }
  }
}

