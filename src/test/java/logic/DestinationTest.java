package test.java.logic;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.ParseException;

import main.java.data.members.*;
import main.java.util.LogPrinter;

public class DestinationTest {

	@Test
	public void getDestinationNameTest() {
		try {
			final Destination d = new Destination("taubTest123", new MapLocation(32.778069, 35.021935));
			Assert.assertEquals("taubTest123", d.getDestinationName());
			d.deleteParseObject();

		} catch (final Exception ¢) {
			LogPrinter.createLogFile(¢);
			Assert.fail();
		}
	}

	@Test
	public void getEntranceTest() {
		try {
			final Destination d = new Destination("taubTest123", new MapLocation(32.778069, 35.021935));
			Assert.assertEquals(d.getEntrance().getLat(), 32.778069, 0);
			Assert.assertEquals(d.getEntrance().getLon(), 35.021935, 0);
			d.deleteParseObject();

		} catch (final Exception ¢) {
			LogPrinter.createLogFile(¢);
			Assert.fail();
		}
	}

	// this test checks there is no destination in the db with the name
	// taubTest123
	@Test
	public void destinationExistsTest1() {

		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
	}

	// this test inserts a destination to the db and then checks that there is a
	// dest with the name taubTest123
	@Test
	public void destinationExistsTest2() {

		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
		try {
			final Destination d = new Destination("taubTest123", new MapLocation(32.778069, 35.021935));
			Assert.assertEquals(Destination.destinationExists("taubTest123"), true);
			d.deleteParseObject();

		} catch (final Exception ¢) {
			LogPrinter.createLogFile(¢);
			Assert.fail();
		}
	}

	/*
	 * this test checks that when you try to set destination name to a name that
	 * already exists, an AlreadyExists exception is thrown.
	 */
	@Test
	public void setDestinationNameTest() {
		Destination d1 = null, d2 = null;
		try {
			d1 = new Destination("taubTest123", new MapLocation(32.778069, 35.021935));
			d2 = new Destination("taubTest124", new MapLocation(32.778069, 35.021935));
			d1.setDestinationName("taubTest124");
			Assert.fail();
		} catch (final Exception e) {
			Assert.assertEquals(e.getClass().getSimpleName(), "AlreadyExists");
			try {
				d1.deleteParseObject();
				d2.deleteParseObject();
			} catch (final ParseException e2) {
				LogPrinter.createLogFile(e2);
				Assert.fail();
			}
		}
	}

	// this test check the setDestinationName function when there are no
	// problems with the name
	@Test
	public void setDestinationNameTest2() {
		try {
			final Destination d = new Destination("taubTest123", new MapLocation(32.778069, 35.021935));
			d.setDestinationName("taubTest124");
			Assert.assertEquals(d.getDestinationName(), "taubTest124");
			Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
			Assert.assertEquals(Destination.destinationExists("taubTest124"), true);
			d.deleteParseObject();

		} catch (final Exception ¢) {
			LogPrinter.createLogFile(¢);
			Assert.fail();
		}
	}

	@Test
	public void setEntranceTest() {
		try {

			Destination d1 = new Destination("taubTest123", new MapLocation(32.777318, 35.021149));
			d1.setEntrance(new MapLocation(32.778069, 35.021935));
			Assert.assertEquals(d1.getEntrance().getLat(), 32.778069, 0);
			Assert.assertEquals(d1.getEntrance().getLon(), 35.021935, 0);
			Destination d2 = new Destination("taubTest123");
			Assert.assertEquals(d1.getEntrance().getLat(), d2.getEntrance().getLat(), 0);
			Assert.assertEquals(d1.getEntrance().getLon(), d2.getEntrance().getLon(), 0);
			
			d1.setEntrance(32.123, 35.123);
			Assert.assertEquals(d1.getEntrance().getLat(), 32.123, 0);
			Assert.assertEquals(d1.getEntrance().getLon(), 35.123, 0);
			d2 = new Destination("taubTest123");
			Assert.assertEquals(d1.getEntrance().getLat(), d2.getEntrance().getLat(), 0);
			Assert.assertEquals(d1.getEntrance().getLon(), d2.getEntrance().getLon(), 0);
			
			d1.deleteParseObject();
		} catch (final Exception ¢) {
			LogPrinter.createLogFile(¢);
			Assert.fail();
		}
	}

	/*
	 * this test shows that if there is a destination with the same name, an
	 * AlreadyExists exception will be thrown, hence preventing inserting
	 * another destination with the same name to the DB
	 */
	@Test
	public void constructorTest1() {

		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
		Destination d = null;
		try {
			d = new Destination("taubTest123", new MapLocation(32.778069, 35.021935));
			Assert.assertEquals(Destination.destinationExists("taubTest123"), true);
			d = new Destination("taubTest123", new MapLocation(32.778069, 35.021935));
			Assert.fail();

		} catch (final Exception e) {
			Assert.assertEquals(e.getClass().getSimpleName(), "AlreadyExists");
			try {
				d.deleteParseObject();
			} catch (final ParseException e2) {
				LogPrinter.createLogFile(e2);
				Assert.fail();
			}
		}

	}

	/*
	 * this test shows that if there is a destination in the db with name X you
	 * have can initialize a Destination object with the data of X
	 */

	@Test
	public void constructorTest2() {

		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
		try {
			final Destination d = new Destination("taubTest123", new MapLocation(32.778069, 35.021935)),
					d2 = new Destination("taubTest123");
			Assert.assertEquals("taubTest123", d2.getDestinationName());
			Assert.assertEquals(d.getEntrance().getLat(), d2.getEntrance().getLat(), 0);
			Assert.assertEquals(d.getEntrance().getLon(), d2.getEntrance().getLon(), 0);
			d.deleteParseObject();
		} catch (final Exception ¢) {
			LogPrinter.createLogFile(¢);
			Assert.fail();
		}
	}

	/*
	 * this test shows that if there isn't a destination in the db with name X
	 * and you try to initialize a Destination object with the data of X you
	 * will get a NotExists exception
	 */

	@Test
	public void constructorTest3() {

		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
		try {
			new Destination("taubTest123");
			Assert.fail();
		} catch (final Exception ¢) {
			Assert.assertEquals(¢.getClass().getSimpleName(), "NotExists");
		}
	}

	@Test
	public void getDestinationsTest() {
		assert Destination.getDestinations().keySet().contains("Computer Science Faculty");
	}

	@Test
	public void constructorTest4() {
		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
		try {
			final Destination d = new Destination("taubTest123", 32.778069, 35.021935);
			Destination d2 = new Destination("taubTest123");
			Assert.assertEquals("taubTest123", d2.getDestinationName());
			Assert.assertEquals(d.getEntrance().getLat(), d2.getEntrance().getLat(), 0);
			Assert.assertEquals(d.getEntrance().getLon(), d2.getEntrance().getLon(), 0);
			d.deleteParseObject();
		} catch (final Exception ¢) {
			LogPrinter.createLogFile(¢);
			Assert.fail();
		}
	}

	@Test
	public void constructorTest5() {
		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
		try {
			final Destination d = new Destination("taubTest123", 32.778069, 35.021935);
			Destination d2 = new Destination(new MapLocation(32.778069, 35.021935));
			Assert.assertEquals("taubTest123", d2.getDestinationName());
			Assert.assertEquals(d.getEntrance().getLat(), d2.getEntrance().getLat(), 0);
			Assert.assertEquals(d.getEntrance().getLon(), d2.getEntrance().getLon(), 0);
			d.deleteParseObject();
		} catch (final Exception ¢) {
			LogPrinter.createLogFile(¢);
			Assert.fail();
		}
	}

}
