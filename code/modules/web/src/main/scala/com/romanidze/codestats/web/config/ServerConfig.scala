package com.romanidze.codestats.web.config

/**
 * Configuration for server launch
 * @param host host for launch
 * @param port port for launch
 * @param prefix REST API prefix
 *
 * @author Andrey Romanov
 */
case class ServerConfig(host: String, port: Int, prefix: String)
