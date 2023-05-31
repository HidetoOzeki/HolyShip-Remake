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
    public int xoffset = 0;
    public int yoffset = 0;
    int center_x;
    int center_y;
    Vec3 view_offset;

    Bitmap texture = null;

    int fillcolor = 0;

    public Screen(BufferedImage image,int width,int height){
        w = width;
        h = height;
        center_x = w/2;
        center_y = h/2;
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        context = image.getGraphics();

        view_offset = new Vec3(0,0,0);
    }

    public void background(int color){
        for(int i = 0;i < w*h;i++){
            pixels[i] = color;
        }
    }

    public void render(int x,int y,Bitmap img){

        x-=xoffset;
        y-=yoffset;
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

    public void put(int x,int y,int col){
        if(x < 0||x >= w)return;
        if(y < 0||y >= h)return;
        pixels[x+y*w] = col;
    }

    public void rect(int x,int y,int sx,int sy,int col){
        x-=xoffset;
        y-=yoffset;
        for(int i = 0;i < sx;i++){
            for(int j = 0;j < sy;j++){
                put(x+i,y+j,col);
            }
        }
    }

    public void setFillColor(int col){
        this.fillcolor = col;
    }

    public void setTexture(Bitmap texture){
        this.texture = texture;
    }

    public void tline(int y,int xl,int xr,UVcoord uv1,UVcoord uv2){
        if(texture==null)return;
        int length = xr-xl;
        double du = (uv2.u-uv1.u)/length;
        double dv = (uv2.v-uv1.v)/length;
        double u = uv1.u;
        double v = uv1.v;
        for(int i = xl;i < xr;i++){
            u+=du;
            v+=dv;
            put(i,y,texture.getUV(u,v));
        }
    }

    public void triangle(Vec3 v1,Vec3 v2,Vec3 v3,UVcoord uv1,UVcoord uv2,UVcoord uv3){
        Vec3 temp;
        if(v1.y > v3.y){
            temp = v1;
            v1 = v3;
            v3 = temp;
        }
        if(v1.y > v2.y){
            temp = v1;
            v1 = v2;
            v2 = temp;
        }
        if(v2.y > v3.y){
            temp = v2;
            v2 = v3;
            v3 = temp;
        }

        double a = (v3.x-v1.x)/(v3.y-v1.y);
        double b = (v2.x-v1.x)/(v2.y-v1.y);
        double c = (v3.x-v2.x)/(v3.y-v2.y);

        double l = v1.x;
        double r = v1.x;

        if((int)v1.y==(int)v2.y){
            boolean sides = v1.x < v2.x;
            l = sides ? v1.x:v2.x;
            r = sides ? v2.x:v1.x;
        }

        if(a > b){
            for(int i = (int)v1.y;i < v2.y;i++){
                tline(i,(int)l,(int)r,null,null);
                l+=b;
                r+=a;
            }
            for(int i = (int)v2.y;i < v3.y;i++){
                tline(i,(int)l,(int)r,null,null);
                l+=c;
                r+=a;
            }
        }else{
            for(int i = (int)v1.y;i < v2.y;i++){
                tline(i,(int)l,(int)r,null,null);
                l+=a;
                r+=b;
            }
            for(int i = (int)v2.y;i < v3.y;i++){
                tline(i,(int)l,(int)r,null,null);
                l+=a;
                r+=c;
            }
        }
    }

    public void triangle(Vec3 v1,Vec3 v2,Vec3 v3){
        Vec3 temp;
        if(v1.y > v3.y){
            temp = v1;
            v1 = v3;
            v3 = temp;
        }
        if(v1.y > v2.y){
            temp = v1;
            v1 = v2;
            v2 = temp;
        }
        if(v2.y > v3.y){
            temp = v2;
            v2 = v3;
            v3 = temp;
        }

        double a = (v3.x-v1.x)/(v3.y-v1.y);
        double b = (v2.x-v1.x)/(v2.y-v1.y);
        double c = (v3.x-v2.x)/(v3.y-v2.y);

        double l = v1.x;
        double r = v1.x;

        if((int)v1.y==(int)v2.y){
            boolean sides = v1.x < v2.x;
            l = sides ? v1.x:v2.x;
            r = sides ? v2.x:v1.x;
        }

        if(a > b){
            for(int i = (int)v1.y;i < v2.y;i++){
                for(int j = (int)l;j < r;j++){put(j,i,fillcolor);}
                l+=b;
                r+=a;
            }
            for(int i = (int)v2.y;i < v3.y;i++){
                for(int j = (int)l;j < r;j++){put(j,i,fillcolor);}
                l+=c;
                r+=a;
            }
        }else{
            for(int i = (int)v1.y;i < v2.y;i++){
                for(int j = (int)l;j < r;j++){put(j,i,fillcolor);}
                l+=a;
                r+=b;
            }
            for(int i = (int)v2.y;i < v3.y;i++){
                for(int j = (int)l;j < r;j++){put(j,i,fillcolor);}
                l+=a;
                r+=c;
            }
        }
    }

    public Vec3 getScreenCoordinate(Vec3 v){
        v.sub(view_offset); //subtract or add?
        double x = v.x-v.z;
        double y = ((v.z+v.x)/2)+(v.y/2);
        return new Vec3(x,y,0);
    }

    public void setFontColor(int col){
        context.setColor(new Color(col));
    }

    public void setFont(String font,boolean bold,int size){
        int STYLE = bold ? Font.BOLD : Font.PLAIN;
        context.setFont(new Font(font,STYLE,size));
    }

    public void text(String s,int x,int y){
        x-=xoffset;
        y-=yoffset;
        context.drawString(s,x,y);
    }
}