(ns gilded-rose.core-test
  (:require [clojure.test :refer [is deftest]]
            [clojure.spec :as s]
            [clojure.spec.test :as st]
            [clojure.test.check :as tc]
            [clojure.pprint :as pprint]
            [clojure.test.check.generators :as gen]
            [gilded-rose.core :refer [update-quality update-item-quality] :as c]))

(defn check' [spec-check]
  (is (nil? (-> spec-check st/summarize-results :check-failed))))

(deftest test-all
  (check' (st/check)))
