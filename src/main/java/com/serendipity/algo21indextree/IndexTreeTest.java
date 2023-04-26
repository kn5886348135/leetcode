package com.serendipity.algo21indextree;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/26/19:46
 */
public class IndexTreeTest {

    public static void main(String[] args) {
        int len = 100;
        int value = 100;
        int testTimes = 2000000;
        boolean success = true;
        IndexTree tree = new IndexTree(len);
        IndextTreeFake fake = new IndextTreeFake(len);
        for (int i = 0; i < testTimes; i++) {
            int index = (int) (Math.random() * len) + 1;
            if (Math.random() <= 0.5) {
                int add = (int) (Math.random() * value);
                tree.add(index, add);
                fake.add(index, add);
            } else {
                if (tree.sum(index) != fake.sum(index)) {
                    System.out.println("index tree failed");
                    success = false;
                    break;
                }
            }
        }
        System.out.println(success ? "success" : "failed");
    }
}
