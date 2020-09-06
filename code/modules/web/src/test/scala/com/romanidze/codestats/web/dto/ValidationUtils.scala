package com.romanidze.codestats.web.dto

import tethys._
import tethys.jackson._
import tethys.readers.ReaderError

import scala.io.{BufferedSource, Source}

object ValidationUtils extends munit.Assertions {

  def getDecodedObject[A: JsonReader](filePath: String): A = {

    val fileData: BufferedSource = Source.fromResource(filePath)
    val fileString: String = fileData.mkString
    fileData.close()

    val fileObj: Either[ReaderError, A] = fileString.jsonAs[A]

    assume(fileObj.isRight)

    fileObj.toOption.get

  }

  def validateDecode[A: JsonReader](expectedObj: A, filePath: String): Unit = {

    val fileObj: A = getDecodedObject(filePath)
    assertEquals(fileObj, expectedObj)

  }

}
