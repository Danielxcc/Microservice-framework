package bgu.spl.mics;

public class TestCallback implements Callback<String> {

    public TestCallback(){

    }
    @Override
    public void call(String c) {
        System.out.println("Hello There!");
    }

}
