package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.MavenPair;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private static MessageBusImpl messageBus = null;

	private MessageBusImpl(){
		MSHash = new ConcurrentHashMap<>();
		typeHash = new ConcurrentHashMap<>();
		FutureHash = new ConcurrentHashMap<>();
	}

	public static MessageBusImpl getInstance(){
		if(messageBus == null){
			messageBus = new MessageBusImpl();
		}
		return messageBus;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		//creates a hash map for type if type has not been subscribed to.
		typeHash.putIfAbsent(type, new ConcurrentLinkedQueue<>());
		// adds m to the subscribed microservices of type.
		typeHash.get(type).add(m);
		// adds type to the types which m is subscribed to.
		MSHash.get(m).getKey().add(type);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		//creates a hash map for type if type has not been subscribed to.
		typeHash.putIfAbsent(type, new ConcurrentLinkedQueue<>());
		// adds m to the subscribed microservices of type.
		typeHash.get(type).add(m);
		// adds type to the types which m is subscribed to.
		MSHash.get(m).getKey().add(type);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		Future<T> f = FutureHash.get(e);
		f.resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// gets the type of the Broadcast we need to send.
		Class<? extends Broadcast> type = b.getClass();

		// iterates over the microservices subscribed to the Broadcast, and sends the broadcast to them.
		ConcurrentLinkedQueue<MicroService> hash = typeHash.get(type);
		for (MicroService m: hash) {
			MSHash.get(m).getValue().add(b);
		}
	}


	@Override
	public synchronized <T> Future<T> sendEvent(Event<T> e) {
		// gets the type of event we need to send.
		Class<? extends Event> type = e.getClass();
		// only send the event if there is a microservice subscribed to the event
		if(!(typeHash.get(type) == null) && !(typeHash.get(type).isEmpty())) {
			// Round-robin: takes the microservice from the queue, and returns him to the back of the queue.
			MicroService m = typeHash.get(type).remove();
			typeHash.get(type).add(m);

			// adds the event to the message queue of the microservice.
			MSHash.get(m).getValue().add(e);

			//creates a new future object, and adds it to the futureHash, then returns the future object.
			Future<T> future = new Future<>();
			FutureHash.putIfAbsent(e, future);
			return future;
		}
		else{
			return null;
		}
	}

	@Override
	public void register(MicroService m) {
		/*
			creates a queue of messages for the microservice, then creates a pair containing
			the queue with a vector of the events that the microservice is subscribed to, then
			adds the pair to the MSHash with the microservice as key.
		 */

		ConcurrentLinkedQueue<Message> queue = new ConcurrentLinkedQueue<>();
		MavenPair pair = new MavenPair(new Vector(), queue);
		MSHash.putIfAbsent(m,pair);

	}

	@Override
	public void unregister(MicroService m) {
		//gets the vector with the messages that the microservice is subscribed to.
		MavenPair x = MSHash.get(m);
		Vector<Class> typeVector = x.getKey();

		// unsubscribes the microservice m from the messages that it's subscribed to.
		for (Class i: typeVector) {
			ConcurrentLinkedQueue<MicroService> iHash = typeHash.get(i);
			iHash.remove(m);
		}

		// removes the microservice m from the hash map of the microservices.
		MSHash.remove(m);
	}

	@Override
	public synchronized Message awaitMessage(MicroService m) throws InterruptedException {
		while(MSHash.get(m).getValue().isEmpty()){
			try{wait(500);} catch (InterruptedException e) { }
		}
		Message message = MSHash.get(m).getValue().remove();
		return message;
	}

	/*
	 	a concurrent hash map which, for each microservice registered, contains: a vector of the subscribed types of messages the microservice specializes in,
	 	and a concurrent link queue with the messages that the microservice has received.
	 */
	private final ConcurrentHashMap<MicroService, MavenPair> MSHash;

	/*
		a concurrent hash map which, for each type of Event, contains a Queue for the round-robin of the microservices subscribed to the message.
	 */
	private final ConcurrentHashMap<Class, ConcurrentLinkedQueue<MicroService>> typeHash;

	/*
		a concurrent hash map which contains the future of each event with the microservice which takes care of the event.
	 */
	private final ConcurrentHashMap<Event, Future> FutureHash;
}
