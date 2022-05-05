package no.hvl.dat110.ac.restservice;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.route.HttpMethod.post;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App {
	
	static AccessLog accesslog = null;
	static AccessCode accesscode = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		// objects for data stored in the service
		
		accesslog = new AccessLog();
		accesscode  = new AccessCode();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {
			
		 	Gson gson = new Gson();
		 	
		 	return gson.toJson("IoT Access Control Device");
		});
		
		// TODO: implement the routes required for the access control service
		// as per the HTTP/REST operations described in the project description

		post("/accessdevice/log/", (request, response) -> {
			return accesslog.add(request.body());
		});

		get("/accessdevice/log/", (request, response) -> {
			return accesslog.log;
		});

		get("/accessdevice/log/{id}", (request, response) -> {
			return accesslog.get(Integer.parseInt(request.body()));
		});

		// TODO: Not sure what to do here to convert body to a int[]
		put("/accessdevice/code", (request, response) -> {
			int[] tab = new int[request.contentLength()];
			for (int i = 0; i < request.contentLength(); i++){
				tab[i] = Integer.getInteger(request.body(), i);
			}
			accesscode.setAccesscode(tab);
			return accesscode;
		});

		get("/accessdevice/code", (request, response) -> {
			return accesscode.getAccesscode();
		});

		delete("/accessdevice/log/", (request, response) -> {
			accesslog.clear();
			return accesslog.log;
		});
		
    }
    
}
