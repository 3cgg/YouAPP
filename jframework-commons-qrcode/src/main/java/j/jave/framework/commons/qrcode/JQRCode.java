/**
 * 
 */
package j.jave.framework.commons.qrcode;

import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * the until class to create or read QRCode. 
 * @author J
 */
public class JQRCode {
	
	public static final String JPG="JPG";
	
	public static final int SIZE=256;
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JQRCode.class);

	
	/**
	 * QRCode with {@value JQRCode.SIZE} and {@value JQRCode.JPG}
	 * if any exception occurs, the empty array of byte returned. 
	 * @param content
	 * @return
	 */
	public static byte[] createQRCode(String content){
		return createQRCode(content, SIZE, JPG);
	}
	
	
	/**
	 * create QRCode -2D graphic . 
	 * if any exception occurs, the empty array of byte returned. 
	 * @param content
	 * @param size
	 * @param imageFormat
	 * @return
	 */
	public static byte[] createQRCode(String content, int size, String imageFormat){   
        try{   
            // Create the ByteMatrix for the QR-Code that encodes the given String.   
            Hashtable<EncodeHintType, Object> hintMap = new Hashtable<EncodeHintType, Object>();   
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);   
            hintMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
            
            QRCodeWriter qrCodeWriter = new QRCodeWriter();   
            BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hintMap);   
               
            // Make the BufferedImage that are to hold the QRCode   
            int matrixWidth = byteMatrix.getWidth();   
            BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);   
            image.createGraphics();   
            Graphics2D graphics = (Graphics2D) image.getGraphics();   
            graphics.setColor(Color.WHITE);   
            graphics.fillRect(0, 0, matrixWidth, matrixWidth);   
  
            // Paint and save the image using the ByteMatrix   
            graphics.setColor(Color.BLACK);   
            for (int i = 0; i < matrixWidth; i++){   
                for (int j = 0; j < matrixWidth; j++){   
                    if (byteMatrix.get(i, j)){   
                        graphics.fillRect(i, j, 1, 1);   
                    }   
                }   
            }   
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(256);
            ImageIO.write(image, imageFormat, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }catch (Exception e) {   
        	LOGGER.error("generate qrcode : "+e.getMessage(),e);
        	return new byte[]{};
        }   
    }   
	
	
	/**
	 * read content from the QRCode .
	 * if any exception occurs, the empty string "" returned. 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
    public static String readQRCode(InputStream inputStream) throws IOException{   
           
		// get the data from the input stream
		BufferedImage image = ImageIO.read(inputStream);

		// convert the image to a binary bitmap source
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		// decode the barcode
		QRCodeReader reader = new QRCodeReader();

		Result result = null;
		try {
			result = reader.decode(bitmap);
			return result.getText();
		} catch (ReaderException e) {
			// the data is improperly formatted
			LOGGER.error("read qrcode:" + e.getMessage(), e);
			return "";
		}
    }   
	
}
