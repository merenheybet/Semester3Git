import numpy as np
import matplotlib.pyplot as plt

def f(y):
    """Die Funktion aus Teil (a), die das DGL-System beschreibt."""
    x, y, v, w = y
    MG = 1
    r = np.sqrt(x**2 + y**2)
    return np.array([v, w, -MG * x / r**3, -MG * y / r**3])

def euler_explicit(f, y0, h, T):
    """
    Explizites Euler-Verfahren zur numerischen Lösung eines DGL-Systems.
    
    :param f: Funktion f, die das DGL-System beschreibt.
    :param y0: Anfangsbedingungen als Array [x0, y0, v0, w0].
    :param h: Zeitschrittweite.
    :param T: Endzeitpunkt.
    :return: Array mit allen Zuständen [x, y, v, w] und Zeitpunkten.
    """
    N = int(T / h)  # Anzahl der Zeitschritte
    t = np.linspace(0, T, N+1)  # Zeitpunkte
    y = np.zeros((N+1, 4))  # Lösungsspeicher (4 Dimensionen)
    y[0] = y0
    
    for i in range(N):
        y[i+1] = y[i] + h * f(y[i])  # Euler-Schritt
    
    return t, y

def main_explicit_euler():
    # Anfangswerte und Parameter
    x0, w0 = 1, 1
    y0 = np.array([x0, 0, 0, w0])  # [x(0), y(0), v(0), w(0)]
    T = 6.3  # Endzeitpunkt (ungefähr 2π)
    
    # Teste verschiedene Schrittweiten h
    hs = [1e-1, 1e-2, 1e-3, 1e-4]
    exact_solution = np.array([np.cos(T), np.sin(T)])  # Exakte Lösung am Endzeitpunkt
    
    errors = []
    for h in hs:
        t, y = euler_explicit(f, y0, h, T)
        numerical_solution = y[-1, :2]  # [x_N, y_N] am Endzeitpunkt
        error = np.linalg.norm(numerical_solution - exact_solution)  # Fehler e_h
        errors.append((h, error))
        
        # Visualisiere die Trajektorie im x-y-Koordinatensystem
        plt.plot(y[:, 0], y[:, 1], label=f"h={h:.0e}")
    
    # Plot der exakten Lösung
    theta = np.linspace(0, 2 * np.pi, 100)
    plt.plot(np.cos(theta), np.sin(theta), 'k--', label="Exakte Lösung")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.title("Trajektorie des Planeten (EE-Verfahren)")
    plt.legend()
    plt.axis("equal")
    plt.grid()
    plt.show()
    
    # Fehleranalyse
    print("Schrittweite h und zugehöriger Fehler e_h:")
    for h, error in errors:
        print(f"h = {h:.0e}, Fehler e_h = {error:.3e}")

#################################################################
###########################RK4###################################
#################################################################

def runge_kutta_4(f, y0, h, T):
    """
    Klassisches Runge-Kutta-Verfahren 4. Ordnung (RK4) zur numerischen Lösung eines DGL-Systems.
    
    :param f: Funktion f, die das DGL-System beschreibt.
    :param y0: Anfangsbedingungen als Array [x0, y0, v0, w0].
    :param h: Zeitschrittweite.
    :param T: Endzeitpunkt.
    :return: Array mit allen Zuständen [x, y, v, w] und Zeitpunkten.
    """
    N = int(T / h)  # Anzahl der Zeitschritte
    t = np.linspace(0, T, N+1)  # Zeitpunkte
    y = np.zeros((N+1, 4))  # Lösungsspeicher (4 Dimensionen)
    y[0] = y0

    for i in range(N):
        k1 = f(y[i])
        k2 = f(y[i] + h/2 * k1)
        k3 = f(y[i] + h/2 * k2)
        k4 = f(y[i] + h * k3)
        y[i+1] = y[i] + h/6 * (k1 + 2*k2 + 2*k3 + k4)
    
    return t, y

def main_rk4():
    # Anfangswerte und Parameter
    x0, w0 = 1, 1
    y0 = np.array([x0, 0, 0, w0])  # [x(0), y(0), v(0), w(0)]
    T = 6.3  # Endzeitpunkt (ungefähr 2π)

    # Teste verschiedene Schrittweiten h
    hs = [1e-1, 1e-2, 1e-3, 1e-4]
    exact_solution = np.array([np.cos(T), np.sin(T)])  # Exakte Lösung am Endzeitpunkt

    errors = []
    for h in hs:
        t, y = runge_kutta_4(f, y0, h, T)
        numerical_solution = y[-1, :2]  # [x_N, y_N] am Endzeitpunkt
        error = np.linalg.norm(numerical_solution - exact_solution)  # Fehler e_h
        errors.append((h, error))
        
        # Visualisiere die Trajektorie im x-y-Koordinatensystem
        plt.plot(y[:, 0], y[:, 1], label=f"h={h:.0e}")
    
    # Plot der exakten Lösung
    theta = np.linspace(0, 2 * np.pi, 100)
    plt.plot(np.cos(theta), np.sin(theta), 'k--', label="Exakte Lösung")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.title("Trajektorie des Planeten (RK4-Verfahren)")
    plt.legend()
    plt.axis("equal")
    plt.grid()
    plt.show()

    # Fehleranalyse
    print("Schrittweite h und zugehöriger Fehler e_h:")
    for h, error in errors:
        print(f"h = {h:.0e}, Fehler e_h = {error:.3e}")    

#################################################################
###########################impl_euler############################
#################################################################


def implicit_euler(f, y0, h, T, iterations=3):
    """
    Implizites Euler-Verfahren mit Fixpunktiteration.
    
    :param f: Funktion f, die das DGL-System beschreibt.
    :param y0: Anfangsbedingungen als Array [x0, y0, v0, w0].
    :param h: Zeitschrittweite.
    :param T: Endzeitpunkt.
    :param iterations: Anzahl der Fixpunktiterationsschritte.
    :return: Array mit allen Zuständen [x, y, v, w] und Zeitpunkten.
    """
    N = int(T / h)  # Anzahl der Zeitschritte
    t = np.linspace(0, T, N+1)  # Zeitpunkte
    y = np.zeros((N+1, 4))  # Lösungsspeicher (4 Dimensionen)
    y[0] = y0

    for i in range(N):
        y_next = y[i]  # Startwert für Fixpunktiteration
        for _ in range(iterations):
            y_next = y[i] + h * f(y_next)
        y[i+1] = y_next
    
    return t, y

def main_implicit_euler():
    # Anfangswerte und Parameter
    x0, w0 = 1, 1
    y0 = np.array([x0, 0, 0, w0])  # [x(0), y(0), v(0), w(0)]
    T = 6.3  # Endzeitpunkt (ungefähr 2π)

    # Teste verschiedene Schrittweiten h
    hs = [1e-1, 1e-2, 1e-3, 1e-4]
    exact_solution = np.array([np.cos(T), np.sin(T)])  # Exakte Lösung am Endzeitpunkt

    errors = []
    for h in hs:
        t, y = implicit_euler(f, y0, h, T)
        numerical_solution = y[-1, :2]  # [x_N, y_N] am Endzeitpunkt
        error = np.linalg.norm(numerical_solution - exact_solution)  # Fehler e_h
        errors.append((h, error))
        
        # Visualisiere die Trajektorie im x-y-Koordinatensystem
        plt.plot(y[:, 0], y[:, 1], label=f"h={h:.0e}")
    
    # Plot der exakten Lösung
    theta = np.linspace(0, 2 * np.pi, 100)
    plt.plot(np.cos(theta), np.sin(theta), 'k--', label="Exakte Lösung")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.title("Trajektorie des Planeten (IE-Verfahren)")
    plt.legend()
    plt.axis("equal")
    plt.grid()
    plt.show()

    # Fehleranalyse
    print("Schrittweite h und zugehöriger Fehler e_h:")
    for h, error in errors:
        print(f"h = {h:.0e}, Fehler e_h = {error:.3e}")

if __name__ == "__main__":
    main_explicit_euler()
    main_rk4()
    main_implicit_euler()