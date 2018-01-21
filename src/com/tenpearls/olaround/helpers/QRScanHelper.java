package com.tenpearls.olaround.helpers;

import javax.microedition.lcdui.Image;
import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;

import com.sun.lwuit.VideoComponent;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.ui.forms.base.BaseForm;
import com.tenpearls.qr.J2MEImage;
import com.tenpearls.qr.qrcode.QRCodeDecoder;

public class QRScanHelper {
	
	private BaseForm currentForm;
	private VideoControl videoControl;
	private Player player;
	private String result = "";
	
	public QRScanHelper(BaseForm currentForm) {
		this.currentForm = currentForm;
	}
	
	public VideoComponent createVideoComponent() {
		
		VideoComponent videoComponent = null;
		try {
			videoComponent = VideoComponent.createVideoPeer("capture://image");
		
	    	player = (Player) videoComponent.getNativePeer();
	    	player.realize();
	    	videoControl = (VideoControl) player.getControl("VideoControl");
	    	videoComponent.start();
	    	
		} catch (Exception e) {
			e.printStackTrace();
	        player = null;
	        videoControl = null;
		}
		
		return videoComponent;
	}
	
	public void capture() {
		try {
			
			DecodeQR decodeThread = new DecodeQR();
			decodeThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public VideoControl getVideoControl() {
        return videoControl;
    }
    
	
	public void closeCamera() {
        try {
            player.stop();
            player.close();
        } catch (Exception e) {
			e.printStackTrace();
        }
	}
	
	public void setResult(String result) {
		this.result = result;
		if(currentForm != null) {
			currentForm.showScanResult(result);
			currentForm = null; 
		} 
	}
	
	public String getResult() {
		return this.result;
	}
	
	public void handleException(Exception e) {
		System.err.println(e);
	}

	class DecodeQR extends Thread {
	
		public void run() {
            try {
                String result = "";


                String captureString =  "encoding=jpeg&width=640&height=480"; //null //provide null if read image from resource.
                byte[] raw = captureSnapshot(captureString);

                closeCamera();
                // if image capture was successful, start processing
                if (raw != null) {
                	
                    Image image = Image.createImage(raw, 0, raw.length);

                    /* uncomment below line for testing on emulator */
                   // Image image = Image.createImage("/images/QR-ID.png"); 

                    // initialize decoder and decode image matrix
                    QRCodeDecoder decoder = new QRCodeDecoder();
                    try {
                        result = new String(decoder.decode(new J2MEImage(image)));
                    } catch (Exception e) {
                        // continue
                    }
                    if (result.length() == 0) {
                        result = "Decoding failed! Please try again.";
                    }
                } else {
                    result = ApplicationConstants.MSG_QR_ERROR;
                    
                }
            
                setResult(result);
                
              } catch (Exception me) {
            	  handleException(me);
              }
		}
	        
	        public byte[] captureSnapshot(String captureString) {
	            System.out.println("captureSnapshot()");
	            byte[] raw = null;
	            videoControl = getVideoControl();
	           
	            // if there are no settings or no image has been captured
	            // fall back to automatic settings
	            if (raw == null) {
	                try {
	                    raw = videoControl.getSnapshot(captureString);
	                } catch (Exception e) {
	                   e.printStackTrace();
	                }
	            }

	            return raw;
	        }
	}
}
