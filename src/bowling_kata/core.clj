(ns bowling-kata.core
  (:gen-class)
  (:require [clojure.string :as str]))

(def input "| 1 4 | 4 5 | 6 / | 5 / | X | 0 1 | 7 / | 6 / | X | 2 / 6 |")

(defn clean [s]
  (filter seq (str/split (str/replace s #"\s" "" )
                         #"\|"
                         -1)))

(defn strike? [frame]
  (some? (re-find #"^X" (str frame))))

(defn split? [frame]
  (some? (re-find #"^[0-9]+/" frame)))

(defn add-throws [frame]
  (reduce + (map #(if (strike? %)
                    10
                    (Character/digit % 10))
                 frame)))

(defn get-throws-score [f1]
  (add-throws (or (and (split? f1) (str/replace f1  #"^[0-9]+/" "X"))
                  f1)))

(defn get-split-bonus
  [[throw1 & _]]
  (get-throws-score (str throw1)))

(defn get-strike-bonus
  [frame next-frame]
  (let [sf (str frame next-frame)
        f (str (first sf) (second sf))]
    (get-throws-score f)))

(defn get-frame-score
 [frame next-frame next-next-frame]
 (+ (get-throws-score frame)
    (or (and (split? frame) (get-split-bonus next-frame))
        (and (strike? frame) (get-strike-bonus next-frame next-next-frame))
        0)))


(defn add-scores
  ([scores]
   (add-scores scores 0))
  ([[n n1 n2 & _ :as scores] total]
   (if (nil? n)
      total
       (recur (rest scores) (+ total (get-frame-score n n1 n2))))))

(defn -main
  "The bowling project reads a file with the score of a bowling match and returns the final score"
  [& Args]
  (let [[path & _ ] Args]
    (println (add-scores (clean (slurp path))))))
