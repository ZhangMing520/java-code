package com.example.works.list;

/**
 * @author zhangming
 * @date 2020/6/4 22:49
 */


import java.util.Date;

/**
 * 环节路径指向
 *
 * @author hao.caosh@ttpai.cn
 * @time 2019-09-04 11:25
 */
public class ProcessNodePath {

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 路径类型(0.流转->实线 1.等待->虚线 2.移交->箭头
     */
    private Integer pathType;
    /**
     * 来向节点
     */
    private Long nodePrior;
    /**
     * 去向节点
     */
    private Long nodeNext;
    /**
     * 流程类型
     */
    private Integer nodeType;
    /**
     * 流程ID
     */
    private Long processId;
    /**
     * 节点状态
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPathType() {
        return pathType;
    }

    public void setPathType(Integer pathType) {
        this.pathType = pathType;
    }

    public Long getNodePrior() {
        return nodePrior;
    }

    public void setNodePrior(Long nodePrior) {
        this.nodePrior = nodePrior;
    }

    public Long getNodeNext() {
        return nodeNext;
    }

    public void setNodeNext(Long nodeNext) {
        this.nodeNext = nodeNext;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "ProcessNodePath{" +
                "id=" + id +
                ", pathType=" + pathType +
                ", nodePrior=" + nodePrior +
                ", nodeNext=" + nodeNext +
                ", nodeType=" + nodeType +
                ", processId=" + processId +
                ", state=" + state +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
