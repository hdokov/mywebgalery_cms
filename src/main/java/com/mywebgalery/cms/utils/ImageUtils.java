package com.mywebgalery.cms.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtils {

	private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

	public static final String RESOURCES_PATH = ConfigMngr.getUploadDir();

	private static final BufferedImage sample = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);

	public static byte[] resize(byte[] data, int width) throws IOException{
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
		image = resize(image, width);
		return getImageData(image);
	}//resize


    public static BufferedImage resize(BufferedImage image, int width) {
		ImageIcon s = new ImageIcon(image.getScaledInstance(width, -1, Image.SCALE_SMOOTH));
		return convert(s);
	}

	public static BufferedImage convert(ImageIcon s) {
		BufferedImage image1 = new BufferedImage(s.getIconWidth(),s.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		image1.getGraphics().drawImage(s.getImage(), 0, 0, null);

		return image1;
	}

	public static byte[] getImageData(BufferedImage image){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			if(ImageIO.write(image, "png", out)){
				return out.toByteArray();
			}
		} catch (IOException e) {
			log.error("Cannot convert image", e);
		}
		return null;

	}

	public static BufferedImage load(File f){
		try {
			return ImageIO.read(f);
		} catch (IOException e) {
			log.error("Cannot load image", e);
			return null;
		}
	}

	public static BufferedImage load(InputStream in){
		try {
			return ImageIO.read(in);
		} catch (IOException e) {
			log.error("Cannot load image", e);
			return null;
		}
	}

	public static void save(BufferedImage image, File out){
		try {
			ImageIO.write(image, "png", out);
		} catch (IOException e) {
			log.error("Cannot save image", e);
		}
	}

	public static byte[] renderButton(String text, Font font) throws IOException{

        FontMetrics fm =sample.getGraphics().getFontMetrics(font);
		BufferedImage image = new BufferedImage(fm.stringWidth(text)+10,fm.getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString(text, 5 , fm.getAscent());

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if(ImageIO.write(image, "png", out)){
			return out.toByteArray();
		}
		return null;
	}

}
