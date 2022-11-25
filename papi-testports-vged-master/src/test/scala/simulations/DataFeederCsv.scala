package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class DataFeederCsv extends Simulation {

  // http config
  val httpConf = http.baseUrl("https://gorest.co.in")
    .header(name = "Authorization", value = "Bearer 98d8133d84942ecbf9fe467697ac921df7d586d0bc8d10e34d4b97fff43dd035")

  // set csv in a variable
  // options: circular, shuffle, random, queue
  val csvFeeder = csv("./src/test/resources/data/getUser.csv").circular

  // functions
  def getSingleUser() = {
    repeat(7) {
      feed(csvFeeder)
        .exec(http("Get single user request")
          .get("/public-api/users/${userid}")
          .check(jsonPath("$.data.name").is("${name}"))
          .check(status.in(200, 304)))
        .pause(2)
    }
  }

  // scenario
  val scn = scenario("CSV FEEDER test")
    .exec(getSingleUser())

  // setup
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)

}
