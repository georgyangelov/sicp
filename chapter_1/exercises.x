extend XSpecGroup, do
  def exercise(name, block)
    it 'Exercise '.concat(name), block
  end

  def example(name, block)
    it 'Example '.concat(name), block
  end
end

XSpec.describe 'Chapter 1', do
  def square(a)
    a * a
  end

  # Define a procedure that takes three numbers as arguments and returns the sum
  # of the squares of the two larger numbers.
  exercise '1.3, page 27', do
    def fn(a, b, c)
      if a < b and a < c
        square(b) + square(c)
      elsif b < a and b < c
        square(a) + square(c)
      else
        square(c) + square(b)
      end
    end

    expect_eq fn(1, 2, 3), 4 + 9
    expect_eq fn(2, 2, 2), 4 + 4
    expect_eq fn(6, 1, 3), 36 + 9
  end

  def improve_iteratively(x, improve)
    guess = 1.0
    improvement = 1.0

    while improvement > 0.001
      new_guess = improve guess, x

      improvement = ((new_guess - guess) / guess).abs
      guess = new_guess
    end

    guess
  end

  exercise '1.7, page 33', do
    def average(x, y)
      (x + y) / 2.0
    end

    def sqrt(x)
      improve_iteratively x, { |guess, x| average guess, x / guess }
    end

    expect_between 4.9, sqrt(25.0), 5.1
  end

  exercise '1.8, page 33', do
    def cube_root(x)
      improve_iteratively x, { |y, x| (x / square(y) + 2.0 * y) / 3.0 }
    end

    expect_between 4.999, cube_root(125.0), 5.001
  end

  example '1.2.1, page 41', do
    def factorial(n)
      if n == 1
        n
      else
        n * factorial(n - 1)
      end
    end

    expect_eq factorial(1), 1
    expect_eq factorial(2), 2
    expect_eq factorial(3), 6
    expect_eq factorial(4), 24
  end

  example '1.2.1, iterative, page 43', do
    def factorial(n)
      result = 1

      while n > 1
        result = result * n
        n = n - 1
      end

      result
    end

    expect_eq factorial(1), 1
    expect_eq factorial(2), 2
    expect_eq factorial(3), 6
    expect_eq factorial(4), 24
  end

  exercise '1.10, page 47', do
    def ackermann(x, y)
      if y == 0
        0
      elsif x == 0
        2 * y
      elsif y == 1
        2
      else
        ackermann x - 1, ackermann(x, y - 1)
      end
    end

    expect_eq ackermann(0, 3), 6
    expect_eq ackermann(1, 3), 8
    expect_eq ackermann(2, 3), 16
  end

  exercise '1.11, recursive, page 53', do
    def recursive(n)
      if n < 3
        n
      else
        recursive(n - 1) + 2 * recursive(n - 2) + 3 * recursive(n - 3)
      end
    end

    expect_eq recursive(3), 4
    expect_eq recursive(4), 11
    expect_eq recursive(5), 25
  end

  exercise '1.11, iterative, page 53', do
    def iterative(n)
      if n < 3
        n
      else
        current_n = 3
        sum_3 = 0
        sum_2 = 1
        sum_1 = 2

        while current_n < n
          new_sum = sum_1 + 2 * sum_2 + 3 * sum_3
          sum_3 = sum_2
          sum_2 = sum_1
          sum_1 = new_sum
        end

        sum_1
      end
    end
  end

  exercise '1.12, page 54', do
    def pascal(x, y)
      if x <= 0 or y <= 0
        1
      else
        pascal(x - 1, y) + pascal(x, y - 1)
      end
    end

    expect_eq pascal(0, 0), 1
    expect_eq pascal(1, 1), 2
    expect_eq pascal(2, 1), 3
    expect_eq pascal(1, 2), 3
  end

  exercise '1.19, page 62', do
    def fib(n)
      a = 1
      b = 0
      p = 0
      q = 1
      count = n

      while count != 0
        if count.even?
          p1 = q*q + p*p
          q1 = 2*p*q + q*q

          p = p1
          q = q1

          count = count / 2
        else
          a1 = b*q + a*q + a*p
          b1 = b*p + a*q

          a = a1
          b = b1

          count = count - 1
        end
      end

      b
    end

    expect_eq fib(0), 0
    expect_eq fib(1), 1
    expect_eq fib(2), 1
    expect_eq fib(3), 2
    expect_eq fib(4), 3
    expect_eq fib(5), 5
    expect_eq fib(6), 8
    expect_eq fib(7), 13
  end
end
