package bgu.spl.mics;

public class TestBroadcast implements Broadcast {
    public TestBroadcast(String message){
        this.message = message;
    }

    public String play(){
        System.out.println(message);
        return message;
    }

    private String message;
}
