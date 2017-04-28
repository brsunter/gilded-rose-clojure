(ns gilded-rose.items.legendary
  (:require [gilded-rose.items.item :as item]))

(def legendary-name "Sulfuras, Hand of Ragnaros")

(defmethod item/update-item legendary-name
  [{:keys [sell-in name quality] :as item}]
  item)
