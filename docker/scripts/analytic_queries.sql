SELECT 
DISTINCT(language) AS language, COUNT(language) AS repo_count 
FROM docker.parquet_files 
GROUP BY language 
ORDER BY repo_count 
ASC LIMIT 10;

SELECT DISTINCT(starredAt) FROM docker.parquet_files WHERE isStarred=1;

SELECT DISTINCT (name) FROM docker.parquet_files;