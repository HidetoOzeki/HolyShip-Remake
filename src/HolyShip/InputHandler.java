package HolyShip;
import java.awt.event.*;
public class InputHandler extends KeyAdapter{
    public boolean[] keys = new boolean[0xffff];
    public InputHandler(){

    }

    public void toggle(int kc,boolean pressed){
        if(kc>0&&kc < keys.length)keys[kc] = pressed;
    }

    @Override
    public void keyPressed(KeyEvent k){
        toggle(k.getKeyCode(),true);
    }

    @Override
    public void keyReleased(KeyEvent k){
        toggle(k.getKeyCode(),false);
    }
}