package textures;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

public class Texture {
    private int textureId;
    private int width;
    private int height;

    public Texture(String fileName){
	BufferedImage image;
	try {
	    InputStream stream = Texture.class.getClassLoader().getResourceAsStream(fileName);
	    image = ImageIO.read(stream);
	    width = image.getWidth();
	    height = image.getHeight();

	    int[] pixels = new int[width * height];
	    image.getRGB(0, 0, width, height, pixels, 0, width);

	    ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

	    for (int j = 0; j < height; j++){
		for (int i = 0; i < width; i++) {
		    int pixel = pixels[j*width + i];
		    buffer.put((byte)((pixel >> 16) & 0xFF)); //RED
		    buffer.put((byte)((pixel >> 8) & 0xFF));  //GREEN
		    buffer.put((byte)(pixel & 0xFF));  //BLUE
		    buffer.put((byte)((pixel >> 24) & 0xFF));  //ALPHA
		}
	    }

	    buffer.flip();

	    textureId = glGenTextures();

	    glBindTexture(GL_TEXTURE_2D, textureId);

	    //Setup wrap mode
	    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
	    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);


	    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

	    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);


	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public void bind(int sampler) {
	if (sampler >= 0 && sampler <= 31) {
	    glActiveTexture(GL_TEXTURE0 + sampler);
	    glBindTexture(GL_TEXTURE_2D, textureId);
	}
    }

    public int getTextureId() {
	return textureId;
    }
}
