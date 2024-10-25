import java.util.ArrayList;
import java.util.List;

/**
 * A class describing a petri net.
 */
public class PetriNet {
	
	private final List<Place> places;
	private final List<Transition> transitions;



	/**
	 * Creates a new, empty petri net.
	 */
	public PetriNet() {
		this.places = new ArrayList<Place>();
		this.transitions = new ArrayList<Transition>();
	}



	/**
	 * Checks whether the specified transition can fire.
	 *
	 * @param transition the transition to check
	 * @return true if the transition can fire; false otherwise
	 */
	public boolean canFire(final Transition transition) {
		// Check if all inputs have enough markers to fire
		for (Place p : transition.getInputs()){
			int tokenAmount = p.getTokens();
			if (transition.getInputWeight(p) > tokenAmount){return false;}
		}

		for (Place p : transition.getOutputs()){
			int tokenAmount = p.getTokens();
			int remainingCapacity = p.getCapacity() - tokenAmount;
			if (transition.getOutputWeight(p) > remainingCapacity){return false;}
		}

		return true;
	}



	/**
	 * Fires the specified transition. This method may only be called if
	 * canFire returned true for the specified transition.
	 *
	 * @param transition the transition to fire
	 */
	public void fire(final Transition transition) {
		assert canFire(transition);
		// Reduce tokens from the inputs by their relative weight.
		for (Place in : transition.getInputs()){
			in.setTokens(in.getTokens() - transition.getInputWeight(in));
		}

		for (Place out : transition.getOutputs()){
			out.setTokens(out.getTokens() + transition.getOutputWeight(out));
		}
	}



	// Helper factory methods below:

	/**
	 * Creates a new place without tokens and without capacity.
	 */
	public Place createPlace() {
		return createPlace(0);
	}



	/**
	 * Creates a new place with the specified number of tokens but without
	 * capacity.
	 */
	public Place createPlace(final int initialTokens) {
		return createPlace(initialTokens, Integer.MAX_VALUE);
	}



	/**
	 * Creates a new place with the specified number of tokens and capacity.
	 */
	public Place createPlace(final int initialTokens, final int capacity) {
		final StringBuilder nameBuilder = new StringBuilder();
		int id = places.size();
		while (id >= 26) {
			nameBuilder.append((char) ('A' + id % 26));
			id /= 26;
		}
		nameBuilder.append((char) ('A' + id));

		final Place result = new Place(nameBuilder.reverse().toString(), initialTokens, capacity);
		this.places.add(result);
		return result;
	}



	/**
	 * Creates a new transition.
	 */
	public Transition createTransition() {
		final Transition result = new Transition("t" + this.transitions.size());
		this.transitions.add(result);
		return result;
	}



	// Accessor methods below

	/**
	 * Returns the list of places in this petri net.
	 * 
	 * @return the list of places
	 */
	public List<Place> getPlaces() {
		return this.places;
	}



	/**
	 * Returns the list of transitions in this petri net.
	 * 
	 * @return the list of transitions
	 */
	public List<Transition> getTransitions() {
		return this.transitions;
	}
}

