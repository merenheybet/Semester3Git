import numpy as np
from scipy.optimize import linprog

# Konkrete 9x5-Matrix A
A = np.array([
    [ 1,  1,  0,  0,  0],
    [ 0,  1,  1,  0,  0],
    [ 0,  0,  1,  1,  0],
    [ 1,  0,  0,  1,  0],
    [ 0,  1,  0,  1,  0],
    [ 0,  1,  0,  0,  1],
    [ 0,  0,  0,  1,  1],
    [ 1,  1,  1,  1,  1],
    [ -1,  -1,  -1,  -1,  -1]
])

# Konkreter 9-Vektor b
b = np.array([1, 1, 1, 1, 1, 1, 1, 3, -3])

# Zielfunktion auf 0 setzen: minimiert "nichts", d. h. wir prüfen nur Machbarkeit
c = np.zeros(5)

# Lösen des LPs A x <= b, x >= 0
res = linprog(c, A_ub=A, b_ub=b, method='highs')

# Ergebnis ausgeben
if res.success:
    print("Zulässige Lösung gefunden für x:\n", res.x)
    print("Wert von A x:\n", A @ res.x)
else:
    print("Keine Lösung gefunden. Status:", res.message)


