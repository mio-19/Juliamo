package juliamo.core

import juliamo.ast.{HasPos, Literal, SourcePos}
import juliamo.hashrepr.ReprID

type CoreID = ReprID

sealed trait CoreExpr
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