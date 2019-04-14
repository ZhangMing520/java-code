1. CompletableFuture

- 向一个 CompletableFuture 对象添加一个 Action

| 方法 | 参数 | 描述  |
|:-: | :-: |  :-: |
| thenApply | T->U | 为结果提供一个函数 |
| thenCompose | T->CompletableFuture<U> | 对结果调用一个函数，并执行返回的Future结果|
| handle | (T ,Throwable) -> U | 处理结果或者错误 |
| thenAccept | T->void | 同thenApply类似，但是结果为 void 类型 |
| whenComplete | (T,Throwable) -> void | 同 handle 类似，到那时结果为void类型|
| thenRun | Runnable | 执行返回void的 Runnable 对象 |

- 组合多个对象

| 方法 | 参数 | 描述  |
|:-: | :-: |  :-: |
| thenCombine  | CompletableFuture<U> , (T,U)->V | 执行两个对象，并将结果按照指定的函数组合起来 |
| thenAcceptBoth | CompletableFuture<U> , (T,U)->void  | 同 thenCombine 类似，但是结果为void类型   |
| runAfterBoth |  CompletableFuture<?> , Runnable | 在两个对象完成后，执行Runnable对象  |
| applyToEither  | CompletableFuture<T> , T-> V  | 当其中一个对象的结果可用时，将结果传递给指定的函数  |
| acceptEither  |  CompletableFuture<T> , T-> void | 同 applyToEither 类似，但是结果为void类型  |
| runAfterEither |  CompletableFuture<?> , Runnable | 在其中一个对象结束后执行Runnable对象  |
| static allOf  | CompletableFuture<?> ...  | 在所有 Future 对象结束后结束，并返回 void 结果  |
| static anyOf  |  CompletableFuture<?> ...  | 在任何一个 Future 对象结束后结束，并返回 void 结果  |


2. 常用函数式接口

| 函数式接口  |  参数类型  | 返回类型  | 抽象方法名  | 描述   | 其他方法  |
| :-:  |  :-:  | :-:  | :-:  | :-:   | :-:  |
|  Runnable |  无  |  void  |  run  |  执行一个没有参数和返回值的操作  |   |
|  Supplier<T> | 无   |  T  |  get |  提供一个T类型的值  |   |
| Consumer<T>  |  T  |  void |  accept | 处理一个T类型的值   | chain  |
| BiConsumer<T,U>  | T,U   | void  | accept  | 处理T类型和U类型的值   | chain  |
| Function<T,R>  | T   |  R |  apply  | 一个参数类型为T的函数   | compose,andThen ,identity  |
| BiFunction<T,U,R>  |  T,U   |  R | apply  | 一个参数类型为T和U的函数   | andThen  |
| UnaryOperator<T>  |  T  |  T |  apply  | 对类型T进行一元操作   | compose,andThen,identity  |
| BinaryOperator<T>  | T,T   | T  | apply  |  对类型T进行二元操作  | andThen  |
| Predicate<T>  |  T  | boolean  | test  |  一个计算boolean值的函数  | And,Or,negate,isEqual  |
| BiPredicate<T,U>  |  T,U  |  boolean | test  |  一个含有两个恶参数、计算Boolean值的函数  | And,Or,negate  |

3. 为原始类型提供的函数式接口：p,q为int、long、double类型

| 函数式接口  |  参数类型  | 返回类型  | 抽象方法名 |
| :-:  |  :-: | :-:  |  :-: |
| BooleanSupplier  | none  | boolean  | getAsBoolean  |
| PSupplier  | none  | p  |  getAsP  |
| PConsumer  |  p |  void | accept  |
| ObjPConsumer<T>  | T,p  | void  | accept  |
| PFunction<T>  |  p  |  T  |  apply  |
| PTOQFunction  |  p | q  | applyAsQ  |
| ToPFunction<T>  |  T | p  |  applyAsP |
| ToPBiFunction<T,U>  | T,U  |  p | applyAsP  |
| PUnaryOperator  | p  |  p |  applyAsP |
| PBinaryOperator  | p,p  |  p |  applyAsP |
| PPredicate  |  p | boolean  | test  |
