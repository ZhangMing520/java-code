/**
 * @file Win32RegKey.c
 * @author your name (you@domain.com)
 * @brief
 * @version 0.1
 * @date 2022-07-22
 *
 * @copyright Copyright (c) 2022

$env:inc_vc="C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Tools\MSVC\14.29.30133\include"
$env:inc_sdk_sh="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\shared"
$env:inc_sdk_um="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\um"
$env:inc_sdk_ucrt="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\ucrt"
$env:inc_sdk_winrt="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\winrt"
$env:inc_vc_lib="C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Tools\MSVC\14.29.30133\lib\x64\*.lib"
$env:inc_sdk_um_lib="C:\Program Files (x86)\Windows Kits\10\Lib\10.0.18362.0\um\x64\*.lib"
$env:inc_sdk_ucrt_lib="C:\Program Files (x86)\Windows Kits\10\Lib\10.0.18362.0\ucrt\x64\*.lib"

cl -I $env:JAVA_HOME\include -I $env:JAVA_HOME\include\win32 -I"$env:inc_sdk_ucrt" -I"$env:inc_sdk_um" -I"$env:inc_vc" -I"$env:inc_sdk_sh" /LD Win32RegKey.c "$env:inc_sdk_um_lib" "$env:inc_vc_lib" "$env:inc_sdk_ucrt_lib" /FeWin32RegKey.dll

编译过程出现以下错误，需要将错误中的 lib 移动到其他目录，使其不在 cl.exe 的编译 lib 目录下（比如：clang_rt.asan_dbg_dll_thunk-x86_64.lib）
clang_rt.asan_dbg_dll_thunk-x86_64.lib(sanitizer_coverage_win_dll_thunk.cpp.obj) : error LNK2005: __sanitizer_dump_trace_pc_guard_coverage 已经在 clang_rt.asan-x86_64.lib(sanitizer_coverage_libcdep_new.cpp.obj) 中定义
 
**/
#include "Win32RegKey.h"
#include "Win32RegKeyNameEnumeration.h"
#include <string.h>
#include <stdlib.h>
#include <windows.h>

JNIEXPORT jobject JNICALL Java_Win32RegKey_getValue(JNIEnv *env, jobject this_obj, jstring name)
{
    const char *cname;
    jstring path;
    const char *cpath;
    HKEY hkey;
    DWORD type;
    DWORD size;
    jclass this_class;
    jfieldID id_root;
    jfieldID id_path;
    HKEY root;
    jobject ret;
    char *cret;

    // get the class
    this_class = (*env)->GetObjectClass(env, this_obj);
    // get field IDs
    id_root = (*env)->GetFieldID(env, this_class, "root", "I");
    id_path = (*env)->GetFieldID(env, this_class, "path", "Ljava/lang/String;");

    root = (HKEY)(*env)->GetIntField(env, this_obj, id_root);
    path = (jstring)(*env)->GetObjectField(env, this_obj, id_path);
    cpath = (*env)->GetStringUTFChars(env, path, NULL);

    // open registry key
    if (RegOpenKeyEx(root, cpath, 0, KEY_READ | KEY_WOW64_64KEY, &hkey) != ERROR_SUCCESS)
    {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "Win32RegKeyException"), "Open key failed");
        (*env)->ReleaseStringUTFChars(env, path, cpath);
        return NULL;
    }
    (*env)->ReleaseStringUTFChars(env, path, cpath);
    cname = (*env)->GetStringUTFChars(env, name, NULL);

    // find the type and size of value
    if (RegQueryValueEx(hkey, cname, NULL, &type, NULL, &size) != ERROR_SUCCESS)
    {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "Win32RegKeyException"), "Query value key failed");
        RegCloseKey(hkey);
        (*env)->ReleaseStringUTFChars(env, name, cname);
        return NULL;
    }
    // get memory to hold the value
    cret = (char *)malloc(size);

    if (RegQueryValueEx(hkey, cname, NULL, &type, cret, &size) != ERROR_SUCCESS)
    {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "Win32RegKeyException"), "Query value key failed");
        free(cret);
        RegCloseKey(hkey);
        (*env)->ReleaseStringUTFChars(env, name, cname);
        return NULL;
    }
    // depending on the type, store the value in a string, integer, or byte array
    if (type == REG_SZ)
    {
        ret = (*env)->NewStringUTF(env, cret);
    }
    else if (type == REG_DWORD)
    {
        jclass class_Integer = (*env)->FindClass(env, "java/lang/Integer");
        jmethodID id_Integer = (*env)->GetMethodID(env, class_Integer, "<init>", "(I)V");
        int value = *(int *)cret;
        ret = (*env)->NewObject(env, class_Integer, id_Integer, value);
    }
    else if (type == REG_BINARY)
    {
        ret = (*env)->NewByteArray(env, size);
        (*env)->SetByteArrayRegion(env, (jarray)ret, 0, size, cret);
    }
    else
    {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "Win32RegKeyException"), "Unsupported value type");
        ret = NULL;
    }
    free(cret);
    RegCloseKey(hkey);
    (*env)->ReleaseStringUTFChars(env, name, cname);
    return ret;
}

JNIEXPORT void JNICALL Java_Win32RegKey_setValue(JNIEnv *env, jobject this_obj, jstring name, jobject value)
{
    const char *cname;
    jstring path;
    const char *cpath;
    HKEY hkey;
    DWORD type;
    DWORD size;
    jclass this_class;
    jclass class_value;
    jclass class_Integer;
    jfieldID id_root;
    jfieldID id_path;
    HKEY root;
    const char *cvalue;
    int ivalue;

    // get the class
    this_class = (*env)->GetObjectClass(env, this_obj);
    // get the field IDs
    id_root = (*env)->GetFieldID(env, this_class, "root", "I");
    id_path = (*env)->GetFieldID(env, this_class, "path", "Ljava/lang/String;");

    // get the field
    root = (HKEY)(*env)->GetIntField(env, this_obj, id_root);
    path = (jstring)(*env)->GetObjectField(env, this_obj, id_path);
    cpath = (*env)->GetStringUTFChars(env, path, NULL);

    // open the registry value
    // 如果失败，EXE需要管理员权限执行
    if (RegOpenKeyEx(root, cpath, 0, KEY_WRITE | KEY_WOW64_64KEY, &hkey) != ERROR_SUCCESS)
    {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "Win32RegKeyException"), "Open key failed2");
        (*env)->ReleaseStringUTFChars(env, path, cpath);
        return;
    }
    (*env)->ReleaseStringUTFChars(env, path, cpath);
    cname = (*env)->GetStringUTFChars(env, name, NULL);

    class_value = (*env)->GetObjectClass(env, value);
    class_Integer = (*env)->FindClass(env, "java/lang/Integer");
    if ((*env)->IsAssignableFrom(env, class_value, (*env)->FindClass(env, "java/lang/String")))
    {
        // it is a string -- get a pointer to the characters
        cvalue = (*env)->GetStringUTFChars(env, (jstring)value, NULL);
        type = REG_SZ;
        size = (*env)->GetStringLength(env, (jstring)value) + 1;
    }
    else if ((*env)->IsAssignableFrom(env, class_value, class_Integer))
    {
        jmethodID id_intValue = (*env)->GetMethodID(env, class_Integer, "intValue", "()I");
        ivalue = (*env)->CallIntMethod(env, value, id_intValue);
        type = REG_DWORD;
        cvalue = (char *)&ivalue;
        size = 4;
    }
    else if ((*env)->IsAssignableFrom(env, class_value, (*env)->FindClass(env, "[B")))
    {

        cvalue = (char *)(*env)->GetByteArrayElements(env, (jstring)value, NULL);
        type = REG_BINARY;
        size = (*env)->GetArrayLength(env, (jarray)value);
    }
    else
    {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "Win32RegKeyException"), "Unsupported value type");
        RegCloseKey(hkey);
        (*env)->ReleaseStringUTFChars(env, name, cname);
        return;
    }

    // set the value
    if (RegSetValueEx(hkey, cname, 0, type, cvalue, size) != ERROR_SUCCESS)
    {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "Win32RegKeyException"), "Set value failed");
    }
    RegCloseKey(hkey);
    (*env)->ReleaseStringUTFChars(env, name, cname);

    if (type == REG_SZ)
    {
        (*env)->ReleaseStringUTFChars(env, (jstring)value, cvalue);
    }
    else if (type = REG_BINARY)
    {
        (*env)->ReleaseByteArrayElements(env, (jarray)value, (jbyte *)cvalue, 0);
    }
}

// helper function to start enumeration names
static int startNameEnumeration(JNIEnv *env, jobject this_obj, jclass this_class)
{
    jfieldID id_index;
    jfieldID id_count;
    jfieldID id_root;
    jfieldID id_path;
    jfieldID id_hkey;
    jfieldID id_maxsize;

    HKEY root;
    jstring path;
    const char *cpath;
    HKEY hkey;
    DWORD maxsize = 0;
    DWORD count = 0;

    // get the field IDs
    id_root = (*env)->GetFieldID(env, this_class, "root", "I");
    id_path = (*env)->GetFieldID(env, this_class, "path", "Ljava/lang/String;");
    id_hkey = (*env)->GetFieldID(env, this_class, "hkey", "I");
    id_maxsize = (*env)->GetFieldID(env, this_class, "maxsize", "I");
    id_index = (*env)->GetFieldID(env, this_class, "index", "I");
    id_count = (*env)->GetFieldID(env, this_class, "count", "I");

    // get the field values
    root = (HKEY)(*env)->GetIntField(env, this_obj, id_root);
    path = (jstring)(*env)->GetObjectField(env, this_obj, id_path);
    cpath = (*env)->GetStringUTFChars(env, path, NULL);

    // open the registry key
    if (RegOpenKeyEx(root, cpath, 0, KEY_READ | KEY_WOW64_64KEY, &hkey) != ERROR_SUCCESS)
    {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "Win32RegKeyException"), "Open key failed3");
        (*env)->ReleaseStringUTFChars(env, path, cpath);
        return -1;
    }
    // query count and max length of names
    if (RegQueryInfoKey(hkey, NULL, NULL, NULL, NULL, NULL, NULL, &count, &maxsize, NULL, NULL, NULL) != ERROR_SUCCESS)
    {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "Win32RegKeyException"), "Query info key failed");
        RegCloseKey(hkey);
        return -1;
    }

    (*env)->SetIntField(env, this_obj, id_hkey, (DWORD)hkey);
    (*env)->SetIntField(env, this_obj, id_maxsize, maxsize + 1);
    (*env)->SetIntField(env, this_obj, id_index, 0);
    (*env)->SetIntField(env, this_obj, id_count, count);
    return count;
}
JNIEXPORT jboolean JNICALL Java_Win32RegKeyNameEnumeration_hasMoreElements(JNIEnv *env, jobject this_obj)
{
    jclass this_class;
    jfieldID id_index;
    jfieldID id_count;
    int index;
    int count;

    this_class = (*env)->GetObjectClass(env, this_obj);

    id_index = (*env)->GetFieldID(env, this_class, "index", "I");
    id_count = (*env)->GetFieldID(env, this_class, "count", "I");

    index = (*env)->GetIntField(env, this_obj, id_index);
    if (index == -1)
    {
        count = startNameEnumeration(env, this_obj, this_class);
        index = 0;
    }
    else
    {
        count = (*env)->GetIntField(env, this_obj, id_count);
    }
    return index < count;
}

JNIEXPORT jstring JNICALL Java_Win32RegKeyNameEnumeration_nextElement(JNIEnv *env, jobject this_obj)
{
    jclass this_class;
    jfieldID id_index;
    jfieldID id_hkey;
    jfieldID id_count;
    jfieldID id_maxsize;

    HKEY hkey;
    int index;
    int count;
    DWORD maxsize;

    char *cret;
    jstring ret;

    this_class = (*env)->GetObjectClass(env, this_obj);

    id_index = (*env)->GetFieldID(env, this_class, "index", "I");
    id_count = (*env)->GetFieldID(env, this_class, "count", "I");
    id_hkey = (*env)->GetFieldID(env, this_class, "hkey", "I");
    id_maxsize = (*env)->GetFieldID(env, this_class, "maxsize", "I");

    index = (*env)->GetIntField(env, this_obj, id_index);
    if (index == -1)
    {
        count = startNameEnumeration(env, this_obj, this_class);
        index = 0;
    }
    else
    {
        count = (*env)->GetIntField(env, this_obj, id_count);
    }

    if (index >= count)
    {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "java/util/NoSuchElementException"), "past end of enumeration");
        return NULL;
    }

    maxsize = (*env)->GetIntField(env, this_obj, id_maxsize);
    hkey = (HKEY)(*env)->GetIntField(env, this_obj, id_hkey);
    cret = (char *)malloc(maxsize);

    // find the next name
    if (RegEnumValue(hkey, index, cret, &maxsize, NULL, NULL, NULL, NULL) != ERROR_SUCCESS)
    {
        (*env)->ThrowNew(env, (*env)->FindClass(env, "Win32RegKeyException"), "Enum value failed");
        free(cret);
        RegCloseKey(hkey);
        (*env)->SetIntField(env, this_obj, id_index, count);
        return NULL;
    }
    ret = (*env)->NewStringUTF(env, cret);
    free(cret);

    // increment index
    index++;
    (*env)->SetIntField(env, this_obj, id_index, index);
    if (index == count)
    {
        RegCloseKey(hkey);
    }
    return ret;
}