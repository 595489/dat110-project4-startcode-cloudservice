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
			accesslog.add(request.body());

			return accesslog.toJson();

//			Gson gson = new Gson();
//			return gson.toJson("test post");
		});

		get("/accessdevice/log/", (request, response) -> {
			return accesslog.toJson();
		});

		get("/accessdevice/log/:id", (request, response) -> {
			Gson gson = new Gson();

			return gson.toJson(accesslog.get(Integer.parseInt(request.params(":id"))));
		});

		// TODO: Not sure what to do here to convert body to a int[]. Also, not tested
		put("/accessdevice/code", (request, response) -> {
			int[] tab = new int[request.contentLength()];
			for (int i = 0; i < request.contentLength(); i++){
				tab[i] = Integer.getInteger(request.body(), i);
			}
			accesscode.setAccesscode(tab);

			Gson gson = new Gson();

			return gson.toJson(accesscode);
		});

		get("/accessdevice/code", (request, response) -> {
			Gson gson = new Gson();

			return gson.toJson(accesscode.getAccesscode());
		});

		delete("/accessdevice/log/", (request, response) -> {
			accesslog.clear();
			return accesslog.toJson();
		});
		
    }
    
}
