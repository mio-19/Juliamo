package juliamo.core

import juliamo.ast.{HasPos, Literal, QualifiedName, SourcePos}
import juliamo.hashrepr.ReprID

import scala.ref.WeakReference

type CoreID = ReprID

final case class Subst() // TODO

sealed trait CoreExpr {
  def subst(subst: Subst): CoreExpr = ???

  private var whnfCache = new WeakReference[CoreExpr](null)

  private var normalizeCache = new WeakReference[CoreExpr](null)

  final def whnf: CoreExpr = whnfCache.get match {
    case Some(x) if x != null => x
    case _ => {
      val x = whnfImpl
      val whnfCache = new WeakReference(x)
      this.whnfCache = whnfCache
      x.whnfCache = whnfCache
      x
    }
  }

  def whnfImpl: CoreExpr = ??? // TODO

  final def normalize: CoreExpr = normalizeCache.get match {
    case Some(x) if x != null => x
    case _ => {
      val x = normalizeImpl
      val normalizeCache = new WeakReference(x)
      this.normalizeCache = normalizeCache
      x.normalizeCache = normalizeCache
      x
    }
  }

  def mapImpl(f: CoreExpr => CoreExpr): CoreExpr = ??? // TODO

  final def map(f: CoreExpr => CoreExpr): CoreExpr = {
    val result = mapImpl(f)
    if((this eq result) || this == result) return this
    result
  }

  def normalizeImpl: CoreExpr = whnf.map(_.normalize)
}

sealed trait NormalForm extends CoreExpr {
  override def whnfImpl = this
  override def normalizeImpl = this
}

final case class AbsoluteModuleName(module: Vector[String], name: String)

object CoreExpr {
  final case class Lit(x: Literal) extends NormalForm
  final case class PureFunctionCall(f: CoreExpr, args: Vector[CoreExpr]) extends CoreExpr
  final case class LocalRef(id: CoreID) extends NormalForm
  final class GlobalRef(val name: AbsoluteModuleName, typeExpr: CoreExpr, thunk: => CoreExpr) extends CoreExpr {
    lazy val load = thunk
  }
}

sealed trait CoreType extends CoreExpr {
  def parents: Set[CoreType] = throw new UnsupportedOperationException("TODO")
}

object CoreType {
  enum LitType extends CoreType with NormalForm:
    case Integer extends LitType
    case String extends LitType
    case Character extends LitType

  case object Effect extends CoreType

  final case class RecordType() extends CoreType // TODO

  final case class UnionType(xs: Set[CoreExpr]) extends CoreType // TODO
}

final case class CoreEffect() extends CoreExpr // TODO

final case class UnionEffect(xs: Set[CoreExpr]) extends CoreExpr

enum CoreStmt extends HasPos:
  case Return(override val pos: SourcePos, expr: CoreExpr) extends CoreStmt
  case Resume(override val pos: SourcePos, expr: CoreExpr) extends CoreStmt
  case FunctionCallBind(override val pos: SourcePos) extends CoreStmt // TODO


final case class CoreError() extends CoreExpr // TODO // used in typechecking