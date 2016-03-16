package org.kyrincloud.Spider.core.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.kyrincloud.Spider.core.constant.Constant;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片处理工具类
 * @author kyrin
 */
public class ImageUtil {

	private static final String IMAGE_TYPE = "png";

	/**
	 * 图片压缩(补白)（按照最长的边进行等比扩缩）
	 * @param file
	 * @param rename
	 * @throws IOException
	 */
	public static void compass(InputStream is, String rename) throws IOException {
		String newImgName = rename;
		initPath(new File(newImgName).getParent());
		BufferedImage img = ImageIO.read(is);
		int w = img.getWidth(), h = img.getHeight();

		Image itemp = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);//创建图像的缩放版本
		AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(1, 1), null);
		itemp = op.filter(img, null);
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D gra = image.createGraphics();
		gra.setColor(Color.white);
		gra.fillRect(0, 0, w, h);//填充背景颜色
		gra.drawImage(itemp, 0, 0, w, h, Color.white, null); //计算坐标， 绘制缩小后的图  
		gra.dispose();
		itemp = image;
		saveToFile(newImgName, (BufferedImage) itemp);
	}

	
	/**
	 * 同过指定父目录，删除图片
	 * @param path
	 * @return
	 */
	public static boolean removeImg(String parentPath,String name) {
		File file = new File(parentPath);
		if (!file.exists()) {
			return false;
		}
		if (file.canWrite()) {
			if (file.isFile() && file.getName().equals(name)) {
				return file.delete();
			} else if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if(files[i].isFile()){
						if(files[i].getName().equals(name)){
							return files[i].delete();
						}
					}else{
						 removeImg(files[i].getAbsolutePath(),name);
					}
				}
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断目录是否存在，不存在就创建
	 * @param path
	 */
	private static void initPath(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	//将图片存为文件
	private static void saveToFile(String file, BufferedImage image) throws IOException {
		Thumbnails.of(image).scale(1).outputFormat(IMAGE_TYPE).toFile(file);
	}
	
}
