(ns gilded-rose.items.legendary
  (:require [gilded-rose.items.item :as i]))

(def legendary-name "Sulfuras, Hand of Ragnaros")

(defmethod i/update-item legendary-name [item] item)
