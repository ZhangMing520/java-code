1. log4j2 单独使用只需要使用log4j-api ， log4j-core
2. log4j2和slf4j 一起使用需要log4j-api ， log4j-core，log4j-slf4j-impl，slf4j-api，但是log4j-slf4j-impl间接引入了其他三项，所以只需要引入一个就行了

```xml
<!--门面-->
<!-- <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>${slf4j.version}</version>
</dependency>-->

<!--桥接器:告诉slf4j使用Log4j2
   包含 slf4j-api  log4j-api  log4j-core
-->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>${log4j2.version}</version>
</dependency>

        <!-- 具体实现 -->
        <!--
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>${log4j2.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>${log4j2.version}</version>
        </dependency>
        -->
```
