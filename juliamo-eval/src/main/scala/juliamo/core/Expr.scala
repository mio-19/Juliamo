package juliamo.core

import juliamo.ast.{HasPos, Literal, SourcePos}

enum CoreExpr extends HasPos:
  case Lit(override val pos: SourcePos, x: Literal) extends CoreExpr



enum CoreStmt extends HasPos:
  case Return(override val pos: SourcePos, expr: CoreExpr) extends CoreStmt
  case Resume(override val pos: SourcePos, expr: CoreExpr) extends CoreStmt