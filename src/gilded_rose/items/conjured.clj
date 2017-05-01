(ns gilded-rose.items.conjured
  (:require [clojure.spec :as s]
            [gilded-rose.items.item :as i]))

(def conjured-name "Conjured Beanie")

(defmethod i/update-item conjured-name [{:keys [sell-in] :as item}]
  (if (neg? sell-in)
    (i/decrease-quality item 4)
    (i/decrease-quality item 2)))
