
(ns app.comp.login
  (:require [respo.core :refer [defcomp <> div input button span]]
            [respo.comp.space :refer [=<]]
            [respo.comp.inspect :refer [comp-inspect]]
            [respo-ui.core :as ui]
            [app.schema :as schema]
            [respo-md.comp.md :refer [comp-md-block]]
            [app.style :as style]))

(def initial-state {:username "", :password ""})

(defn on-input [state k] (fn [e dispatch! mutate!] (mutate! (assoc state k (:value e)))))

(defn on-submit [username password signup?]
  (fn [e dispatch!]
    (dispatch! (if signup? :user/sign-up :user/log-in) [username password])
    (.setItem js/localStorage (:local-storage-key schema/configs) [username password])))

(defcomp
 comp-login
 (states)
 (let [state (or (:data states) initial-state)]
   (div
    {:style (merge ui/flex ui/center)}
    (div
     {:style {:font-size 40,
              :margin-bottom 20,
              :font-weight 100,
              :font-family ui/font-fancy}}
     (comp-md-block "Impatiens is a tiny chatroom." {}))
    (div
     {:style {}}
     (div
      {}
      (input
       {:placeholder "Username",
        :value (:username state),
        :style ui/input,
        :on-input (on-input state :username)})))
    (=< nil 8)
    (div
     {:style {}}
     (button
      {:inner-text "Sign up",
       :style (merge style/button),
       :on-click (on-submit (:username state) (:password state) true)})
     (=< 8 nil)
     (button
      {:inner-text "Sign in",
       :style (merge style/button),
       :on-click (on-submit (:username state) (:password state) false)})))))
