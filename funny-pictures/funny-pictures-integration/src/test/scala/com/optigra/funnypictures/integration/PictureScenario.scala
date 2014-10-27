package com.optigra.funnypictures.integration

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class PictureScenario extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8080/funny-pictures-rest-api/api") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml,application/json;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_10 = Map("Content-Type" -> """application/x-www-form-urlencoded""") // Note the headers specific to a given request

  val scn = scenario("Test Pictures") // A scenario is a chain of requests and pauses
    .exec(http("Request Pictures")
      .get("/pictures"))
    

  setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
}
