(ns gilded-rose.core-test
  (:require [clojure.test :refer [is deftest]]
            [clojure.spec :as s]
            [clojure.test.check.impl]
            [clojure.spec.test :as st]
            [gilded-rose.items.spec :as is]
            [gilded-rose.core :as c]
            [gilded-rose.legacy :as l]))

(defn spec-passed?
  [spec-res]
  (not (some :failure spec-res)))

(deftest test-all
  (is (spec-passed? (st/check))))
