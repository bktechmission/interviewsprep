package designpattern;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PubSubPattern {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bazz bz1 =  (arg)->{System.out.println("arg is "+arg);};
		UITKEventBus.subscribe("FooPublishAnything", bz1);
		
		UITKEventBus.publish("FooPublishAnything", ()->"foo published I am "+20);
		//EventBus.unsubscribe("bhupi", bz1);
		
		UITKEventBus.publish("FooPublishAnything", ()->"foo published time for tea ");
	}

}

class UITKEventBus{
	static final Map<String, List<Bazz>> map = new HashMap<>();
	
	static void subscribe(String topic, Bazz c) {
		List<Bazz> bz = map.get(topic);
		if(bz==null) {
			bz = new LinkedList<>();
			map.put(topic, bz);
		}
		
		bz.add(c);
	}
	
	static void unsubscribe(String topic, Bazz c) {
		List<Bazz> bz = map.get(topic);
		if(bz!=null) {
			bz.remove(bz.indexOf(c));
		}
		if(bz.isEmpty()) {
			map.remove(topic);
		}
		
	}
	
	static void publish(String topic, Foo1 foo) {
		List<Bazz> consumers = map.get(topic);
		if(consumers!=null) {
			consumers.forEach(c->{c.consumeMessage(foo.publishMessage());});
		}
		else {
			System.out.println("EventBus does not have any subscriber to topic: "+topic);
		}
	}
}

interface Foo1{
	String publishMessage();
}

interface Bazz{
	void consumeMessage(String message);
}

