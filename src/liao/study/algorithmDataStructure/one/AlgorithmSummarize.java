package liao.study.algorithmDataStructure.one;

/*
 * @description:
 *  何为算法：
 *      概述：1：借助某种工具，遵照一定规则，以明确而机械的形式进行。
 *           2：算法，即特定计算模型下，旨在解决特定问题的指令序列。
 *           3：计算模型 = 计算机 = 信息处理工具
 *
 *              需要满足如下条件 = 算法
 *                  输入  待处理的信息 - 问题
 *                  输出  经处理的结果 - 答案
 *                      正确性：确实可以解决指定的问题
 *                      确定性：任一算法都可以描述为一个由基本操作组成的序列
 *                      可行性：每一基本操作都可实现，且在常数时间内完成。
 *                      有穷性：对于任何输入，经有穷次基本操作，都可以得到输出。
 *
 *
 *
 *  algorithm - 算法 (傲歌睿赠)
 *  data structure - 数据结构
 *      structure (斯拽科特)
 * @author: Liao
 */
public class AlgorithmSummarize {

    public static void main(String[] args) {
        int hailStone = hailStone(5);
        System.out.println(hailStone);
    }

    private static int count = 0;

    /**
     * HailStone 序列
     * @return 递归次数
     */
    private static int hailStone(int n) {

        //最终值
        int a = 1;

        while (n < 0) {
            if (n % 2 == 0) {
                n = n / 2;
            }else {
                n = n * 3 + 1;
            }
            count++;
        }

        return count;
    }

}
