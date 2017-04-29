(ns gilded-rose.items.default
  (:require [clojure.spec :as s]
            [gilded-rose.items.item :as i]))

(defn update-item-quality
  [{:keys [sell-in] :as item}]
  (if (neg? sell-in)
    (update item :quality #(- % 2))
    (update item :quality dec)))

(defmethod i/update-item :default
  [item]
  (-> (update-item-quality item)
      (update :sell-in dec)))
