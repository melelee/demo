package com.melelee.word;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AddPageNumbersToExistingDocument {
    public static void main(String[] args) throws IOException {
//        simple();
//        test();

        baidu();
    }

    private static void simple() throws IOException {
        // 1. 加载现有Word文档
        try (XWPFDocument document = new XWPFDocument(Files.newInputStream(Paths.get("D:\\询价相关菜单.docx")))) {

            List<XWPFFooter> footerList = document.getFooterList();

            for (XWPFFooter xwpfFooter : footerList) {
                xwpfFooter.clearHeaderFooter();
            }
            XWPFFooter footer = document.createFooter(HeaderFooterType.DEFAULT);
            footer.createParagraph().createRun().setText("footer");


            // 4. 保存修改后的文档
            try (FileOutputStream out = new FileOutputStream("D:\\modified.docx")) {
                document.write(out);
            }
        }
    }

//        <dependency>
//            <groupId>org.apache.poi</groupId>
//            <artifactId>poi-ooxml</artifactId>
//            <version>5.2.5</version>
//        </dependency>



    public static void test() throws IOException {
        try (XWPFDocument document = new XWPFDocument(Files.newInputStream(Paths.get("D:\\询价相关菜单.docx")))) {

            //创建一个新的XWPFFooter对象
            XWPFFooter footer = document.createFooter(HeaderFooterType.DEFAULT);

            //创建新的XWPFParagraph对象
            XWPFParagraph paragraph = footer.createParagraph();
            //设置样式居中
            paragraph.setAlignment(ParagraphAlignment.CENTER);

            //设置段落对象
            //新的段落对象
            XWPFRun runPre = paragraph.createRun();
            runPre.setText("- ");


            //新的段落对象
            XWPFRun run = paragraph.createRun();

            CTR ctr = run.getCTR();

            //新的CTFldChar对象
            CTFldChar begin = ctr.addNewFldChar();
            begin.setFldCharType(STFldCharType.BEGIN);

            CTText ctText = ctr.addNewInstrText();
            ctText.setStringValue("PAGE");
            ctText.setSpace(SpaceAttribute.Space.PRESERVE);

            CTFldChar end = ctr.addNewFldChar();
            end.setFldCharType(STFldCharType.END);


            //设置段落对象
            //新的段落对象
            XWPFRun runSuf = paragraph.createRun();
            runSuf.setText(" -");

            // 将页脚添加到所有的页面
            XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document);
            headerFooterPolicy.createFooter(STHdrFtr.DEFAULT, new XWPFParagraph[]{paragraph});

            // 保存文档
            try (FileOutputStream out = new FileOutputStream("D:\\modified.docx")) {
                document.write(out);
            }
        }
    }

    public static void baidu() throws IOException {

        // 加载现有的 Word 文档
        try (FileInputStream fileInputStreamfis = new FileInputStream("D:\\询价相关菜单.docx")) {
            XWPFDocument document = new XWPFDocument(fileInputStreamfis);

            // 获取页眉页脚策略
            XWPFHeaderFooterPolicy headerFooterPolicy = document.getHeaderFooterPolicy();

            if (headerFooterPolicy == null) {
                // 如果文档没有页眉页脚策略，则创建一个
                headerFooterPolicy = document.createHeaderFooterPolicy();
            }

            // 获取页脚（通常为第一个页脚）
            XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);

            // 在页脚中添加一个段落
            XWPFParagraph paragraph = footer.createParagraph();

            // 在段落中创建一个运行（Run），并设置页码
            XWPFRun run = paragraph.createRun();
            run.setText("页码: ");

            // 添加页码字段
            // 在 Word 中，页码字段的文本是 "{ PAGE }"
            run.setText("{ PAGE }");

            // 保存修改后的文档
            try (FileOutputStream fos = new FileOutputStream("D:\\baidu.docx")) {
                document.write(fos);
            }
        }

    }
}
