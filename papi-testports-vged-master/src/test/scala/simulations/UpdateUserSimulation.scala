package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class UpdateUserSimulation extends Simulation {

  // http config
  val httpConf = http.baseUrl("https://reqres.in/")
    .header(name = "Accept", value = "application/json")
    .header(name = "content-type", value = "application/json")

  // scenario
  val scn = scenario("Check JSON Path")
    .exec(http("Get specific user")
      .put("/api/users/2")
      .body(RawFileBody("./src/test/resources/bodies/UpdateUser.json")).asJson
      .check(status.in(200 to 210)))

    .exec(http("delete user")
    .delete("/api/users/2")
    .check(status.in(200 to 204)))

  // setup
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)

}
