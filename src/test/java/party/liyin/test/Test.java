package party.liyin.test;

import com.hankcs.hanlp.HanLP;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.queryparser.classic.ParseException;
import party.liyin.wordanalytics.WordAnalytics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
    @org.junit.Test
    public void runTest() throws IOException, ParseException {
        WordAnalytics analytics = new WordAnalytics(new File("datatest"));
        Document document1 = new Document();
        document1.add(new TextField("title", "队长说：这个老汉奸杀了我们两个兄弟", Field.Store.YES));
        document1.add(new TextField("source", "Telegram", Field.Store.YES));
        analytics.addDoc(document1);
        Document document2 = new Document();
        document2.add(new TextField("title", "很高兴认识你", Field.Store.YES));
        document2.add(new TextField("source", "Telegram", Field.Store.YES));
        analytics.addDoc(document2);
        Document document3 = new Document();
        document3.add(new TextField("title", "我觉得这个可以有", Field.Store.YES));
        document3.add(new TextField("source", "Telegram", Field.Store.YES));
        analytics.addDoc(document3);
        Document document4 = new Document();
        document4.add(new TextField("title", "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", Field.Store.YES));
        document4.add(new TextField("source", "Telegram", Field.Store.YES));
        analytics.addDoc(document4);
        Document document5 = new Document();
        document5.add(new TextField("title", "今天是2018年3月23日，我们明天出去玩好不好", Field.Store.YES));
        document5.add(new TextField("source", "Telegram", Field.Store.YES));
        analytics.addDoc(document5);
        Document document6 = new Document();
        document6.add(new TextField("title", "SQLite 默认的 fts4 全文索引用咱的单字切分效率不错呀", Field.Store.YES));
        document6.add(new TextField("source", "Telegram", Field.Store.YES));
        analytics.addDoc(document6);
        System.out.println("=============== Learn ===============");
        System.out.println(document1.getField("title"));
        System.out.println(document2.getField("title"));
        System.out.println(document3.getField("title"));
        System.out.println(document4.getField("title"));
        System.out.println(document5.getField("title"));
        System.out.println(document6.getField("title"));
        System.out.println("\n=============== Search for 这个可以 ===============");
        ArrayList<Document> searched = analytics.newSearch("title", "这个可以", 10);
        searched.forEach(o -> {
            System.out.println(o.getField("title").stringValue());
        });
    }
    @org.junit.Test
    public void seg(){
        System.out.println(HanLP.segment("SQLite 默认的 fts4 全文索引用咱的单字切分效率不错呀"));
    }
}
