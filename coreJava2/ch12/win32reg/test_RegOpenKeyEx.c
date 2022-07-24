#include <stdio.h>
#include <windows.h>

/**
 * @brief   测试打开注册表函数正确参数 RegOpenKeyEx
 *
 *
 $env:inc_vc="C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Tools\MSVC\14.29.30133\include"
   $env:inc_sdk_sh="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\shared"
   $env:inc_sdk_um="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\um"
   $env:inc_sdk_ucrt="C:\Program Files (x86)\Windows Kits\10\Include\10.0.18362.0\ucrt"
   $env:inc_vc_lib="C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Tools\MSVC\14.29.30133\lib\x64\*.lib"
   $env:inc_sdk_um_lib="C:\Program Files (x86)\Windows Kits\10\Lib\10.0.18362.0\um\x64\*.Lib"
   $env:inc_sdk_ucrt_lib="C:\Program Files (x86)\Windows Kits\10\Lib\10.0.18362.0\ucrt\x64\*.lib"

  cl -I "$env:inc_vc"  -I $env:inc_sdk_ucrt -I $env:inc_sdk_sh -I $env:inc_sdk_um  test_RegOpenKeyEx.c  "$env:inc_sdk_um_lib" "$env:inc_vc_lib"  "$env:inc_sdk_ucrt_lib"

 *
 */

int main()
{
  HKEY hKeyRoot = HKEY_LOCAL_MACHINE;
  LPTSTR lpSubKey;
  LONG lResult;
  HKEY hkey;

  // KEY_WOW64_64KEY 打开某一些注册表的时候，必须使用，否则打开失败
  lResult = RegOpenKeyEx(hKeyRoot, "SOFTWARE\\JavaSoft\\Java Runtime Environment", 0, KEY_WRITE | KEY_WOW64_64KEY, &hkey);
  if (lResult == ERROR_SUCCESS)
  {
    printf("%s\n", "ok");
  }
  else
  {
    printf("%s\n", "Failed with value");
  }
}