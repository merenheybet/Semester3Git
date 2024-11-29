import sys

def ack(x, y, memo=None) -> int:
    if x < 0 or y < 0:
        # -1 is the error return value
        return -1

    # Initialize the memoization dictionary on the first call
    if memo is None:
        memo = {}

    # Check if the result is already computed
    if (x, y) in memo:
        return memo[(x, y)]

    # Base cases
    if x == 0:
        result = y + 1
    elif y == 0:
        result = ack(x - 1, 1, memo)
    else:
        result = ack(x - 1, ack(x, y - 1, memo), memo)

    # Store the result in the memo dictionary
    memo[(x, y)] = result
    return result

sys.setrecursionlimit(300000)

print(ack(4, 1))  # Test the function
