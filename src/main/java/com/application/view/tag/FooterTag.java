package com.application.view.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Date;

public class FooterTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private static final String COPYRIGHT_SYMBOL = "&copy";
    private String year = String.valueOf(new Date().getYear()+1900);
    private String author;

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String copyrightStr = COPYRIGHT_SYMBOL+" "+year+" "+author;
            JspWriter out = pageContext.getOut();
            out.write(copyrightStr);
        } catch (IOException e) {
            logger.error("Unable to write to output stream. {}", e.getMessage());
            throw new JspException("Unable to write to output stream.", e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
