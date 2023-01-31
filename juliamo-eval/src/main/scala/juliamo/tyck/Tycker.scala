package juliamo.tyck

class TyckerState {
  val metas: mutable.HashMap[Nothing, Nothing] = new mutable.HashMap() // TODO

}

type InferResult = Option[(CoreExpr, CoreExpr)] // TODO

type CheckResult = Option[CoreExpr]

trait Tycker[T] {
  def infer(state: TyckerState, expr: T): InferResult

  def check(state: TyckerState, expr: T, typeExpr: CoreExpr): CheckResult
}