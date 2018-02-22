package iterables;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


public class ObservableTest {
	
	final static PublishSubject<String> percentSoldOutScoreSubject = PublishSubject.create();
	
	public static void main(String[] args)
	{
		/*ObservableTest observableTest = new ObservableTest();
		Observable<String> todoObservable = Observable.just("foo", "bar");
		Disposable subscribe = todoObservable.subscribe(System.out::println, e -> e.printStackTrace());
		subscribe.dispose();
		System.out.println("Main Thread " +Thread.currentThread().getName());
		Flowable.fromCallable(() -> {
			System.out.println("Started some server call on thread : "+Thread.currentThread().getName());
			
		    Thread.sleep(10000); //  imitate expensive computation
		    
		    System.out.println("End some server call on thread : "+Thread.currentThread().getName());
		    
		    return "Done from callable with thread";
		})
		  .subscribeOn(Schedulers.io())	//Typically, you can move computations or blocking IO to some other thread via subscribeOn
		  .observeOn(Schedulers.single())	//Once the data is ready, you can make sure they get processed on the foreground or GUI thread via observeOn
		  .subscribe(s->{
			  System.out.println(s + " on Thread "+Thread.currentThread().getName());
		  }, Throwable::printStackTrace,() -> System.out.println("We are done!"));

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		observableTest.run();
		
		Flowable.range(1, 10)
		  .observeOn(Schedulers.computation())		//This example flow squares the numbers from 1 to 10 on the computation Scheduler
		  .map(v -> {System.out.println("mapped produced on thread : "+Thread.currentThread().getName());return v * v;})  //However, the lambda v -> v * v doesn't run in parallel for this flow; it receives the values 1 to 10 on the same computation thread one after the other.
		  .blockingSubscribe(s->{
			  System.out.println("consumed on thread : "+Thread.currentThread().getName() +"  value as "+ s);
		  });	// consumes the results on the "main" thread (more precisely, the caller thread of blockingSubscribe). 
		
		System.out.println("\n____________________________");
		Flowable.range(1, 10)
		  .flatMap(v ->						// make map run on separate thread
		      Flowable.just(v)
		        .subscribeOn(Schedulers.computation())
		        .map(w -> {System.out.println("flatMap produced on thread : "+Thread.currentThread().getName());return w * w;})
		  )
		.blockingSubscribe(s->{
			  System.out.println("consumed on thread : "+Thread.currentThread().getName() +"  value as "+ s);
		  });
		
		System.out.println("\n____________________________");
		Flowable.range(1, 10)
		.parallel()
		.runOn(Schedulers.computation())
		.map(w -> {System.out.println("parallel mapped  on thread : "+Thread.currentThread().getName());return w * w;})
		.sequential()
		.blockingSubscribe(s->{
			  System.out.println("consumed on thread : "+Thread.currentThread().getName() +"  value as "+ s);
		  });
		*/
		HotelModel.callsHotelLoadsOnPage();
	}
	
	public static final void getResultsFromServer()
	{
		UrgencyObserver<String> urgObserver = new UrgencyObserver<String>();
		urgObserver.scoreSubject.subscribe(percentSoldOutScoreSubject);
		fetchCompressionScore().subscribe(urgObserver);
	}
	
	// this is mimic http calls
	private static Observable<String> fetchCompressionScore(){
		return Observable.fromCallable(() -> {
			System.out.println("\nstarted fetchCompressionScore some server call on thread : "+Thread.currentThread().getName());
		    Thread.sleep(5000); //  imitate expensive computation
		    System.out.println("end fetchCompressionScore call on thread : "+Thread.currentThread().getName());
		    return "Done from callable with thread fetchCompressionScore is 25";
		});
	}
	
	// this is mimic hotel presenter calls
	private static class HotelModel {
		// mimic onFinishInflate()
		static {
			System.out.println(".... Loaded HotelResultsPresenter.kt onFinishInflate()  ...");
			ObservableTest.percentSoldOutScoreSubject.subscribeOn(Schedulers.io()).observeOn(Schedulers.single()).subscribe(x->  {
					System.out.println("\n\n\nLoaded Urgency Views  : " + x + "  on thread : "+Thread.currentThread().getName());
				});
		}
		public static void callsHotelLoadsOnPage()
		{
			// create the whole observable with threads attached to it
			Observable<String> hotelresult = Observable.fromCallable(() -> {
				System.out.println("\nStarted HotelResultsObservable Spiner call on thread : "+Thread.currentThread().getName());
			    Thread.sleep(5000); //  imitate expensive computation
			    System.out.println("End HotelResultsObservable Spiner call on thread : "+Thread.currentThread().getName());
			    return "results of hotelResultsObservable";
			}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
			
			System.out.println("... Created hotelresult observable ...");
			
			
			hotelresult.subscribe(s -> {
				getResultsFromServer();
				System.out.println("... Done with FIRST hotelResultsObservable Subscribe ..."+Thread.currentThread().getName() +"   result = "+s);
			});
			
			
			hotelresult.subscribe(s -> {
				System.out.println("... Done with Second hotelResultsObservable Subscribe ..."+Thread.currentThread().getName() +"   result = "+s);
			});
			
			
			System.out.println("... Loaded  HotelResults Page ...");
			
			try {
				Thread.sleep(1000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	

	
	private static class UrgencyObserver<T> implements Observer<T> {
		PublishSubject<T> scoreSubject = PublishSubject.create();

		@Override
		public void onNext(T t) {
            scoreSubject.onNext(t);
		}

		@Override
		public void onError(Throwable e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onComplete() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSubscribe(Disposable d) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private static void pullExample() {
		final List<String> list = Lists.newArrayList("Java", "C", "C++", "PHP", "Go");

		final Iterator<String> iterator = list.iterator();

		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	private static void pushExample() {
		final List<String> list = Lists.newArrayList("Java", "C", "C++", "PHP", "Go");

		final Observable<String> observable = Observable.fromIterable(list);

		observable.subscribe(System.out::println, System.out::println,
				() -> System.out.println("We are done!"));
	}

	public void run() {
		pullExample();
		pushExample();
	}

}
