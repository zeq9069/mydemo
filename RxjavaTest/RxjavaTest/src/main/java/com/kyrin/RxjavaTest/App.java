package com.kyrin.RxjavaTest;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Action3;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Hello Rxjava
 *
 */
public class App {

	public static void main(String[] args) {

		// rxjava_1();
		// rxjava_2();
		// rxjava_3();
		// rxjava_4();
		//rxjava_5();
		//rxjava_6();
		//rxjava_7();
		//rxjava_8();
		rxjava_9();
	}

	public static <T> void rxjava_1() {

		// 观察者
		Observable<String> observable = (Observable<String>) Observable.create(new Observable.OnSubscribe<String>() {

			public void call(Subscriber<? super String> sub) {
				sub.onNext("--- Hello world ---");
				sub.onError(new Exception());
				sub.onCompleted();
			}
		});

		// 订阅者
		Subscriber<String> sub = new Subscriber<String>() {
			public void onNext(String arg0) {
				System.out.println(arg0);
			}

			public void onError(Throwable arg0) {
				System.out.println(arg0);
			}

			public void onCompleted() {
				System.out.println("---completed---");
			}
		};

		observable.subscribe(sub);
	}

	public static <T> void rxjava_2() {

		// 观察者
		Observable<String> observable = (Observable<String>) Observable.create(new Observable.OnSubscribe<String>() {
			public void call(Subscriber<? super String> sub) {
				sub.onNext("--- Hello world ---");
			}
		});

		// 订阅者
		Action0 onCompleted = new Action0() {
			public void call() {
				System.out.println("--- onCompleted ---");
			}
		};

		Action1<String> onNext = new Action1<String>() {
			public void call(String arg0) {
				System.out.println("--- onNext ---");
			}
		};

		Action1<Throwable> onError = new Action1<Throwable>() {
			public void call(Throwable t) {
				System.out.println("--- onError ---");
			}
		};

		Action2<String, String> action2 = new Action2<String, String>() {

			public void call(String t1, String t2) {
				System.out.println("--- Action2 arg1 , arg2---");
			}
		};

		Action3<String, String, String> action3 = new Action3<String, String, String>() {

			public void call(String t1, String t2, String t3) {
				System.out.println("--- Action3 arg1 , arg2 , arg3---");
			}
		};

		observable.subscribe(onNext);

		observable.subscribe(onNext, onError);

		observable.subscribe(onNext, onError, onCompleted);

	}

	public static void rxjava_3() {
		Action1<String> onNext = new Action1<String>() {

			public void call(String t) {
				System.out.println(t);
			}
		};

		Observable<String> ob = Observable.just("Hello kyrin");
		ob.subscribe(onNext);

		Observable.just("Hello, world!").map(new Func1<String, String>() {
			public String call(String s) {
				return s + " -Dan";
			}
		}).subscribe(s -> System.out.println(s));
	}

	// the map() operator can be used to transform one emitted item into
	// another:
	public static void rxjava_4() {
		Observable.just("Hello, world!").map(new Func1<String, Integer>() {
			@Override
			public Integer call(String s) {
				return s.hashCode();
			}
		}).subscribe(i -> System.out.println(String.format("s hashcode -> %d ", i)));

		// or
		Observable.just("Hello, world!").map(s -> s + " -Dan").subscribe(s -> System.out.println(s));

		// or
		Observable.just("Hello, world!").map(s -> s.hashCode()).map(i -> Integer.toString(i))
				.subscribe(s -> System.out.println(s));

	}

	// we'll take a dip into the large pool of operators available to you when
	// using RxJava.
	public static void rxjava_5() {

		Observable.from(new String[] { "1", "2", "3" }).subscribe(new Action1<String>() {
			@Override
			public void call(String t) {
				System.out.println(t);
			}
		});
	}

	//Observable.flatMap() takes the emissions of one Observable and returns the emissions of another Observable to take its place
	public static <T> void rxjava_6() {

		Observable<List<String>> ob = Observable.create(new Observable.OnSubscribe<List<String>>() {

			@Override
			public void call(Subscriber<? super List<String>> t) {
				System.out.println("Hello kyrin");
				t.onNext(Arrays.asList("1","2","3"));
			}
		});
		
		ob.flatMap(new Func1<List<String>, Observable<String>>() {

			@Override
			public Observable<String> call(List<String> t) {
				return Observable.from(t);
			}
		}).subscribe(new Action1<String>() {
			@Override
			public void call(String t) {
				System.out.println(t);
			}
		});
	}
	
	//flatMap() can return any Observable it wants.
	public static void rxjava_7(){
		Observable<List<String>> ob = Observable.create(new Observable.OnSubscribe<List<String>>(){

			@Override
			public void call(Subscriber<? super List<String>> t) {
				// 生成数据
				t.onNext(Arrays.asList("http://www.baidu.com","http://wwww.meituan.com","http://www.google.com","http://www.baidu.com","http://www.google.com","http://www.google.com",null));
			}
			
		});
		
		ob.flatMap(new Func1<List<String>, Observable<? extends String>>() {
			@Override
			public Observable<? extends String> call(List<String> urls) {
				return Observable.from(urls);
			}
		}).flatMap(new Func1<String, Observable<? extends String>>() {
			@Override
			public Observable<? extends String> call(String url) {
				return getTitle(url);
			}
		}).filter(new Func1<String, Boolean>() { //getTitle() returns null if the URL 404s. We don't want to output "null"; it turns out we can filter them out!
					@Override
					public Boolean call(String t) {
						return t == null ? false : true; 
					}
		}).take(5) //And now we want to only show 5 results at most:
		.doOnNext(new Action1<String>() { //Now we want to save each title to disk along the way , doOnNext() allows us to add extra behavior each time an item is emitted, in this case saving the title.
			@Override
			public void call(String t) {
				saveTitle(t);
			}
			
			private void saveTitle(String title){
				//保存业务逻辑
				System.out.println(String.format("save title[ %s ] success",title));
			}
		})
		.subscribe(new Action1<String>() {

			@Override
			public void call(String title) {
				System.out.println(title);
			}
		});
		
	}
	
	// Returns the title of a website, or null if 404
	private static Observable<String> getTitle(String url){
		Observable<String> ob = Observable.create(new Observable.OnSubscribe<String>(){
			@Override
			public void call(Subscriber<? super String> t) {
				
				t.onNext(getTitle(url));
			}
			
			//业务逻辑实现
			private String getTitle(String url){
				String title = null;
				if(url == null){
					return title;
				}
				switch(url){
					case "http://www.baidu.com":
						title = "baidu";
						break;
					case "http://wwww.meituan.com":
						title = "meituan";
						break;
					case "http://www.google.com":
						title = "google";
						break;
					default:
						break;
				}
				return title;
			}
		});
		return ob;
	}
	
	
	
	
	/**
	 *  part 3
	 * 
	Up until this point, we've largely been ignoring onComplete() and onError(). They mark when an Observable is going to stop emitting items and the reason for why (either a successful completion, or an unrecoverable error).

Our original Subscriber had the capability to listen to onComplete() and onError(). Let's actually do something with them:
	 * 
	 * 
	 */
	
	/*
	 * onError() is called if an Exception is thrown at any time.

This makes error handling much simpler. I can just handle every error at the end in a single function.

The operators don't have to handle the Exception.

You can leave it up to the Subscriber to determine how to handle issues with any part of the Observable chain because Exceptions skip ahead to onError().

You know when the Subscriber has finished receiving items.

Knowing when a task is done helps the flow of your code. (Though it is possible that an Observable may never complete.)


	 */
	public static void rxjava_8(){
		Observable.just("Hello kyrin").map(new Func1<String, String>() {

			@Override
			public String call(String t) {
				
				return potentialException(t);
			}
			
			public String potentialException(String t){//潜在的异常
				return t;
			}
		}).map(new Func1<String, String>() {

			@Override
			public String call(String t) {
				
				return anthorPotentialException(t);
			}
			
			public String anthorPotentialException(String t){//另一个潜在的异常
				int res = 4/0 ;
				return t;
			}
		}).subscribe(new Subscriber<String>() {

			@Override
			public void onCompleted() {
				System.out.println("--- onCompleted ---");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("--- onError ---");
			}

			@Override
			public void onNext(String t) {
				System.out.println("--- onNext ---");
			}
		});
		
	}
	
	
	/**
	 * schedulers
	 * 
	 * You've got an Android app that makes a network request. That could take a long time, so you load it in another thread. Suddenly, you've got problems!

Multi-threaded Android applications are difficult because you have to make sure to run the right code on the right thread; mess up and your app can crash. The classic exception occurs when you try to modify a View off of the main thread.

In RxJava, you can tell your Observer code which thread to run on using subscribeOn(), and which thread your Subscriber should run on using observeOn():
	 */
	
	public static void rxjava_9(){
		Observable<String> ob = Observable.create(new Observable.OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> t) {
				
				t.onNext("Hello kyrin");
			}
		});
		
		ob.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.newThread())
		.subscribe(new Action1<String>() {  //There's something I've been hiding from you. When you call Observable.subscribe(), it returns a Subscription. This represents the link between your Observable and your Subscriber:
			@Override
			public void call(String t) {
				System.out.println(t);
			}
		});
		
		//subscription
		
		Subscription subscription = Observable.just("Hello, World!")
			    .subscribe(new Action1<String>() {
					@Override
					public void call(String t) {
						System.out.println(t);
					}
				});
		
		subscription.unsubscribe(); //What's nice about how RxJava handles unsubscribing is that it stops the chain. If you've got a complex chain of operators, using unsubscribe will terminate wherever it is currently executing code3. No unnecessary work needs to be done!
		
		System.out.println("Unsubscribed=" + subscription.isUnsubscribed());
		
	}
}
