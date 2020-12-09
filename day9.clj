(ns user
  (:require [clojure.string :as str]))

(defn prepare-input [input]
  "Prepares input as a vector of big ints"
  (->> input
       (str/split-lines)
       (mapv bigint)))

(defn read-input-from-file [filename]
  (-> filename
      (slurp)
      (prepare-input)))

(def test-input (read-input-from-file "input/day9.test.in"))
(def input (read-input-from-file "input/day9.in"))

(defn two-numbers-sum-to [number coll]
  (some #(contains? coll (- number %1))
        coll))

(defn solve [preamble window-size prepared-input]
  (loop [idx preamble]
    (let [number (nth prepared-input idx)
          window (into #{} (subvec prepared-input (- idx window-size) idx))]
      (if (two-numbers-sum-to number window)
          (recur (inc idx))
          number))))
