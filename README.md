# SameSentence

Author: CubeSky

A Full text index and search library base on HanLP and Lucene.

## Install

You can use `Cube Repo` at [https://cubesky-mvn.github.io/](https://cubesky-mvn.github.io/)

## API

### Create Index Directroy
```java
WordAnalytics analytics = new WordAnalytics(new File("datadirectory"));
```

### Add Documents
```java
Document document = new Document();
document.add(new TextField("title", "测试", Field.Store.NO));
document.add(new TextField("content", "我觉得这个可以有", Field.Store.YES));
analytics.addDoc(document1);
```

### Search
#### New Search
```java
ArrayList<Document> searched = analytics.newSearch("content", "这个", 10);
```

#### Next Search
```java
ArrayList<Document> searched = analytics.search("content", "这个", 10);
```

#### Clear Pervious Search Cache
```java
analytics.clearSearch()
```

### Close
```java
analytics.close()
```


## Dependency

| Name   | Description                                                                                               | Package Name        |
|:--     |:--                                                                                                        |:--                  |
| HanLP  | 自然语言处理 中文分词 词性标注 命名实体识别 依存句法分析 关键词提取 新词发现 短语提取 自动摘要 文本分类 拼音简繁  | `com.hankcs.hanlp`  |
| Lucene | The Apache Lucene project develops open-source search software                                             | `org.apache.lucene` |

## License

This library is under Apache License 2.0.