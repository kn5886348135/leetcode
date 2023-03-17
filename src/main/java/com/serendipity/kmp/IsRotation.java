package com.serendipity.kmp;

/**
 * @author jack
 * @version 1.0
 * @description 判断str1和str2是否是旋转字符串
 * @date 2023/03/17/20:32
 */
public class IsRotation {

    public static void main(String[] args) {
        String str1 = "guanyunchang";
        String str2 = "changguanyun";
        System.out.println(isRotation(str1, str2));
    }

    public static boolean isRotation(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }
        String str = str2 + str2;
        return getIndexOf(str, str1) != -1;
    }

    public static int getIndexOf(String str1, String str2) {
        if (str1.length() < str2.length()) {
            return -1;
        }

        char[] sch = str1.toCharArray();
        char[] mch = str2.toCharArray();

        int sIndex = 0;
        int mIndex = 0;
        int[] next = getNextArray(mch);
        while (sIndex < sch.length&&mIndex<mch.length) {
            if (sch[sIndex] == mch[mIndex]) {
                sIndex++;
                mIndex++;
            } else if (next[mIndex] == -1) {
                sIndex++;
            } else {
                mIndex = next[mIndex];
            }
        }
        return mIndex == mch.length ? sIndex - mIndex : -1;
    }

    public static int[] getNextArray(char[] mch) {
        if (mch.length == 1) {
            return new int[]{ -1 };
        }
        int[] next = new int[mch.length];
        next[0] = -1;
        next[1] = 0;
        int pos = 2;
        int cn = 0;
        while (pos < next.length) {
            if (mch[pos - 1] == mch[cn]) {
                next[pos++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[pos++] = 0;
            }
        }
        return next;
    }

}
