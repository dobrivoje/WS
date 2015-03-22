/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.files.uploader;

import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 *
 * @author root
 */
public class UploadReceiver implements Upload.Receiver, Upload.ProgressListener, Upload.StartedListener, Upload.FinishedListener {

    private final ProgressBar indicator;
    private final Layout layout;
    private final String filePath;
    private File file;

    public UploadReceiver(ProgressBar indicator, Layout layout, String filePath) {
        this.indicator = indicator;
        this.layout = layout;
        this.filePath = filePath;
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        FileOutputStream fos;
        try {
            file = new File(filePath.concat(filename));
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
            return null;
        }

        return fos;
    }

    @Override
    public void updateProgress(long readBytes, long contentLength) {
        float fs = ((float)readBytes / contentLength);
        indicator.setValue(fs);
    }

    @Override
    public void uploadStarted(Upload.StartedEvent event) {
        layout.addComponent(indicator);
        Notification.show("upload started.", Notification.Type.HUMANIZED_MESSAGE);
    }

    @Override
    public void uploadFinished(Upload.FinishedEvent event) {
        layout.removeComponent(indicator);
        Notification.show("upload finished.", Notification.Type.HUMANIZED_MESSAGE);
    }

}
