package br.com.vgnh.consolidator

import br.com.vgnh.consolidator.ConsolidatorApp.TweetInfo
import com.datastax.spark.connector.streaming._
import org.apache.spark.streaming.dstream.DStream


/**
  * Created by casimiro on 17/07/16.
  */
package object HashtagWorkflow {

  case class Hashtag (lang:String, hashtag:String, hour:Long, rand:Int, num:Long)

  def configure (stream: DStream[TweetInfo]): Unit = {
    val hashtagStream = stream.map({ ti =>
      val hour = ti.timestamp - (ti.timestamp % 3600000)
      new Tuple2((ti.lang, ti.hashtag, hour), 1)
    })
    val hashtagCountStream = hashtagStream.reduceByKey((v1:Int, v2:Int) => v1+v2)
    val hashtagCountTableStream = hashtagCountStream.map({x =>
      val r = scala.util.Random
      Hashtag(x._1._1, x._1._2, x._1._3, r.nextInt(), x._2)
    })
    hashtagCountTableStream.saveToCassandra("vgnh", "hashtag_count")
  }

}
