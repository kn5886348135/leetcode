package com.serendipity.algo23sortedlist;

/**
 * @author jack
 * @version 1.0
 * @description 有序表接口
 * @date 2023/07/26/19:10
 */
public interface SortedList<K extends Comparable<K>, V> {

    /**
     * 以cur为顶点右旋
     *
     * @param cur 当前节点
     * @return 子树右旋后的头结点
     */
    SortedListNode<K, V> rightRotate(SortedListNode<K, V> cur);

    /**
     * 以cur为顶点左旋
     *
     * @param cur 当前节点
     * @return 子树左旋后的头结点
     */
    SortedListNode<K, V> leftRotate(SortedListNode<K, V> cur);

    /**
     * 维护当前节点的平衡
     *
     * @param cur 当前节点
     * @return 平衡后的子树的头结点
     */
    SortedListNode<K, V> maintain(SortedListNode<K, V> cur);

    /**
     * 搜索最接近key的节点
     *
     * @param key 输入查找的key
     * @return 查找结果
     */
    SortedListNode<K, V> findLastIndex(K key);

    /**
     * 搜索不小于且最靠近key的节点
     *
     * @param key 输入key
     * @return 查找结果
     */
    SortedListNode<K, V> findLastNoSmallIndex(K key);

    /**
     * 搜索不大于且最靠近key的节点
     *
     * @param key 输入key
     * @return 查找结果
     */
    SortedListNode<K, V> findLastNoBigIndex(K key);

    /**
     * 增加节点
     *
     * @param cur   当前节点
     * @param key   输入key
     * @param value 输入value
     * @return 插入的节点
     */
    SortedListNode<K, V> add(SortedListNode<K, V> cur, K key, V value);

    /**
     * 在cur这棵树上，删掉key所代表的节点
     * 返回cur这棵树的新头部
     *
     * @param cur 当前节点
     * @param key 输入key
     * @return 删除后的新节点
     */
    SortedListNode<K, V> delete(SortedListNode<K, V> cur, K key);

    /**
     * 获取整棵树的大小
     *
     * @return 整棵树的节点数
     */
    int size();

    /**
     * 判断是否包含某个key
     *
     * @param key 输入key
     * @return true，包含，false不包含
     */
    boolean containsKey(K key);

    /**
     * 对外使用，新增节点
     *
     * @param key   输入key
     * @param value 输入value
     */
    void put(K key, V value);

    /**
     * 删除节点
     *
     * @param key 输入key
     */
    void remove(K key);

    /**
     * 获取节点的value
     *
     * @param key 输入key
     * @return key对应的value
     */
    V get(K key);

    /**
     * 获取最小的key
     *
     * @return 最小的key
     */
    K firstKey();

    /**
     * 最大的一个key
     *
     * @return 最大的key
     */
    K lastKey();

    /**
     * 搜索小于key的最大值
     *
     * @param key 输入key
     * @return 小于key最右边的key
     */
    K floorKey(K key);

    /**
     * 搜索大于key的最小值
     *
     * @param key 输入key
     * @return 大于key的最小值
     */
    K ceilingKey(K key);
}
