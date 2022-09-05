package org.lod.teacher.bad;

public class Client {

    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        teacher.command(new GroupLeader());
    }
}
