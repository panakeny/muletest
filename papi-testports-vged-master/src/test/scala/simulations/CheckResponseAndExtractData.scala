package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CheckResponseAndExtractData extends Simulation {

  // http config
  val httpConf = http.baseUrl("https://gorest.co.in")

    .header(name = "Authorization", value = "Bearer 98d8133d84942ecbf9fe467697ac921df7d586d0bc8d10e34d4b97fff43dd035")

  // scenario
  val scn = scenario("Check Correlation and extract data")

    .exec(http("Get all users")
      .get("/public-api/users")
      .check(jsonPath("$.data[0].id").saveAs("userId")))

    .exec(http("Get specific user")
      .get("/public-api/users/${userId}")
      .check(jsonPath("$.data.id").is("26"))
      .check(jsonPath("$.data.name").is("Washington Luis Cabral"))
      .check(jsonPath("$.data.email").is("wluissilva@live.com")))

  // setup
  setUp(scn.inject(atOnceUsers(1))) protocols (httpConf)

}
