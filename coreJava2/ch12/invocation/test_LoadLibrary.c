/**
 * @file test_LoadLibrary.c
 * @author your name (you@domain.com)
 * @brief
 * @version 0.1
 * @date 2022-07-24
 *
 * $env:inc_vc="C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Tools\MSVC\14.29.30133\include"
   $env:inc_sdk_sh="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\shared"
   $env:inc_sdk_um="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\um"
   $env:inc_sdk_ucrt="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\ucrt"
   $env:inc_vc_lib="C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Tools\MSVC\14.29.30133\lib\x64\*.lib"
   $env:inc_sdk_um_lib="C:\Program Files (x86)\Windows Kits\10\Lib\10.0.18362.0\um\x64\*.Lib"
   $env:inc_sdk_ucrt_lib="C:\Program Files (x86)\Windows Kits\10\Lib\10.0.18362.0\ucrt\x64\*.lib"

  cl  -I "$env:inc_vc"  -I $env:inc_sdk_ucrt -I $env:inc_sdk_sh -I $env:inc_sdk_um  test_LoadLibrary.c  "$env:inc_sdk_um_lib" "$env:inc_vc_lib"  "$env:inc_sdk_ucrt_lib"

  应用程序是64位的，jvm.dll也是64位的（depends查看），所以不是64位程序加载32位dll的问题
 *
 *
 * @copyright Copyright (c) 2022
 *
 */

#include <stdio.h>
#include <windows.h>

int main()
{
    HINSTANCE hGetProcIDDLL = LoadLibrary("D:\\Program Files\\Java\\jre1.8.0_131\\bin\\server\\jvm.dll");

    if (!hGetProcIDDLL)
    {
        int error = GetLastError();
        printf("%s %d", "could not load the dynamic library", error);
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}