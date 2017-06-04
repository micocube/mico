package com.mico.test;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author laids
 *         poi 工具类
 *         1.word 转为html
 */
public class PoiUtils {
    private static Logger log = Logger.getLogger("PoiUtils");

    /**
     * 测试方法
     *
     * @param args
     */
    @Test
    public void test() throws Exception{
        String path = "F://";
        String file = "j.docx";
        convertExcel2Html("F://doc/table.xls","F://doc/table.html");
        boolean convert = wordToHtml("F://doc/j.doc", "F://doc/j.html", "F://doc/jimg");
    }

    /**
     * 文档转换工具类 将word 转为html
     * 注意有些并非直接在本地生成的文档比如从智联上下载的简历，
     * 可能转换失败，可以尝试用Office将文档转为Word 97-2003或者 Word 2007.
     *
     * @param docPath 文档路径
     * @param target  html路径
     * @param imgPath 图片保存文件夹
     * @return 转换是否成功
     */
    public static boolean wordToHtml(String docPath, String target, final String imgPath) {
        long startTime = System.currentTimeMillis();
        File imgDir = new File(imgPath);
        if (!imgDir.exists()) {
            imgDir.mkdirs();
        } else {
            if (!imgDir.isDirectory()) {
                log.info("图片保存路径错误！");
                return false;
            }
        }
        try {
            InputStream input = new FileInputStream(docPath);
            InputStream bufferedIn = new BufferedInputStream(input);
            HWPFDocument wordDocument = null;
            boolean b = POIFSFileSystem.hasPOIFSHeader(bufferedIn);
            //doc 是OLED 文档  即97-2003文档 使用HWPFDocument
            if (docPath.endsWith("doc")) {
                wordDocument = new HWPFDocument(bufferedIn);
                WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
                wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                    public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                        return imgPath + File.separator + suggestedName;
                    }
                });
                wordToHtmlConverter.processDocument(wordDocument);
                List pics = wordDocument.getPicturesTable().getAllPictures();
                if (pics != null) {
                    for (int i = 0; i < pics.size(); i++) {
                        Picture pic = (Picture) pics.get(i);
                        try {
                            String str = pic.suggestFullFileName();
                            pic.writeImageContent(new FileOutputStream(imgPath + File.separator + str));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Document htmlDocument = wordToHtmlConverter.getDocument();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                DOMSource domSource = new DOMSource(htmlDocument);
                StreamResult streamResult = new StreamResult(outStream);

                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer serializer = tf.newTransformer();
                serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
                serializer.setOutputProperty(OutputKeys.METHOD, "html");
                serializer.transform(domSource, streamResult);
                outStream.close();

                String content = new String(outStream.toByteArray());

                writeFile(new File(target), content, "utf-8");
            } else if (docPath.endsWith("docx")) {
                //docx 是xml规范的word文档 使用XWPFDocument
                OPCPackage opcPackage = POIXMLDocument.openPackage(docPath);
                XWPFDocument document = new XWPFDocument(opcPackage);


                XHTMLOptions options = XHTMLOptions.create().indent(4);
                // Extract image
                File imageFolder = new File(imgPath);
                options.setExtractor(new FileImageExtractor(imageFolder));
                // URI resolver
                options.URIResolver(new FileURIResolver(imageFolder));

                File outFile = new File(target);
                outFile.getParentFile().mkdirs();
                OutputStream out = new FileOutputStream(outFile);
                XHTMLConverter.getInstance().convert(document, out, options);


            } else {
                log.info("不支持的文档类型！");
            }
            log.info("Convert " + docPath + " to " + target + " with " + (System.currentTimeMillis() - startTime) + " ms.");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("转换失败,可能是文档不规范引起的,您可以尝试用Office将文档转为Word 97-2003或者 Word 2007.");
            return false;
        }
        return true;
    }

    private static void writeFile(File file, String content, String charset) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                    file), charset);
            osw.append(content);
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convertExcel2Html(String excelFilePath,String htmlFilePath)
            throws IOException,ParserConfigurationException,TransformerException
    {
        File excelFile = new File(excelFilePath);
        File htmlFile = new File(htmlFilePath);
        File htmlFileParent = new File(htmlFile.getParent());
        InputStream is = null;
        OutputStream out = null;
        StringWriter writer = null;
        try{
            if(excelFile.exists()){
                if(!htmlFileParent.exists()){
                    htmlFileParent.mkdirs();
                }
                is = new FileInputStream(excelFile);
                HSSFWorkbook workBook = new HSSFWorkbook(is);
                ExcelToHtmlConverter converter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());

                converter.processWorkbook(workBook);

                writer = new StringWriter();
                Transformer serializer = TransformerFactory.newInstance().newTransformer();
                serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
                serializer.setOutputProperty(OutputKeys.METHOD, "html");
                serializer.transform(
                        new DOMSource(converter.getDocument()),
                        new StreamResult(writer) );
                out = new FileOutputStream(htmlFile);
                out.write(writer.toString().getBytes("UTF-8"));
                out.flush();
                out.close();
                writer.close();
            }
        }finally{
            try{
                if(is != null){
                    is.close();
                }
                if(out != null){
                    out.close();
                }
                if(writer != null){
                    writer.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}