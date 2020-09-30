package fpmentorship.applicative.fixed

import fpmentorship.applicative.fixed.Repository1

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class Main {

  private val repo:Repository[Future] = Repository1

  implicit val ec: ExecutionContext = ExecutionContext.global

  val receta2: Future[Unit] = for {
    e <-  repo.get(1)
    result <- repo.persist(e)
    _ <- repo.log(result.toString)
  } yield ()
}
//TODO: flatmap that shit
object Main extends App {
  implicit val ec: ExecutionContext = ExecutionContext.global
  val a = new Main().receta2
  a.onComplete {
    case Failure(exception) => println(exception.getMessage)
    case Success(value)     => println(value)
  }
  Await.ready(a, Duration.Inf)
}