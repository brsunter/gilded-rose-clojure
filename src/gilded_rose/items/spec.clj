(ns gilded-rose.items.spec
  (:require [clojure.spec :as s]))

(s/def ::item-names #{"Aged Brie"
                      "Sulfuras, Hand Of Ragnaros"
                      "Backstage passes to a TAFKAL80ETC concert"})

(s/def ::sell-in int?)
(s/def ::name (s/or :item-name ::item-names :s string?))
(s/def ::quality (s/int-in 0 51))
(s/def ::item (s/keys :req-un [::sell-in ::name ::quality]))
(s/def ::items (s/coll-of ::item))
