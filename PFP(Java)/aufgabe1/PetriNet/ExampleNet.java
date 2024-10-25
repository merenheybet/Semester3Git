public class ExampleNet {
	public static PetriNet getExample() {
		PetriNet result = new PetriNet();

		// Create places and transitions
		Place a = result.createPlace(1, 1);
		Place b = result.createPlace();
		Place c = result.createPlace();

		Transition t1 = result.createTransition();
		Transition t2 = result.createTransition();
		Transition t3 = result.createTransition();

		// Set inputs and outputs of the transitions
		t1.addInput(a, 1);
		t1.addOutput(b, 1);
		t2.addInput(c, 2);
		t2.addOutput(a, 1);
		t3.addInput(b, 1);
		t3.addOutput(c, 3);

		return result;
	}
}

