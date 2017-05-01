(ns gilded-rose.items.legendary-test
  (:require [gilded-rose.items.item :as i]
            [gilded-rose.items.legendary :as l]
            [clojure.test :refer [deftest testing is] :as t]))

(def legendary-item
  {:name l/legendary-name
   :quality 80
   :sell-in 20})

(deftest test-legendary
  (testing "Legendary quality never changes"
    (is (= 80 (:quality (i/update-item legendary-item))))))
