package HolyShip.gfx;

public class UVcoord {
    public double u,v,w;

    public UVcoord(){
        w = 1.0;
        u = 0.0;
        v = 0.0;
    }

    public UVcoord(double u,double v, double w){
        this.u = u;
        this.v = v;
        this.w = w;
    }
}
