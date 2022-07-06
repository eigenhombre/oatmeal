(defproject oatmeal "0.0.17"
  :description "A tool for working with Common Lisp projects"
  :url "http://github.com/eigenhombre/oatmeal"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [luhhujbb/clostache "1.5.0"]
                 [dev.nubank/docopt "0.6.1-fix7"]
                 [environ "1.2.0"]
                 [me.raynes/fs "1.4.6"]]
  :main ^:skip-aot oatmeal.core
  :uberjar-name "oatmeal.jar"
  :target-path "target/%s"
  :aliases {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]}
  :profiles {:uberjar {:aot :all}
             :kaocha {:dependencies [[lambdaisland/kaocha "1.0.887"]]}
             :dev {:plugins [[lein-bikeshed "0.5.2"]
                             [jonase/eastwood "0.9.9"]
                             [lein-kibit "0.1.8"]
                             [lein-shell "0.5.0"]]}}
  :release-tasks [["vcs" "assert-committed"]
                  ["test"]
                  ["change" "version" "leiningen.release/bump-version"
                   "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag" "v" "--no-sign"]
                  ["vcs" "push"]
                  [["shell" "scripts/gh_release.sh"]]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]])
