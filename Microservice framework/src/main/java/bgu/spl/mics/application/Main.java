package bgu.spl.mics.application;

import bgu.spl.mics.*;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/** This is the Main class of the application. You should parse the input file, 
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		String filepath = args[0];
		Input input = new Input();
		try {
			input = JsonInputReader.getInputFromJson(filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MessageBusImpl m = MessageBusImpl.getInstance();
		Ewoks Ewoks = bgu.spl.mics.application.passiveObjects.Ewoks.getInstance();
		int ewoks = input.getEwoks();
		int id = 1;
		while(ewoks > 0){
			Ewoks.addEwok(id);
			id++;
			ewoks--;
		}
		Diary diary = Diary.getInstance();
		holder hold = holder.getInstance();
		//create every microservice
		HanSoloMicroservice hanSoloMicroservice = new HanSoloMicroservice();
		C3POMicroservice c3POMicroservice = new C3POMicroservice();
		LeiaMicroservice leiaMicroservice = new LeiaMicroservice(input.getAttacks());
		R2D2Microservice r2D2Microservice = new R2D2Microservice(input.getR2D2());
		LandoMicroservice landoMicroservice = new LandoMicroservice(input.getLando());

		CountDownLatch latch = new CountDownLatch(5); //number of microservices.
		hold.setlatch(latch);
		hold.setEndLatch(new CountDownLatch(5));
		new Thread(hanSoloMicroservice).start();
		new Thread(c3POMicroservice).start();
		new Thread(leiaMicroservice).start();
		new Thread(r2D2Microservice).start();
		new Thread(landoMicroservice).start();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//after the latch is released, create and send the output.
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			FileWriter writer = new FileWriter(args[1]);
			gson.toJson(diary,writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
