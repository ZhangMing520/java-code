#include <stdio.h>
#include "org_example_Foo.h"

// $env:urct_i="D:\Windows Kits\10\Include\10.0.19041.0\ucrt"
// $env:vc_i="D:\Program Files (x86)\Microsoft Visual Studio 14.0\VC\include"
// $env:vc_lib="D:\Program Files (x86)\Microsoft Visual Studio 14.0\VC\lib\amd64"
// $env:um_lib="D:\Windows Kits\10\Lib\10.0.19041.0\um\x64"
// $env:ucrt_lib="D:\Windows Kits\10\Lib\10.0.19041.0\ucrt\x64"
// cl /I "$env:urct_i" /I "$env:vc_i" /I "$env:JAVA_HOME\Include" /I "$env:JAVA_HOME\Include\win32" foo.c /link /LIBPATH:"$env:vc_lib" /LIBPATH:"$env:um_lib" /LIBPATH:"$env:ucrt_lib" /DLL /OUT:foo.dll

JNIEXPORT void JNICALL Java_org_example_Foo_bar__Ljava_lang_String_2Ljava_lang_Object_2(JNIEnv *env,
                                                                                        jobject thisObject, jstring str, jobject obj)
{
  jclass cls = (*env)->GetObjectClass(env, thisObject);
  // 不存在 j
  jfieldID fieldID = (*env)->GetFieldID(env, cls, "j", "I");
  if ((*env)->ExceptionOccurred(env))
  {
    printf("Exception!\n");
    (*env)->ExceptionClear(env);
  }

  fieldID = (*env)->GetFieldID(env, cls, "i", "I");
  jint value = (*env)->GetIntField(env, thisObject, fieldID);
  printf("Hello, World 0x%x\n", value);
  return;
}