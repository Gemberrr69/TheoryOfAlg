# Дулгер-Шевченко Артьом Олексiйович, КН-31. Практична робота №2, варіант 7
# Просте: бульбашкове. Покращене: Шелла. Ключ пошуку: 1.
import sys

#Вiдповiдь на питання №1: Бульбашкове сортування зробило 14 порівнянь і 8 обмінів 
#(5 + 4 + 3 + 2 за чотири проходи).На вже відсортованому масиві порівнянь було б лише 5.
#Вiдповiдь на питання №2: 1 це єдиний спосіб гарантувати повне і остаточне сортування всього масиву.

"""
Вхід:            3 8 5 1 7 4
Бінарний 1 на невідсортованому: -1  (хибно, ключ є в масиві)
Кроки бульбашкового:
  обмін 1 (прохід 1): 3 5 8 1 7 4
  обмін 2 (прохід 1): 3 5 1 8 7 4
  обмін 3 (прохід 1): 3 5 1 7 8 4
  обмін 4 (прохід 1): 3 5 1 7 4 8
  обмін 5 (прохід 2): 3 1 5 7 4 8
  обмін 6 (прохід 2): 3 1 5 4 7 8
  обмін 7 (прохід 3): 1 3 5 4 7 8
  обмін 8 (прохід 3): 1 3 4 5 7 8
Бульбашка:       1 3 4 5 7 8
Порівнянь у бульбашковому: 14
Шелл:            1 3 4 5 7 8
Лінійний 1:   індекс 3   (з нуля, у вихідному)
Бінарний 1:   індекс 0   (з нуля, у відсортованому)
Бінарний 99:  -1
Злиття:          1 2 4 5 6 9 11
Порівнянь на вже відсортованому: 5
"""

# Потужна мова
if hasattr(sys.stdout, "reconfigure"):
    sys.stdout.reconfigure(encoding="utf-8")

# Лічильник порівнянь для бульбашкового сортування
cmp_bubble = 0


def print_arr(label, a):
    print(label + " ".join(str(x) for x in a))


# Просте сортування: бульбашкове
def bubble_sort(a, verbose=False):
    global cmp_bubble
    n = len(a)
    step = 0
    for i in range(n - 1):
        swapped = False
        for j in range(n - 1 - i):
            cmp_bubble += 1
            if a[j] > a[j + 1]:
                a[j], a[j + 1] = a[j + 1], a[j]
                swapped = True
                if verbose:
                    step += 1
                    print(f"  обмін {step} (прохід {i + 1}): "
                          + " ".join(str(x) for x in a))
        if not swapped:
            break


# Покращене сортування: Шелла (кроки n/2, n/4, ..., 1)
def shell_sort(a):
    n = len(a)
    gap = n // 2
    while gap >= 1:
        for i in range(gap, n):
            tmp = a[i]
            j = i
            while j >= gap and a[j - gap] > tmp:
                a[j] = a[j - gap]
                j -= gap
            a[j] = tmp
        gap //= 2


# Лінійний пошук
def linear_search(a, key):
    for i in range(len(a)):
        if a[i] == key:
            return i
    return -1


# Бінарний пошук
def binary_search(a, key):
    lo, hi = 0, len(a) - 1
    while lo <= hi:
        mid = (lo + hi) // 2
        if a[mid] == key:
            return mid
        if a[mid] < key:
            lo = mid + 1
        else:
            hi = mid - 1
    return -1


# Злиття двох впорядкованих масивів
def merge_arrays(A, B):
    res = []
    i = j = 0
    while i < len(A) and j < len(B):
        if A[i] <= B[j]:
            res.append(A[i])
            i += 1
        else:
            res.append(B[j])
            j += 1
    while i < len(A):
        res.append(A[i])
        i += 1
    while j < len(B):
        res.append(B[j])
        j += 1
    return res


def main():
    global cmp_bubble
    input_arr = [3, 8, 5, 1, 7, 4]
    key = 1
    A = [2, 6, 9]
    B = [1, 4, 5, 11]

    print_arr("Вхід:            ", input_arr)

    # Бінарний пошук на невідсортованому
    print(f"Бінарний {key} на невідсортованому: "
          f"{binary_search(input_arr, key)}  (хибно, ключ є в масиві)")

    bubble = input_arr[:]
    print("Кроки бульбашкового:")
    bubble_sort(bubble, verbose=True)
    print_arr("Бульбашка:       ", bubble)
    print("Порівнянь у бульбашковому:", cmp_bubble)

    shell = input_arr[:]
    shell_sort(shell)
    print_arr("Шелл:            ", shell)

    print(f"Лінійний {key}:   індекс {linear_search(input_arr, key)}"
          "   (з нуля, у вихідному)")
    print(f"Бінарний {key}:   індекс {binary_search(shell, key)}"
          "   (з нуля, у відсортованому)")
    print("Бінарний 99: ", binary_search(shell, 99))

    print_arr("Злиття:          ", merge_arrays(A, B))

    # Для питання 1: порівняння на вже відсортованому масиві
    cmp_bubble = 0
    sorted_arr = bubble[:]
    bubble_sort(sorted_arr)
    print("Порівнянь на вже відсортованому:", cmp_bubble)


if __name__ == "__main__":
    main()