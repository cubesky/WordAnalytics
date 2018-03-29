package party.liyin.wordanalytics;

import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.NIOFSDirectory;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class WordAnalytics implements Closeable {
    private NIOFSDirectory niofsDirectory;
    private IndexWriter writer;
    private HanLPAnalyzer analyzer;
    private ScoreDoc lastScoreDoc;

    public WordAnalytics(String documentIndexDir) throws IOException {
        this(new File(documentIndexDir));
    }

    public WordAnalytics(File documentIndexDir) throws IOException {
        if(!documentIndexDir.exists()){
            documentIndexDir.mkdir();
        } else {
            if(documentIndexDir.isFile()){
                documentIndexDir.delete();
                documentIndexDir.mkdir();
            }
        }
        analyzer = new HanLPAnalyzer();
        niofsDirectory = new NIOFSDirectory(documentIndexDir.toPath());
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        writer = new IndexWriter(niofsDirectory, indexWriterConfig);
    }

    public ArrayList<Document> search(String title, String querystr, int hitsperpage) throws ParseException, IOException {
        Query query = new QueryParser(title, analyzer).parse(querystr);
        try (IndexReader reader = DirectoryReader.open(niofsDirectory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs;
            if (lastScoreDoc == null) {
                docs = searcher.search(query, hitsperpage);
            } else {
                docs = searcher.searchAfter(lastScoreDoc, query, hitsperpage);
            }
            ScoreDoc[] hits = docs.scoreDocs;
            if(hits.length == 0) return new ArrayList<>();
            lastScoreDoc = hits[hits.length - 1];
            return Arrays.stream(hits).map(scoreDoc -> {
                try {
                    return searcher.doc(scoreDoc.doc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toCollection(ArrayList::new));
        }
    }

    public void clearSearch() {
        lastScoreDoc = null;
    }

    public ArrayList<Document> newSearch(String title, String querystr, int hitsperpage) throws IOException, ParseException {
        clearSearch();
        return search(title, querystr, hitsperpage);
    }

    public void addDoc(Document document) throws IOException {
        writer.addDocument(document);
        writer.commit();
    }

    @Override
    public void close() throws IOException {
        writer.close();
        niofsDirectory.close();
    }
}
