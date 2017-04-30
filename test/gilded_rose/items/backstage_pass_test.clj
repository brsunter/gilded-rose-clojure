(ns gilded-rose.items.backstage-pass-test
  (:require
   [gilded-rose.items.backstage-pass :as b]
   [gilded-rose.items.item :as i]
   [clojure.test :refer [deftest testing is] :as t]))

(def over-backstage-pass
  {:name b/backstage-pass-name
   :quality 20
   :sell-in -1})

(deftest test-backstage-pass
  (testing "Quality is zero when concert is over"
    (is (= 0 (:quality (i/update-item over-backstage-pass))))))
