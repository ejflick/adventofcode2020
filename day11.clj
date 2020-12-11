(ns user
  (:require [clojure.string :as s]))

(def empty \L)
(def occupied \#)
(def floor \.)

(defn read-input [filename]
  (->> filename
       (slurp)
       (s/split-lines)
       (mapv char-array)))

(def simple-input [[\L \L \L] [\L \L \.]])
(def test-input (read-input "input/day11.test.in"))
(def input (read-input "input/day11.in"))

(defn adjacent-seats [row col seat-map]
  (let [ul (get-in seat-map [(dec row) (dec col)])
        u  (get-in seat-map [(dec row) col])
        ur (get-in seat-map [(dec row) (inc col)])
        l  (get-in seat-map [row (dec col)])
        r  (get-in seat-map [row (inc col)])
        dl (get-in seat-map [(inc row) (dec col)])
        d  (get-in seat-map [(inc row) col])
        dr (get-in seat-map [(inc row) (inc col)])]
    (list ul u ur l r dl d dr)))

(defn update-seat [row col seat-map]
  (let [seat-at-position        (get-in seat-map [row col])
        occupied-adjacent-seats (count (filter #{occupied} (adjacent-seats row col seat-map)))]
    (cond
      (and (= seat-at-position empty) (= 0 occupied-adjacent-seats)) occupied
      (and (= seat-at-position occupied) (>= occupied-adjacent-seats 4)) empty
      :else seat-at-position)))

(defn mapv-indexed [f coll]
  (into [] (map-indexed f coll)))

(defn update-seats [seat-map]
  (mapv-indexed (fn [row _]
                  (mapv-indexed (fn [col _]
                                  (update-seat row col seat-map))
                                _))
                seat-map))

(defn solve [seat-map]
  (loop [previous seat-map
         updated  (update-seats seat-map)]
    (if (= previous updated)
      updated
      (recur updated (update-seats updated)))))
