package fpmentorship.applicative.fixed

import cats.Monad
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

/**
 * definicion generica de un repositorio: cada implementacion decidira con que monadas concretas se
 * interactua para Read, Update y log
 * @tparam T1 Monada para obtener un registro
 */
abstract class Repository[T1[_] : Monad] {
  def get(id: Int): T1[Entity]
  def persist(e: Entity): T1[Entity]
  def log(s: String): T1[Unit]
}

case class Entity(id:Int)

/**
 * Aqui una posible implementacion:
 * obtener puede dar que el elemento buscado esta, o no => Optional
 * persistir va a tardar => future
 * logar puede ser que falle al escribir => Try
 */
object Repository1 extends Repository[Future] {

  override def get(id: Int): Future[Entity] = Monad[Future].pure(Entity(id))

  override def persist(e: Entity): Future[Entity] = Monad[Future].pure(e)

  override def log(s: String): Future[Unit] = Monad[Future].pure(Unit)
}
