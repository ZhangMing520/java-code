package com.demo.works.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: zhangming
 * @Date:Create in 7/7/18 9:54 PM
 * @Description: git diff 的 java 实现
 */
public class GitDiff {
    public static class Node {
        int st;
        String subTpl;
        int length;
        int op;

        public Node(int st, int length, int op) {
            this.st = st;
            this.length = length;
            this.op = op;
        }

        public Node(int st, int length, int op, String subTpl) {
            this.length = length;
            this.op = op;
            this.st = st;
            this.subTpl = subTpl;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "length=" + length +
                    ", st=" + st +
                    ", op=" + op +
                    '}';
        }
    }

    // 下标都是偏移+1
    // 1是相同 2 是多了 3 是少了
    public static List<Node> LCS(String tpl, String str, int strSt) {
        if (tpl.length() == 0 && str.length() == 0) {
            return new ArrayList<>();
        } else if (tpl.length() == 0) {
            return new ArrayList<>(Collections.singletonList(new Node(strSt, str.length(), 2)));
        } else if (str.length() == 0) {
            return new ArrayList<>(
                    Collections.singletonList(new Node(strSt, tpl.length(), 3, tpl)));
        }

        int[][] dp = new int[tpl.length() + 1][str.length() + 1];
        int maxi, maxj, maxk;
        maxi = maxj = maxk = 0;
        for (int i = 1; i <= tpl.length(); i++) {
            for (int j = 1; j <= str.length(); j++) {
                if (tpl.charAt(i - 1) == str.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] >= maxk) {
                        maxk = dp[i][j];
                        maxi = i;
                        maxj = j;
                    }
                }
            }
        }

        List<Node> list = new ArrayList<>();
        if (maxk == 0) {
            list.add(new Node(strSt, tpl.length(), 3, tpl));
            list.add(new Node(strSt, str.length(), 2));
        } else {
            list.addAll(LCS(tpl.substring(0, maxi - maxk), str.substring(0, maxj - maxk), strSt));

            list.add(new Node(maxj - maxk, maxk, 1));

            list.addAll(
                    LCS(tpl.substring(maxi, tpl.length()), str.substring(maxj, str.length()), maxj));
        }
        return list;
    }

    public static void main(String args[]) {

        String tpl = "【#company#】您的验证码是#code#";
        String str = "你好【啊啊啊】您的验证码是123";
        List<Node> list = LCS(tpl, str, 0);
        for (Node node : list) {
            if (node.op == 1) {
                System.out.println("sam:" + str.substring(node.st, node.st + node.length));
            } else if (node.op == 2) {
                System.out.println("add:" + str.substring(node.st, node.st + node.length));
            } else if (node.op == 3) {
                System.out.println("sub:" + node.subTpl);
            }
        }
    }

}
