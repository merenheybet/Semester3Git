maximum = 0
n = -4

maximum = n

while n != 1:
    if maximum < n:
        maximum = n

    if (n % 2) == 0:
        n /= 2

    else:
        n = (3*n +1)
    
print(maximum)
