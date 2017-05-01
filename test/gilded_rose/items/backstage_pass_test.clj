(ns gilded-rose.items.backstage-pass-test
  (:require
   [gilded-rose.items.backstage-pass :as b]
   [gilded-rose.items.item :as i]
   [clojure.test :refer [deftest testing is] :as t]))

(def over-backstage-pass
  {:name b/backstage-pass-name
   :quality 20
   :sell-in -1})

(deftest test-backstage-pass-over
  (testing "Quality is zero when concert is over"
    (is (= 0 (:quality (i/update-item over-backstage-pass))))))

(def normal-backstage-pass
  {:name b/backstage-pass-name
   :quality 20
   :sell-in 20})

(deftest test-backstage-pass-normal
  (testing "Quality increases by 1 when more than 10 days away"
    (is (= 21 (:quality (i/update-item normal-backstage-pass))))))

(def ten-days-before-pass
  {:name b/backstage-pass-name
   :quality 20
   :sell-in 10})

(def rising-backstage-pass
  {:name b/backstage-pass-name
   :quality 20
   :sell-in 7})

(def last-rising-day-pass
  {:name b/backstage-pass-name
   :quality 20
   :sell-in 6})

(deftest test-backstage-pass-rising
  (testing "Quality rises by 2 on day 10"
    (is (= 22 (:quality (i/update-item ten-days-before-pass)))))
  (testing "Quality rises by 2 on between day 10 and 5"
    (is (= 22 (:quality (i/update-item rising-backstage-pass)))))
  (testing "Quality rises by 2 on day 6"
    (is (= 22 (:quality (i/update-item last-rising-day-pass))))))

(def first-peak-backstage-pass
  {:name b/backstage-pass-name
   :quality 20
   :sell-in 5})

(def peak-backstage-pass
  {:name b/backstage-pass-name
   :quality 30
   :sell-in 3})

(def last-day-backstage-pass
  {:name b/backstage-pass-name
   :quality 20
   :sell-in 0})

(def max-quality-pass
  {:name b/backstage-pass-name
   :quality 48
   :sell-in 3})

(deftest test-backstage-pass-peak
  (testing "Quality rises by 3 on day 5"
    (is (= 23 (:quality (i/update-item first-peak-backstage-pass)))))
  (testing "Quality rises by 3 on between day 9 and 6"
    (is (= 33 (:quality (i/update-item peak-backstage-pass)))))
  (testing "Quality increases by 3 on the last day (sell-in 0)"
    (is (= 23 (:quality (i/update-item last-day-backstage-pass)))))
  (testing "Quality never goes over 50"
    (is (= 50 (:quality (i/update-item max-quality-pass))))))
