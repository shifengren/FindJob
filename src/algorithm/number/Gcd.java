package algorithm.number;

public class Gcd {

    public static int gcd(int x, int y){
        if(x <y )
            return gcd(y, x);
        if(y==0) return x;
        else return gcd(x-y, y);
    }

    public static int gcd2(int x, int y){
        return y==0 ? x : gcd2(y, x%y);
    }

    public static int gcd3(int x, int y){
        if(x<y) return gcd3(y, x);
        if(y==0) return x;
        else{
            if((x & 0x1) == 0){
                if((y&0x1) == 0) return (gcd3(x>>1, y>>1) << 1);
                else return gcd3(x>>1, y);
            }else{
                if((y&0x1) == 0 ) return gcd3(x, y>>1);
                else return gcd3(y, x-y);
            }
        }
    }

    public static void main(String[] args){
        System.out.println(gcd(3,2));
    }

}
