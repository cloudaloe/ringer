(ns ringer.core
  (:use ringer.sql)
  (:use ring.adapter.jetty)
  (:use clj-stacktrace.core)
  (:gen-class main true))

;(require '[clojure.java.io :as io]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello my ringer"})

;(run-jetty handler {:port 3000})

(defn -main [& args]
  ; detect configuration, from default location or command line argument
  (let [[first-arg] args]
    (if (nil? first-arg)
      (def config "config/mysql.cfg")
      (def config (str "config/" first-arg))))

  (println (str "Using configuration file " config "."))

  (def mysql-config (read-string (slurp config))) ;TBA: confirming the configuration
  (println (str "Mysql connection details: " mysql-config))

  (create-master-level1 mysql-config "crazy15")
)