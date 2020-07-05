package com.example.works.list;

import com.alibaba.fastjson.JSONArray;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangming
 * @date 2020/6/4 22:48
 */
public class ListTree {

    public static void main(String[] args) {
        List<ProcessNodePath> pathList = JSONArray.parseArray("[{\"createTime\":1591180703000,\"id\":787,\"modifyTime\":1591180703000,\"nodeNext\":1162,\"nodePrior\":1161,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591180703000,\"id\":788,\"modifyTime\":1591180703000,\"nodeNext\":1163,\"nodePrior\":1162,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591180703000,\"id\":789,\"modifyTime\":1591236497000,\"nodeNext\":1164,\"nodePrior\":1174,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591180703000,\"id\":790,\"modifyTime\":1591180703000,\"nodeNext\":1165,\"nodePrior\":1164,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591180703000,\"id\":791,\"modifyTime\":1591180703000,\"nodeNext\":1166,\"nodePrior\":1165,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591180703000,\"id\":792,\"modifyTime\":1591180703000,\"nodeNext\":1167,\"nodePrior\":1166,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591180703000,\"id\":793,\"modifyTime\":1591180703000,\"nodeNext\":1168,\"nodePrior\":1167,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591180703000,\"id\":794,\"modifyTime\":1591180703000,\"nodeNext\":1169,\"nodePrior\":1168,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591180703000,\"id\":795,\"modifyTime\":1591180703000,\"nodeNext\":1170,\"nodePrior\":1169,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591180703000,\"id\":796,\"modifyTime\":1591180703000,\"nodeNext\":1171,\"nodePrior\":1170,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591180703000,\"id\":797,\"modifyTime\":1591180703000,\"nodeNext\":1172,\"nodePrior\":1171,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591180703000,\"id\":798,\"modifyTime\":1591180703000,\"nodeNext\":1173,\"nodePrior\":1172,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591236497000,\"id\":799,\"modifyTime\":1591236497000,\"nodeNext\":1174,\"nodePrior\":1163,\"pathType\":1,\"processId\":87,\"state\":0},{\"createTime\":1591236497000,\"id\":800,\"modifyTime\":1591236497000,\"nodeNext\":1175,\"nodePrior\":1163,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591236497000,\"id\":801,\"modifyTime\":1591236590000,\"nodeNext\":1174,\"nodePrior\":1176,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591236590000,\"id\":802,\"modifyTime\":1591236590000,\"nodeNext\":1176,\"nodePrior\":1175,\"pathType\":1,\"processId\":87,\"state\":0},{\"createTime\":1591236590000,\"id\":803,\"modifyTime\":1591236590000,\"nodeNext\":1177,\"nodePrior\":1175,\"pathType\":0,\"processId\":87,\"state\":0},{\"createTime\":1591236590000,\"id\":804,\"modifyTime\":1591236590000,\"nodeNext\":1176,\"nodePrior\":1177,\"pathType\":0,\"processId\":87,\"state\":0}]", ProcessNodePath.class);

        getAllInvalidPrior(pathList, null );

        for (ProcessNodePath nodePath : pathList) {
            System.out.println(nodePath);
        }
    }


    private static void getAllInvalidPrior(List<ProcessNodePath> allNodePath, ProcessNodePath processNodePath) {
        // nodePrior 相同个数 > 2  分叉闭合
        Map<Long, Long> nextCountMap = allNodePath.stream().collect(Collectors.groupingBy(ProcessNodePath::getNodeNext, Collectors.counting()));
        // nodeNext 相同个数 > 2 分叉开始
        Map<Long, Long> priorCountMap = allNodePath.stream().collect(Collectors.groupingBy(ProcessNodePath::getNodePrior, Collectors.counting()));

        if (priorCountMap.containsValue(2L) && nextCountMap.containsValue(2L)) {
            // 有分叉
            final List<Long> priorList = new ArrayList<>();
            priorCountMap.forEach((k, v) -> {
                if (v == 2L) {
                    priorList.add(k);
                }
            });
            final List<Long> nextList = new ArrayList<>();
            nextCountMap.forEach((k, v) -> {
                if (v == 2L) {
                    nextList.add(k);
                }
            });
            // 找出最小节点
            ProcessNodePath firstNodePath = allNodePath.stream().min(Comparator.comparing(ProcessNodePath::getNodePrior)).get();
            // <nodePrior,List<NodePath>>
            Map<Long, List<ProcessNodePath>> priorListMap = allNodePath.stream().collect(Collectors.groupingBy(ProcessNodePath::getNodePrior, Collectors.toList()));
            ProcessNodePath currentNodePath = firstNodePath;
            ProcessNodePath backNodePath = null;
            // 0 作废state是1，删除是2
            int state = 0;
            while (true) {
                Long nodePrior = currentNodePath.getNodePrior();
                Long nodeNext = currentNodePath.getNodeNext();
                if (priorList.contains(nodeNext)) {
                    // 开始分叉
                    state = 1;
                } else if (nextList.contains(nodePrior) && backNodePath != null) {
                    currentNodePath = backNodePath;
                    backNodePath = null;
                    continue;
                } else if (nextList.remove(nodePrior) && nextList.size() == 0) {
                    // 闭合完毕
                    state = 2;
                }

                if (state != 0) {
                    currentNodePath.setState(state);
                }
                List<ProcessNodePath> pathList = priorListMap.get(nodeNext);
                if (pathList == null || pathList.size() == 0) {
                    break;
                }
                currentNodePath = pathList.get(0);
                if (pathList.size() > 1) {
                    // 另一个分支
                    backNodePath = pathList.get(1);
                }
            }
        }
    }
}
