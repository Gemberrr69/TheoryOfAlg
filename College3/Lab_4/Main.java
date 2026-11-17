package Lab_4;

// Дулгер-Шевченко Артьом Олексійович, Кн-31. Практична робота №4, варіант 7
// Рекурсія: факторіал, Фібоначчі, перестановки, комбінації.

import java.util.Arrays;

/*
  === Результати для n = 4 ===
  Факторіал (4!): 24
  Фібоначчі (рекурсивно) fib(4): 3
  Фібоначчі (ітеративно) fibIter(4): 3
 
  === Всі перестановки множини [X, Y] ===
  [X, Y]
  [Y, X]
 
  === Кількість комбінацій ===
  C(2, 2) = 1
  C(2, 1) = 2
 
  Дерево викликів fib(4):
 
                fib(4)
               /      \
           fib(3)     fib(2)=1
          /      \
      fib(2)=1  fib(1)=1
 
  fib(4) = fib(3) + fib(2) = (1 + 1) + 1 = 3
 */
public class Main {

    //UTF-8 текст
    static void setupUtf8Console() {
        if (System.getProperty("os.name", "").toLowerCase().contains("win")) {
            try {
                new ProcessBuilder("cmd", "/c", "chcp 65001 >nul").inheritIO().start().waitFor();
            } catch (Exception ignored) {
            }
        }
        System.setOut(new java.io.PrintStream(new java.io.FileOutputStream(java.io.FileDescriptor.out), true, java.nio.charset.StandardCharsets.UTF_8));
        System.setErr(new java.io.PrintStream(new java.io.FileOutputStream(java.io.FileDescriptor.err), true, java.nio.charset.StandardCharsets.UTF_8));
    }


    // Рекурсивний факторіал, базовий випадок n <= 1
    public static long factorial(int n) {
        if (n < 0 || n > 20) {   // 21! вже не влазить у long
            throw new IllegalArgumentException("factorial: n має бути в межах 0..20, отримано " + n);
        }
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    // Рекурсивний Фібоначчі: fib(1) = fib(2) = 1
    public static long fib(int n) {
        if (n <= 0) return 0;
        if (n == 1 || n == 2) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    // Ітеративний Фібоначчі (цикл)
    public static long fibIter(int n) {
        if (n <= 0) return 0;
        if (n == 1 || n == 2) return 1;

        long prev1 = 1, prev2 = 1, current = 0;
        for (int i = 3; i <= n; i++) {
            current = prev1 + prev2;
            prev1 = prev2;
            prev2 = current;
        }
        return current;
    }

    // Рекурсивна генерація перестановок: міняємо місцями і спускаємося глибше
    public static void generatePermutations(String[] elements, int currentIndex) {
        if (currentIndex >= elements.length - 1) {
            System.out.println(Arrays.toString(elements));
            return;
        }
        for (int i = currentIndex; i < elements.length; i++) {
            swap(elements, currentIndex, i);
            generatePermutations(elements, currentIndex + 1);
            swap(elements, currentIndex, i);
        }
    }

    private static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // C(n, k) = n! / (k! * (n-k)!), рахуємо мультиплікативно, щоб не переповнювати long факторіалами
    public static long combinations(int n, int k) {
        if (k < 0 || k > n) return 0;
        k = Math.min(k, n - k);
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - k + i) / i;   // ділиться націло на кожному кроці
        }
        return result;
    }

    public static void main(String[] args) {
        setupUtf8Console();

        int n = 4;
        String[] set = {"X", "Y"};

        System.out.println("=== Результати для n = " + n + " ===");
        System.out.println("Факторіал (" + n + "!): " + factorial(n));
        System.out.println("Фібоначчі (рекурсивно) fib(" + n + "): " + fib(n));
        System.out.println("Фібоначчі (ітеративно) fibIter(" + n + "): " + fibIter(n));

        System.out.println("\n=== Всі перестановки множини " + Arrays.toString(set) + " ===");
        generatePermutations(set, 0);

        int setSize = set.length;
        System.out.println("\n=== Кількість комбінацій ===");
        System.out.println("C(" + setSize + ", 2) = " + combinations(setSize, 2));
        System.out.println("C(" + setSize + ", 1) = " + combinations(setSize, 1));
    }
}
