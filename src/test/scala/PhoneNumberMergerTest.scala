import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class PhoneNumberMergerTest extends FlatSpec with Matchers with TableDrivenPropertyChecks {

  private val LandlineA = PhoneNumber(Landline, "1234")
  private val BusinessA = PhoneNumber(Business, "2323")
  private val MobileA = PhoneNumber(Mobile, "4567")
  private val MobileB = PhoneNumber(Mobile, "9999")

  "A phone number merger" should "return an empty list if old and new are empty" in {
    PhoneNumberMerger.merge(Nil, Nil) shouldBe Nil
  }

  it should "return the old items if the new items are empty" in {
    val oldNumbers = Seq(LandlineA)

    PhoneNumberMerger.merge(oldNumbers, Nil) shouldBe oldNumbers
  }

  it should "return the new items if the old items are empty" in {
    val newNumbers = Seq(LandlineA)

    PhoneNumberMerger.merge(Nil, newNumbers) shouldBe newNumbers
  }

  it should "replace old items with new items of same type" in {
    val oldNumbers = Seq(MobileA)
    val newNumbers = Seq(MobileB)

    PhoneNumberMerger.merge(oldNumbers, newNumbers) shouldBe newNumbers
  }

  it should "merge phone numbers of differing type" in {
    val oldNumbers = Seq(BusinessA)
    val newNumbers = Seq(LandlineA)

    val merged = PhoneNumberMerger.merge(oldNumbers, newNumbers)

    merged should contain theSameElementsAs (oldNumbers ++ newNumbers)
  }

  it should "merge and replace" in {
    val oldNumbers = Seq(BusinessA, MobileA)
    val newNumbers = Seq(LandlineA, MobileB)

    val merged = PhoneNumberMerger.merge(oldNumbers, newNumbers)

    merged should contain theSameElementsAs Seq(BusinessA, MobileB, LandlineA)
  }

}
