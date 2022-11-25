package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt

class CheckPauseTimeAndResponse extends Simulation {

  // http config
  val httpConf = http.baseUrl("https://reqres.in/")
    .header(name = "Accept", value = "application/json")
    .header(name = "content-type", value = "application/json")

  // scenario
  val scn = scenario("user api calls")
    .exec(http("list all users")
      .get("/api/users?page=2")
      .check(status.is(200)))

    .pause(5)

    .exec(http("single user api")
      .get("/api/users/2")
      .check(status.in(200 to 210)))

    .pause(1, 10)

    .exec(http("single user not found api")
      .get("/api/users/2")
      .check(status.not(400), status.not(500)))

    .pause(3000.milliseconds)

  // setup
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)

}
