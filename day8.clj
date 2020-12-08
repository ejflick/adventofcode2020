(ns user
  (:require [clojure.string :as s]))

(def input (-> (slurp "day8.in")
               (s/split-lines)))

(def test-input (-> (slurp "day8.test.in")
                    (s/split-lines)))

;; (defn create-op [input]
;;   (let [[op arg] (s/split input #" ")]
;;     (list op arg)))
;;

(defn create-op [input]
  (s/split input #" "))

(defn execute [input]
  (let [ops         (mapv create-op input)
        last-op-idx (count input)]
    (loop [op-idx 0
           acc 0
           visited {}
           last-jump nil]
      (let [[op arg] (nth ops op-idx)
            new-visited (update-in visited [op-idx] #(conj %1 last-jump))]
        (cond
          (find visited op-idx) (find visited op-idx)
          (> op-idx last-op-idx) acc
          (= "jmp" op) (recur (+ op-idx (Integer. arg)) acc new-visited op-idx)
          (= "acc" op) (recur (inc op-idx) (+ acc (Integer. arg)) new-visited last-jump)
          :else (recur (inc op-idx) acc new-visited last-jump))))))

(defn solve [input]
  (->> input
       (into [])
       (execute)))
