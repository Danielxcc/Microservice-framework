package bgu.spl.mics;
import bgu.spl.mics.application.passiveObjects.Ewok;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

//import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.Math;

public class EwokTest {

    private Ewok ewok;

    @BeforeEach
    public void setUp(){
        int num = (int)(Math.random() * 1000);
        ewok = new Ewok(num);
    }

    @Test
    public void testAcquire(){
        assertTrue(ewok.isAvailable());
        ewok.acquire();
        assertFalse(ewok.isAvailable());
    }

    public void testRelease(){
        ewok.acquire();
        assertFalse(ewok.isAvailable());
        ewok.release();
        assertTrue(ewok.isAvailable());
    }

}
