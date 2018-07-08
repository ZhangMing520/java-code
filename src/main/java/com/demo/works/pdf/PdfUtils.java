package com.demo.works.pdf;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;


/**
 * @author : zhangming
 * @date 2018/7/8 0:26
 * <p>
 * 读取 pdf 内容
 */
public class PdfUtils {

    private PdfUtils() {
    }


    /**
     * @param file
     * @return
     */
    public static String pdf2Text(File file) {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        try (PDDocument document = PDDocument.load(file)) {
            // 未被加密
            if (!document.isEncrypted()) {

                // 获取 text
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setSortByPosition(true);

                String pdfFileInText = stripper.getText(document);

                return pdfFileInText;
            } else {
                System.out.println("Encrypted document is not support!");
            }

        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    // fixme
    public static void text2Pdf(String filePath, String pdfSavePath) {
        // suppress the Dock icon on OS X
        System.setProperty("apple.awt.UIElement", "true");

        TextToPDF app = new TextToPDF();

        try (PDDocument doc = new PDDocument()) {

            try (FileReader fileReader = new FileReader(filePath)) {
                app.createPDFFromText(doc, fileReader);
            }
            doc.save(pdfSavePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        File file = new File("C:\\Users\\zhangming\\Desktop\\pdf1.pdf");
        String pdf2Str = pdf2Text(file);
        System.out.println(pdf2Str);
    }
}