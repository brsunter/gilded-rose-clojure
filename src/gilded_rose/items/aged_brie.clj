(ns gilded-rose.items.aged-brie
  (:require [gilded-rose.items.item :as item]))

(def aged-brie-name "Aged Brie")

(defmethod item/update-item aged-brie-name
  [{:keys [sell-in name quality] :as item}]
  (-> (update item :quality inc)
      (update :sell-in dec)))
