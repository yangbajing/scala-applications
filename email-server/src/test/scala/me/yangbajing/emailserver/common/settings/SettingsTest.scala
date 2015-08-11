package me.yangbajing.emailserver.common.settings

import org.scalatest.{Matchers, WordSpec}

/**
 * Settings Test
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-11.
 */
class SettingsTest extends WordSpec with Matchers {
  "Settings" should {
    "emails is nonEmpty" in {
      Settings.config.emails should have size 2
    }

    "server is nonEmpty" in {
      Settings.config.server.interface should not be empty
      Settings.config.server.port should be > 0
    }
  }
}
