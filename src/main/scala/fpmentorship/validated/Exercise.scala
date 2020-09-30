package fpmentorship.validated

import cats.data.ValidatedNec
import cats.data.Validated.{Invalid, Valid}
import cats.implicits._
import scala.concurrent.Future


abstract class ValidationError
final case class InvalidSolverRequestId(id: String) extends ValidationError
final case class InvalidSolverData(data: String) extends ValidationError

object Validation {
  type Validation[T] = ValidatedNec[ValidationError, T]
}
import Validation._
object SolverRequestId {
  def safe(id: String): Validation[SolverRequestId] =
    if (id.nonEmpty) {
      Valid(SolverRequestId(id))
    } else {
      Invalid(InvalidSolverRequestId(id)).toValidatedNec
    }
  def unsafe(id: String): SolverRequestId = SolverRequestId(id)
}
//TODO: protect from construction
final case class SolverRequestId(id: String) extends AnyVal
object SolverData {
  def fromString(data: String): Validation[SolverData] = if (data.nonEmpty) {
    Valid(SolverData(data))
  } else {
    Invalid(InvalidSolverData(data)).toValidatedNec
  }
}
final case class SolverData(data: String) extends AnyVal
final case class SolverRequest(id: String, data: String)
class SolverExecutor {
  def run(id: SolverRequestId, data: SolverData): Future[Unit] = Future.unit
}
class RequestHandler(solverExecutor: SolverExecutor) {
  def handle(request: SolverRequest): Validation[Future[Unit]] =
    (SolverRequestId.safe(request.id), SolverData.fromString(request.data)).mapN(solverExecutor.run)
}
object Main {
  def main(args: Array[String]): Unit = {
    val result: Validation[Future[Unit]] = new RequestHandler(new SolverExecutor()).handle(SolverRequest("", ""))
    //      val result: Validation[Future[Unit]] = new RequestHandler(new SolverExecutor()).handle(SolverRequest("validId", ""))
    println(result)
  }
}