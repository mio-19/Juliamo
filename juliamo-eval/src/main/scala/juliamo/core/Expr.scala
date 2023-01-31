package juliamo.core

import juliamo.ast.{HasPos, Literal, SourcePos}

enum CoreExpr extends HasPos:
  case Lit(override val pos: SourcePos, x: Literal) extends CoreExpr
  case PureFunctionCall(override val pos: SourcePos) extends CoreExpr // TODO



enum CoreStmt extends HasPos:
  case Return(override val pos: SourcePos, expr: CoreExpr) extends CoreStmt
  case Resume(override val pos: SourcePos, expr: CoreExpr) extends CoreStmt
  case FunctionCallBind(override val pos: SourcePos) extends CoreStmt // TODO