package HolyShip.gfx;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.DataBufferInt;
import java.awt.Color;
public class Screen {
    int[] pixels;
    int w,h;
    Graphics context;
    public Screen(BufferedImage image,int width,int height){
        w = width;
        h = height;
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        context = image.getGraphics();
    }

    public void render(int x,int y,Bitmap img){
        for(int i = 0;i < img.getHeight();i++){
            int py = y+i;
            if(py < 0||py >= h)continue;
            for(int j = 0;j < img.getWidth();j++){
                int px = x+j;
                if(px < 0||px >= w)continue;
                int color = img.getRaster()[j+i*img.getWidth()];
                if(color!=0)pixels[px+py*w] = color;
            }
        }
    }

    public void setFontSize(int size){
        //context.setFont(null);
    }

    public void setFontColor(int col){
        context.setColor(new Color(col));
    }

    public void setFont(String font,int size){
        context.setFont(new Font(font,Font.PLAIN,size));
    }

    public void text(String s,int x,int y){
        context.drawString(s,x,y);
    }

    public void background(int color){
        for(int i = 0;i < w*h;i++){
            pixels[i] = color;
        }
    }
}