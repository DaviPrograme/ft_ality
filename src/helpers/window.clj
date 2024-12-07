(ns helpers.window
  (:require [helpers.defs.atoms :refer [commands is-run?]]
            [helpers.aux :refer [is-recognized-key?]])
  (:import [javax.swing JFrame JPanel]
           [java.awt.event KeyAdapter KeyEvent]
           [javax.sound.sampled AudioSystem Clip]
           [java.io File]
           [java.awt Toolkit]))


(defn print-commands [commands]
  (doseq [cmd @commands]
    (print cmd "\n"))
  (print "\n\n"))


(defn upload-image [caminho-imagem]
  (.getImage (Toolkit/getDefaultToolkit) caminho-imagem))

;; Função para tocar uma música em loop
(defn play-music [caminho-musica]
  (let [audio-file (File. caminho-musica)
        audio-stream (AudioSystem/getAudioInputStream audio-file)
        clip (AudioSystem/getClip)]
    (.open clip audio-stream)
    (.start clip)
    clip))

;; Função que retorna um listener de teclado
(defn- key-listener [frame keys-map]
  (proxy [KeyAdapter] []
    (keyPressed [e]
      (let [key-code (.getKeyCode e)]
        (let [key-pressed (KeyEvent/getKeyText key-code)
              time (System/currentTimeMillis)]
          (when (is-recognized-key? key-pressed keys-map)
            (swap! commands concat [{:key key-pressed :time time}])
            ;; (print-commands commands)
          ))
        (when (= key-code KeyEvent/VK_ESCAPE)
          (println "Exit...")
          (.dispose frame)
          (reset! is-run? false))))))

;; Função para criar a janela
(defn create-frame [keys-map]
  (let [frame (JFrame. "FT_ALITY")
        panel (proxy [JPanel] []
                (paintComponent [g]
                  (proxy-super paintComponent g)
                  (.drawImage g (upload-image "./MK_SRC/Mortal-Kombat-3.jpg") 0 0 this)))]
    (.add frame panel)
    (.addKeyListener frame (key-listener frame keys-map)) ;; Passa a referência do frame
    (.setSize frame 1200 675)
    (.setVisible frame true)
    (.setDefaultCloseOperation frame JFrame/EXIT_ON_CLOSE)
    (play-music "./MK_SRC/music.wav")))