/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery.Excel;

import db.ent.Document;
import java.io.File;
import Gallery.common.IDocBeans;

/**
 *
 * @author root
 */
public class DocFile implements IDocBeans<File> {

    private File file;
    private Document doc;

    public DocFile(File file, Document doc) {
        this.file = file;
        this.doc = doc;
    }

    @Override
    public void setBean(File file) {
        this.file = file;
    }

    @Override
    public File getBean() {
        return file;
    }

    @Override
    public void setDocument(Document bean) {
        this.doc = bean;
    }

    @Override
    public Document getDocument() {
        return doc;
    }
};
