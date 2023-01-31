package juliamo.tyck

import scala.collection.mutable
import juliamo.core._

class TyckerState {
  val metas: mutable.HashMap[Nothing, Nothing] = new mutable.HashMap() // TODO
  val loadedModules: Nothing = ??? // TODO

}

type InferResult = Option[(CoreExpr, CoreExpr)] // TODO

final case class StmtWithExpr(stmts: Vector[CoreStmt], expr: CoreExpr)

type CheckResult = Option[StmtWithExpr]

trait Tycker[T] {
  def infer(state: TyckerState, expr: T): InferResult

  def check(state: TyckerState, expr: T, typeExpr: CoreExpr): CheckResult
}