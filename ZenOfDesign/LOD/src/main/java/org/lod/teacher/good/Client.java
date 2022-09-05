package org.lod.teacher.good;


import java.util.ArrayList;
import java.util.List;

public class Client {

    public static void main(String[] args) {
        List<Girl> girls = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            girls.add(new Girl());
        }
        Teacher teacher = new Teacher();
        teacher.command(new GroupLeader(girls));
    }
}
