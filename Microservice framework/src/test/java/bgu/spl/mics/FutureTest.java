package bgu.spl.mics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {

    private Future<String> future;

    @BeforeEach
    public void setUp(){
        future = new Future<>();
    }

    @Test
    public void testGet()
    {
        assertFalse(future.isDone());
        future.resolve("");
        future.get();
        assertTrue(future.isDone());
    }

    @Test
    public void testResolve(){
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        assertEquals(future.get(), str);
    }

    @Test
    public void testIsDone(){
        String str = "someResult";
        assertFalse(future.isDone());
        future.resolve(str);
        assertTrue(future.isDone());
    }

    @Test
    public void testGetWithTimeOut() throws InterruptedException
    {
        assertFalse(future.isDone());
        future.get(100,TimeUnit.MILLISECONDS);
        assertFalse(future.isDone());
        future.resolve("foo");
        assertEquals(future.get(100,TimeUnit.MILLISECONDS),"foo");
    }

    @Test
    public void testTimeOut() throws InterruptedException
    {
        // checking to see if get really waits for the specified time, result should be close enough.
        // added only for self curiosity, not really practical.
        long start = System.nanoTime();
        String result = future.get(2,TimeUnit.MILLISECONDS);
        System.out.println(System.nanoTime() - start);
        assertEquals(null, result);
    }
}
