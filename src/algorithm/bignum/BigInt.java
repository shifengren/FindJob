package algorithm.bignum;

/**
 * 实现高精度整数
 * 已经实现的操作 + -
 */
public class BigInt {

    private static final int PLUS = 1;
    private static final int MINUS = -1;
    private static final int MAXSIZE = 100000;

    // 从右向左存储，每位数字用一个字节表示, 最`右边`是高位，`左边`时低位
    char[] digits;
    int sign;  // PLUS or MINUS
    int lastDigitIdx; // 最高位所对应的下标
    int size=0; // number of digits;

    public BigInt(){
        this(MAXSIZE);
    }
    public BigInt(int capacity){
        digits = new char[capacity];
        sign = PLUS;
    }
    public BigInt(String numStr){
        sign = numStr.charAt(0) == '-' ? MINUS : PLUS;
        size = sign==PLUS ? numStr.length() : numStr.length()-1;
        lastDigitIdx = 0;
        digits = new char[size];
        int limit = sign==MINUS ? 1 : 0;
        for(int i=numStr.length()-1; i>=limit; --i){
            digits[lastDigitIdx++] = numStr.charAt(i);
        }
        lastDigitIdx--;
    }
    public int size(){
        return this.size;
    }
//    @Override
    public String toString() {
        String s = "";
        if (sign == MINUS) s = "-";
        for(int i=lastDigitIdx; i>=0; --i)
            s += digits[i];
        return s;
    }

    // 加法
    // 麻烦的地方:符号位的处理
    // case1. (+a) + (+b) =+(a+b);  case 2. (-a)+(-b) = -(a+b)
    // case3. (+a) + (-b) = a-b[变减法];  case 4. (-a) + (+b) = b-a[变减法];
    public static BigInt plus(BigInt a, BigInt b) {
        // 两数相加，和的最大位数 = Max(a.size(), b.size())+1;
        BigInt c = new BigInt(Math.max(a.size(), b.size())+1);
        if (a.sign == b.sign) { // case 1,2
            c.sign = a.sign;
            plusAbs(a, b, c);

        } else { // case 3,4
            if (a.sign == MINUS) { // mean b.sign == PLUS
                a.sign = PLUS;
                sub(b, a); // a and b are PLUS number
                a.sign = MINUS;
            } else { // mean b.sign == MINUS
                b.sign = PLUS;
                sub(a, b); // a and b are PLUS number
                b.sign = MINUS;
            }
        }
        return c;
    }

    /**
     * 绝对值加法
     */
    // 首先把结果的所有数字初始化为0，则最高位的进位无需特殊处理
    // 最后调用zero_justify来调整最高位下标lastDigitIdx的值。同时也把-0更正为0
    public static void plusAbs(BigInt a, BigInt b, BigInt c) {
        c.lastDigitIdx = Math.max(a.lastDigitIdx, b.lastDigitIdx);
        int carry = 0;
        int ai=0, bi=0;
        for (int i = 0; i <= c.lastDigitIdx; ++i) {
            ai = i < a.size() ? (a.digits[i]-'0') : 0;
            bi = i < b.size() ? (b.digits[i]-'0') : 0;
            int tmp = (carry + (ai+bi));
            c.digits[i] = (char) (tmp % 10 + '0');
            carry = tmp / 10;
        }
        zeroJustify(c);
    }

    /**
     * 修改lastDigitIdx
     */
    // 在每个运算的结尾调用该函数时有益无害的
    private static void zeroJustify(BigInt c) {
        while (c.lastDigitIdx > 0 && (c.digits[c.lastDigitIdx] == '0'))
            c.lastDigitIdx--;
        if (c.lastDigitIdx == 0 && c.digits[0] == '0')
            c.sign = PLUS; // 把-0 修改为 +0
    }

    // 减法
    // 只有两个正数相减，才执行真正的减法
    // 1. (-a) - (+b) = (-a) + (-b)
    // 2. (+a) - (-b) = (+a) + (+b)
    // 3. (-a) - (-b) = (-a) + (+b)
    // 上面三种情况，只需要改变减数的符号。注意最后一种情况，会进一步变成b-a,然后，再次调用减法函数

    // 加法
    // (+a) + (+b) = +(a+b) [加法];  (-a)+(-b) = -(a+b) [加法]
    // (+a) + (-b) = (a-b) [变减法];  (-a) + (+b) = b-a [变减法];

    // 减法
    // 1. (+a) - (+b) = (a-b) [减法];  2. (-a) - (+b) = -(a+b) [变加法]
    // 3. (+a) - (-b) = +(a+b) [变加法];  4. (-a) - (-b) = (b-a) [减法]

    // 减法中的符号处理
    // 首先判断两个数中是否至少有一个是负数, 若是，则改变减数的符号，然后执行加法
    // 直接计算前，先要比较a和b的绝对值
    public static BigInt sub(BigInt a, BigInt b) { // c = a-b
        BigInt c = new BigInt(Math.max(a.size(), b.size())+1);
        if (a.sign == b.sign) { // 1, 4
            // 比较绝对值
            if (compare(a, b) == PLUS) { // a>b
                c.sign = PLUS;
                subAbs(a, b, c);
            } else { // a <= b
                c.sign = MINUS;
                subAbs(b, a, c);
            }
        } else { // 2,3
            c.sign = a.sign;
            plusAbs(a, b, c);
        }
        return c;
    }

    /**
     * 绝对值减法, 需要保证差不是负数
     */
    // 和加法类似，但需要考虑借位
    public static void subAbs(BigInt a, BigInt b, BigInt c) { // c = a-b s.t a>=b
        c.lastDigitIdx = Math.max(a.lastDigitIdx, b.lastDigitIdx);
        int borrow = 0;
        int ai=0, bi=0;
        for (int i = 0; i <= c.lastDigitIdx; ++i) {
            ai = i<a.size() ? (a.digits[i] - '0') : 0;
            bi = i<b.size() ? (b.digits[i] - '0') : 0;
            int v = (ai - borrow - bi);
            if (ai > 0)
                borrow = 0;

            if (v < 0) {
                v += 10;
                borrow = 1;
            }
            c.digits[i] = (char)((v + '0'));
        }
        zeroJustify(c);
    }

    /**
     * 比较运算
     */
    // 从符号位开始，从左到右 进行
    // a>b return 1;
    // a<b return -1;
    // a==b return 0;
    public static int compare(BigInt a, BigInt b) {
        if (a.sign == MINUS && b.sign == PLUS) return MINUS;
        if (a.sign == PLUS && b.sign == MINUS) return PLUS;

        // a b has the same sign
        // a.lastDigitIdx < b.lastDigitIdx:
        //       a,b都是正数: a < b return -1;
        //       a,b都是负数: a > b return 1;
        // a.lastDigitIdx > b.lastDigitIdx:
        //       a,b都是正数: a > b return 1;
        //       a,b都是负数: a < b return -1;
        if (b.lastDigitIdx > a.lastDigitIdx) return MINUS * a.sign;
        if (a.lastDigitIdx > b.lastDigitIdx) return PLUS * a.sign;

        // 从高位开始判断
        // a and b has the same lastDigitIdx
        for (int i = a.lastDigitIdx; i >= 0; --i) {
            if (a.digits[i] > b.digits[i])
                return PLUS * a.sign;
            if (b.digits[i] > a.digits[i])
                return MINUS * a.sign;
        }
        return 0;
    }


    public static void main(String[] args) {

        BigInt n1 = new BigInt("123456");
        BigInt n2 = new BigInt("987654321");

        BigInt c = BigInt.sub(n1, n2);

        System.out.println(c);

    }


}
