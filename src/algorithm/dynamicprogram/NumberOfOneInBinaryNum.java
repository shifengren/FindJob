package algorithm.dynamicprogram;

/**
 *  see detailes in http://www.jianshu.com/p/558b1ae429fa
 */
public class NumberOfOneInBinaryNum {

    private static int numOne(int v){
        int num = 0;
        while(v!=0){
            v &= (v-1);
            num++;
        }
        return num;
    }

    public static void main(String[] args){
        System.out.println(numOne(-3));
    }

}
