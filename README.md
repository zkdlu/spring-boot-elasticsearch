# Elasticsearch
- 분산 검색엔진


# ELK 스택

## 1. Logstash
- 데이터 수집, 로그 파싱 엔진
## 2. Elasticsearch
- 검색엔진
## 3. Kibana
- 분석 및 시각화 플랫폼


# RDBMS와 용어 비교
|RDBMS |ElasticSearch|
|-------------|------|
|Database|Index|
|Table|Type|
|Row|Document|
|Column|Field|
|Index|Analyze|
|Primary key|_id|
|Schema|Mapping|
|Physical partition|Shard|
|Logical partition|Route|
|Relational|Parent/Chille, Nested|
|SQL|Query DSL|


# 도커 설치
```sh
$ docker pull docker.elastic.co/elasticsearch/elasticsearch:7.6.2

$ docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.6.2
```

# 테스트
- 9300: 전송포트, 9200: http포트

### 1. 생성 (http://host:9200/{index}/{type}/{id}) - {id}를 입력하지 않으면 랜덤한 문자열을 id로 생성 됨
- Request 
  ```http
  POST http://localhost:9200/database/user/1
  {
    "name": "zkdlu",
    "age": 25
  }
  ```
- Response
  ```json
  {
    "_index": "database",
    "_type": "user",
    "_id": "5",
    "_version": 1,
    "result": "created",
    "_shards": {
        "total": 2,
        "successful": 1,
        "failed": 0
    },
    "_seq_no": 5,
    "_primary_term": 1
  }
  ```

### 2. 수정 (http://host:9200/{index}/{type}/{id}
- Request 
  ```http
  PUT http://localhost:9200/database/user/1
  {
    "name": "zkdlu",
    "age": 24
  }
  ```
 - Response
   ```json
   {
      "_index": "database",
      "_type": "user",
      "_id": "1",
      "_version": 3,
      "result": "updated",
      "_shards": {
          "total": 2,
          "successful": 1,
          "failed": 0
      },
      "_seq_no": 6,
      "_primary_term": 1
   }
   ```
   
### 3. 조회 (http://host:9200/{index}/{type}/{id}
- Request 
  ```http
  GET http://localhost:9200/database/user/1
  ```
 - Response
   ```json
    {
      "_index": "database",
      "_type": "user",
      "_id": "1",
      "_version": 3,
      "_seq_no": 6,
      "_primary_term": 1,
      "found": true,
      "_source": {
          "name": "zkdlu",
          "age": 24
      }
    }
   ```
   
### 4. 삭제 (http://host:9200/{index}/{type}/{id}
- Request 
  ```http
  DELETE http://localhost:9200/database/user/1
  ```
 - Response
   ```json
    {
      "_index": "database",
      "_type": "user",
      "_id": "1",
      "_version": 4,
      "result": "deleted",
      "_shards": {
          "total": 2,
          "successful": 1,
          "failed": 0
      },
      "_seq_no": 7,
      "_primary_term": 1
    }    
  
    
### 4. 전체 조회 (http://host:9200/{index}/{type}/{id}/_search
- Request 
  ```http
  GET http://localhost:9200/database/user/_search
  ```
 - Response
   ```json
    {
        "took": 945,
        "timed_out": false,
        "_shards": {
            "total": 1,
            "successful": 1,
            "skipped": 0,
            "failed": 0
        },
        "hits": {
            "total": {
                "value": 4,
                "relation": "eq"
            },
            "max_score": 1.0,
            "hits": [
                {
                    "_index": "database",
                    "_type": "user",
                    "_id": "2",
                    "_score": 1.0,
                    "_source": {
                        "name": "zkdlu2",
                        "age": 25
                    }
                },
                ... 생략
   ```

### 4-1. 전체 조건 조회 (http://host:9200/{index}/{type}/{id}/_search?q={key}:{value}
- Request 
  ```http
  GET localhost:9200/database/user/_search?q=name:zkdlu
  ```
 - Response
   ```json
     {
        "took": 2,
        "timed_out": false,
        "_shards": {
            "total": 1,
            "successful": 1,
            "skipped": 0,
            "failed": 0
        },
        "hits": {
            "total": {
                "value": 1,
                "relation": "eq"
            },
            "max_score": 1.0296195,
            "hits": [
                {
                    "_index": "database",
                    "_type": "user",
                    "_id": "1",
                    "_score": 1.0296195,
                    "_source": {
                        "name": "zkdlu",
                        "age": 25
                    }
                }
            ]
        }
    }
   ```


# Version

|Elasticsearch |Spring boot|
|-------------|------|
|7.6.2|2.3.x|
