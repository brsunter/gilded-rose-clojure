(ns gilded-rose.items.item)

(defmulti update-item :name)

(defn update-item-quality
  [{:keys [sell-in name quality] :as item}]
  (if (neg? sell-in)
    (update item :quality #(- % 2))
    (update item :quality dec)))

(defmethod update-item :default
  [{:keys [sell-in name quality] :as item}]
  (-> (update-item-quality item)
      (update :sell-in dec)))
