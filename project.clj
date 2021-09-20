(defproject oatmeal "0.1.0-SNAPSHOT"
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
  :profiles {:uberjar {:aot :all}})
