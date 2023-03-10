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

  final def whnf: CoreExpr = if(whnfCache == null) this else whnfCache.get match {
    case Some(x) if x != null => x
    case _ => {
      val x = whnfImpl
      this.whnfCache = new WeakReference(x)
      if (this == x) this.whnfCache = null
      x.whnfCache = null
      x
    }
  }

  protected def whnfImpl: CoreExpr = ??? // TODO

  final def normalize: CoreExpr = if(normalizeCache == null) this else normalizeCache.get match {
    case Some(x) if x != null => x
    case _ => {
      val x = normalizeImpl
      this.normalizeCache = new WeakReference(x)
      if (this == x) this.normalizeCache = null
      this.whnfCache = this.normalizeCache
      x.normalizeCache = null
      x.whnfCache = x.normalizeCache
      x
    }
  }

  protected def mapImpl(f: CoreExpr => CoreExpr): CoreExpr = ??? // TODO

  final def map(f: CoreExpr => CoreExpr): CoreExpr = {
    val result = mapImpl(f)
    if((this eq result) || this == result) return this
    result
  }

  protected def normalizeImpl: CoreExpr = whnf.map(_.normalize)
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

    override def whnfImpl = load.whnf

    override def normalizeImpl = load.normalize
  }
  final case class Arg(id: CoreID, typeExpr: CoreExpr)
  final case class Closure(args: Vector[Arg], body: Vector[CoreStmt]) extends CoreExpr {
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

sealed trait CoreStmt extends HasPos {
  def subst(subst: Subst): CoreExpr = ???
}

final case class Return(override val pos: SourcePos, expr: CoreExpr) extends CoreStmt
final case class Resume(override val pos: SourcePos, expr: CoreExpr) extends CoreStmt
final case class FunctionCallBind(override val pos: SourcePos) extends CoreStmt // TODO


final case class CoreError() extends CoreExpr // TODO // used in typechecking