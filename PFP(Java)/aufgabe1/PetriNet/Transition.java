import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * A transition in a petri net. A transition has a name, a list of weighted
 * incoming edges, and a list of weighted outgoing edges.
 */
public class Transition {

	private final String name;
	private final List<Place> inputs;
	private final List<Integer> inputWeights;
	private final List<Place> outputs;
	private final List<Integer> outputWeights;



	/**
	 * Creates a new transition with the specified name. Initially the
	 * transition is not connected to any place.
	 *
	 * @param name the name of the transition
	 * @see PetriNet#createTransition()
	 */
	public Transition(final String name) {
		this.name = name;
		this.inputs = new ArrayList<Place>();
		this.inputWeights = new ArrayList<Integer>();
		this.outputs = new ArrayList<Place>();
		this.outputWeights = new ArrayList<Integer>();
	}



	/**
	 * Returns the name of the transition.
	 *
	 * @return the name of the transition
	 */
	public String getName() {
		return this.name;
	}



	/**
	 * Returns the places from which this transition consumes tokens.
	 * 
	 * @return the list of input places
	 * @see Transition#getOutputs()
	 */
	public List<Place> getInputs() {
		return Collections.unmodifiableList(this.inputs);
	}



	/**
	 * Returns the weight of an input place. The weight is the number of tokens
	 * consumed from the specified place when this transition fires.
	 *
	 * @param place the input place
	 * @return the weight for the specified place
	 * @see Transition#getOutputWeight(Place)
	 */
	public int getInputWeight(final Place place) {
		return getWeight(this.inputs, this.inputWeights, place);
	}



	/**
	 * Adds a directed edge from the specified place to this transition. So, if
	 * this transition fires, it will consume the specified amount of tokens
	 * from the specified place. If this method is called multiple times on the
	 * same place, the weights are added.
	 *
	 * @param place the input place
	 * @param weight the number of tokens consumed
	 * @see Transition#addOutput(Place,int)
	 */
	public void addInput(final Place place, final int weight) {
		add(this.inputs, this.inputWeights, place, weight);
	}



	/**
	 * Removes a directed edge from the specified place to this transition.
	 *
	 * @param place the input place
	 * @see Transition#removeOutput(Place)
	 */
	public void removeInput(final Place place) {
		remove(this.inputs, this.inputWeights, place);
	}



	/**
	 * Returns the places in which this transition produces tokens.
	 * 
	 * @return the list of output places
	 * @see Transition#getInputs()
	 */
	public List<Place> getOutputs() {
		return Collections.unmodifiableList(this.outputs);
	}



	/**
	 * Returns the weight of an output place. The weight is the number of tokens
	 * produced in the specified place when this transition fires.
	 *
	 * @param place the output place
	 * @return the weight for the specified place
	 * @see Transition#getInputWeight(Place)
	 */
	public int getOutputWeight(final Place place) {
		return getWeight(this.outputs, this.outputWeights, place);
	}



	/**
	 * Adds a directed edge from this transition to the specified place. So, if
	 * this transition fires, it will produce the specified amount of tokens
	 * in the specified place. If this method is called multiple times on the
	 * same place, the weights are added.
	 *
	 * @param place the output place
	 * @param weight the number of tokens produced
	 * @see Transition#addInput(Place,int)
	 */
	public void addOutput(final Place place, final int weight) {
		add(this.outputs, this.outputWeights, place, weight);
	}



	/**
	 * Removes a directed edge from this transition to the specified place.
	 *
	 * @param place the output place
	 * @see Transition#removeInput(Place)
	 */
	public void removeOutput(final Place place) {
		remove(this.outputs, this.outputWeights, place);
	}



	private int getWeight(final List<Place> places, final List<Integer> weights, final Place place) {
		final int idx = places.indexOf(place);
		if (idx < 0) {
			throw new IllegalArgumentException();
		}

		return weights.get(idx);
	}



	private void add(final List<Place> places, final List<Integer> weights, final Place place,
			final int weight) {
		if (weight <= 0) {
			throw new IllegalArgumentException();
		}

		final int prevIdx = places.indexOf(place);
		if (prevIdx >= 0) {
			weights.set(prevIdx, weights.get(prevIdx) + weight);
		} else {
			places.add(place);
			weights.add(weight);
		}
	}



	private void remove(final List<Place> places, final List<Integer> weights, final Place place) {
		final int idx = places.indexOf(place);
		if (idx < 0) {
			throw new IllegalArgumentException();
		}

		places.remove(idx);
		weights.remove(idx);
	}



	@Override
	public String toString() {
		return this.name;
	}
}

