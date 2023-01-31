package juliamo.core

import juliamo.ast.{HasPos, Literal, SourcePos}
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

  def normalizeImpl: CoreExpr = ??? // TODO
}
object CoreExpr {
  final case class Lit(x: Literal) extends CoreExpr
  final case class PureFunctionCall() extends CoreExpr // TODO
}

sealed trait CoreType extends CoreExpr {
  def parents: Set[CoreType] = throw new UnsupportedOperationException("TODO")
}

object CoreType {
  enum LitType extends CoreType:
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