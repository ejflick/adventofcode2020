(ns user
  (:require [clojure.string :as str]))

(defstruct node :bag :rules)
(defstruct edge :to :from )

(def test-input (slurp "day7.test.in"))
;; (def input (slurp "day7.in"))
(def test-line "dark orange bags contain 3 bright white bags, 4 muted yellow bags.")

(def bag-for-rule-matcher #"(\w+ \w+){1}")
(def bag-rule-matcher #"(\d+) (\w+ \w+)")

(defn extract-bag [input]
  (first (re-find bag-for-rule-matcher input)))

(defn extract-rule [input]
  (map rest (re-seq bag-rule-matcher input)))

(defn add-rules-to-bag [bag rules graph]
  (update-in graph [bag] #(conj %1 rules)))

(defn add-rules-to-bags-in-rule [bag rules graph]
  ())

(defn create-rule [bag rule]
  {:number (first rule)
   :to (second rule)
   :from bag})

(defn create-rules [bag rules]
  (map create-rule rules))

(defn add-rules [bag rules graph]
  (let [rules (map #(create-rules bag %1) rules)]
    (->> graph
         (add-rules-to-bag bag rules)
         (add-rules-to-bags-in-rule bag rules))))

(defn node-for-rule [graph rule]
  (let [bag   (extract-bag rule)
        rules (map extract-rule rule)]
    (assoc graph bag (add-rules bag rules graph))))

(defn solve-input [input]
  (reduce node-for-rule {} input))
