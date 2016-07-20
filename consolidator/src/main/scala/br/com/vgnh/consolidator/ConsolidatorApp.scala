package br.com.vgnh.consolidator

import br.com.vgnh.consolidator.workflow.UserWorkflow
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream.DStream

/**
  * @author acasimiro
  */
object ConsolidatorApp {

  val cassandraHost = "127.0.0.1"
  val checkpointDirectory: String = "/tmp/spark-checkpoint-dir"

  case class TweetInfo (tweetId:Long, screenName:String, numFollowers:Int, lang:String, hashtag:String, timestamp:Long)

  def main(args : Array[String]) {
    val tweetLogsFolder = args(0)
    val batchInterval = args(1).toInt

    def functionToCreateContext(): StreamingContext = {
      val conf = new SparkConf().setAppName("ConsolidatorApp")
        .set("spark.cassandra.connection.host", cassandraHost)
      val ssc = new StreamingContext(conf, Seconds(batchInterval))

      val textStream:DStream[String] = ssc.fileStream[LongWritable, Text, TextInputFormat](
      tweetLogsFolder, (p:Path) => true, newFilesOnly=false).map(_._2.toString)
      val tweetInfoStream = textStream.map({ t =>
        val l = t.split('|')
        TweetInfo(l(0).toLong, l(1), l(2).toInt, l(3), l(4), l(5).toLong)
      })
      UserWorkflow.configure(tweetInfoStream)
      HashtagWorkflow.configure(tweetInfoStream)

      ssc.checkpoint(checkpointDirectory)
      ssc
    }

    val ssc = StreamingContext.getOrCreate(checkpointDirectory, functionToCreateContext _)
    ssc.start()
    ssc.awaitTermination()
  }

}
