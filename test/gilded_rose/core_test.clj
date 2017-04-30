(ns gilded-rose.core-test
  (:require [clojure.test :refer [is deftest]]
            [clojure.test.check.impl]
            [clojure.spec.test :as st]
            [gilded-rose.core]
            [gilded-rose.legacy]))

(defn spec-passed?
  [spec-res]
  (not (some :failure spec-res)))

(deftest test-all
  (is (spec-passed? (st/check))))
