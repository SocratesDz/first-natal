(ns first-natal.android.core
 (:require [reagent.core :as r]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def text-input (r/adapt-react-class (.-TextInput ReactNative)))
(def alert (.-Alert ReactNative))
(def button (r/adapt-react-class (.-Button ReactNative)))
(def scroll-view (r/adapt-react-class (.-ScrollView ReactNative)))
(def flat-list (r/adapt-react-class (.-FlatList ReactNative)))
(def section-list (r/adapt-react-class (.-SectionList ReactNative)))

; Hello world
(defn hello-world []
 [text "Hello World!"])

; Bananas example
(defn bananas []
 (let [img {:uri "https://upload.wikimedia.org/wikipedia/commons/d/de/Bananavarieties.jpg"}]
  [image {:source img}]))

; Greeting sample
(defn greeting [name]
 [text (str "Hello " name "!")])

(defn lots-of-greetings []
 [view {:style {:align-items "center"}}
  (greeting "Rexxar")
  (greeting "Jaina")
  (greeting "Valeera")])

; Blink sample
(defn blink [txt]
 (let [show-text (r/atom true)]
  (fn [txt]
   (js/setTimeout #(swap! show-text not) 1000)
   [text (if @show-text
          txt
          "")])))



(defn blink-app []
 [view {:style {:align-items "center"}}
  [blink "I love to blink"]
  [blink "Why did they ever take this out of HTML"]
  [blink "Look at me look at me look at me"]])

; Lots of style
(def styles {:bigblue {:color :blue
                       :fontWeight :bold
                       :fontSize 30}
             :red {:color :red}})

(defn lots-of-styles []
 [view
  [text {:style (:red styles)} "just red"]
  [text {:style (:bigblue styles)} "just bigblue"]
  [text {:style [(:bigblue styles) (:red styles)]} "bigblue, then red"]
  [text {:style [(:red styles) (:bigblue styles)]} "red, then bigblue"]])

; Fixed dimensions basics
(defn fixed-dimensions-basics []
 [view
  [view {:style {:width 50 :height 50 :background-color :powderblue}}]
  [view {:style {:width 100 :height 100 :background-color :skyblue}}]
  [view {:style {:width 150 :height 150 :background-color :steelblue}}]])

; Flex dimensions basics
(defn flex-dimensions-basics []
 [view {:style {:flex 1}}
  [view {:style {:flex 1 :background-color :powderblue}}]
  [view {:style {:flex 2 :background-color :skyblue}}]
  [view {:style {:flex 3 :background-color :steelblue}}]])

; Flex directions basics
(defn flex-direction-basics []
 [view {:style {:flex 1
                :flex-direction :column
                :justify-content :center
                :align-items :center}}
  [view {:style {:width 50 :height 50 :background-color :powderblue}}]
  [view {:style {:width 50 :height 50 :background-color :skyblue}}]
  [view {:style {:width 50 :height 50 :background-color :steelblue}}]])

; Handling user input
(def pizza-translator-state (r/atom "")) ;; global state

(defn pizza-translator []
 [view {:style {:padding 10}}
  [text-input {:style {:height 40}
               :placeholder "Type here to translate!"
               :on-change-text #(reset! pizza-translator-state %)}]
  [text {:style {:padding 10 :font-size 42}}
   (.join (.map (.split @pizza-translator-state " ") #(when (not-empty %) "🍕")) " ")]])

; Handling touches
(def button-basics-style
 {:container {:flex 1
              :justify-content :center}
  :button-container {:margin 20}
  :alternative-layout-button-container {:margin 20
                                        :flex-direction :row
                                        :justify-content :space-between}})

(defn on-press-button [] (.alert alert "You tapped the button!"))

(defn button-basics []
 [view {:style (button-basics-style :container)}
  [view {:style (button-basics-style :button-container)}
   [button {:on-press on-press-button
            :title "Press Me"}]]
  [view {:style (button-basics-style :button-container)}
   [button {:on-press on-press-button
            :title "Press Me"
            :color "#841584"}]]
  [view {:style (button-basics-style :alternative-layout-button-container)}
   [button {:on-press on-press-button
            :title "This looks great!"}]
   [button {:on-press on-press-button
            :title "OK!"
            :color "#841584"}]]])

;; ScrollView
(def favicon (js/require "./images/cljs.png"))

(defn i-scrolled-and-what-happened-next-shocked-me []
 [scroll-view
  [text {:style {:font-size 96}} "Scroll me plz"]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [text {:style {:font-size 96}} "If you like"]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [text {:style {:font-size 96}} "Scrolling down"]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [text {:style {:font-size 96}} "What's the best"]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [text {:style {:font-size 96}} "Framework around?"]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [image {:source favicon}]
  [text {:style {:font-size 96}} "Re-Natal"]])

;; Using a listview
(def flat-list-styles {:container {:flex 1
                                   :padding-top 22}
                       :item {:padding 10
                              :font-size 18
                              :height 44}
                       :section-header {:padding-top 2
                                        :padding-left 10
                                        :padding-right 10
                                        :padding-bottom 2
                                        :font-size 14
                                        :font-weight :bold
                                        :background-color "rgba(247,247,247,1.0)"}})

(def flat-list-data #js[#js {:key "Devin"}
                        #js {:key "Jackson"}
                        #js {:key "James"}
                        #js {:key "Joel"}
                        #js {:key "John"}
                        #js {:key "Jillian"}
                        #js {:key "Jimmy"}
                        #js {:key "Julie"}])

(defn flat-list-basics []
 [view {:style (:container flat-list-styles)}
  [flat-list {:data flat-list-data
              :render-item (fn [item] (r/as-element [text {:style (:item flat-list-styles)}
                                                     (.. item -item -key)]))}]])

;; Section List Basics
(def sections-data #js [#js {:title "D"
                             :data #js ["Devin"]}
                        #js {:title "J"
                             :data #js ["Jackson" "James" "Jillian" "Jimmy"
                                        "Joel" "John" "Julie"]}])

(defn section-list-basics []
 [view {:style (:container flat-list-data)
        :sections sections-data
        :render-item #(r/as-element [text {:style (:item flat-list-styles)}
                                     (.. % -item)])
        :render-section-header #(r/as-element [text {:style (:section-header flat-list-styles)}
                                               (.. % -section -title)])
        :key-extractor #(%2)}])

(defn app-root []
 [view {:style {:flex 1}}
  ;(hello-world)
  ;(lots-of-greetings)
  ;(blink-app)
  ;(lots-of-styles)
  ;(fixed-dimensions-basics)
  ;(flex-dimensions-basics)
  ;(flex-direction-basics)
  ;(pizza-translator)
  ;(button-basics)
  ;(i-scrolled-and-what-happened-next-shocked-me)
  ;(flat-list-basics)
  (section-list-basics)])

 ;(bananas)) ; I want this to work, but it won't. I guess I'll have to shrug
; it off

(defn init []
 (.registerComponent app-registry "Hello World" #(r/reactify-component app-root)))
