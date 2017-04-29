(ns gilded-rose.items.backstage-pass
  (:require [clojure.spec :as s]
            [gilded-rose.items.item :as i]))

(def backstage-pass-name "Backstage passes to a TAFKAL80ETC concert")

(s/def ::backstage-pass-quality-level
  (s/or :over #(> 0 %)
        :normal #(> 10 %)
        :rising #(>= 6 % 10)
        :peak #(>= 0 % 5)))

(defn update-backstage-pass-quality
  [pass quality-level]
  (case quality-level
    :over (assoc pass :quality 0)
    :normal (update pass :quality inc)
    :rising (update pass :quality #(+ 2 %))
    :peak (update pass :quality #(+ 3 %))))

(defmethod i/update-item backstage-pass-name
  [{:keys [sell-in] :as item}]
  (let [[quality-level _] (s/conform ::backstage-pass-quality-level sell-in)]
    (-> (update-backstage-pass-quality item quality-level)
        (update :sell-in dec))))
