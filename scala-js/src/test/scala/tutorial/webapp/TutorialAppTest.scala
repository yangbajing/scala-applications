package tutorial.webapp

import org.scalajs.jquery.jQuery
import utest._

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-08-09.
  */
object TutorialAppTest extends TestSuite {

  TutorialApp.setupUI()

  override def tests = TestSuite {
    'HelloWorld {
      assert(jQuery("p:contains('您好, 世界!')").length >= 1)
    }

    'ButtonClick {
      def messageCount = jQuery("p:contains('You clicked the button!')").length

      val button = jQuery("button:contains('Click me!')")

      assert(button.length == 1)
      assert(messageCount == 0)

      for (c <- 1 to 5) {
        button.click()
        assert(messageCount == c)
      }
    }
  }

}
