/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.DocumentType;
import java.util.List;

/**
 *
 * @author root
 */
public interface IDocumentTypeController {

    public List<DocumentType> getAllDocumentTypes();

    public DocumentType getDocumentType(long ID);

    public DocumentType getImageDocumentType();

    public DocumentType getDocDocumentType();

    public DocumentType getMMDocumentType();

}
