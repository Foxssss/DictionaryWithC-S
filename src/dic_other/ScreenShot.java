package dic_other;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class ScreenShot {
	public BufferedImage getScreenShot(int x, int y, int width, int height) {
		BufferedImage bfImage = null;
		try {
			Robot robot = new Robot();
			bfImage = robot.createScreenCapture(new Rectangle(x, y, width,
					height));
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return bfImage;
	}

	/**
	 * 指定屏幕区域截图，保存到指定目录
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param savePath
	 *            - 文件保存路径
	 * @param fileName
	 *            - 文件保存名称
	 * @param format
	 *            - 文件格式
	 */
	public void screenShotAsFile(int x, int y, int width, int height,
			String savePath, String fileName, String format) {
		try {
			Robot robot = new Robot();
			BufferedImage bfImage = robot.createScreenCapture(new Rectangle(x,
					y, width, height));
			File path = new File(savePath);
			File file = new File(path, fileName + "." + format);
			ImageIO.write(bfImage, format, file);
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ScreenShot sa = new ScreenShot();
		sa.screenShotAsFile(0, 0, 100, 100, "G:/", "test", "png");
	}
}
