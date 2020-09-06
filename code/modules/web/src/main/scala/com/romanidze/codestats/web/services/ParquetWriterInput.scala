package com.romanidze.codestats.web.services

import com.romanidze.codestats.protobuf.Definition.GitHubInfoRecord

case class ParquetWriterInput(username: String, records: List[GitHubInfoRecord])
