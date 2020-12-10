(ns user
  (:require [clojure.string :as s]))

(defn adjust-for-wall-and-laptop [voltages]
  "Need to include wall and laptop in our numbers apparently."
  (let [laptop-voltage (+ 3 (apply max voltages))
        wall 0]
    (conj voltages laptop-voltage wall)))

(defn solve [filename]
  (->> (slurp filename)
       (s/split-lines)
       (map #(Integer/parseInt %1))
       (adjust-for-wall-and-laptop)
       (sort >)
       (partition 2 1)
       (map #(reduce - %1))
       (frequencies)
       (vals)
       (reduce *)))
