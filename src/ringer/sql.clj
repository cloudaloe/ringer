(ns ringer.sql
  (:require [clojure.java.jdbc :as sql])
  (:use [clojure.stacktrace]))


(defn create-master-level1
  ([mysql-config name]
    (let [{:keys [host port schema]} mysql-config
          rest-of-configuration (dissoc mysql-config :host :port :schema)
          subname (str "//" host ":" port "/" schema)
          mysql-conn (conj {:subprotocol "mysql" :subname subname :schema schema}, rest-of-configuration)]
      (try
        (sql/with-connection mysql-conn
          (sql/create-table
            name
            [:accountKey "INT UNSIGNED"]
            [:identifierKey "VARCHAR(64)"]
            [:identifierVal "VARCHAR(64)"]
            [:metricID "INT UNSIGNED"])
          ;(println (sql/do-commands "create table basic4(accountKey INT UNSIGNED)"))
          )
        (catch Exception e
          (println (str "Exception in MySQL access: " e))
          ;(root-cause e)
          (println "Stacktrace:")
          (print-cause-trace e)
          ;(print-stack-trace e)
          ;(print-throwable e)
          ;(parse-exception e) ;this provides the exception output as a map. Part of the clj-stacktrace namespace. Useful for Gelf logging
          ))
      )
  )
)