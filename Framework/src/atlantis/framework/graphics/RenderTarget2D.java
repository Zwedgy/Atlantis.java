package atlantis.framework.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

/**
 * A render target 2D is a back buffer image.
 * @author Yannick
 */
public class RenderTarget2D extends BufferedImage {
	protected Graphics graphics;
	protected DataBuffer dataBuffer;
	protected int bufferSize;
	
	public RenderTarget2D(int width, int height) {
		super(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		this.graphics = this.getGraphics();
		this.dataBuffer = this.getRaster().getDataBuffer();
		this.bufferSize = this.dataBuffer.getSize();
	}

	/**
	 * Clear the back buffer with the specified color
	 * @param color Color to use.
	 */
	public void clear(Color color) {
		int alpha = color.getAlpha();
		int blue = color.getBlue();
		int green = color.getGreen();
		int red = color.getRed();
		
		for (int i = 0; i < this.bufferSize; i += 4) {
			this.dataBuffer.setElem(i, alpha);
			this.dataBuffer.setElem(i + 1, blue);
			this.dataBuffer.setElem(i + 2, green);
			this.dataBuffer.setElem(i + 3, red);
		}
	}
	
	public void draw(Graphics graphics) {
		graphics.drawImage(this, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}