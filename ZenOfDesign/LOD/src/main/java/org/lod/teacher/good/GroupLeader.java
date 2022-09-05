package org.lod.teacher.good;

import java.util.List;

public class GroupLeader {
    private List<Girl> girls;

    public GroupLeader(List<Girl> girls) {
        this.girls = girls;
    }

    public void countGirls() {
        System.out.println("女生数量是：" + girls.size());
    }
}
