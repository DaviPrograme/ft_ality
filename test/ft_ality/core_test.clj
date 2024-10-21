(ns ft-ality.core-test
  (:require [clojure.test :refer :all]
            [ft-ality.main :refer :all]
            [helpers.file :refer :all]
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