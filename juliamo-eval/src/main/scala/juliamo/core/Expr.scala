package juliamo.core

import juliamo.ast.{HasPos, Literal, SourcePos}

enum CoreExpr extends HasPos:
  case Lit(override val pos: SourcePos, x: Literal) extends CoreExpr