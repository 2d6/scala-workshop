package workshop


/**
  * In this set of exercises, we want to introduce new types representing storage units (Megabytes and so on).
  * This will enable us to use these types in our APIs, thus
  * - encapsulating conversion of these types (Megabyte => Kilobyte) and e.g. operations like addition and multiplication.
  * - reducing the likelihood of mixing up sizes when using our API (think maxSize(bytes: Long) versus
  * maxSize(megabytes: Megabyte))
  *
  * In order to enable fluid usage of our API, we also want to provide a convenient way to convert simple integers to
  * storage units by extending the interface of Long using implicit classes. This will enable the user to very
  * readably create instances of our types. Example:
  * maxSize(2 megabytes) versus maxSize(MegaByte(2))
  *
  * The exercises will make use of implicit function definitions and classes. There is one significant use case of the
  * keyword 'implicit', which will not be covered in this set of exercises: implicit function parameters. This case
  * is covered in the exercise set 'Functions'.
  */
object Implicits {

  sealed trait StorageUnit {
    /**
      * The value of the storage unit. This should e.g. return 1 if the implementing instance Byte represents one Byte.
      */
    val value: Long

    /**
      * A human readable representation of the unit, e.g. '1 Byte'
      */
    def mkString: String
  }

  /*
  TDD exercise:
  Create a case class 'Byte', which extends the trait StorageSize.

  Provide methods '+' and '-', enabling addition and subtraction of Bytes from one another. This should make the
  following expressions compile:
  val eightBytes: Byte = Byte(4) + Byte(4)
  val twoBytes: Byte = Byte(3) - Byte(1)
   */

  /*
  TDD exercise:
  Create a new case class 'Kilobyte', representing 1000 bytes and also extending the StorageUnit trait.

  Provide methods '+' and '-', enabling addition and subtraction of Kilobytes from one another. This should make the
  following expressions compile:
  val twoKilobytes: Kilobyte = Kilobyte(1) + Kilobyte(1)
  val fourKilobytes: Kilobyte = Kilobyte(5) - Kilobyte(1)
   */

  /*
  TDD exercise:
  Right now, we can only add and subtract Bytes to/from Bytes and Kilobytes to/from Kilobytes. Obviously, it would be
  nice to be able to add Bytes to Kilobytes and vice versa. Since Bytes cannot be represented by integer values of
  Kilobyte, these mixed additions should always yield instances of Byte.

  Provide a conversion function 'asBytes' which converts instances of Kilobyte to Byte. Define this function in the
  companion object to Kilobyte. This should make the following code compile:
  val y: Byte = Kilobyte.asByte(Kilobyte(1)) + Byte(1)
   */

  /*
  Exercise:
  The conversion syntax is pretty ugly right now. You might even consider making the conversion function an instance
  method, which would result in code like this:
  val y: Byte = Kilobyte(1).asBytes + Byte(1)

  However, as an API user, I still have to care about this low-level detail of unit conversion. The explicit call makes
  the code less readable. I would like it if I do not have to think about these trivial conversions. Luckily, the Scala
  compiler has a way of taking over this menial task via implicits.

  Add the keyword 'implicit' in front of the 'asBytes' function. Now, you will see that suddenly, the following code
  will compile:
  val x: Byte = Kilobyte(1) + Byte(1) - Kilobyte(1)
  Your IDE will probably highlight where the compiler inserts implicit conversions.
   */

  /*
  Exercise:
  This is already pretty nice. We can now fluidly add and subtract bytes and kilobytes, not caring about how to actually
  convert between these units. By way of implicits, we do not even have to specify where to apply appropriate conversions.

  However, I previously mentioned that I would like to instantiate storage unit values using the following Syntax:
  val byte: Byte = 1 byte
  val bytes: Byte = 1 kilobyte + 2 bytes

  Implement an 'implicit class StorageUnitLong' providing the following convenience methods:
    def byte: Byte
    def bytes: Byte

    def kilobyte: Kilobyte
    def kilobytes: Kilobyte

  This should make the following code compile:
    import workshop.Implicits.StorageUnitLong
    def printUnit(storageUnit: StorageUnit): Unit = println(storageUnit.mkString)
    printUnit(1000.kilobytes - 1.byte)
  Which should print "999 Bytes" when executed.
   */
}
