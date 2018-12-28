package sample.respository

import com.github.tminglei.slickpg.{ExPostgresProfile, PgDate2Support}

trait SlickProfile extends ExPostgresProfile with PgDate2Support {
  override val api: API = new super.API with DateTimeImplicits {}
}

object SlickProfile extends SlickProfile
