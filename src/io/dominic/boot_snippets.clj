(ns io.dominic.boot-snippets)

;; Enable/disable resolution of dependencies dynamically
(intern
  (the-ns 'boot.core)
  'resolve-dependencies? (atom true))

(alter-var-root
  #'boot.core/add-dependencies!
  (fn [orig]
    (fn [old new env]
      (if @boot.core/resolve-dependencies? (orig old new env) new))))

(defmacro with-env
  "A macro for wrapping a task with a specific environment
   works with black magic.

   Perfect for isolating clojure builds from clojurescript/build task
   dependencies.

   Example usage:
   (note, boot-cljs currently requires my fork to work)

   (deftask frontend
    \"Compile cljs\"
    []
    (comp
      (watch)
      (reload)
      (cljs-repl)
      (with-env
        {:dependencies '[[org.omclj/om \"1.0.0-alpha28\"]]}
        (cljs))
      (target :dir #{\"target\"})))"
  [env-map expr]
  `(let [orig-env# (boot.core/get-env)
         new-env#  ~env-map]
     (reset! boot.core/resolve-dependencies? false)
     (apply boot.core/set-env! (mapcat identity new-env#))
     (let [result# ~expr]
       (apply boot.core/set-env! (mapcat identity orig-env#))
       (reset! boot.core/resolve-dependencies? true)
       result#)))
