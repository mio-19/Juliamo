package juliamo.repr

import scala.collection.immutable

sealed trait TypeRepr extends HasID {
  def parents: Set[TypeRepr] = Set(AnyT)
}

case object IntT extends TypeRepr {
  override def id = RefID.constant("Type Integer")
}

case object CharT extends TypeRepr {
  override def id = RefID.constant("Type Unicode Character")
}

case object StringT extends TypeRepr {
  override def id = RefID.constant("Type String")
}

type ModuleName = Vector[String]

object ModuleName {
  val builtin: ModuleName = Vector()
}

final case class RecordName(module: ModuleName, name: String)

final class Record(val name: RecordName, fieldsDelay: =>immutable.HashMap[String, TypeRepr], parentsOrAny: Set[TypeRepr] = null) extends TypeRepr {
  lazy val fields: immutable.HashMap[String, TypeRepr] = fieldsDelay
  override def parents = if (parentsOrAny == null) AnyT.parents else parentsOrAny
}

object Record {
  def builtin(name: String, fields: immutable.HashMap[String, TypeRepr]): Record = new Record(RecordName(ModuleName.builtin, name), fields)
}

val UnitT = Record.builtin("Unit", immutable.HashMap())

val AnyT = Record.builtin("Any", immutable.HashMap())


case object FunctionT extends TypeRepr {
  override def id = RefID.constant("Type Function")
}