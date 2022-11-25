package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class FixedDurationLoadSimulation extends Simulation {

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
  val scn = scenario("Fixed Duration Load Simulation")
    .forever() {
      exec(getAllUsersRequest())
        .pause(5)
        .exec(getSingleUserRequest())
        .pause(5)
        .exec(addUser())
    }

  // setup
  setUp(scn.inject(
    nothingFor(5),
    atOnceUsers(10),
    rampUsers(50) during (30 seconds)
  ).protocols(httpConf)
  ).maxDuration(1 minute)

}
