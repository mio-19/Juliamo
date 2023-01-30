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

  def id: RefID = RecIDCatcher.get match {
    case Some(history) if history.contains(this) => RefID.OnRec
    case _ => RecIDCatcher.callWithOrUpdate(Set(this), _.incl(this)) {
      idImpl
    }
  }
}

