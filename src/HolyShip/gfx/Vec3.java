package HolyShip.gfx;
public class Vec3{
    public double x,y,z;
    public Vec3(){
        x=y=z = 0.0;
    }
    public Vec3(double x,double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void rotateX(double angle){
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double yy = cos*y-sin*z;
        double zz = sin*y+cos*z;
        this.y = yy;
        this.z = zz;
    }

    public void rotateY(double angle){
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double xx = cos*x+sin*z;
        double zz = -sin*x+cos*z;
        this.x = xx;
        this.z = zz;
    }

    public void rotateZ(double angle){
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double xx = cos*x-sin*y;
        double yy = sin*x+cos*y;
        this.x = xx;
        this.y = yy;
    }

    public void add(Vec3 v){
        this.x+=v.x;
        this.y+=v.y;
        this.z+=v.z;
    }

    public void sub(Vec3 v){
        this.x-=v.x;
        this.y-=v.y;
        this.z-=v.z;
    }

    public void mult(Vec3 v){
        this.x*=v.x;
        this.y*=v.y;
        this.z*=v.z;
    }

    public void div(Vec3 v){
        this.x/=v.x;
        this.y/=v.y;
        this.z/=v.z;
    }

    public double magnitunte(){
        return Math.sqrt(x*x+y*y+z*z);
    }

    public Vec3 normal(){
        double m = this.magnitunte();
        return new Vec3(x/m,y/m,z/m);
    }
}