(ns gilded-rose.items.aged-brie
  (:require [gilded-rose.items.item :as i]))

(def aged-brie-name "Aged Brie")

(defmethod i/update-item aged-brie-name [item]
  (-> (i/increase-quality item 1)
      (update :sell-in dec)))
