package com.mico.utils.lucene;

import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

/**
 * @author laids on 2017-02-06 for test.
 */
public class UtilsTest {

    @Test
    public void test() throws Exception {
        FSDirectory fsDirectory = LuceneUtils.openFSDirectory("F://luceneDir");
        //写
        IndexWriterConfig writerConfig = new IndexWriterConfig(new HanLPAnalyzer());
        IndexWriter indexWrtier = LuceneUtils.getIndexWrtier(fsDirectory, writerConfig);
        Document doc = new Document();
            /*
            * 参数说明 public Field(String name, String value, FieldType type)
            * name : 字段名称
            * value : 字段的值 store :
            *  TextField.TYPE_STORED:存储字段值
            */
        doc.add(new Field("name", "lin zhengle", TextField.TYPE_STORED));
        doc.add(new Field("address", "中国上海", TextField.TYPE_STORED));
        doc.add(new Field("contents", "I am learning lucene ", TextField.TYPE_STORED));


        LuceneUtils.addIndex(indexWrtier, doc);


        //读
        IndexReader indexReader = LuceneUtils.getIndexReader(fsDirectory);

        IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher(indexReader);

        QueryParser parser = new QueryParser("contents", new HanLPAnalyzer());
        Query query = parser.parse("am");

        Page<Document> page = new Page<Document>(1, 10);
        HighlighterParam highlighterParam = new HighlighterParam(true, "contents", "<font color='red'>", "</font>", 200);
        LuceneUtils.pageQuery(indexSearcher, fsDirectory, query, page, highlighterParam, writerConfig);

        LuceneUtils.closeIndexWriter(indexWrtier);
        LuceneUtils.closeIndexReader(indexReader);
        LuceneUtils.closeDirectory(fsDirectory);

        for (Document document : page.getItems()) {

            System.out.println(document.getField("contents").stringValue());
        }


    }
}
