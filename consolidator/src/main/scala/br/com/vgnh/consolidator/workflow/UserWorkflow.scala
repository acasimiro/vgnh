package br.com.vgnh.consolidator.workflow

import br.com.vgnh.consolidator.ConsolidatorApp.TweetInfo
import org.apache.spark.streaming.dstream.DStream
import com.datastax.spark.connector.streaming._


/**
  * Created by casimiro on 17/07/16.
  */
package object UserWorkflow {

  case class User (dummy_id:Int, screen_name:String, num_followers:Int)

  def configure (stream: DStream[TweetInfo]): Unit = {
    val userStream = stream.map(ti => (ti.screenName, ti.numFollowers))
    val uniqueUserStream = userStream.reduceByKey((nf1:Int, nf2:Int) => nf1)
    val userTableStream = uniqueUserStream.map({tup => User(1, tup._1, tup._2)})
    userTableStream.saveToCassandra("vgnh", "user")
  }

}
