package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TestApiSimulation extends Simulation {

  // http config
  val httpConf = http.baseUrl("https://reqres.in/")
    .header(name = "Accept", value = "application/json")
    .header(name = "content-type", value = "application/json")

  // scenario
  val scn = scenario("get user")
    .exec(http("get user request")
      .get("/api/users/2")
      .check(status.is(200)))

  // setup
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)

}