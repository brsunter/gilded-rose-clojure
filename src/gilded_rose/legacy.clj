(ns gilded-rose.legacy
  (:require [clojure.spec :as s]
            [gilded-rose.items.spec :as is]))

(s/fdef update-quality
        :args (s/cat :items ::is/items)
        :ret ::is/items)

(defn update-quality [items]
  (map
    (fn[item] (cond
      (and (< (:sell-in item) 0) (= "Backstage passes to a TAFKAL80ETC concert" (:name item)))
        (merge item {:quality 0})
      (or (= (:name item) "Aged Brie") (= (:name item) "Backstage passes to a TAFKAL80ETC concert"))
        (if (and (= (:name item) "Backstage passes to a TAFKAL80ETC concert") (>= (:sell-in item) 5) (< (:sell-in item) 10))
          (merge item {:quality (inc (inc (:quality item)))})
          (if (and (= (:name item) "Backstage passes to a TAFKAL80ETC concert") (>= (:sell-in item) 0) (< (:sell-in item) 5))
            (if (>= 50 (+ (:quality item) 3))
              (merge item {:quality (inc (inc (inc (:quality item))))})
              (merge item {:quality 50}))
            (if (< (:quality item) 50)
              (merge item {:quality (inc (:quality item))})
              item)))
      (< (:sell-in item) 0)
        (if (= "Backstage passes to a TAFKAL80ETC concert" (:name item))
          (merge item {:quality 0})
          (if (or (= "+5 Dexterity Vest" (:name item)) (= "Elixir of the Mongoose" (:name item)))
            (if (<= 0 (- (:quality item) 2))
              (merge item {:quality (- (:quality item) 2)})
              (merge item {:quality 0}))
            item))
      (or (= "+5 Dexterity Vest" (:name item)) (= "Elixir of the Mongoose" (:name item)))
      (if (<= 0 (dec (:quality item) ))
        (merge item {:quality (dec (:quality item))})
        (merge item {:quality 0}))
      :else item))
  (map (fn [item]
      (if (not= "Sulfuras, Hand of Ragnaros" (:name item))
        (merge item {:sell-in (dec (:sell-in item))})
        item))
  items)))
