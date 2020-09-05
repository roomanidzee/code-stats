package com.romanidze.codestats.parquet

import com.romanidze.codestats.protobuf.Definition.GitHubInfoRecord
import com.typesafe.scalalogging.LazyLogging
import monix.connect.parquet.Parquet
import monix.eval.Task
import monix.reactive.Observable
import org.apache.hadoop.fs.Path
import org.apache.parquet.hadoop.ParquetWriter
import org.apache.parquet.proto.ProtoWriteSupport
import monix.execution.Scheduler.Implicits.global

import scala.annotation.nowarn

object ParquetDataWriter extends LazyLogging {

  def writeAggregatedData(filePath: String, dataForWrite: List[GitHubInfoRecord]): Task[Long] = {

    logger.debug("file path: {}, data size: {}", filePath, dataForWrite.size)

    val writeSupport = new ProtoWriteSupport[GitHubInfoRecord](classOf[GitHubInfoRecord])

    @nowarn
    val writer = new ParquetWriter[GitHubInfoRecord](new Path(filePath), writeSupport)

    Observable
      .fromIterable(dataForWrite)
      .consumeWith(Parquet.writer(writer))

  }

}
