from tabulate import tabulate
import sys


def ack(x,y) -> int:
    if x < 0 or y < 0:
        # -1 is the error return value
        return -1

    if x == 0:
        return y + 1
    
    if y == 0:
        return ack(x-1, 1)

    return ack(x-1, ack(x, y-1))
    
data = []

sys.setrecursionlimit(300000)

# for x in range(0,5):
#     for y in range(1,6):
#         data.append([(x,y), ack(x,y)])

# col_names = ["x,y", "ack(x,y)"]

# print(tabulate(data, headers=col_names, tablefmt="pretty"))

print(ack(4, 1))

