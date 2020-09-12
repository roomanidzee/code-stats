package com.romanidze.codestats.web.config

/**
 * Configuration for GitHub client
 * @param base base GitHub REST API url
 * @param origin GitHub username for data interaction
 * @param page GitHub data page index
 * @param limit GitHub data limit value
 *
 * @author Andrey Romanov
 */
case class ClientConfig(base: String, origin: String, page: Short, limit: Short)
