/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.files.uploader;

import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author root
 */
public class UploadReceiver implements Receiver, ProgressListener, StartedListener, FinishedListener, FailedListener {

    private final ProgressBar indicator;
    private final String filePath;
    private File file;

    private static final List<String> allowedMIMEtypes = new ArrayList<>(Arrays.asList(
            "image/bmp", "image/gif", "image/jpeg", "image/png", "image/tiff"));

    public UploadReceiver(ProgressBar indicator, String filePath) {
        this.indicator = indicator;
        this.filePath = filePath;
    }

    public void checkAndMakeRootDir(String path) {
        File dir = new File(path);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        FileOutputStream fos = null;

        try {
            checkAndMakeRootDir(filePath);
            file = new File(filePath.concat(filename));
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
        }

        return fos;
    }

    @Override
    public void updateProgress(long readBytes, long contentLength) {
        float fs = ((float) readBytes / contentLength);
        indicator.setValue(fs);
    }

    @Override
    public void uploadStarted(Upload.StartedEvent event) {
        for (String mt : allowedMIMEtypes) {
            if (mt.equalsIgnoreCase(event.getMIMEType())) {

            }
        }
    }

    @Override
    public void uploadFinished(Upload.FinishedEvent event) {
    }

    @Override
    public void uploadFailed(Upload.FailedEvent event) {
    }

}
