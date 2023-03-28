package com.serendipity.skills.suffixarray2;

/**
 * @author jack
 * @version 1.0
 * @description 给定两个字符串str1和str2，
 *              想把str2整体插入到str1中的某个位置
 *              形成最大的字典序
 *              返回字典序最大的结果
 * @date 2023/03/28/14:31
 */
public class InsertS2MakeMostAlphabeticalOrder {

    public static void main(String[] args) {
        int range = 10;
        int len = 50;
        int testTime = 100000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int s1Len = (int) (Math.random() * len);
            int s2Len = (int) (Math.random() * len);
            String s1 = randomNumberString(s1Len, range);
            String s2 = randomNumberString(s2Len, range);
            String ans1 = maxCombine1(s1, s2);
            String ans2 = maxCombine2(s1, s2);
            if (!ans1.equals(ans2)) {
                System.out.println("Oops!");
                System.out.println(s1);
                System.out.println(s2);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("功能测试结束");

        System.out.println("==========");

        System.out.println("性能测试开始");
        int s1Len = 1000000;
        int s2Len = 500;
        String s1 = randomNumberString(s1Len, range);
        String s2 = randomNumberString(s2Len, range);
        long start = System.currentTimeMillis();
        maxCombine2(s1, s2);
        long end = System.currentTimeMillis();
        System.out.println("运行时间 : " + (end - start) + " ms");
        System.out.println("性能测试结束");
    }

    // 暴力方法
    public static String maxCombine1(String str1, String str2) {
        if (str1 == null || str1.length() == 0) {
            return str2;
        }
        if (str2 == null || str2.length() == 0) {
            return str1;
        }
        String p1 = str1 + str2;
        String p2 = str2 + str1;
        String ans = p1.compareTo(p2) > 0 ? p1 : p2;
        for (int end = 1; end < str1.length(); end++) {
            String cur = str1.substring(0, end) + str2 + str1.substring(end);
            if (cur.compareTo(ans) > 0) {
                ans = cur;
            }
        }
        return ans;
    }

    // 利用DC3生成后缀数组
    public static String maxCombine2(String str1, String str2) {
        if (str1 == null || str1.length() == 0) {
            return str2;
        }
        if (str2 == null || str2.length() == 0) {
            return str1;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int n = chs1.length;
        int m = chs2.length;
        int min = chs1[0];
        int max = chs1[0];
        for (int i = 1; i < n; i++) {
            min = Math.min(min, chs1[i]);
            max = Math.max(max, chs1[i]);
        }
        for (int i = 0; i < m; i++) {
            min = Math.min(min, chs2[i]);
            max = Math.max(max, chs2[i]);
        }
        int[] all = new int[n + m + 1];
        int index = 0;
        for (int i = 0; i < n; i++) {
            all[index++] = chs1[i] - min + 2;
        }
        all[index++] = 1;
        for (int i = 0; i < m; i++) {
            all[index++] = chs2[i] - min + 2;
        }
        DC3 dc3 = new DC3(all, max - min + 2);
        int[] rank = dc3.rank;
        int comp = n + 1;
        for (int i = 0; i < n; i++) {
            // 第一次出现str1的字典序小于str2
            if (rank[i] < rank[comp]) {
                int best = bestSplit(str1, str2, i);
                return str1.substring(0, best) + str2 + str1.substring(best);
            }
        }
        return str1 + str2;
    }

    // 决定str2的位置
    public static int bestSplit(String str1, String str2, int first) {
        int n = str1.length();
        int m = str2.length();
        int end = n;
        for (int i = first, j = 0; i < n && j < m; i++, j++) {
            if (str1.charAt(i) < str2.charAt(j)) {
                end = i;
                break;
            }
        }
        String bestPrefix = str2;
        int bestSplit = first;
        for (int i = first + 1, j = m - 1; i <= end; i++, j--) {
            String curPrefix = str1.substring(first, i) + str2.substring(0, j);
            if (curPrefix.compareTo(bestPrefix) >= 0) {
                bestPrefix = curPrefix;
                bestSplit = i;
            }
        }
        return bestSplit;
    }

    public static class DC3 {

        public int[] sa;
        public int[] rank;

        public DC3(int[] nums, int max) {
            this.sa = sa(nums, max);
            this.rank = rank();
        }

        private int[] sa(int[] nums, int max) {
            int len = nums.length;
            // nums长度可能小于3
            int[] arr = new int[len + 3];
            for (int i = 0; i < len; i++) {
                arr[i] = nums[i];
            }
            return skew(arr, len, max);
        }

        private int[] skew(int[] nums, int n, int max) {
            int n0 = (n + 2) / 3, n1 = (n + 1) / 3, n2 = n / 3, n02 = n0 + n2;
            int[] s12 = new int[n02 + 3], sa12 = new int[n02 + 3];
            for (int i = 0, j = 0; i < n + (n0 - n1); ++i) {
                if (0 != i % 3) {
                    s12[j++] = i;
                }
            }
            // 基数排序
            radixPass(nums, s12, sa12, 2, n02, max);
            radixPass(nums, sa12, s12, 1, n02, max);
            radixPass(nums, s12, sa12, 0, n02, max);
            int name = 0, c0 = -1, c1 = -1, c2 = -1;
            for (int i = 0; i < n02; ++i) {
                if (c0 != nums[sa12[i]] || c1 != nums[sa12[i] + 1] || c2 != nums[sa12[i] + 2]) {
                    name++;
                    c0 = nums[sa12[i]];
                    c1 = nums[sa12[i] + 1];
                    c2 = nums[sa12[i] + 2];
                }
                if (1 == sa12[i] % 3) {
                    s12[sa12[i] / 3] = name;
                } else {
                    s12[sa12[i] / 3 + n0] = name;
                }
            }
            // 递归
            if (name < n02) {
                sa12 = skew(s12, n02, name);
                for (int i = 0; i < n02; i++) {
                    s12[sa12[i]] = i + 1;
                }
            } else {
                for (int i = 0; i < n02; i++) {
                    sa12[s12[i] - 1] = i;
                }
            }
            // 拿到s0的顺序
            int[] s0 = new int[n0], sa0 = new int[n0];
            for (int i = 0, j = 0; i < n02; i++) {
                if (sa12[i] < n0) {
                    s0[j++] = 3 * sa12[i];
                }
            }
            // 拿到全部顺序
            radixPass(nums, s0, sa0, 0, n0, max);
            int[] sa = new int[n];
            for (int p = 0, t = n0 - n1, k = 0; k < n; k++) {
                int i = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                int j = sa0[p];
                if (sa12[t] < n0 ? leq(nums[i], s12[sa12[t] + n0], nums[j], s12[j / 3]) :
                        leq(nums[i], nums[i + 1], s12[sa12[t] - n0 + 1], nums[j], nums[j + 1], s12[j / 3 + n0])) {
                    sa[k] = i;
                    t++;
                    if (t == n02) {
                        for (k++; p < n0; p++, k++) {
                            sa[k] = sa0[p];
                        }
                    }
                } else {
                    sa[k] = j;
                    p++;
                    if (p == n0) {
                        for (k++; t < n02; t++, k++) {
                            sa[k] = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                        }
                    }
                }
            }
            return sa;
        }

        private void radixPass(int[] nums, int[] input, int[] output, int offset, int n, int k) {
            int[] cnt = new int[k + 1];
            for (int i = 0; i < n; ++i) {
                cnt[nums[input[i] + offset]]++;
            }
            for (int i = 0, sum = 0; i < cnt.length; ++i) {
                int t = cnt[i];
                cnt[i] = sum;
                sum += t;
            }
            for (int i = 0; i < n; ++i) {
                output[cnt[nums[input[i] + offset]]++] = input[i];
            }
        }

        private boolean leq(int a1, int a2, int b1, int b2) {
            return a1 < b1 || (a1 == b1 && a2 <= b2);
        }

        private boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
            return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
        }

        private int[] rank() {
            int len = this.sa.length;
            int[] ans = new int[len];
            for (int i = 0; i < len; i++) {
                ans[this.sa[i]] = i;
            }
            return ans;
        }
    }

    public static String randomNumberString(int len, int range) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * range) + '0');
        }
        return String.valueOf(str);
    }
}
