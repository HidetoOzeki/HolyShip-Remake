package HolyShip;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import HolyShip.gfx.Screen;
import HolyShip.gfx.Bitmap;
import java.awt.event.*;
public class Main implements Runnable{

    public static int WIDTH = 320;
    public static int HEIGHT = 240;
    public static int SCALE = 3;

    BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

    JFrame window;
    BufferStrategy str;

    Screen screen = new Screen(image,WIDTH,HEIGHT);
    InputHandler input;

    Bitmap fractal;

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
    }
    public static void main(String[] args){
        Main main = new Main();
        main.run();
    }

    float x,y = WIDTH/3;

    public void render(){
        Graphics g = str.getDrawGraphics();

        screen.render((int)x, (int)y, fractal);

        screen.setFontColor(0xffffff);

        screen.setFont("Arial", 14);

        screen.text("日本語テキスト", WIDTH/2, HEIGHT/2);

        //screen.background(0x000000);

        g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);

        g.dispose();
        str.show();
    }
    float tick = 0;
    public void tick(){
        tick+=0.01;
        x+=Math.sin(tick)*1.0;
        y+=Math.cos(tick)*0.2;
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