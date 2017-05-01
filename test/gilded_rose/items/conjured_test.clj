(ns gilded-rose.items.conjured
  (:require [gilded-rose.items.conjured :as c]
            [gilded-rose.items.item :as i]
            [clojure.test :refer [deftest testing is] :as t]))

(def normal-conjured
  {:name c/conjured-name
   :quality 5
   :sell-in 2})

(def expired-conjured
  {:name c/conjured-name
   :quality 5
   :sell-in -2})

(def low-expired-conjured
  {:name c/conjured-name
   :quality 1
   :sell-in -2})

(deftest test-conjured
  (testing "Conjured item decreases by 2"
    (is (= 3 (:quality (i/update-item normal-conjured)))))
  (testing "Conjured item decreases by 4"
    (is (= 1 (:quality (i/update-item expired-conjured)))))
  (testing "Cojured quality is never negative"
    (is (= 0 (:quality (i/update-item low-expired-conjured))))))
