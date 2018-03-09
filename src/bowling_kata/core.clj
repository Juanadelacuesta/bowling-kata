(ns bowling-kata.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn parse-game [s]
  (filter seq (-> s
                  (str/replace #"\s" "")
                  (str/split #"\|"))))

(defn strike? [frame]
  (some? (re-find #"^X" (str frame))))

(defn spare? [throws]
  (let [frame (str throws)] (or (some? (re-find #"^S" frame))
                                (some? (re-find #"^[0-9]+/" frame)))))

(defn throw-score [frame]
  (cond (strike? frame)  10
        (spare? frame)   10
        :else            (Character/digit frame 10)))

(defn add-throws [frame]
  (reduce + 0 (map throw-score frame)))

(defn get-throws-score [frame]
  (add-throws (or (and (spare? frame)
                       (str/replace frame  #"^[0-9]+/" "S"))
                  frame)))

(defn get-spare-bonus
  [[throw1 & _]]
  (get-throws-score (str throw1)))

(defn get-strike-bonus
  [frame next-frame]
  (let [bonus-throws (apply str (take 2 (str frame next-frame)))]
    (get-throws-score bonus-throws)))

(defn get-frame-score
 [frame next-frame next-next-frame]
 (+ (get-throws-score frame)
    (or (and (spare? frame) (get-spare-bonus next-frame))
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
  [& args]
  (let [[path & _ ] args]
    (println (add-scores (parse-game (slurp path))))))
