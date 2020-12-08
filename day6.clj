(ns user
  (:require [clojure.set :as set]))

(defn group-inputs [input]
  (->> (s/split input #"\n\n")
       (map #(s/split %1 #"\n"))))

(defn person->set [person]
  (set person))

(defn group->set [group]
  (map person->set group))

(def input (slurp "input/day6.in"))
(def test-input (slurp "input/day6.test.in"))

(def answer (->> input
                 (group-inputs)
                 (map group->set)
                 (map #(apply set/intersection %1))
                 (map count)
                 (reduce +)))
