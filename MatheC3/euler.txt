import numpy as np
import matplotlib.pyplot as plt

def f(t, y):
    return 36 * (2 - y) * np.exp(-(t - 2)**2)

def explicit_euler(f, y0, t0, t_end, h):
    t_values = np.arange(t0, t_end + h, h)
    y_values = np.zeros_like(t_values)
    y_values[0] = y0
    
    for i in range(1, len(t_values)):
        y_values[i] = y_values[i - 1] + h * f(t_values[i - 1], y_values[i - 1])
    
    return t_values, y_values

def implicit_euler(f, y0, t0, t_end, h):
    t_values = np.arange(t0, t_end + h, h)
    y_values = np.zeros_like(t_values)
    y_values[0] = y0

    for i in range(1, len(t_values)):
        t_next = t_values[i]
        y_prev = y_values[i - 1]
        
        # Solving for y_next explicitly since f is simple
        y_values[i] = (y_prev + h * 36 * 2 * np.exp(-(t_next - 2)**2)) / (1 + h * 36 * np.exp(-(t_next - 2)**2))
        
    return t_values, y_values

# Parameters
y0 = 8
t0, t_end = 0, 5
hs = [0.05, 0.1, 0.09]

plt.figure(figsize=(12, 6))

for h in hs:
    # Explicit Euler
    t_exp, y_exp = explicit_euler(f, y0, t0, t_end, h)
    plt.plot(t_exp, y_exp, label=f'Explizit (h={h})')
    print(f"Explizites Euler-Verfahren (h={h}):")
    for t, y in zip(t_exp, y_exp):
        print(f"t = {t:.2f}, y = {y:.6f}")

    # Implicit Euler
    t_imp, y_imp = implicit_euler(f, y0, t0, t_end, h)
    plt.plot(t_imp, y_imp, '--', label=f'Implizit (h={h})')
    print(f"\nImplizites Euler-Verfahren (h={h}):")
    for t, y in zip(t_imp, y_imp):
        print(f"t = {t:.2f}, y = {y:.6f}")
    print("\n" + "-"*40 + "\n")

plt.xlabel('t')
plt.ylabel('y(t)')
plt.title('Lösung der DGL mit explizitem und implizitem Euler-Verfahren')
plt.legend()
plt.grid()
plt.show()