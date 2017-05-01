(ns gilded-rose.items.item-test
  (:require [gilded-rose.items.item :as i]
            [clojure.test :refer [deftest testing is] :as t]))

(def normal-item
  {:name "random"
   :quality 5
   :sell-in 2})

(def day-zero-item
  {:name "item"
   :quality 9
   :sell-in 0})

(def expired-item
  {:name "Random Item"
   :quality 5
   :sell-in -2})

(def low-expired-item
  {:name "Foo"
   :quality 1
   :sell-in -2})

(deftest test-item
  (testing "Default item decreases by 1"
    (is (= 4 (:quality (i/update-item normal-item)))))
  (testing "Default item decreases by 1 on day 0"
    (is (= 8 (:quality (i/update-item day-zero-item)))))
  (testing "Default item decreases by 2 after sell-in-date"
    (is (= 3 (:quality (i/update-item expired-item)))))
  (testing "Cojured quality is never negative"
    (is (= 0 (:quality (i/update-item low-expired-item))))))
