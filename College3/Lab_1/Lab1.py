# Дулгер-Шевченко Артьом Олексiйович, КН-31. Лабораторна №1

"""
=== Прогін A, L, B, D, C на n від 100 до 3200 ===
n        | O(1)   | O(log n)   | O(n)     | O(n log n)   | O(n^2)
------------------------------------------------------------------
100      | 1      | 6          | 100      | 600          | 10000
200      | 1      | 7          | 200      | 1400         | 40000
400      | 1      | 8          | 400      | 3200         | 160000
800      | 1      | 9          | 800      | 7200         | 640000
1600     | 1      | 10         | 1600     | 16000        | 2560000
3200     | 1      | 11         | 3200     | 35200        | 10240000

=== Прогін E та F на малих n ===

--- Функція E O(2^n) ---
n    | Steps O(2^n)
------------------------
8    | 256
10   | 1024
12   | 4096
14   | 16384
16   | 65536
18   | 262144

--- Функція F O(n!) ---
n    | Steps O(n!)
-----------------------
4    | 24
5    | 120
6    | 720
7    | 5040
8    | 40320
9    | 362880
"""


# A. O(1) — без циклу
def func_a(n):
    steps = 1
    total = n * (n + 1) // 2
    return steps


# L. O(log n) — цикл ділить навпіл
def func_l(n):
    steps = 0
    j = n
    while j > 1:
        steps += 1
        j = j // 2
    return steps


# B. O(n) — один цикл
def func_b(n):
    steps = 0
    total = 0
    for i in range(1, n + 1):
        steps += 1
        total += i
    return steps


# D. O(n log n) — вкладений цикл, внутрішній ділить навпіл
def func_d(n):
    steps = 0
    for i in range(1, n + 1):
        j = n
        while j > 1:
            steps += 1
            j = j // 2
    return steps


# C. O(n^2) — два вкладених цикли від 1 до n
def func_c(n):
    steps = 0
    for i in range(1, n + 1):
        for j in range(1, n + 1):
            steps += 1
    return steps


# E. O(2^n) — ітеративний експоненційний ріст
def func_e(n):
    steps = 0
    limit = 1 << n  # 2^n
    for i in range(limit):
        steps += 1
    return steps


# F. O(n!) — факторіальний ріст
def func_f(n):
    steps = 0
    limit = 1
    for i in range(1, n + 1):
        limit *= i
    for i in range(limit):
        steps += 1
    return steps


def main():
    n_values = [100, 200, 400, 800, 1600, 3200]
    n_exp = [8, 10, 12, 14, 16, 18]
    n_fact = [4, 5, 6, 7, 8, 9]

    print("=== Прогін A, L, B, D, C на n від 100 до 3200 ===")
    print(f"{'n':<8} | {'O(1)':<6} | {'O(log n)':<10} | {'O(n)':<8} | "
          f"{'O(n log n)':<12} | {'O(n^2)':<12}")
    print("-" * 66)
    for n in n_values:
        print(f"{n:<8} | {func_a(n):<6} | {func_l(n):<10} | {func_b(n):<8} | "
              f"{func_d(n):<12} | {func_c(n):<12}")

    print("\n=== Прогін E та F на малих n ===")

    print("\n--- Функція E O(2^n) ---")
    print(f"{'n':<4} | {'Steps O(2^n)':<12}")
    print("-" * 24)
    for n in n_exp:
        print(f"{n:<4} | {func_e(n):<12}")

    print("\n--- Функція F O(n!) ---")
    print(f"{'n':<4} | {'Steps O(n!)':<12}")
    print("-" * 23)
    for n in n_fact:
        print(f"{n:<4} | {func_f(n):<12}")


if __name__ == "__main__":
    main()