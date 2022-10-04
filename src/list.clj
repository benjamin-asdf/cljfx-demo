(ns list)

(require '[cljfx.api :as fx]
         '[cljfx.flowless :as fx.flowless])

(fx/on-fx-thread
 (fx/create-component
  {:fx/type :stage
   :showing true
   :scene {:fx/type :scene
           :root {:fx/type fx.flowless/virtualized-scroll-pane ;; add scroll bars
                  :content {:fx/type fx.flowless/virtual-flow
                            :cell-factory identity
                            :items (for [i (range 1000)]
                                     {:fx/type :label :text (str i)})}}}}))
