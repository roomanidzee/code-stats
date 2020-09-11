package com.romanidze.codestats.web.config

/**
 * Configuration for GitHub client
 * @param baseURL base GitHub REST API url
 * @param requestUsername GitHub username for data interaction
 *
 * @author Andrey Romanov
 */
case class ClientConfig(baseURL: String, requestUsername: String)
