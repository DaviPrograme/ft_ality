(ns helpers.key
  (:import [javax.swing JFrame JPanel]
           [java.awt.event KeyAdapter KeyEvent]
           [javax.sound.sampled AudioSystem Clip FloatControl]
           [java.io File]
           [java.awt Graphics Toolkit]))

(def commands (atom []))

(defn print-commands [commands]
  (doseq [cmd @commands]
    (print cmd "\n"))
  (print "\n\n"))


(defn carregar-imagem [caminho-imagem]
  (.getImage (Toolkit/getDefaultToolkit) caminho-imagem))

;; Função para tocar uma música em loop
(defn tocar-musica [caminho-musica]
  (let [audio-file (File. caminho-musica)
        audio-stream (AudioSystem/getAudioInputStream audio-file)
        clip (AudioSystem/getClip)]
    (.open clip audio-stream)
    (.start clip)
    clip))

;; Função que retorna um listener de teclado
(defn- key-listener [frame]
  (proxy [KeyAdapter] []
    (keyPressed [e]
      (let [  key-code (.getKeyCode e)
              time (System/currentTimeMillis)]
        (swap! commands conj {:key (KeyEvent/getKeyText key-code) :time time})
        (print-commands commands)
        (when (= key-code KeyEvent/VK_ESCAPE)
          (println "Saindo...")
          (.dispose frame)))) ;; Acesso ao frame aqui
    (keyReleased [e]
      (let [key-code (.getKeyCode e)]
        (println "Tecla liberada:" (KeyEvent/getKeyText key-code))))))

;; Função para criar a janela
(defn create-frame []
  (let [frame (JFrame. "FT_ALITY")
        panel (proxy [JPanel] []
                (paintComponent [g]
                  (proxy-super paintComponent g)
                  (.drawImage g (carregar-imagem "./MK_SRC/Mortal-Kombat-3.jpg") 0 0 this)))]
    (.add frame panel)
    (.addKeyListener frame (key-listener frame)) ;; Passa a referência do frame
    (.setSize frame 1200 675)
    (.setVisible frame true)
    (.setDefaultCloseOperation frame JFrame/EXIT_ON_CLOSE)
    (tocar-musica "./MK_SRC/music.wav")))