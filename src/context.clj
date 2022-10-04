(def context-1
  (fx/create-context
    {:tasks [{:text "Buy milk" :done false}
             {:text "Buy socks" :done true}]}))

;; Simple subscription function that depends on :tasks key of wrapped map. Whenever value
;; of :tasks key "changes" (meaning whenever there will be created a new context with
;; different value on :tasks key), subscribing to this function will lead to a call to
;; this function instead of cache lookup
(defn task-count [context]
  (count (fx/sub-val context :tasks)))

;; Using subscription functions:
(fx/sub-ctx context-1 task-count) ; => 2

;; Another subscription function depends on :tasks key of wrapped map
(defn remaining-task-count [context]
  (count (remove :done (fx/sub-val context :tasks))))

(fx/sub-ctx context-1 remaining-task-count) ; => 1

;; Indirect subscription function that depends on 2 previously defined subscription
;; functions, which means that whenever value returned by `task-count` or
;; `remaining-task-count` changes, subscribing to this function will lead to a call
;; instead of cache lookup
(defn task-summary [context]
  (prn :task-summary)
  (format "Tasks: %d/%d"
          (fx/sub-ctx context remaining-task-count)
          (fx/sub-ctx context task-count)))

(fx/sub-ctx context-1 task-summary) ; (prints :task-summary) => "Tasks: 1/2"

;; Creating derived context that reuses cache from `context-1`
(def context-2
  (fx/swap-context context-1 assoc-in [:tasks 0 :text] "Buy bread"))

;; Validating that cache entry is reused. Even though we updated :tasks key, there is no
;; reason to call `task-summary` again, because it's dependencies, even though
;; recalculated, return the same values
(fx/sub-ctx context-2 task-summary) ; (does not print anything) => "Tasks: 1/2"
