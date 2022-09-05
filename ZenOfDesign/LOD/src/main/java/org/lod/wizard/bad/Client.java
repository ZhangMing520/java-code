package org.lod.wizard.bad;

/**
 * @author zhangming
 * <p>
 * {@link Wizard}类把太多的方法暴露给{@link InstallSoftware}类，两者的朋友关系太亲密了，耦合关系变得异常牢固。
 * <p>
 * 如果要将{@link Wizard}类中的{@link Wizard#first()}方法返回值的类型由int修改为boolean，就需要修改{@link InstallSoftware}类，
 * 从而把修改变更的风险扩散开了
 */
public class Client {

    public static void main(String[] args) {
        InstallSoftware installSoftware = new InstallSoftware();
        installSoftware.installWizard(new Wizard());
    }
}
