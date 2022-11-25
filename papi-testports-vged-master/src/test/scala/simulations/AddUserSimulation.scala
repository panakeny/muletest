package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class AddUserSimulation extends Simulation {

  // http config
  val httpConf = http.baseUrl("https://reqres.in/")
    .header(name = "Accept", value = "application/json")
    .header(name = "content-type", value = "application/json")

  // scenario
  val scn = scenario("Add User Scenario")
    .exec(http("add user request")
      .post("/api/users")
      .body(RawFileBody("./src/test/resources/bodies/AddUser.json")).asJson
      .check(status.in(200 to 201)))

    .pause(3)

    .exec(http("get user request")
      .get("/api/users/2")
      .check(status.is(200)))

    .pause(2)

    .exec(http("get all users request")
      .get("/api/users?page=2")
      .check(status.is(200)))

  // setup
  setUp(scn.inject(atOnceUsers(10))).protocols(httpConf)

}
