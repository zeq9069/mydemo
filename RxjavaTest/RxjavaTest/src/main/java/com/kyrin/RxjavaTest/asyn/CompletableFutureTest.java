package com.kyrin.RxjavaTest.asyn;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * 
 * java8中自带的CompletableFuture
 * 
 * 1.计算结果完成时的处理
 * 	当CompletableFuture的计算结果完成，或者抛出异常的时候，我们可以执行特定的Action
 * 		（1）CompletableFuture<T> 	whenComplete(BiConsumer<? super T,? super Throwable> action)
 *		（2） CompletableFuture<T> 	whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
 *		（3） CompletableFuture<T> 	whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor)
 *		（4） CompletableFuture<T>   exceptionally(Function<Throwable,? extends T> fn)
 * 
 * 2.转换（回调）
 * 		1） CompletableFuture<U> 	thenApply(Function<? super T,? extends U> fn)
 *		2） CompletableFuture<U> 	thenApplyAsync(Function<? super T,? extends U> fn)
 *		3） CompletableFuture<U> 	thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
 *
 * 3. 纯消费（执行不返回值的action）
 * 			1）  CompletableFuture<Void> 	thenAccept(Consumer<? super T> action)
 *			2）  CompletableFuture<Void> 	thenAcceptAsync(Consumer<? super T> action)
 *			3）  CompletableFuture<Void> 	thenAcceptAsync(Consumer<? super T> action, Executor executor)
 *
 * 4.组合
 * 这一组方法接受一个Function作为参数，这个Function的输入是当前的CompletableFuture的计算值，返回结果将是一个新的CompletableFuture，这个新的CompletableFuture会组合原来的CompletableFuture和函数返回的CompletableFuture。因此它的功能类似:

			1） CompletableFuture<U> 	thenCompose(Function<? super T,? extends CompletionStage<U>> fn)
			2） CompletableFuture<U> 	thenComposeAsync(Function<? super T,? extends CompletionStage<U>> fn)
			3） CompletableFuture<U> 	thenComposeAsync(Function<? super T,? extends CompletionStage<U>> fn, Executor executor)


   5.Either
    
    thenAcceptBoth和runAfterBoth是当两个CompletableFuture都计算完成，而我们下面要了解的方法是当任意一个CompletableFuture计算完成的时候就会执行。

			1） CompletableFuture<Void> 	acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action)
			2） CompletableFuture<Void> 	acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action)
			3） CompletableFuture<Void> 	acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor)
			4） <U> CompletableFuture<U> 	applyToEither(CompletionStage<? extends T> other, Function<? super T,U> fn)
			5） <U> CompletableFuture<U> 	applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T,U> fn)
			6） <U> CompletableFuture<U> 	applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T,U> fn, Executor executor)

	6.辅助方法 allOf 和 anyOf

前面我们已经介绍了几个静态方法：completedFuture、runAsync、supplyAsync,下面介绍的这两个方法用来组合多个CompletableFuture。
allOf方法是当所有的CompletableFuture都执行完后执行计算。

anyOf方法是当任意一个CompletableFuture执行完后就会执行计算，计算的结果相同。

public static CompletableFuture<Void> 	    allOf(CompletableFuture<?>... cfs)
public static CompletableFuture<Object> 	anyOf(CompletableFuture<?>... cfs)

7.如果你用过Guava的Future类，你就会知道它的Futures辅助类提供了很多便利方法，用来处理多个Future，而不像Java的CompletableFuture，只提供了allOf、anyOf两个方法。 比如有这样一个需求，将多个CompletableFuture组合成一个CompletableFuture，这个组合后的CompletableFuture的计算结果是个List,它包含前面所有的CompletableFuture的计算结果，guava的Futures.allAsList可以实现这样的功能，但是对于java CompletableFuture，我们需要一些辅助方法

 * 
 * 		
 *
 *
 * 
 * @author kyrin
 *
 */
public class CompletableFutureTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		CompletableFuture<String> f = CompletableFuture.supplyAsync(new Supplier<String>() {
			public String get() {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
				}
					//throw new RuntimeException();
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
				}
				return "5";
			}
		});
		
		f.thenApply(new Function<String, Object>() {
			public Object apply(String t) {
				System.out.println("thenApply -> "+t);
				return null;
			};
		});
		
		f.thenApply((r) -> {
			System.out.println("thenApply -> "+r);
			return null;
		});
		
		
		f.thenAccept(new Consumer<String>() {
			@Override
			public void accept(String t) {
				System.out.println("thenAccept ->"+t);
			}
		});
		
		f.thenRun(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("--- thenRun ---");
			}
		});
		
		f.thenAcceptAsync(new Consumer<String>() {
			@Override
			public void accept(String t) {
				System.out.println("thenAcceptAsync ->"+t);
			}
		});
		
		CompletableFuture<String> res = f.handle((ok,u) -> {
			System.out.println(ok);
			if(ok != null){
				return "6";
			}
			return null;
		});
		
		try{
			System.out.println(res.get(10,TimeUnit.MILLISECONDS));
			System.out.println(f.get());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
