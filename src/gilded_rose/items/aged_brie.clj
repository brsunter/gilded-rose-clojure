(ns gilded-rose.items.aged-brie
  (:require [gilded-rose.items.item :as i]))

(def aged-brie-name "Aged Brie")

(defmethod i/update-item aged-brie-name [item]
  (-> (update item :quality inc)
      (update :sell-in dec)))
