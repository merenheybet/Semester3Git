def arithmetic_mean(numbers):
    """
    Calculate the arithmetic mean (average) of a list of numbers.

    :param numbers: List of numbers
    :return: Arithmetic mean (float)
    """
    if not numbers:  # Check if the list is empty
        raise ValueError("The list of numbers is empty")
    return sum(numbers) / len(numbers)

def area_of_circle(radius):
    """
    Calculate the area of a circle given its radius.

    :param radius: Radius of the circle
    :return: Area of the circle (float)
    """
    if radius < 0:  # Check if the radius is negative
        raise ValueError("The radius cannot be negative")
    import math
    return math.pi * radius ** 2

def factorial(n):
    """
    Calculate the factorial of a given non-negative integer.

    :param n: Non-negative integer
    :return: Factorial of n (int)
    """
    if n < 0:
        raise ValueError("The input cannot be negative")
    if n == 0 or n == 1:
        return 1
    result = 1
    for i in range(2, n + 1):
        result *= i
    return result

def fibonacci_sequence(terms):
    """
    Generate the Fibonacci sequence up to a specified number of terms.

    :param terms: Number of terms to generate (non-negative integer)
    :return: List containing the Fibonacci sequence
    """
    if terms < 0:
        raise ValueError("The number of terms cannot be negative")
    if terms == 0:
        return []
    if terms == 1:
        return [0]
    sequence = [0, 1]
    for _ in range(2, terms):
        sequence.append(sequence[-1] + sequence[-2])
    return sequence

# Example usage:
# Arithmetic Mean
numbers = [1, 2, 3, 4, 5]
print(f"Arithmetic Mean: {arithmetic_mean(numbers)}")

# Area of Circle
radius = 5
print(f"Area of Circle: {area_of_circle(radius)}")

# Factorial
n = 5
print(f"Factorial of {n}: {factorial(n)}")

# Fibonacci Sequence
terms = 10
print(f"Fibonacci Sequence ({terms} terms): {fibonacci_sequence(terms)}")

# Tests
def test_arithmetic_mean():
    assert arithmetic_mean([1, 2, 3, 4, 5]) == 3
    assert arithmetic_mean([10, 20, 30]) == 20
    assert arithmetic_mean([5]) == 5
    try:
        arithmetic_mean([])
    except ValueError as e:
        assert str(e) == "The list of numbers is empty"

def test_area_of_circle():
    import math
    assert math.isclose(area_of_circle(1), math.pi, rel_tol=1e-9)
    assert math.isclose(area_of_circle(0), 0, rel_tol=1e-9)
    assert math.isclose(area_of_circle(2), 4 * math.pi, rel_tol=1e-9)
    try:
        area_of_circle(-1)
    except ValueError as e:
        assert str(e) == "The radius cannot be negative"

def test_factorial():
    assert factorial(0) == 1
    assert factorial(1) == 1
    assert factorial(5) == 120
    assert factorial(10) == 3628800
    try:
        factorial(-1)
    except ValueError as e:
        assert str(e) == "The input cannot be negative"

def test_fibonacci_sequence():
    assert fibonacci_sequence(0) == []
    assert fibonacci_sequence(1) == [0]
    assert fibonacci_sequence(2) == [0, 1]
    assert fibonacci_sequence(5) == [0, 1, 1, 2, 3]
    assert fibonacci_sequence(10) == [0, 1, 1, 2, 3, 5, 8, 13, 21, 34]
    try:
        fibonacci_sequence(-1)
    except ValueError as e:
        assert str(e) == "The number of terms cannot be negative"

# Run tests
test_arithmetic_mean()
test_area_of_circle()
test_factorial()
test_fibonacci_sequence()