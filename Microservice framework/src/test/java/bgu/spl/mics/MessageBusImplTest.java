package bgu.spl.mics;

import bgu.spl.mics.application.Callbacks.AttackCallback;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.services.C3POMicroservice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {

    private static MessageBusImpl messageBus;

    @BeforeAll
    static void setUp() {
        messageBus = MessageBusImpl.getInstance();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void complete() {
        /**
         * changes:
         *  changed the callback in line 35 from null to new AttackCallback()
         */
        C3POMicroservice m = new C3POMicroservice();
        messageBus.register(m);
        AttackEvent a = new AttackEvent();
        Class A = a.getClass();
        m.subscribeEvent(A, new AttackCallback());
        messageBus.subscribeEvent(a.getClass(), m);
        Future<Boolean> f = messageBus.sendEvent(a);
        messageBus.complete(a,true);
        assertTrue(f.isDone());
    }

    @Test
    void awaitMessage() throws InterruptedException {
        /*
            In this test we are checking the methods sendEvent,sendBroadcast and awaitMessage.
            as we are checking the methods of the messageBus, we decided to use the methods directly through the messageBus.
         */
        TestMicroService m1 = new TestMicroService();
        TestMicroService m2 = new TestMicroService();
        TestMicroService m3 = new TestMicroService();
        messageBus.register(m1);
        messageBus.register(m2);
        messageBus.register(m3);

        // First test
        TestEvent a = new TestEvent();
        Class A = TestEvent.class;
        messageBus.subscribeEvent(A, m1);
        messageBus.sendEvent(a);
        Message b = messageBus.awaitMessage(m1);
        boolean first = b.equals(a);
        assertTrue(first);

        // Second test
        TestBroadcast sand = new TestBroadcast("I don't like sand");
        Class B = TestBroadcast.class;
        messageBus.subscribeBroadcast(B,m2);
        messageBus.subscribeBroadcast(B,m3);
        messageBus.sendBroadcast(sand);
        Message sand1 = messageBus.awaitMessage(m2);
        Message sand2 = messageBus.awaitMessage(m3);
        boolean second = sand1.equals(sand2) && sand1.equals(sand);
        assertTrue(second);

    }
}