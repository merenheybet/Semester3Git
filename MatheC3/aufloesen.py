import math

def nachx(n, y):
    if n==0:
        return 1
    
    # m = n-1
    xm = nachx(n-1, y)

    xn = xm + y - xm * math.log(xm)

    return xn


for i in range(0,30):
    print(nachx(i, 2))