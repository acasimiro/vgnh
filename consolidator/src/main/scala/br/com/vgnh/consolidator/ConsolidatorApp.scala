package br.com.vgnh.consolidator

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

  val checkpointDirectory: String = "/tmp/spark-checkpoint-dir"
  val tweetLogsFolder: String = "file:///tmp/collector/data"

  def main(args : Array[String]) {
    def functionToCreateContext(): StreamingContext = {
      val conf = new SparkConf().setAppName("ConsolidatorApp")
      val ssc = new StreamingContext(conf, Seconds(3))

      val stream:DStream[String] = ssc.fileStream[LongWritable, Text, TextInputFormat](
        tweetLogsFolder, (p:Path) => true, newFilesOnly=false).map(_._2.toString)
      stream.print()
      ssc.checkpoint(checkpointDirectory)
      ssc
    }
    val ssc = StreamingContext.getOrCreate(checkpointDirectory, functionToCreateContext _)
    ssc.start()
    ssc.awaitTermination()
  }

}
