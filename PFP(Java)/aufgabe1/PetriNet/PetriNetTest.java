import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class PetriNetTest {

	private PetriNet petriNetz;

	@Before
	public void setUp() {
		petriNetz = new PetriNet();
	}

	@Test(timeout = 1000)
	public void testCanFireWithBothInAndOut() {
		final PetriNet petriNet = new PetriNet();
		final Place placeA = petriNet.createPlace(1, 1);

		final Transition t1 = petriNet.createTransition();
		t1.addInput(placeA, 1);
		t1.addOutput(placeA, 1);

		assertTrue("t1 can fire", petriNet.canFire(t1));
	}

	@Test(timeout = 1000)
	public void testExampleNetNumberOfPlacesAndTransitions() {
		final PetriNet petriNet = ExampleNet.getExample();
		assertEquals("ExampleNet should have 3 places", 3, petriNet.getPlaces().size());
		assertEquals("ExampleNet should have 3 transitions", 3, petriNet.getTransitions().size());
	}

	@Test(timeout = 1000)
	public void testCanFireWithTwoTransitions() {
		final PetriNet petriNet = new PetriNet();
		final Place placeA = petriNet.createPlace(1);
		final Place placeB = petriNet.createPlace(0);
		final Place placeC = petriNet.createPlace(1);

		final Transition t1 = petriNet.createTransition();
		t1.addInput(placeA, 1);
		t1.addOutput(placeB, 1);

		final Transition t2 = petriNet.createTransition();
		t2.addInput(placeA, 1);
		t2.addOutput(placeC, 2);

		assertTrue("Initially t1 can fire", petriNet.canFire(t1));
		assertTrue("Initially t2 can fire", petriNet.canFire(t2));
	}

	@Test(timeout = 1000)
	public void testFireWithTwoTransitions() {
		final PetriNet petriNet = new PetriNet();
		final Place placeA = petriNet.createPlace(1);
		final Place placeB = petriNet.createPlace(0);
		final Place placeC = petriNet.createPlace(1);

		final Transition t1 = petriNet.createTransition();
		t1.addInput(placeA, 1);
		t1.addOutput(placeB, 1);

		final Transition t2 = petriNet.createTransition();
		t2.addInput(placeA, 1);
		t2.addOutput(placeC, 2);

		petriNet.fire(t2);

		assertFalse("There should be no more tokens in A -> t1 cannot fire", petriNet.canFire(t1));
		assertFalse("There should be no more tokens in A -> t2 cannot fire", petriNet.canFire(t2));
	}

	@Test(timeout = 1000)
	public void testCreatePlace() {
		Place place = petriNetz.createPlace(5, 10);
		assertEquals("Place should have 5 tokens", 5, place.getTokens());
		assertEquals("Place should have capacity of 10", 10, place.getCapacity());
	}

	@Test(timeout = 1000)
	public void testCreateTransition() {
		Transition transition = petriNetz.createTransition();
		assertNotNull("Transition should not be null", transition);
	}

	@Test(timeout = 1000)
	public void testCanFireTransition() {
		Place placeA = petriNetz.createPlace(2, 5);
		Transition transition = petriNetz.createTransition();
		transition.addInput(placeA, 1);
		transition.addOutput(placeA, 1);

		assertTrue("Transition should be able to fire", petriNetz.canFire(transition));
	}

	@Test(timeout = 1000)
	public void testCannotFireTransitionDueToLackOfTokens() {
		Place placeA = petriNetz.createPlace(0, 5);
		Transition transition = petriNetz.createTransition();
		transition.addInput(placeA, 1);
		transition.addOutput(placeA, 1);

		assertFalse("Transition should not be able to fire due to lack of tokens", petriNetz.canFire(transition));
	}

	@Test(timeout = 1000)
	public void testFireTransition() {
		Place placeA = petriNetz.createPlace(2, 5);
		Transition transition = petriNetz.createTransition();
		transition.addInput(placeA, 1);
		transition.addOutput(placeA, 1);

		petriNetz.fire(transition);

		assertEquals("Place should have 2 tokens after firing", 2, placeA.getTokens());
	}

	@Test(timeout = 1000)
	public void testCannotFireTransitionDueToCapacity() {
		Place placeA = petriNetz.createPlace(2, 2);
		Transition transition = petriNetz.createTransition();
		transition.addInput(placeA, 1);
		transition.addOutput(placeA, 1);

		assertTrue("Transition should not be able to fire due to capacity limit", petriNetz.canFire(transition));
	}

	@Test(timeout = 1000)
	public void testComplexNet() {
		Place placeA = petriNetz.createPlace(1, 5);
		Place placeB = petriNetz.createPlace(0, 5);
		Place placeC = petriNetz.createPlace(0, 5);

		Transition t1 = petriNetz.createTransition();
		t1.addInput(placeA, 1);
		t1.addOutput(placeB, 1);

		Transition t2 = petriNetz.createTransition();
		t2.addInput(placeB, 1);
		t2.addOutput(placeC, 1);

		assertTrue("t1 should be able to fire", petriNetz.canFire(t1));
		petriNetz.fire(t1);
		assertTrue("t2 should be able to fire after t1", petriNetz.canFire(t2));
	}
}