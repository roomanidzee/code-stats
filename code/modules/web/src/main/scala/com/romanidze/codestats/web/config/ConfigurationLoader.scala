package com.romanidze.codestats.web.config

import pureconfig.ConfigSource
import pureconfig.error.ConfigReaderFailures
import pureconfig.generic.auto._

object ConfigurationLoader {

  def load: Either[ConfigReaderFailures, ApplicationConfig] = {

    val configPath = sys.props.get("CONFIG_PATH")

    if (configPath.isDefined) {
      ConfigSource.file(configPath.get).load[ApplicationConfig]
    }

    ConfigSource.default.load[ApplicationConfig]

  }

}
