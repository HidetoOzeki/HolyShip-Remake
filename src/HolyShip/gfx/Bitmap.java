package HolyShip.gfx;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
public class Bitmap{
    int w,h;
    int[] pixels;
    public Bitmap(String filename){

        try {
            BufferedImage bitmap = ImageIO.read(new File(filename));
            w = bitmap.getWidth();
            h = bitmap.getHeight();
            pixels = bitmap.getRGB(0, 0, w, h, null, 0, w);
        }catch(IOException e){
            e.printStackTrace();
        }


    }

    public int getWidth(){
        return w;
    }
    
    public int getHeight(){
        return h;
    }

    public int[] getRaster(){
        return pixels;
    }
}