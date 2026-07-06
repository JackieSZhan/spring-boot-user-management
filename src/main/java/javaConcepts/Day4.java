package javaConcepts;

import java.util.concurrent.*;

/**
 *What is a thread in java?
 * Single thread:  [task 1] -> [task2]...
 * multi threading: thread 1 ->[ task 1]
 *                  thread 2 -> [task 2]
 *                  ....
 *                  all running at the same time
 * // old way to create one thread
 * class MyThread extends Thread{
 *
 * @Override
 * public void run(){
 *     // this your task implementation
 * }
 * }
 *
 * // new way -> runnable functional interface
 * Thread t = new Thread(() -> {
 *     sout("Running thread ->" + Thread.currentThread().getName());
 *
 * })
 *
 * the problems with creating threads manually:
 * 1: how do you know how many threads you need when your system is running
 * 2: creating a thread and destory a thread are expensive!!!!
 * 3: no reuse
 *
 * how can we solve above problems? -> thread pool -> a place that holds a lot threads
 * Thread pool: let's say your thread pool can only have 5 threads
 *  thread 1 ->idle
 *  thread 2 -> running taskA
 *  thread 3 -> running task B
 *  thread 4 -> idle
 *  thread 5 -> working on task C
 *  if you want to submit task D and E to thread pool and then thread 1 and thread 4 will
 *  work on it
 *  if you want to submit task F to thread pool , if there is not idle worker, then you
 *  have to wait outside.
 *
 *
 *  Executor vs ExecutorService vs Executors
 *  Executor : it is an interface and only has one method:
 *      void execute(Runnable command); -> will execute a task
 *
 *
 *  Executor Service: it is same with executor, but it provides more
 *  useful functionality: submitting task that returns a result
 *  , shutting the pool down safely. waiting for tasks to complete
 *
 *      Runnable vs Callable:
 *          runnable: return voids and cannot throw checked exception
 *          Callable: return a value of type T, and throw checked exception
 *
 * Executors:
 *  this class allows you to create different threads
 *
 *  newFixedthreadpool(n) -> a pool with exactly n threads always
 *  newCachedThreadPool -> a pool that creates a new thread as needed, but reuses idle threads and kill threads that stay idle for 60 seconds
 *  newSingleThreadExecutor -> a pool with 1 thread
 *  newScheduledThreadPool() ->
 *  newWorkStealPool() ->
 *
 *  but we do not use Executors in real world
 *  what we use is : ThreadPoolExecutor
 *  the reason is that you can have bounded tasks in task queue
 *  which mean you are trying to avoid -> OutOfMemoryError.
 *
 *
 *  execute function v s submit() function
 *  execute(): fire - and - forget, no result, no way to know if/how it failed
 *  submit(): still no result,  but you get a future you can check for completion/ execeptions
 *
 *
 * Future and Completable Future
 * Completable Future implements Future class, but add a huge set
 * of extra features that allow you manage your tasks
 * like you can chain what happens after a task completes,
 * or combine multiple async tasks together
 * or handle exceptions
 *
 *
 *
 * Java 21 new feature -> virtual thread
 * A thread is the smallest unit of execution in java
 *
 * how java create a thread -> your thread will be managed by OS
 * your java code - new Thread(() ->{doWork()}) -> JVM
 *  -> make a system call -> OS -> create OS thread , allocates 1BM memory
 *  adds to OS scheduler -> decides when and where it runs -> cup cores
 *
 *
 *  Virtual threads are lightweight threads managed by the JVM
 *  instead of the OS
 *
 *  your java code ->
 *  Thread.ofVirtual().start((0-> dowork())
 *  -> JVM Scheduler <- fully managed inside the JVM
 *  -> mounts virtual thread onto a carrier thread, unmounts when blocking, remount when ready.
 *  -> carrier thread (OS thread, platform thread)
 *  ->OS scheduler
 *  -> CPU cores
 *
 *  when you create a virtual thread, the JVM create a lightweight object on the heap
 * a virtual thread is an object!!!
 *
 * Java thread -> 1:1 mapping -> OS thread
 * Java virtual thread -> M:N mapping -> OS
 *
 *
 *
 *What is JVM
 * it stands for java virtual machine
 * it is used for running and compilling java code
 *
 * your java code -> Java compiler -> turns your java code->
 * bytecode: .class file
 * -> JVM: class loader -> will load .class file
 *      Execution engine: it is for interpreting and executing the
 *      bytecode that is loaded into memory by the class loader
 *    runtime data areas: it is a memory to store bytecode, objects....
 *
 *   Memory: three -> memory area, heap and stack
 *   heap -> important !!!!
 *   the heap is used for managing ans storing objects
 *   The components of heap are young generation, old generation
 *   and metaspace
 *   young generation:  this is a place where newly created objects are stored
 *   User user = new User("Matt") -> newly created -> will be stored in young generation
 *   the components of yong generation:
 *      1: eden space: the main area where most of the new objects are created
 *      2: survivor spaces: S0 and S1. one used as the source and the other used as the target for moving live objects after a GC
 *    Old generation: when objects in the survivor spaces survive multiple GC, they are eventually moved to
 *    the old generation.
 *   stack: is used to store data for method invocations.
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class Day4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(()-> System.out.println("running"));
        Future<?> future1 = pool.submit(() -> System.out.println("running"));

        boolean done = future1.isDone();
        Future<Integer> f2 = pool.submit(()->{
            Thread.sleep(1000);
            return 100;
        });
        Integer result = f2.get();

        CompletableFuture<Integer> cfA = CompletableFuture.supplyAsync( () -> 1);
        CompletableFuture<Integer> cfB = CompletableFuture.supplyAsync( () ->2);

        CompletableFuture<Integer> combined = cfA.thenCombine(cfB, (a,b) -> a + b);
        System.out.println(combined.get());

        CompletableFuture<Void> all = CompletableFuture.allOf(cfA, cfB);
        all.get();

        CompletableFuture<Object> any = CompletableFuture.anyOf(cfA,cfB);
        System.out.println(any.get());
    }
}
