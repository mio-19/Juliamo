package juliamo.ast

import java.nio.file.Path

final case class LineColum(line: Long, column: Long)

final case class SourcePos(file: Path, start: LineColum, end: LineColum)

trait HasPos {
  def pos: SourcePos
}