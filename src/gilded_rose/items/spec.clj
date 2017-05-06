(ns gilded-rose.items.spec
  (:require [clojure.spec :as s]
            [clojure.test.check.generators :as gen]))

(s/def ::item-names #{"Aged Brie"
                      "Sulfuras, Hand Of Ragnaros"
                      "Backstage passes to a TAFKAL80ETC concert"
                      "Conjured Beanie"
                      "+5 Dexterity Vest"
                      "Elixir of the Mongoose"})

(s/def ::default-name string?)

(s/def ::name (s/with-gen
                string?
                #(gen/one-of [(s/gen ::default-name) (s/gen ::item-names)])))

(s/def ::sell-in int?)

(s/def ::quality (s/int-in 0 51))
(s/def ::item (s/keys :req-un [::sell-in ::name ::quality]))
(s/def ::items (s/coll-of ::item))
