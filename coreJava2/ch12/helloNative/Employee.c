/**
 * @file Employee.c
 * @author your name (you@domain.com)
 * @brief
 * @version 0.1
 * @date 2022-07-19
 *
 * @copyright Copyright (c) 2022
 *
 * gcc -fPIC -I $JAVA_HOME/include -I $JAVA_HOME/include/linux -shared -o libEmployee.so Employee.c
 */
#include "Employee.h"

/**
 * @brief 第二个参数不再是 jclass 类型而是 jobject 类型。实际上，它和this引用等价。
 *      静态方法得到的是类的引用，而非静态方法得到的是对隐式的 this 参数对象的引用。
 *
 * @return JNIEXPORT
 */
JNIEXPORT void JNICALL Java_Employee_raiseSalary(JNIEnv *env, jobject this_obj, jdouble byPercent)
{
    // get the class
    // 类引用只在本地方法返回之前有效。因此，不能在代码中缓冲 GetObjectClass 的返回值
    // 必须在每次执行本地方法时候都调用 GetObjectClass
    jclass class_Employee = (*env)->GetObjectClass(env, this_obj);

    // get field ID
    // 开销最大的一步
    jfieldID id_salary = (*env)->GetFieldID(env, class_Employee, "salary", "D");

    // get the field value
    jdouble salary = (*env)->GetDoubleField(env, this_obj, id_salary);

    salary *= 1 + byPercent / 100;

    // set the field value
    (*env)->SetDoubleField(env, this_obj, id_salary, salary);

    /////////////// 访问静态域
    // 没有对象，必须使用 FindClass 代替 GetObjectClass 来获得类引用
    jclass class_System = (*env)->FindClass(env, "java/lang/System");

    jfieldID id_out = (*env)->GetStaticFieldID(env, class_System, "out", "Ljava/io/PrintStream");

    jobject obj_out = (*env)->GetStaticObjectField(env, class_System, id_out);
}