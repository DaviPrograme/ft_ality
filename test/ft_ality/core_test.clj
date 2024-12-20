(ns ft-ality.core-test
  (:require [clojure.test :refer :all]
            [ft-ality.main :refer :all]
            [helpers.file :refer :all]
            [helpers.tree :refer :all]
            [helpers.monitor :refer :all]
            [helpers.aux :refer :all]
            [helpers.validator :refer [is-section-separator-valid?,
              is-section-commands-valid?
              is-section-combos-valid?]]))


(deftest path-exists?-test
  (testing "testando com um arquivo que não existe"
    (is (= false (path-exists? "./nao_existo"))))
  (testing "testando com um arquivo que existe"
    (is (= true (path-exists? "./test/ft_ality/helpers/test.txt")))))


(deftest is-file?-test
  (testing "testando com um arquivo que não existe"
    (is (= false (is-file? "./nao_existo"))))
  (testing "testando com um arquivo que existe"
    (is (= true (is-file? "./test/ft_ality/helpers/test.txt"))))
  (testing "testando com uma pasta que existe"
    (is (= false (is-file? "./test/ft_ality/helpers")))))


(deftest get-content-file-test
  (testing "testando com um arquivo que tem conteudo"
    (is (= "42 São Paulo" (get-content-file "./test/ft_ality/helpers/test.txt"))))
  (testing "testando com um arquivo que não tem conteudo"
    (is (= "" (get-content-file "./test/ft_ality/helpers/empty.txt")))))


(deftest is-section-separator-valid?-test
  (testing "testando com um arquivo pedrão correto"
    (is (= true (is-section-separator-valid? (get-content-file "./test/ft_ality/helpers/separator/correct1.grm") false))))
  (testing "testando com um arquivo que a linha separadora tem mais de um underline"
    (is (= true (is-section-separator-valid? (get-content-file "./test/ft_ality/helpers/separator/correct2.grm") false))))
  (testing "testando com um arquivo que a linha separadora mas sem conteudo na segunda seção"
    (is (= true (is-section-separator-valid? (get-content-file "./test/ft_ality/helpers/separator/correct3.grm") false))))
  (testing "testando com um arquivo que a linha separadora mas sem conteudo na primeira seção"
    (is (= true (is-section-separator-valid? (get-content-file "./test/ft_ality/helpers/separator/correct4.grm") false))))
  (testing "testando com um arquivo que só tenha a linha separadora"
    (is (= true (is-section-separator-valid? (get-content-file "./test/ft_ality/helpers/separator/correct5.grm") false))))
  (testing "testando com um arquivo que não tem a lnhha separadora de seções"
    (is (= false (is-section-separator-valid? (get-content-file "./test/ft_ality/helpers/separator/error1.grm") false))))
  (testing "testando com um arquivo que na linha separadora tenha um caracter que não seja o underline"
    (is (= false (is-section-separator-valid? (get-content-file "./test/ft_ality/helpers/separator/error2.grm") false))))
  (testing "testando com um arquivo que tenha underline também na primeira seção"
    (is (= false (is-section-separator-valid? (get-content-file "./test/ft_ality/helpers/separator/error3.grm") false))))
  (testing "testando com um arquivo que tenha underline também na segunda seção"
    (is (= false (is-section-separator-valid? (get-content-file "./test/ft_ality/helpers/separator/error4.grm") false)))))


(deftest is-section-commands-valid?-test
  (testing "testando com o arquivo padrão"
    (is (= true (is-section-commands-valid? (get-content-file "./test/ft_ality/helpers/commands/correct1.grm") false))))
  (testing "testando com um arquivo que esta usando as setas nos comandos"
    (is (= true (is-section-commands-valid? (get-content-file "./test/ft_ality/helpers/commands/correct2.grm") false))))
  (testing "testando com um arquivo que tem espaços colocados de forma indiscriminada"
    (is (= true (is-section-commands-valid? (get-content-file "./test/ft_ality/helpers/commands/correct3.grm") false))))
  (testing "testando com um arquivo que esta passando um comando que não é um caracter nem as setas nem o espaço"
    (is (= false (is-section-commands-valid? (get-content-file "./test/ft_ality/helpers/commands/error1.grm") false))))
  (testing "testando com um arquivo que esta passando uma tecla que não é um caracter do alfabeto"
    (is (= false (is-section-commands-valid? (get-content-file "./test/ft_ality/helpers/commands/error2.grm") false))))
  (testing "testando com um arquivo que esta com duas teclas iguais para golpes diferentes"
    (is (= false (is-section-commands-valid? (get-content-file "./test/ft_ality/helpers/commands/error3.grm") false))))
  (testing "testando com um arquivo que esta com dois golpes iguais para teclas diferentes"
    (is (= false (is-section-commands-valid? (get-content-file "./test/ft_ality/helpers/commands/error4.grm") false))))
  (testing "testando com um arquivo que esta coom uma linha com 2 dashs"
    (is (= false (is-section-commands-valid? (get-content-file "./test/ft_ality/helpers/commands/error5.grm") false))))
  (testing "testando com um arquivo que esta com uma linhha que só tem o dash "
    (is (= false (is-section-commands-valid? (get-content-file "./test/ft_ality/helpers/commands/error6.grm") false))))
  (testing "testando com um arquivo que esta com uma linhha que tem um dash com apenas espaços a direita e a esquerda"
    (is (= false (is-section-commands-valid? (get-content-file "./test/ft_ality/helpers/commands/error7.grm") false)))))

  (deftest is-section-combos-valid?-test
    (testing "testando com o arquivo padrão"
      (is (= true (is-section-combos-valid? (get-content-file "./test/ft_ality/helpers/combos/correct1.grm") false))))
    (testing "testando com arquivo que s golpes na seção de combos varia entre maiusculas e minusculas diferente da seção de comandos"
      (is (= true (is-section-combos-valid? (get-content-file "./test/ft_ality/helpers/combos/correct2.grm") false))))
    (testing "testando com arquivo que na seção de combos e de comandos os golpes tem variações de espaços diferentes"
      (is (= true (is-section-combos-valid? (get-content-file "./test/ft_ality/helpers/combos/correct3.grm") false))))
    (testing "testando com arquivo sem a seção de combos"
      (is (= false (is-section-combos-valid? (get-content-file "./test/ft_ality/helpers/combos/error1.grm") false))))
    (testing "testando com arquivo com a seção de combos mas contendo apenas espaços em brancos"
      (is (= false (is-section-combos-valid? (get-content-file "./test/ft_ality/helpers/combos/error2.grm") false))))
    (testing "testando com um arquivo que esta coom uma linha com 2 dashs"
      (is (= false (is-section-combos-valid? (get-content-file "./test/ft_ality/helpers/combos/error3.grm") false))))
    (testing "testando com um arquivo que um dos combos nao tem nome"
      (is (= false (is-section-combos-valid? (get-content-file "./test/ft_ality/helpers/combos/error4.grm") false))))
    (testing "testando com um arquivo que um dos combos tem um goolpe nao menciionado na seção de comandos"
      (is (= false (is-section-combos-valid? (get-content-file "./test/ft_ality/helpers/combos/error5.grm") false)))))

(deftest keys-commands-map-test
  (let [expected1 {"1" "UM" "2" "DOIS" "3" "TRES"}
        expected2 {"1" "UM" "2" "DOIS" "3" "42 SP"}]
    (testing "testando com o arquivo padrão"
      (is (= expected1 (keys-commands-map (get-content-file "./test/ft_ality/helpers/keys-commands-map/correct1.grm")))))
    (testing "testando com um arquivo que tem espaços iinserridos no conteudo maas nao deve alterar o resultado"
      (is (= expected1 (keys-commands-map(get-content-file "./test/ft_ality/helpers/keys-commands-map/correct2.grm")))))
    (testing "testando com arquivo que mescla entre maiusculas e minusculas"
      (is (= expected1 (keys-commands-map (get-content-file "./test/ft_ality/helpers/keys-commands-map/correct3.grm")))))
    (testing "testando com um arquivo que o nome do golpe é composto por duas palavras separada com espaços"
      (is (= expected2 (keys-commands-map (get-content-file "./test/ft_ality/helpers/keys-commands-map/correct4.grm")))))))


(deftest strikes-commands-map-test
  (let [expected1 {"UM" "1" "DOIS" "2" "TRES" "3"}
        expected2 {"UM" "1" "DOIS" "2" "42 SP" "3"}]
    (testing "testando com o arquivo padrão"
      (is (= expected1 (strikes-commands-map (get-content-file "./test/ft_ality/helpers/keys-commands-map/correct1.grm")))))
    (testing "testando com um arquivo que tem espaços iinserridos no conteudo maas nao deve alterar o resultado"
      (is (= expected1 (strikes-commands-map(get-content-file "./test/ft_ality/helpers/keys-commands-map/correct2.grm")))))
    (testing "testando com arquivo que mescla entre maiusculas e minusculas"
      (is (= expected1 (strikes-commands-map (get-content-file "./test/ft_ality/helpers/keys-commands-map/correct3.grm")))))
    (testing "testando com um arquivo que o nome do golpe é composto por duas palavras separada com espaços"
      (is (= expected2 (strikes-commands-map (get-content-file "./test/ft_ality/helpers/keys-commands-map/correct4.grm")))))))


(defn- equal-nodes? [nodeA nodeB]
  (and (= (:key nodeA) (:key  nodeB)) (= (:special nodeA) @(:special  nodeB)) (=  (:branches nodeA) @(:branches  nodeB))))

(deftest create-node-test 
  (let [expected1 {:key "A" :special nil :branches {}} result1 (create-node "A" nil)
        expected2 {:key "B" :special "nome do golpe especial" :branches {}} result2 (create-node "B" "nome do golpe especial")
        expected3 {:key nil :special nil :branches {}} result3 (create-node nil nil)]
    (testing "testando passando a tecla como string e special como nil"
      (is (equal-nodes? expected1 result1)))
    (testing "testando passando a tecla como string e special como string"
      (is (equal-nodes? expected2 result2)))
    (testing "testando passando a tecla como nil e special como nil"
      (is (equal-nodes? expected3 result3)))))


(defn- equal-nodes2? [nodeA nodeB]
  (and (= (:key nodeA) (:key  nodeB)) (= @(:special nodeA) @(:special  nodeB)) (=  @(:branches nodeA) @(:branches  nodeB))))


(deftest insert-branche-into-node-test 
  (let [node (create-node "A" nil)
        nodeB (create-node "B" nil)
        nodeC (create-node "C" nil)]
    (testing "testando com um node que não tem branches"
      (is (empty? @(:branches node))))
    (testing "testando inserindo um nó"
      (insert-branche-into-node node nodeB)
      (is (and (not (empty? @(:branches node))) (contains? @(:branches node) "B") (equal-nodes2? (get @(:branches node) "B") nodeB))))
    (testing "testando inserindo um outro nó"
      (insert-branche-into-node node nodeC)
      (is (and (not (empty? @(:branches node))) (contains? @(:branches node) "C") (equal-nodes2? (get @(:branches node) "C") nodeC))))))

(deftest all-commnds-part-sequence?-test
  (let [time 1
        test1 []
        test2 [{:time 1} {:time 2}{:time 3}{:time 4}{:time 5}{:time 6}{:time 7}{:time 8}{:time 9}]
        test3 [{:time 1} {:time 2}{:time 3}{:time 4}{:time 5}{:time 6}{:time 7}{:time 9}{:time 10}]]
    (testing "com uma lista vazia de comandos"
      (is (= true (all-commnds-part-sequence? test1 time))))
    (testing "com uma lista que todos os comandos fazem parte da sequencia"
      (is (= true (all-commnds-part-sequence? test2 time))))
    (testing "com uma lista que nem todos os comandos fazem parte da sequencia"
      (is (= false (all-commnds-part-sequence? test3 time))))))


(deftest filter-empty-string-test
  (let [test1 []
        test2 ["1" "2" "3"]
        test3 ["1" "" "2" "3"]
        expected ["1" "2" "3"]]
    (testing "com uma lista vazia" (is (= [] (filter-empty-string test1))))
    (testing "com uma lista de string onde nenhuma é vazia" (is (= expected (filter-empty-string test2))))
    (testing "com uma lista de string onde um elemento é vazio" (is (= expected (filter-empty-string test3))))))


(deftest treat-combined-strike-test
  (let [test1 "4+2+s+p"
        test2 "4++2++s++p"
        test3 "42sp"
        expected1 #{"4", "2", "s", "p"}
        expected2 #{"4", "2", "s", "p"}
        expected3 #{"42sp"}]
    (testing "com uma  lista de comandos normal" (is (= expected1 (treat-combined-strike test1))))
    (testing "com uma lista de comandos com o sinal + a mais que o devido" (is (= expected2 (treat-combined-strike test2))))
    (testing "com uma lista de comandos com apenas um comando" (is (= expected3 (treat-combined-strike test3))))))


(deftest build-combo-map-test
  (let [test1 "Uppercut-Down+Back Punch"
        test2 "Uppercut-Left,Left,Front Punch"
        test3 "Uppercut-Down+Back Punch,Left,Left,Front Punch"
        test4 "   Uppercut     -    Down   +   Back    Punch , Left , Left ,  Front      Punch       "
        expected1 {:name "UPPERCUT" :list [#{"DOWN" "BACK PUNCH"}]}
        expected2 {:name "UPPERCUT" :list ["LEFT" "LEFT" "FRONT PUNCH"]}
        expected3 {:name "UPPERCUT" :list [#{"DOWN" "BACK PUNCH"} "LEFT" "LEFT" "FRONT PUNCH"]}
        expected4 {:name "UPPERCUT" :list [#{"DOWN" "BACK PUNCH"} "LEFT" "LEFT" "FRONT PUNCH"]}]
    (testing "com um golpes acionados ao mesmo tempo" (is (= expected1 (build-combo-map test1))))
    (testing "com um golpes acionados de forma sequencial" (is (= expected2 (build-combo-map test2))))
    (testing "com um golpes acionados ao mesmo tempo e sequencial" (is (= expected3 (build-combo-map test3))))
    (testing "com um golpes acionados ao mesmo tempo e sequencial, com mais espaços na string" (is (= expected4 (build-combo-map test4))))))


(deftest count-commands-next-sequence-test
  (let [seq-time 1
        test1 [{:time 1}]
        test2 [{:time 1}{:time 3}]
        test3 [{:time 1}{:time 2}]
        test4 [{:time 1}{:time 3}{:time 4}{:time 5}{:time 6}{:time 7}{:time 8}{:time 9}]
        test5 [{:time 1}{:time 2}{:time 3}{:time 4}{:time 5}{:time 7}{:time 8}{:time 9}]
        test6 [{:time 1} {:time 2}{:time 3}{:time 4}{:time 5}{:time 6}{:time 7}{:time 8}{:time 10}]
        test7 [{:time 1} {:time 2}{:time 3}{:time 4}{:time 5}{:time 6}{:time 7}{:time 8}{:time 9}]
        expected1 1
        expected2 1
        expected3 2
        expected4 1
        expected5 5
        expected6 8
        expected7 9]
    (testing "com apenas um elemento" (is (= expected1 (count-commands-next-sequence 0 test1 seq-time))))
    (testing "com dois elementos sendo que o segundo não faz parte da sequencia" (is (= expected2 (count-commands-next-sequence 0 test2 seq-time))))
    (testing "com dois elementos sendo que todos fazem parte da sequencia" (is (= expected3 (count-commands-next-sequence 0 test3 seq-time))))
    (testing "com oito elementos sendo que o segundo não faz parte da sequencia" (is (= expected4 (count-commands-next-sequence 0 test4 seq-time))))
    (testing "com oito elementos sendo que um elemento do meio nao faz parte da sequencia" (is (= expected5 (count-commands-next-sequence 0 test5 seq-time))))
    (testing "com nove elementos sendo que o elemento final nao faz parte da sequencia" (is (= expected6 (count-commands-next-sequence 0 test6 seq-time))))
    (testing "com nove elementos sendo que todos fazem parte da sequencia" (is (= expected7 (count-commands-next-sequence 0 test7 seq-time))))
    ))


(deftest count-keys-pressed-same-time-test
  (let [wait-time 1
        key-focus {:time 0}
        test0 []
        test1 [{:time 2}]
        test2 [{:time 1}]
        test3 [{:time 0.1}{:time 0.2}{:time 0.3}{:time 0.9999999999999}{:time 1.00}{:time 1.00000000000001}{:time 2}{:time 3}{:time 4}{:time 5}]
        test4 [{:time 0.1} {:time 0.2}{:time 0.3}{:time 0.4}{:time 0.5}{:time 0.6}{:time 0.7}{:time 0.8}{:time 0.9999999999999}{:time 1.0}{:time 1.00000000000001}]
        test5 [{:time 0.1} {:time 0.2}{:time 0.3}{:time 0.4}{:time 0.5}{:time 0.6}{:time 0.7}{:time 0.8}{:time 0.9999999999998}{:time 1.0}{:time 0.9999999999999}]
        expected0 0
        expected1 0
        expected2 1
        expected3 5
        expected4 10
        expected5 11]
    (testing "sem elementos na lista de comandos" (is (= expected0 (count-keys-pressed-same-time 0 key-focus test0 wait-time))))
    (testing "com um elemento na lista mas que não foi pressionado juntamente" (is (= expected1 (count-keys-pressed-same-time 0 key-focus test1 wait-time))))
    (testing "com um elemento na lista que foi pressionado juntamente" (is (= expected2 (count-keys-pressed-same-time 0 key-focus test2 wait-time))))
    (testing "com dez elementos na lista mas apenas 5 foram pressionados em conjunto com a tecla foco" 
      (is (= expected3 (count-keys-pressed-same-time 0 key-focus test3 wait-time))))
    (testing "com dez elementos na lista mas apenas 1 não foi pressionado em conjunto com a tecla foco" 
      (is (= expected4 (count-keys-pressed-same-time 0 key-focus test4 wait-time))))
    (testing "com todos elementos da lista de comandos sendo pressionados juntos" (is (= expected5 (count-keys-pressed-same-time 0 key-focus test5 wait-time))))
    ))


(deftest handle-commands-test
  (let [wait-time 1
        key-focus {:time 0}
        test0 []
        test1 [{:key "A" :time 1}]
        test2 [{:key "A" :time 1} {:key "A" :time 2}]
        test3 [{:key "A" :time 1} {:key "A" :time 3}]
        test4 [{:key "A" :time 1} {:key "B" :time 2}]
        test5 [{:key "A" :time 1} {:key "B" :time 3}]
        test6 [{:key "A" :time 1}{:key "A" :time 2}{:key "A" :time 3}{:key "A" :time 4}{:key "A" :time 5}{:key "A" :time 6}{:key "A" :time 7}]
        test7 [{:key "A" :time 1}{:key "B" :time 2}{:key "C" :time 3}{:key "D" :time 4}{:key "E" :time 5}{:key "F" :time 6}{:key "G" :time 7}]
        test8 [{:key "A" :time 1}{:key "B" :time 3}{:key "C" :time 5}{:key "D" :time 7}{:key "E" :time 9}{:key "F" :time 11}{:key "G" :time 13}]
        test9 [{:key "A" :time 1}{:key "B" :time 3}{:key "C" :time 5}{:key "D" :time 6}{:key "E" :time 8}{:key "F" :time 10}{:key "G" :time 12}]
        test10 [{:key "A" :time 0.1}{:key "A" :time 0.2}{:key "A" :time 0.3}{:key "A" :time 0.4}{:key "A" :time 0.5}{:key "A" :time 0.6}{:key "A" :time 0.7}]
        expected0 []
        expected1 ["A"]
        expected2 ["A"]
        expected3 ["A" "A"]
        expected4 [#{"A" "B"}]
        expected5 ["A" "B"]
        expected6 ["A" "A" "A" "A"]
        expected7 [#{"A" "B"} #{"C" "D"} #{"E" "F"} "G"]
        expected8 ["A" "B" "C" "D" "E" "F" "G"]
        expected9 ["A" "B" #{"C" "D"} "E" "F" "G"]
        expected10 ["A"]]
    (testing "sem elementos na lista de comandos" (is (= expected0 (handle-commands [] test0 wait-time))))
    (testing "com um elemento na lista" (is (= expected1 (handle-commands [] test1 wait-time))))
    (testing "com 2 elementos na lista sendo iguais e pressionados juntos" (is (= expected2 (handle-commands [] test2 wait-time))))
    (testing "com 2 elementos na lista sendo iguais e pressionados separados" (is (= expected3 (handle-commands [] test3 wait-time))))
    (testing "com 2 elementos na lista sendo diferentes e pressionados juntos" (is (= expected4 (handle-commands [] test4 wait-time))))
    (testing "com 2 elementos na lista sendo diferentes e pressionados separados" (is (= expected5 (handle-commands [] test5 wait-time))))
    (testing "com varios elementos iguais" (is (= expected6 (handle-commands [] test6 wait-time))))
    (testing "com varios elementos diferentes"  (is (= expected7 (handle-commands [] test7 wait-time))))
    (testing "com varios elementos diferentes mas com apenas 2 sendo pressionados juntos" (is (= expected8 (handle-commands [] test8 wait-time))))
    (testing "com varios elementos diferentes mas com apenas 2 sendo pressionados separados" (is (= expected9 (handle-commands [] test9 wait-time))))
    (testing "com varios elementos iguais pressionados juntos" (is (= expected10 (handle-commands [] test10 wait-time))))
    ))