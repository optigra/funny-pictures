package com.optigra.funnypictures.integration

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class PictureScenario extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8080/funny-pictures-rest-api/api")
    .acceptHeader("application/json")

  val pictureDetails = Array(Map("name" -> "name1", "url" -> "/414f8a8d-d856-4010-b33d-96b58e419233.png"))
                       .queue
    
  val scn = scenario("Manage Pictures")
	.feed(pictureDetails)
	
	    .exec(http("Post Picture")
	    		.post("/pictures")
	    		.header("Content-Type", "application/json")
	    		.body(StringBody("""{ "name": "${name}" , "url": "${url}" }"""))
	    		.asJSON
	    		.check(status.is(201))
	    		.check(jsonPath("$.id").find.saveAs("savedId"))
	    )
	    		
	 .pause(500 milliseconds, 2 seconds)
	 	.exec(
      		http("Get picture")
        	.get("/pictures/${savedId}")
        	.check(status.is(200)))
			
    	.exec(
      		http("Update picture")
        	.put("/pictures/${savedId}")
			.header("Content-Type", "application/json")
	    	.body(StringBody("""{ "name": "updatedName" , "url": "${url}" }"""))
			.asJSON
        	.check(status.is(200)))
	 
        .exec(
      		http("Get picture")
        	.get("/pictures/${savedId}")
        	.check(status.is(200))
        	.check(jsonPath("$.name").find.is("updatedName")))
        	
        	
	 	.exec(http("Delete picture")
        	.delete("/pictures/${savedId}")
        	.check(status.is(204)))

  setUp(
      scn.inject(atOnceUsers(1)).protocols(httpConf)
  )
}
