package com.demo.works.pdf;

import com.demo.works.diff.diff_match_patch;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author : zhangming
 * @date 2018/7/8 0:49
 */
public class PdfDiff {


    public static void main(String[] args) throws IOException {

        String src = PdfUtils.pdf2Text(new File("C:\\Users\\zhangming\\Desktop\\pdf1.pdf"));
        String dst = PdfUtils.pdf2Text(new File("C:\\Users\\zhangming\\Desktop\\pdf2.pdf"));

        diff_match_patch dmp = new diff_match_patch();

        // Execute one reverse diff as a warmup.
        LinkedList<diff_match_patch.Diff> diffs = dmp.diff_main(src, dst, true);


        FileWriter writer = new FileWriter(new File("C:\\Users\\zhangming\\Desktop\\diff.txt"));


        HTMLTag div = new HTMLTag("div");
        HTMLTag span = new HTMLTag("span");

        Map<String, List<String>> insStyle = new HashMap<>();
        insStyle.put("style", Arrays.asList("background:#e6ffe6;"));
        HTMLTag ins = new HTMLTag("ins", insStyle);

        Map<String, List<String>> delStyle = new HashMap<>();
        delStyle.put("style", Arrays.asList("background:#ffe6e6;"));
        HTMLTag del = new HTMLTag("del", delStyle);

        StringBuilder html = new StringBuilder();
        for (diff_match_patch.Diff diff : diffs) {
//            writer.append("@@" + diff.operation + "@@" + diff.text);
            switch (diff.operation) {
                case INSERT:
                    ins.setTagContent(diff.text);
                    html.append(ins);
                    break;
                case EQUAL:
                    span.setTagContent(diff.text);
                    html.append(span);
                    break;
                case DELETE:
                    del.setTagContent(diff.text);
                    html.append(del);
                    break;
                default:
                    break;
            }
        }

        // 换行符
        String htmlStr = html.toString();


        div.setTagContent(htmlStr.replaceAll("\\r\\n", "<br>"));

        writer.write(div.toString());

        writer.flush();
        writer.close();

//        String filePath = "C:\\Users\\zhangming\\Desktop\\diff.txt";
//        String savePath = "C:\\Users\\zhangming\\Desktop\\diff.pdf";
//        PdfUtils.text2Pdf(filePath, savePath);

//        TextToPDF.main(new String[]{"", filePath, savePath});
//


//        System.setProperty("apple.awt.UIElement", "true");
//        TextToPDF app = new TextToPDF();
//
//        try (PDDocument doc = new PDDocument()) {
//
//
//            try (FileReader fileReader = new FileReader(filePath)) {
//                app.createPDFFromText(doc, fileReader);
//            }
//            doc.save(savePath);
//        }
    }


    static class HTMLTag {
        private String tagName;
        private Map<String, List<String>> tagAttrs;
        private String tagContent;


        public HTMLTag() {
        }

        public HTMLTag(String tagName) {
            this.tagName = tagName;
        }

        public HTMLTag(String tagName, Map<String, List<String>> tagAttrs) {
            this.tagName = tagName;
            this.tagAttrs = tagAttrs;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public void setTagAttrs(Map<String, List<String>> tagAttrs) {
            this.tagAttrs = tagAttrs;
        }

        public void setTagContent(String tagContent) {
            this.tagContent = tagContent;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("<").append(tagName).append(" ");
            if (tagAttrs != null && tagAttrs.size() > 0) {
                tagAttrs.forEach((k, vList) -> {
                    sb.append(k).append("='");
                    vList.forEach(attr -> {
                        // 还需要定制分隔符号
                        sb.append(attr);
                    });

                    sb.append("' ");
                });
            }
            sb.append(" >");

            if (tagContent != null) {
                sb.append(tagContent);
            }

            sb.append("</").append(tagName).append(">");
            return sb.toString();
        }
    }
}
