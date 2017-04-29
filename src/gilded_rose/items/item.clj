(ns gilded-rose.items.item)

(defmulti update-item :name)

(def max-quality 50)

(defn decrease-quality
  [item x]
  (update item :quality #(Math/abs (- % x))))

(defn increase-quality
  [{:keys [quality] :as item} x]
  (let [next-quality (+ quality x)]
    (if (<= next-quality max-quality)
      (assoc item :quality next-quality)
      (assoc item :quality max-quality))))
