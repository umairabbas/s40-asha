package com.tenpearls.olaround.ui.forms;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Image;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.plaf.Style;
import com.tenpearls.olaround.OlaroundMidlet;
import com.tenpearls.olaround.constants.ImageConstants;
import com.tenpearls.olaround.helpers.ImageHelper;
import com.tenpearls.olaround.helpers.StringUtils;
import com.tenpearls.olaround.ui.components.ImageCell;
import com.tenpearls.olaround.ui.forms.base.BaseForm;

public class GalleryForm extends BaseForm {

	private static String PHOTOS_PATH = "fileconn.dir.photos";
	private final static int STOP_AT_BYTE = 8192; // how far to search for
	private final static int THUMB_MAX_SIZE = 16284; // thumbnail
	private String currDirName;
	static ImageGrid imageGrid;

	public GalleryForm(String title, OlaroundMidlet mainMidlet) {
		super(title, mainMidlet);
	}

	public void onCreate() {
		this.setScrollable(false);
		Image bgImage = ImageHelper.getImageFromResources(ImageConstants.BACKGROUND);
		if (bgImage != null) {
			this.getStyle().setBgImage(bgImage);
			this.getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
			bgImage = null;
		}
		init();
	}

	private void init() {
		if (System.getProperty("microedition.io.file.FileConnection.version") != null) {
			try {
				showCurrDir();
			} catch (SecurityException e) {
				System.out.println("security exception");
			//	e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void showCurrDir() throws IOException {
		Enumeration enum;
		FileConnection currDir = null;

		currDirName = System.getProperty(PHOTOS_PATH);
		System.out.println("showCurrDir()");
		currDir = (FileConnection) Connector.open(currDirName); 
		System.out.println("showCurrDir()");
		if(currDir != null) {
			enum = currDir.list();
			imageGrid = new ImageGrid();
			while (enum.hasMoreElements()) {
				String fileName = (String) enum.nextElement();
				if (StringUtils.isContains(fileName, ".jpg")
						|| StringUtils.isContains(fileName, ".jpeg")
						|| StringUtils.isContains(fileName, ".png")) {
					System.out.println(currDirName + fileName);
					createImage(fileName);
				}
			}
			currDir.close();
			
		}
	}

	private void createImage(String imagePath) {
		Image img = openFileThumb(imagePath);
		
		if (img == null) {
			img = openFile(imagePath);
		}
		
		if (img != null) {
			ImageCell imageCell = new ImageCell(img);
			imageGrid.addComponent(imageCell);
			img = null;
			imageCell = null;
		} else {
			System.out.println("image not found");
		}
	}

	private Image openFile(String fileName) {
		FileConnection fc = null;
		InputStream is = null;
		try {
			fc = (FileConnection) Connector.open(currDirName + fileName);
			if (!fc.exists()) {
				throw new IOException("File does not exists");
			}
			is = fc.openInputStream();
			Image image = Image.createImage(is);
			/*
			 * int size = (int) fc.fileSize(); byte bytes[] = new byte[size];
			 * is.read(bytes, 0, size); if (size<80000)
			 * System.out.println(size); image = Image.createImage(bytes,
			 * 0,size); bytes = null;
			 */
			is.close();
			fc.close();
			return image;
		} catch (Exception e) {
			// CustomDialogBox.showDialog("Error", e.getMessage(), "ok");
		}
		return null;
	}
	
	private Image openFileThumb(String fileName) {
		FileConnection fc = null;
		InputStream is = null;
		try {
			fc = (FileConnection) Connector.open(currDirName + fileName);

			if (!fc.exists()) {
				throw new IOException("File does not exists");
			}

			int size = (int) fc.fileSize();
			is = fc.openInputStream();
			Image image = getThumbnailFromStream(is, size);
			is.close();
			fc.close();
			return image;
		} catch (Exception e) {
		}
		return null;
	}

	private Image getThumbnailFromStream(InputStream str, long fileSize) {
		byte[] tempByteArray = new byte[THUMB_MAX_SIZE]; 
		byte[] bytefileReader = { 0 };
		byte firstByte, secondByte = 0;
		int currentIndex = 0;
		int currByte = 0;
		try {
			str.read(bytefileReader);
			firstByte = bytefileReader[0];
			str.read(bytefileReader);
			secondByte = bytefileReader[0];
			currByte += 2;
			if ((firstByte & 0xFF) == 0xFF && (secondByte & 0xFF) == 0xD8) { 
				byte rByte = 0;
				do {
					while (rByte != -1 && currByte < fileSize) {
						str.read(bytefileReader);
						rByte = bytefileReader[0];
						currByte++;
					}
					str.read(bytefileReader);
					rByte = bytefileReader[0];
					currByte++;
					if (currByte > STOP_AT_BYTE) {
						return null;
					}
				} while ((rByte & 0xFF) != 0xD8 && currByte < fileSize);
				if (currByte >= fileSize) {
					return null;
				}
				tempByteArray[currentIndex++] = -1;
				tempByteArray[currentIndex++] = rByte;
				rByte = 0;

				do {
					while (rByte != -1) {
						str.read(bytefileReader);
						rByte = bytefileReader[0];
						tempByteArray[currentIndex++] = rByte;
					}
					str.read(bytefileReader);
					rByte = bytefileReader[0];
					tempByteArray[currentIndex++] = rByte;
				} while ((rByte & 0xFF) != 0xD9);
				tempByteArray[currentIndex++] = -1;
				Image image = Image.createImage(tempByteArray, 0,
						currentIndex - 1);
				tempByteArray = null;
				return image;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public void actionPerformed(ActionEvent ae) {

	}

	public void onBack() {
		System.out.println("onBack()");
		mainMidlet.showSignUpStep1Form(GalleryForm.this, true);
	}

	public void onShowCompleted() {
		super.onShowCompleted();
		if(imageGrid != null) {
			imageGrid.show();
		} else {
			onBack();
		} 
	};

	public void dealloc() {
		super.dealloc();
		imageGrid = null;
	}

	public void onResume() {

	}

	public void onPause() {

	}

	public void onButtonClick(Button btn) {

	}

	public void onCommandClick(Command cmd) {

	}

	public void loadData(boolean isRefreshed) {

	}

	public void addCommands() {

	}

}
