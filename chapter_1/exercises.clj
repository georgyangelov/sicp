(ns chapter-1.exercises)
(use 'clojure.test)

(defn abs [x] (max x (- x)))

(defn square [x]
    (* x x))

(defn average [x y]
    (/ (+ x y) 2))

(defn improve-iteratively [target improve-guess target-improvement-factor]
    (loop [guess 1
           improvement-factor 1]
        (if (< improvement-factor target-improvement-factor)
            guess
            (let [new-guess (improve-guess guess target)]
                (recur new-guess (/ (abs (- new-guess guess)) guess))))))

; 1.7, page 33

(defn sqrt [x]
    (improve-iteratively
        x
        (fn [guess target] (average guess (/ target guess)))
        0.001))

(deftest test-sqrt
    (let [root (sqrt 25.0)]
        (is (< 4.99 root))
        (is (> 5.01 root))))

; 1.8, page 33

(defn cube-root [x]
    (improve-iteratively
        x
        (fn [y x]
            (/
                (+
                    (/ x (square y))
                    (* 2 y))
                3))
        0.001))

(deftest test-cube-root
    (let [root (cube-root 125.0)]
        (is (< 4.99 root))
        (is (> 5.01 root))))

; Example 1.2.1, page 43

(defn factorial [x]
    (loop [result 1 n 1]
        (if (> n x)
            result
            (recur (* result n) (+ n 1)))))

(deftest test-factorial
    (is (= 1 (factorial 1)))
    (is (= 2 (factorial 2)))
    (is (= 6 (factorial 3)))
    (is (= 24 (factorial 4))))

; Exercise 1.10, page 47

(defn ackermann [x y]
    (cond
        (= y 0) 0
        (= x 0) (* 2 y)
        (= y 1) 2
        :else (ackermann (- x 1) (ackermann x (- y 1)))))

; (A _ 0) => 0
; (A 0 y) => 2y
; (A _ 1) => 2
; (A 1 2) => (A 0 (A 1 1)) => (A 0 2) => 4
; (A 1 3) => (A 0 (A 1 2)) => (A 0 4) => 8
; (A 1 16) => 65536
; (A 1 y) => 2^y
; (A 2 2) => (A 1 (A 2 1)) => (A 1 2) => 4
; (A 2 3) => (A 1 (A 2 2)) => (A 1 4) => 16
; (A 2 4) => (A 1 16) => 65536
; (A 2 y) => 2^y^y
;
; (print (ackermann 2 4))

; Exercise 1.11, page 53

((fn []
    (defn f-recursive [n]
        (if (< n 3)
            n
            (+
                (f-recursive (- n 1))
                (* 2 (f-recursive (- n 2)))
                (* 3 (f-recursive (- n 3))))))

    (defn f-iterative [n]
        (if (< n 3)
            n
            (loop [current-n 3 sum-3 0 sum-2 1 sum-1 2]
                (let [new-sum (+ sum-1 (* 2 sum-2) (* 3 sum-3))]
                    (if (= current-n n)
                        new-sum
                        (recur
                            (+ current-n 1)
                            sum-2
                            sum-1
                            new-sum))))))

    (deftest test-f-recursive
        (is (= 4 (f-recursive 3)))
        (is (= 11 (f-recursive 4)))
        (is (= 25 (f-recursive 5))))

    (deftest test-f-iterative
        (is (= 4 (f-iterative 3)))
        (is (= 11 (f-iterative 4)))
        (is (= 25 (f-iterative 5))))
))

; Exercise 1.12, page 54
; Pascal triangle

((fn []
    (defn f [x y]
        (if (or (<= x 0) (<= y 0))
            1
            (+ (f (- x 1) y) (f x (- y 1)))))

    (deftest test-f
        (is (= 1 (f 0 0)))
        (is (= 2 (f 1 1)))
        (is (= 3 (f 2 1)))
        (is (= 3 (f 1 2))))
))

; Exercise 1.16, page 59

((fn []
    (defn fast-expt [b n]
        (cond
            (= n 0) 1
            (even? n) (square (fast-expt b (/ n 2)))
            :else (* b (fast-expt b (- n 1)))))

    (defn fast-expt-iter [b n]
        (if (= n 0)
            1
            (loop [exponent n result b additional 1]
                (cond
                    (= exponent 1) (* result additional)
                    (even? exponent) (recur (/ exponent 2) (* result result) additional)
                    :else (recur (- exponent 1) result (* additional b))))))

    (deftest test-fast-expt
        (is (= 1 (fast-expt 2 0)))
        (is (= 3 (fast-expt 3 1)))
        (is (= 64 (fast-expt 8 2))
        (is (= 128 (fast-expt 2 7)))))

    (deftest test-fast-expt-iter
        (is (= 1 (fast-expt-iter 2 0)))
        (is (= 3 (fast-expt-iter 3 1)))
        (is (= 64 (fast-expt-iter 8 2))
        (is (= 8 (fast-expt-iter 2 3)))))
))
