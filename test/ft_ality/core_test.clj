(ns ft-ality.core-test
  (:require [clojure.test :refer :all]
            [ft-ality.main :refer :all]
            [helpers.file :refer :all]
            [helpers.tree :refer :all]
            [helpers.monitor :refer :all]
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


(deftest strokes-commands-map-test
  (let [expected1 {"UM" "1" "DOIS" "2" "TRES" "3"}
        expected2 {"UM" "1" "DOIS" "2" "42 SP" "3"}]
    (testing "testando com o arquivo padrão"
      (is (= expected1 (strokes-commands-map (get-content-file "./test/ft_ality/helpers/keys-commands-map/correct1.grm")))))
    (testing "testando com um arquivo que tem espaços iinserridos no conteudo maas nao deve alterar o resultado"
      (is (= expected1 (strokes-commands-map(get-content-file "./test/ft_ality/helpers/keys-commands-map/correct2.grm")))))
    (testing "testando com arquivo que mescla entre maiusculas e minusculas"
      (is (= expected1 (strokes-commands-map (get-content-file "./test/ft_ality/helpers/keys-commands-map/correct3.grm")))))
    (testing "testando com um arquivo que o nome do golpe é composto por duas palavras separada com espaços"
      (is (= expected2 (strokes-commands-map (get-content-file "./test/ft_ality/helpers/keys-commands-map/correct4.grm")))))))


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