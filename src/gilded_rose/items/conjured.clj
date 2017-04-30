(ns gilded-rose.items.conjured
  (:require [clojure.spec :as s]
            [gilded-rose.items.item :as i]))

(def conjured-items #{"Conjured Beanie"})

(defmethod i/update-item conjured-items [{:keys [sell-in] :as item}]
  (if (neg? sell-in)
    (i/decrease-quality item 4)
    (i/decrease-quality item 2)))
