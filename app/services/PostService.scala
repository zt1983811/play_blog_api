package services

import model.{Post, Posts}
import scala.concurrent.Future 

object PostService {
  
  def addPost(post: Post): Future[String] = {
    Posts.add(post)
  }

  def deletePost(id: Long): Future[Int] = {
    Posts.delete(id)
  }

  def getPost(id: Long): Future[Option[Post]] = {
    Posts.get(id)
  }

  def getAllPosts: Future[Seq[Post]] = {
    Posts.listAll
  }
  
  def updatePost(post: Post) : Future[Int] = {
    Posts.update(post)
  }
  
}