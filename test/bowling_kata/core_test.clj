(ns bowling-kata.core-test
  (:require [clojure.test :refer :all]
            [bowling-kata.core :refer :all]))

(deftest bowling-kata-test
  (testing "add-scores add the scores of an input"
    (is (= 133 (add-scores (clean "| 1 4 | 4 5 | 6 / | 5 / | X | 0 1 | 7 / | 6 / | X | 2 / 6 |"))))
    (is (= 300 (add-scores (clean "| X | X  | X | X | X | X | X | X | X | XXX |"))))
    (is (= 242 (add-scores (clean "| X | 1 0 | 6 / | X | X | X | X | X | X | X X X |"))))
    (is (= 20 (add-scores (clean "| 1 1 | 1 1 | 1 1 | 1 1 | 1 1| 1 1| 1 1| 1 1 | 1 1 | 1 1 |")))))

  (testing "get-throws-score returns the scores of the frame's throws"
    (is (= 2 (get-throws-score "11")))
    (is (= 20 (get-throws-score "XX")))
    (is (= 12 (get-throws-score "6/2"))))

 (testing "get-frame-score returns the score of the passed frame including the bonus"
    (is (= 2 (get-frame-score "11" "11" "11")))
    (is (= 30 (get-frame-score "X" "X" "X")))
    (is (= 20 (get-frame-score "6/" "X" "11")))
    (is (= 11 (get-frame-score "6/" "11" "11")))
    (is (= 11 (get-frame-score "6/" nil nil)))
))
