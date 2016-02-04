(def project 'io.dominic/boot-snippets)
(def version "0.1.0")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "1.8.0" :scope "provided"]
                            [adzerk/boot-test "1.1.0" :scope "test"]
                            [adzerk/bootlaces "0.1.13"]])

(require '[adzerk.bootlaces :refer :all])
(bootlaces! version)

(task-options!
 pom {:project     project
      :version     version
      :description "A collection of snippets for working with boot"
      :url         ""
      :scm         {:url "https://github.com/SevereOverfl0w/boot-snippets"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))

(require '[adzerk.boot-test :refer [test]])
