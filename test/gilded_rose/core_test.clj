(ns gilded-rose.core-test
  (:require [clojure.test :refer [is deftest]]
            [clojure.spec :as s]
            [clojure.test.check.impl]
            [clojure.spec.test :as st]
            [gilded-rose.items.spec :as is]
            #_[gilded-rose.core :as c]
            [gilded-rose.legacy :as l]))

(defn spec-passed?
  [spec-res]
  (not (some :failure spec-res)))

#_(s/fdef compare-legacy
  :args (s/cat :items ::is/items)
  :ret ::is/items
  :fn #(= (:ret %) (l/update-quality (:args %))))

#_(defn compare-legacy
  [items]
  (c/update-quality items))

(deftest test-all
  (is (spec-passed? (st/check))))
