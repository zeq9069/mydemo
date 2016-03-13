package org.kyrincloud.Spider.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片处理工具类
 * @author kyrin
 */
public class ImageUtil {

	//图片的宽，高
	private static final int WITH = 960;

	private static final int HEIGHT = 720;

	private static final String IMAGE_TYPE = "png";

	//头像的宽，高
	private static final int AVATOR_WITH = 120;

	private static final int AVATOR_HEIGHT = 120;

	private static final String AVATOR_BASE64_TYPE = "jpg";

	//private static final String PATH = "upload/image/";

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

//		if (w >= WITH / 2 || h >= HEIGHT / 2) {//如果图片太小，不再进行缩放，只不补白
//			if (w >= h) {
//				h = resizeByWidth(w, h);
//				w = WITH;
//			} else if (h >= w) {
//				w = resizeByHeight(w, h);
//				h = HEIGHT;
//			}
//		}

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
	 * 删除图片
	 * @param path
	 * @return
	 */
	public static boolean removeImg(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		if (file.canWrite()) {
			if (file.isFile()) {
				return file.delete();
			} else if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					removeImg(files[i].getAbsolutePath());
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

	/**
	 * 以宽度为基准等比压缩
	 * 按比例计算出图片压缩后的高度
	 * @param w 图片实际宽度
	 * @param h 图片实际高度
	 * @return
	 * @throws IOException
	 */
	private static int resizeByWidth(int w, int h) throws IOException {
		return h * WITH / w;
	}

	/**
	 * 以高度度为基准等比压缩
	 * 按比例计算出图片压缩后的高度
	 * @param w 图片实际宽度
	 * @param h 图片实际高度
	 * @return
	 * @throws IOException
	 */
	private static int resizeByHeight(int w, int h) throws IOException {
		return w * HEIGHT / h;
	}

	//将图片存为文件
	private static void saveToFile(String file, BufferedImage image) throws IOException {
		Thumbnails.of(image).scale(1).outputFormat(IMAGE_TYPE).toFile(file);
		/*File destFile = new File(file);
		FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流  
		ImageIO.write(image, IMAGE_TYPE, out);// 输出到文件流
		out.close();*/
	}
}
