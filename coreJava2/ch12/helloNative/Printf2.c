/**
 * @author zhangming
 * @date   2022-07-18 22:17:03
 *
 * gcc -fPIC -I $JAVA_HOME/include -I $JAVA_HOME/include/linux -shared -o libPrintf2.so Printf2.c
 *
 * gcc -fPIC -I $JAVA_HOME/include -I $JAVA_HOME/include/linux -o Printf2.out Printf2.c
 *
 * javac Printf2Test.java
 * java -Djava.library.path=. Printf2Test
 */
#include "Printf2.h"
#include <string.h>
#include <stdlib.h>
#include <float.h>

/**
 * @brief 如果打印浮点数的格式代码不是 %w.pc 格式的（其中c是e、E、f、F、g、G中的一个），那么将不能对数字进行格式化
 *
 * @param format    a string containing a printf format specifier(such as "%8.2f"). Substrings "%%" are skipped.
 * @return char*  a pointer to the format specifier (skipping the '%') or NULL if there wasn't a unique format specifier.
 */
char *find_format(const char format[])
{
    char *p;
    char *q;

    p = strchr(format, '%');
    while (p != NULL && *(p + 1) == '%')
    {
        // skip %%
        p = strchr(p + 2, '%');
    }
    // %w 之后还有 d
    if (p == NULL)
        return NULL;
    // now check that % is unique
    p++;
    q = strchr(p, '%');
    while (q != NULL && *(q + 1) == '%')
    {
        // skip %%
        q = strchr(q + 2, '%');
    }
    if (q != NULL)
        // not unique
        return NULL;
    // skip past flags
    q = p + strspn(p, " -0+#");
    // skip past field width
    q += strspn(q, "0123456789");
    if (*q == '.')
    {
        // skip past precision
        q++;
        q += strspn(q, "0123456789");
    }

    // not a floating-point format
    if (strchr("eEfFgG", *q) == NULL)
        return NULL;

    return p;
}

JNIEXPORT jstring JNICALL Java_Printf2_sprint(JNIEnv *env, jclass cl,
                                              jstring format, jdouble x)
{
    const char *cformat;
    char *fmt;
    jstring ret;

    // 返回指向字符串的“modified UTF-8”编码的指针
    cformat = (*env)->GetStringUTFChars(env, format, NULL);
    // 从打印的字符串中找出格式化宽度
    // fmt = 8.2f
    fmt = find_format(cformat);
    if (fmt == NULL)
    {
        // 无格式化字符串，直接返回
        ret = format;
    }
    else
    {
        char *cret;
        // 获取格式化宽度，以便获取整个 cret 长度
        // width = 8
        int width = atoi(fmt);
        if (width == 0)
            width = DBL_DIG + 10;
        cret = (char *)malloc(strlen(cformat) + width);
        // format = "Amount due = %8.2f"
        sprintf(cret, cformat, x);
        // 返回一个新的 Java 字符串对象
        ret = (*env)->NewStringUTF(env, cret);
        free(cret);
    }
    // 释放 GetStringUTFChars 返回的指针
    (*env)->ReleaseStringUTFChars(env, format, cformat);
    return ret;
}

int main()
{
    char *fmt = find_format("Amount due = %8.2f");
    printf("%s", fmt);
    return 0;
}