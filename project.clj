(defproject pacl "0.1.0-SNAPSHOT"
  :description "PHP Autocomplete library"
  :url ""
  :license {:name "MIT"
            :url "LICENSE"}
  :repositories [
                 ["nba" "http://bits.netbeans.org/nexus/content/groups/netbeans/"]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.netbeans.modules/org-netbeans-modules-php-api-editor "RELEASE802"]]
  :plugins [[lein-ring "0.8.13"]]
  )
