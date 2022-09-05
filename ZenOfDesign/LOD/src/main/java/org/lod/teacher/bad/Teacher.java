package org.lod.teacher.bad;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangming
 */
public class Teacher {

    public void command(GroupLeader groupLeader) {
        List<Girl> girls = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            girls.add(new Girl());
        }
        groupLeader.countGirls(girls);
    }
}
