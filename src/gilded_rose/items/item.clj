(ns gilded-rose.items.item)

(defmulti update-item :name)

(def max-quality 50)
(def min-quality 0)

(defn decrease-quality
  [{:keys [quality] :as item} x]
  (let [next-quality (- quality x)]
    (if (>= next-quality min-quality)
      (assoc item :quality next-quality)
      (assoc item :quality min-quality))))

(defn increase-quality
  [{:keys [quality] :as item} x]
  (let [next-quality (+ quality x)]
    (if (<= next-quality max-quality)
      (assoc item :quality next-quality)
      (assoc item :quality max-quality))))

(defn default-update-quality
  [{:keys [sell-in] :as item}]
  (if (neg? sell-in)
    (decrease-quality item 2)
    (decrease-quality item 1)))

(defmethod update-item :default
  [item]
  (-> (default-update-quality item)
      (update :sell-in dec)))
