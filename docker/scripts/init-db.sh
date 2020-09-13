#!/bin/bash
set -e

clickhouse-client -n <<-EOSQL
    CREATE DATABASE docker;
    CREATE TABLE docker.parquet_files (
      ghRequestUser String,
      recordCreatedTime Int64,
      name String,
      isStarred UInt8,
      starredAt Int64,
      ownerLogin String,
      ownerType String,
      fork UInt8,
      createdAt Int64,
      updatedAt Int64,
      pushedAt Int64,
      size Int64,
      stargazersCount Int64,
      watchersCount Int64,
      language String,
      hasIssues UInt8,
      hasProjects UInt8,
      hasDownloads UInt8,
      hasWiki UInt8,
      hasPages UInt8,
      forksCount Int64,
      archived UInt8,
      disabled UInt8,
      openIssues Int64
    ) ENGINE = File(Parquet);
EOSQL

search_dir=/opt/parquet_files

for entry in "$search_dir"/*
do
  cat "$entry" | clickhouse-client --query="INSERT INTO docker.parquet_files FORMAT Parquet"
done