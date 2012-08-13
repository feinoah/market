package cn.com.carit.market.common.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * 按比例缩放图片帮助类
 * 
 * @author <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> 2012-7-11
 */
public class ImageUtils {
	protected final Logger logger = Logger.getLogger(getClass());

	/**
	 * 功能描述：缩放图像（按高度和宽度缩放）
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param height
	 *            缩放后的高度
	 * @param width
	 *            缩放后的宽度
	 * @param flag
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 * 
	 */
	public static void scale(String srcImageFile, String result, int height,
			int width, boolean flag) throws Exception {
		String imageType = srcImageFile
				.substring(srcImageFile.lastIndexOf(".") + 1);
		if (imageType.equalsIgnoreCase("png")) {
			flag = true;
		}
		try {
			double widthRatio = 0.0; // 缩放比例
			double heightRatio = 0.0; // 缩放比例
			File f = new File(srcImageFile);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height,
					BufferedImage.SCALE_SMOOTH);
			// 计算比例
			heightRatio = (new Integer(height)).doubleValue() / bi.getHeight();
			widthRatio = (new Integer(width)).doubleValue() / bi.getWidth();
			AffineTransformOp op = new AffineTransformOp(
					AffineTransform.getScaleInstance(widthRatio, heightRatio),
					null);
			itemp = op.filter(bi, null);
			if (flag) {// 补白
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				g.dispose();
				itemp = image;
			}
			BufferedImage bufImg = new BufferedImage(itemp.getWidth(null),
					itemp.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics g = bufImg.createGraphics();
			g.drawImage(itemp, 0, 0, null);
			g.dispose();
			ImageIO.write(bufImg, imageType, new File(result));
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void main(String[] args) throws Exception {
		scale("D:/market/Sina Weibo.png", "D:/market/Sina Weibo.png.40X40.png",
				40, 40, false);
	}

}
