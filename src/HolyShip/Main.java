package HolyShip;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Locale;

import HolyShip.gfx.Screen;
import HolyShip.gfx.Bitmap;
import HolyShip.gfx.Vec3;
import java.awt.event.*;
public class Main implements Runnable{

    public static int WIDTH = 320;
    public static int HEIGHT = 240;
    public static int SCALE = 2;

    BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

    JFrame window;
    BufferStrategy str;

    Screen screen = new Screen(image,WIDTH,HEIGHT);
    InputHandler input;

    Bitmap fractal;

    String[] fontnames;

    public Main(){
        window = new JFrame("HolyShip");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WIDTH*SCALE,HEIGHT*SCALE);
        window.setVisible(true);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.createBufferStrategy(2);
        str = window.getBufferStrategy();
        window.addKeyListener(input = new InputHandler());
        fractal = new Bitmap("res/fractal.png");
        screen.background(0x999999);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fontnames = ge.getAvailableFontFamilyNames(Locale.JAPANESE);
        for(String fonts : fontnames){
            System.out.println(fonts);
        }
    }
    public static void main(String[] args){
        Main main = new Main();
        main.run();
    }

    float x,y = WIDTH/3;

    String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String[] alpharr = alphabets.split("");

    Vec3 angle = new Vec3(0,0,0);

    int red = 0;
    int green = 0;
    int blue = 0;

    double r_shift = 0.0;
    double g_shift = 0.0;
    double b_shift = 0.0;
    double increment = 0;

    Vec3 v1 = new Vec3(WIDTH/2,HEIGHT/2,0);
    Vec3 v2 = new Vec3(WIDTH/3,HEIGHT-50,0);
    Vec3 v3 = new Vec3(WIDTH-WIDTH/3,HEIGHT-50,0);

    public void render(){
        Graphics g = str.getDrawGraphics();

        screen.background(0);

        red = (int)(r_shift*255);
        green = (int)(-r_shift*255);
        int colorshift = (red<<16|green<<8|blue);
        screen.setFontColor(0xffffff);
        screen.setFont("MS Gothic",true, 12);
        screen.text("test テスト", 32, 32);

        for(int i = -alpharr.length/2;i < alpharr.length/2;i++){
            for(int j = -alpharr.length/2;j < alpharr.length/2;j++){
                Vec3 v = new Vec3(i*8.0,0,j*8.0);
                v.rotateX(angle.x);
                v.rotateY(angle.y);
                v.rotateZ(angle.z);
                v = screen.getScreenCoordinate(v);
                v.x+=WIDTH/2;
                v.y+=HEIGHT/2;
                int i_abs = Math.abs(i);
                int j_abs = Math.abs(j);
                //screen.text(alpharr[(i_abs+j_abs)%(alpharr.length-1)], (int)v.x, (int)v.y);
            }
        }

        screen.triangle(v1,v2,v3);


        //screen.background(0x000000);

        g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);

        g.dispose();
        str.show();
    }
    double angle_acc = 0.0;
    public void tick(){
        if(input.keys[KeyEvent.VK_A])angle_acc+=0.05;
        if(input.keys[KeyEvent.VK_D])angle_acc+=-0.05;
        angle.y+=angle_acc;
        angle_acc*=0.9;

        int scroll_pixels = 1;

        if(input.keys[KeyEvent.VK_SHIFT])scroll_pixels=4;

        if(input.keys[KeyEvent.VK_DOWN])screen.yoffset+=scroll_pixels;
        if(input.keys[KeyEvent.VK_UP])screen.yoffset-=scroll_pixels;

        if(input.keys[KeyEvent.VK_SPACE]){
            v1.x = (int)(Math.random()*WIDTH);
            v1.y = (int)(Math.random()*HEIGHT);
            
            v2.x = (int)(Math.random()*WIDTH);
            v2.y = (int)(Math.random()*HEIGHT);
            
            v3.x = (int)(Math.random()*WIDTH);
            v3.y = (int)(Math.random()*HEIGHT);
        }

        increment+=0.01;
        r_shift=Math.sin(increment);
    }

    @Override
    public void run(){
        int fps = 0;
        int tps = 0;
        double ms = 1000.0/60.0;
        double delta = 0;
        double last = System.currentTimeMillis();
        long timer = System.currentTimeMillis();
        long lastframe = System.currentTimeMillis();
        while(true){

            double now = System.currentTimeMillis();
            delta+=(now-last)/ms;
            last = now;
            if(delta>=1.0){
                delta--;
                tps++;
                tick();
            }else{
                fps++;
                render();
                long currentframe = System.currentTimeMillis();
                long sleeptime = 16-(currentframe-lastframe);
                lastframe = currentframe;
                if(sleeptime < 2)sleeptime = 2;
                try {
                Thread.sleep(sleeptime);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

            if(timer+1000 <= System.currentTimeMillis()){
                timer+=1000;
                System.out.println("fps : "+fps+" tps : "+tps);
                fps = 0;
                tps = 0;
            }


        }

    }
}