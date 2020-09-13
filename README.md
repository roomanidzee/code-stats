# code-stats
![build](https://github.com/roomanidzee/code-stats/workflows/build/badge.svg?branch=master)

## What is it?
Project for data scraping and analyse from GitHub API.

## Structure explanation

```code``` - directory for code, which extracts required data from GitHub REST API

```notebooks``` - visual explanation of data

## How to run data extracting ?

 * Go to folder on path ```modules/web/src/main/resources```
 * Edit ```application.conf``` with your required data
 * Go to root of code directory
 * Run ```sbt "clean; runMain com.romanidze.codestats.web.app.Launcher"``` 
