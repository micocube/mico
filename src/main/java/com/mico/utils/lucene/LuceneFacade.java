package com.mico.utils.lucene;

import com.hankcs.hanlp.HanLP;
import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laids on 2017-02-06 for test.
 */
public class LuceneFacade {
    /**
     * 存储文档
     *
     * @param fsdDir 索引存储位置
     * @param docs   文档
     */
    public static void storeDoc(String fsdDir, List<Document> docs) {
        try {
            FSDirectory fsDirectory = LuceneUtils.openFSDirectory(fsdDir);
            //写
            IndexWriterConfig writerConfig = new IndexWriterConfig(new HanLPAnalyzer());
            IndexWriter indexWrtier = LuceneUtils.getIndexWrtier(fsDirectory, writerConfig);
            for (Document doc : docs) {
                LuceneUtils.addIndex(indexWrtier, doc);
            }
            LuceneUtils.closeIndexWriter(indexWrtier);
            LuceneUtils.closeDirectory(fsDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Page<Document> queryPage(String fsdDir, String field, String keyword) {

        Page<Document> page = new Page<Document>(1, 10);
        try {
            FSDirectory fsDirectory = LuceneUtils.openFSDirectory(fsdDir);
            IndexReader indexReader = LuceneUtils.getIndexReader(fsDirectory);

            IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher(indexReader);

            QueryParser parser = new QueryParser(field, new HanLPAnalyzer());
            Query query = parser.parse(keyword);
            Query query1 = new FuzzyQuery(new Term(field,keyword));

            IndexWriterConfig writerConfig = new IndexWriterConfig(LuceneUtils.getDefaultAnalyzer());
            HighlighterParam highlighterParam = new HighlighterParam(true, field, "<font color='#00CCFF'>", "</font>", 200);
            LuceneUtils.pageQuery(indexSearcher, fsDirectory, query, page, highlighterParam, writerConfig);

            LuceneUtils.closeIndexReader(indexReader);
            LuceneUtils.closeDirectory(fsDirectory);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    @Test
    public void test() throws Exception{
        final Document doc = new Document();



            /*
            * 参数说明 public Field(String name, String value, FieldType type)
            *
            * name : 字段名称
            * value : 字段的值 store :
            *  TextField.TYPE_STORED:存储字段值
            */
//        doc.add(new Field("name", "lin zhengle", TextField.TYPE_STORED));
//        doc.add(new Field("address", "中国上海", TextField.TYPE_STORED));
//        doc.add(new Field("contents", "I am learning lucene", TextField.TYPE_STORED));
//        List<Document> documents = new ArrayList<Document>() {{
//            add(doc);
//        }};
//        LuceneFacade.storeDoc("F://luceneDir", documents);

        System.out.println(HanLP.segment("I am learning lucene "));
        Page<Document> documentPage = LuceneFacade.queryPage("F://luceneDir", "contents", "learning");
        for (Document document : documentPage.getItems()) {

            System.out.println(document.getField("contents").stringValue());
        }
    }

}
