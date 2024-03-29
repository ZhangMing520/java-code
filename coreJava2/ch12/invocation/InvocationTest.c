/**
 * @file InvocationTest.c
 * @author your name (you@domain.com)
 * @brief
 *
 * Welcome.java 需要编译 Welcome.class
 *
 * export LD_LIBRARY_PATH=$JAVA_HOME/jre/lib/amd64/server:$LD_LIBRARY_PATH
 *
 * gcc -I $JAVA_HOME/include -I $JAVA_HOME/include/linux -L$JAVA_HOME/jre/lib/amd64/server InvocationTest.c -ljvm  -o InvocationTest
 *
 * $JAVA_HOME/jre/lib/amd64/server 此路径也可能是 $JAVA_HOME/jre/lib/i386/client，找到 libjvm.so 即可
 *
 * Windows
   $env:inc_vc="C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Tools\MSVC\14.29.30133\include"
   $env:inc_sdk_sh="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\shared"
   $env:inc_sdk_um="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\um"
   $env:inc_sdk_ucrt="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\ucrt"
   $env:inc_sdk_winrt="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\winrt"
   $env:inc_vc_lib="C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Tools\MSVC\14.29.30133\lib\x64\*.lib"
   $env:inc_sdk_um_lib="C:\Program Files (x86)\Windows Kits\10\Lib\10.0.18362.0\um\x64\*.Lib"
   $env:inc_sdk_ucrt_lib="C:\Program Files (x86)\Windows Kits\10\Lib\10.0.18362.0\ucrt\x64\*.lib"

   cl -D_WINDOWS -I $env:JAVA_HOME\include -I $env:JAVA_HOME\include\win32 -I "$env:inc_vc"  -I "$env:inc_sdk_ucrt" -I "$env:inc_sdk_sh" -I "$env:inc_sdk_winrt" -I "$env:inc_sdk_um"  InvocationTest.c  "$env:JAVA_HOME\lib\jvm.lib" "$env:inc_sdk_um_lib" "$env:inc_vc_lib" "$env:inc_sdk_ucrt_lib"

 *
 *
 * Cygwin
 * g++ -D_WINDOWS -mno-cygwin -I $JAVA_HOME\include -I $JAVA_HOME\include\win32 -D__int64="long long" -I c:\cygwin\usr\include\w32api InvocationTest.c -o InvocationTest
 *
 * @version 0.1
 * @date 2022-07-21
 *
 * @copyright Copyright (c) 2022
 *
 */
#include <jni.h>
#include <stdlib.h>
#include <string.h>

#ifdef _WINDOWS

// 在 Windows 下，动态链接到 jre/bin/client/jvm.dll 中的 JNI_CreateJavaVM 函数变得非常困难，因为 Vista 改变了链接规则，
// 而 Oracle 的类库仍旧依赖于旧版本的 C 运行时类库。
// 手动加载类库
#include <windows.h>
static HINSTANCE loadJVMLibrary(void);
typedef jint(JNICALL *CreateJavaVM_t)(JavaVM **, void **, JavaVMInitArgs *);

#endif

int main()
{
    JavaVMOption options[2];
    JavaVMInitArgs vm_args;
    JavaVM *jvm;
    JNIEnv *env;
    long status;

    jclass class_Welcome;
    jclass class_String;
    jobjectArray args;
    jmethodID id_main;

#ifdef _WINDOWS
    HINSTANCE hjvmlib;
    CreateJavaVM_t createJavaVM;
#endif

    options[0].optionString = "-Djava.class.path=.";
    memset(&vm_args, 0, sizeof(vm_args));
    vm_args.version = JNI_VERSION_1_2;
    vm_args.nOptions = 1;
    vm_args.options = options;

#ifdef _WINDOWS
    hjvmlib = loadJVMLibrary();
    createJavaVM = (CreateJavaVM_t)GetProcAddress(hjvmlib, "JNI_CreateJavaVM");
    status = (*createJavaVM)(&jvm, (void **)&env, &vm_args);
#else
    status = JNI_CreateJavaVM(&jvm, (void **)&env, &vm_args);
#endif

    if (status == JNI_ERR)
    {
        fprintf(stderr, "Error creating VM\n");
        return 1;
    }

    class_Welcome = (*env)->FindClass(env, "Welcome");
    id_main = (*env)->GetStaticMethodID(env, class_Welcome, "main", "([Ljava/lang/String;)V");

    class_String = (*env)->FindClass(env, "java/lang/String");
    args = (*env)->NewObjectArray(env, 0, class_String, NULL);

    (*env)->CallStaticVoidMethod(env, class_Welcome, id_main, args);

    (*jvm)->DestroyJavaVM(jvm);

    return 0;
}

#ifdef _WINDOWS

static int GetStringFromRegistry(HKEY key, const char *name, char *buf, jint bufsize)
{
    DWORD type, size;

    return RegQueryValueEx(key, name, 0, &type, 0, &size) == 0 && type == REG_SZ && size < (unsigned int)bufsize && RegQueryValueEx(key, name, 0, 0, buf, &size) == 0;
}

static void GetPublicJREHome(char *buf, jint bufsize)
{
    HKEY key, subkey;
    char version[MAX_PATH];

    // find the current version of the JRE
    char *JRE_KEY = "SOFTWARE\\JavaSoft\\Java Runtime Environment";
    if (RegOpenKeyEx(HKEY_LOCAL_MACHINE, JRE_KEY, 0, KEY_READ | KEY_WOW64_64KEY, &key) != 0)
    {
        fprintf(stderr, "Error opening registry key '%s'\n", JRE_KEY);
        exit(1);
    }

    if (!GetStringFromRegistry(key, "CurrentVersion", version, sizeof(version)))
    {
        fprintf(stderr, "Faild reading value of registry key:\n\t%s\\CurrentVersion\n", JRE_KEY);
        RegCloseKey(key);
        exit(1);
    }

    if (RegOpenKeyEx(key, version, 0, KEY_READ | KEY_WOW64_64KEY, &subkey) != 0)
    {
        fprintf(stderr, "Error opening registry key '%s\\%s'\n", JRE_KEY, version);
        RegCloseKey(key);
        exit(1);
    }

    if (!GetStringFromRegistry(subkey, "JavaHome", buf, bufsize))
    {
        fprintf(stderr, "Faild reading value of registry key:\n\t%s\\%s\\JavaHome\n", JRE_KEY, version);
        RegCloseKey(key);
        RegCloseKey(subkey);
        exit(1);
    }
    RegCloseKey(key);
    RegCloseKey(subkey);
}

static HINSTANCE loadJVMLibrary(void)
{
    HINSTANCE h1, h2;
    char msvcdll[MAX_PATH];
    char javadll[MAX_PATH];
    GetPublicJREHome(msvcdll, MAX_PATH);
    strcpy(javadll, msvcdll);
    strncat(msvcdll, "\\bin\\msvcr71.dll", MAX_PATH - strlen(msvcdll));
    msvcdll[MAX_PATH - 1] = '\0';
    // 应用程序是64位的，jvm.dll也是64位的
    strncat(javadll, "\\bin\\server\\jvm.dll", MAX_PATH - strlen(javadll));
    javadll[MAX_PATH - 1] = '\0';

    h1 = LoadLibrary(msvcdll);
    // 此处未加载
    printf("%s\n", msvcdll);
    // if (h1 == NULL)
    // {
    //     fprintf(stderr, "Can't load library msvcr71.dll\n");
    //     exit(1);
    // }
    h2 = LoadLibrary(javadll);
    if (h2 == NULL)
    {
        fprintf(stderr, "Can't load library jvm.dll\n");
        exit(1);
    }
    return h2;
}

#endif