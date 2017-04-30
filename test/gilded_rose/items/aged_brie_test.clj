(ns gilded-rose.items.aged-brie-test
  (:require [gilded-rose.items.aged-brie :as b]
            [gilded-rose.items.item :as i]
            [clojure.test :refer [deftest testing is]:as t]))

(def test-brie {:name b/aged-brie-name
                :quality 5
                :sell-in 2})

(deftest test-aged-brie
  (testing "Aged brie increases in quality"
    (is (= 6 (:quality (i/update-item test-brie))))))
