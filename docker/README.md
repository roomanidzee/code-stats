# Clickhouse Docker Setup

## What is this?
Clickhouse DB , which used for analysing extracted data from Parquet files

## How to launch?

* Merge all your parquet files into ```final.parquet``` with ```parquet-tools``` jar, and place it into ```docker/parquet_files```
* Run ```docker-compose up -d``` for ClickHouse launch
* Run ```docker run -it --rm --network docker_default --link some-clickhouse-server:clickhouse-server yandex/clickhouse-client:20.6.6.7 --host clickhouse-server``` command for connecting with native client interface. With that command you can interact with data in container.

## What is the scheme of data in Apache Parquet format files?

Protobuf schema, which listed below:

```
message GitHubInfoRecord {
  required binary ghRequestUser (STRING) = 1;
  required int64 recordCreatedTime = 2;
  required binary name (STRING) = 3;
  required boolean isStarred = 4;
  required int64 starredAt = 5;
  required binary ownerLogin (STRING) = 6;
  required binary ownerType (STRING) = 7;
  required boolean fork = 8;
  required int64 createdAt = 9;
  required int64 updatedAt = 10;
  required int64 pushedAt = 11;
  required int64 size = 12;
  required int64 stargazersCount = 13;
  required int64 watchersCount = 14;
  required binary language (STRING) = 15;
  required boolean hasIssues = 16;
  required boolean hasProjects = 17;
  required boolean hasDownloads = 18;
  required boolean hasWiki = 19;
  required boolean hasPages = 20;
  required int64 forksCount = 21;
  required boolean archived = 22;
  required boolean disabled = 23;
  required int64 openIssues = 24;
}
```


