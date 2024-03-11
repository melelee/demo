package com.melelee.word;

//////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001-2024 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

import com.aspose.words.Document;
import com.aspose.words.HeaderFooter;
import com.aspose.words.HeaderFooterType;
import com.aspose.words.Paragraph;

public class WordFooter {

    public static void main(String[] args) throws Exception {
        String input = "D:\\评标报告.docx";
        String output = "D:\\output.docx";

        Document doc = new Document(input);
        HeaderFooter footer = new HeaderFooter(doc, HeaderFooterType.FOOTER_PRIMARY);
        doc.getFirstSection().getHeadersFooters().add(footer);

        Paragraph para = footer.appendParagraph("My footer.");

        doc.save(output);
    }
}
