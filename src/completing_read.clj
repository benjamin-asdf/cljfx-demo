(ns completing-read
  (:require [cljfx.api :as fx]))



(defn root-view [{:keys [showing]}]
  {:fx/type :stage
   :showing showing
   :width 960
   :height 540
   :on-close-request {::event ::close-window}
   :scene
   {:fx/type :scene
    :root
    {:fx/type :v-box
     :children [{:fx/type :label
                 :text "Hello, world!"}]}}})
