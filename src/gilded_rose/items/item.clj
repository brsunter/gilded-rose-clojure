(ns gilded-rose.items.item)

(defmulti update-item :name)

(defn decrease-quality
  [item x]
  (update item :quality #(Math/abs (- % x))))
