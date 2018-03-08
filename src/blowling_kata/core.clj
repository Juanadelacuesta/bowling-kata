(ns blowling-kata.core
  (:gen-class))

(require '[clojure.string :as str])

(defn -main
  "I don't do a whole lot ... yet."
  [& Args]
  (println (addScores (clean input))))


(def input "| 1 4 | 4 5 | 6 / | 5 / | X | 0 1 | 7 / | 6 / | X | 2 / 6 |")

(defn clean
  [s] (filter seq (str/split (str/replace s #"\s" "" ) #"\|" -1)) )

(defn strike?
[frame]
(if (re-find #"^X" (str frame))
    true
    false))

(defn split?
[frame]
(if (re-find #"^[0-9]+/" frame)
    true
    false))

(defn addThrows
  ([frame] (reduce + (map #(if (strike? %)
                          10
                          (Character/digit % 10))
                       frame))))

(defn getFrameScore
  [f1]
  (addThrows (or (and (split? f1) (str/replace f1  #"^[0-9]+/" "X"))
                 f1)))

(defn getSplitBonus
  [frame] (let [[t1 t2] frame]
         (getFrameScore (str t1))))

(defn getStrikeBonus
  [frame frame+1]
  (let [sf (str frame frame+1) f (str (first sf) (second sf))]
  (getFrameScore f)))

(defn getFrameBonus
  [f1 f2 f3]
  (or (and (split? f1) (getSplitBonus f2))
      (and (strike? f1) (getStrikeBonus f2 f3))
      0))

(defn addScores
  ([scores]
    (addScores scores 0))
  ([scores total]
   (let [[n n1 n2 & remaining] scores]
    (if (nil? n1)
      (+ total (getFrameScore n))
       (recur (rest scores) (+ total
                               (getFrameScore n)
                               (getFrameBonus n n1 n2)))))))
