1. [undefined-reference-to-jni-createjavavm-linux](https://stackoverflow.com/questions/16860021/undefined-reference-to-jni-createjavavm-linux)

> The way GCC finds symbols was changed fairly recently: now the units to be linked are processed strictly from left to right, and references to libraries (-lYourLibrary) are silently ignored if nothing to their left in the command line needs them.
> To fix this, move -ljvm after the compilation units that use it, for example to the very end of the command line


2. [g++ -L和LD_LIBRARY_PATH](https://blog.51cto.com/u_15177889/2727561)

> LD_LIBRARY_PATH是一个环境变量，它的作用是让动态链接库加载器(ld.so)在运行时(run-time)有一个额外的选项，即增加一个搜索路径列表。注意，LD_LIBRARY_PATH是在运行时，才起作用。这个环境变量中，可以存储多个路径，用冒号分隔。它的厉害之处在于，搜索LD_LIBRARY_PATH所列路径的顺序，先于嵌入到二进制文件中的运行时搜索路径，也先于系统默认加载路径(如/usr/lib)。

> g++ -L:是在编译的时候，去-L指定的地方找库，-l库的名字

3. [LoadLibray失败，返回错误码193](https://blog.csdn.net/lrznumber/article/details/55652650)

- jvm.dll 是64位的，java -version 可以查看
- windows 编译工具 cl.exe，应该选择64位的
- cl -I lib，lib库应该选择64位的 

```c
HINSTANCE hGetProcIDDLL = LoadLibrary("D:\\Program Files\\Java\\jre1.8.0_131\\bin\\server\\jvm.dll");

if (!hGetProcIDDLL)
{
    int error = GetLastError();
    printf("%s %d", "could not load the dynamic library", error);
    return EXIT_FAILURE;
}
```

4. [vscode开发jni无提示](https://code.visualstudio.com/docs/cpp/config-msvc#_cc-configurations)