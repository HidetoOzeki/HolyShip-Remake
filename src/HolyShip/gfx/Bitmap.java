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

    public int getUV(double u,double v){
        if(u<0)u=-u;
        if(v<0)v=-v;
        int xp = (int)(u*w)%w;
        int yp = (int)(v*h)%h;
        
        return pixels[xp+yp*w];
    }
}