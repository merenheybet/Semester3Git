/**
 * A place in a petri net. A place has a name, the current number of tokens,
 * and a capacity.
 */
public class Place {

	private final String name;
	private int tokens;
	private final int capacity;



	/**
	 * Creates a new place with the specified name but without tokens and capacity.
	 *
	 * @param name the name
	 *
	 * @see PetriNet#createPlace()
	 */
	public Place(final String name) {
		this(name, 0);
	}



	/**
	 * Creates a new place with the specified name and tokens but without
	 * capacity.
	 *
	 * @param name the name
	 * @param initialTokens the initial number of tokens
	 *
	 * @see PetriNet#createPlace(int)
	 */
	public Place(final String name, final int initialTokens) {
		this(name, initialTokens, Integer.MAX_VALUE);
	}



	/**
	 * Creates a new place with the specified name, tokens, and capacity.
	 *
	 * @param name the name
	 * @param initialTokens the initial number of tokens
	 * @param capacity the capacity
	 *
	 * @see PetriNet#createPlace(int,int)
	 */
	public Place(final String name, final int initialTokens, final int capacity) {
		this.name = name;
		this.tokens = initialTokens;
		this.capacity = capacity;
	}



	/**
	 * Returns the name of this place.
	 *
	 * @return the name of this place
	 */
	public String getName() {
		return this.name;
	}



	/**
	 * Returns the number of tokens that are currently in this place.
	 *
	 * @return the current number of tokens
	 */
	public int getTokens() {
		return this.tokens;
	}



	/**
	 * Sets the number of tokens that are currently in this place. Note that
	 * this number must not be negative. This method does not check whether the
	 * specified number of tokens is larger than the capacity.
	 *
	 * @param tokens the new number of tokens
	 */
	public void setTokens(final int tokens) {
		if (tokens < 0) {
			throw new IllegalArgumentException();
		}
		this.tokens = tokens;
	}



	/**
	 * Returns the capacity of this place.
	 *
	 * @return the capacity of this place
	 */
	public int getCapacity() {
		return this.capacity;
	}



	@Override
	public String toString() {
		return new StringBuilder()
				.append(this.name)
				.append('(')
				.append(this.tokens)
				.append(')')
				.toString();
	}
}

