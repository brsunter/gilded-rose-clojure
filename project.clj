(defproject gilded-rose "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]]
  :profiles {:dev
             {:dependencies [[org.clojure/test.check "0.9.0"]]}}
  :monkeypatch-clojure-test false
  :plugins [[speclj "3.3.2"]
            [lein-cloverage "1.0.9"]
            [lein-auto "0.1.3"]]
  :test-paths ["test"])
