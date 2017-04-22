(ns gilded-rose.core-test
  (:require [clojure.test :refer [is deftest]]
            [clojure.spec :as s]
            [clojure.spec.test :as st]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [gilded-rose.core :as c]))

(defn spec-passed?
  [spec-res]
  (not (some :failure spec-res)))

(deftest test-all
  (is (spec-passed? (st/check))))
