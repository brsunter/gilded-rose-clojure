(ns gilded-rose.core
  (:require [clojure.spec :as s]
            [gilded-rose.items.spec :as is]
            [gilded-rose.items.item :as i]))

(s/fdef update-quality
        :args (s/cat :items ::is/items)
        :ret ::is/items)

(defn update-quality [items]
  (map i/update-item items))

(defn item [item-name, sell-in, quality]
  {:name item-name, :sell-in sell-in, :quality quality})

(defn update-current-inventory[]
  (let [inventory
        [
         (item "+5 Dexterity Vest" 10 20)
         (item "Aged Brie" 2 0)
         (item "Elixir of the Mongoose" 5 7)
         (item "Sulfuras, Hand Of Ragnaros" 0 80)
         (item "Backstage passes to a TAFKAL80ETC concert" 15 20)
         ]]
    (update-quality inventory)))
