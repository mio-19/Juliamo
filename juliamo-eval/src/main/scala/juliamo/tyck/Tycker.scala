package juliamo.tyck

import juliamo.ast.{Def, Expr}

import scala.collection.mutable
import juliamo.core.*

class TyckerState {
  val metas: mutable.HashMap[Nothing, Nothing] = new mutable.HashMap() // TODO
  val loadedModules: Nothing = ??? // TODO

}

sealed trait InferResult

case class InferSuccess(expr: CoreExpr, typeExpr: CoreExpr) extends InferResult

case class InferFailure(reason: String) extends InferResult

final case class StmtWithExpr(stmts: Vector[CoreStmt], expr: CoreExpr)

type CheckResult = Option[StmtWithExpr]

trait Tycker[T <: Expr] {
  def infer(state: TyckerState, expr: T): InferResult

  def check(state: TyckerState, expr: T, typeExpr: CoreExpr): CheckResult
}

trait DefTycker[T <: Def] {
  // TODO
}