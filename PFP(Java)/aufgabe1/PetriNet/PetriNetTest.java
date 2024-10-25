import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class PetriNetTest {

	@Test(timeout = 1000)
	public void testExampleNetNumberOfPlacesAndTransitions() {
		final PetriNet petriNet = ExampleNet.getExample();
		assertEquals("ExampleNet should have 3 places", 3, petriNet.getPlaces().size());
		assertEquals("ExampleNet should have 3 transitions", 3, petriNet.getTransitions().size());
	}



	@Test(timeout = 1000)
	public void testCanFireWithTwoTransitions() {
		// Test the following petri net:
		//          ( 1 )
		//     t1 <-/   \-> t2
		//   V-/             \-V(2)
		// ( 0 )             ( 1 )
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
		// Test the following petri net:
		//          ( 1 )
		//     t1 <-/   \-> t2
		//   V-/             \-V(2)
		// ( 0 )             ( 1 )
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
}

