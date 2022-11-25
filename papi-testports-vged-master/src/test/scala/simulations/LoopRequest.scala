package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class LoopRequest extends Simulation {

  // http config
  val httpConf = http.baseUrl("https://reqres.in")
    .header(name = "Accept", value = "application/json")
    .header(name = "content-type", value = "application/json")

  // functions
  def getAllUsersRequest() = {
    repeat(2) {
      exec(http("get all users request")
        .get("/api/users?page=2")
        .check(status.is(200)))
    }
  }

  def getSingleUserRequest() = {
    repeat(2) {
      exec(http("get single user request")
        .get("/api/users/11")
        .check(status.is(200)))
    }
  }

  def addUser() = {
    repeat(2) {
      exec(http("get single user request")
        .post("/api/users")
        .body(RawFileBody("./src/test/resources/bodies/AddUser.json")).asJson
        .check(status.is(201)))
    }
  }

  // scenario
  val scn = scenario("user api calls")
    .exec(getAllUsersRequest())
    .pause(2)
    .exec(getSingleUserRequest())
    .pause(2)
    .exec(addUser())

  // setup
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)

}
