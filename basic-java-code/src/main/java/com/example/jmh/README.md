1. benchmark 模式
- Throughput：通过多次调用并执行方法来测试吞吐量，并且记录方法执行的次数，例如“1秒内可以执行多少次调用”。
- AverageTime：执行的平均时间，测试方式和 Throughput 一样，例如“每次调用平均耗时xxx毫秒”。
- SampleTime: 执行时间，随机抽样，最后输出取样结果的分布，例如“99%的调用在xxx毫秒以内，99.99%的调用在xxx毫秒以内”
- SingleShotTime: 以上模式都是默认一次 iteration 是 1s，唯有 SingleShotTime 是只运行一次。往往同时把 warmup 次数设为0，用于测试冷启动时的性能。




#### 
[国外文档](http://tutorials.jenkov.com/java-performance/jmh.html#state-scope)

[国内文档](http://blog.dyngr.com/blog/2016/10/29/introduction-of-jmh/)

[官网示例](http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/)