	# Nome do arquivo: Makefile

# Variáveis de instalação
CLOJURE_SCRIPT_URL=https://download.clojure.org/install/linux-install-1.11.1.1200.sh
LEIN_URL=https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein
CLOJURE_INSTALL_SCRIPT=/tmp/install-clojure.sh
LEIN_PATH=/usr/local/bin/lein

# Regras
.PHONY: all install-clojure install-leiningen install-java

all: install-java install-clojure install-leiningen

# Regra para instalar o Java
install-java:
	sudo apt update
	sudo apt install -y default-jdk default-jdk-headless

# Regra para instalar o Clojure
install-clojure:
	sudo curl -o $(CLOJURE_INSTALL_SCRIPT) $(CLOJURE_SCRIPT_URL)
	sudo chmod +x $(CLOJURE_INSTALL_SCRIPT)
	sudo $(CLOJURE_INSTALL_SCRIPT)
	sudo rm $(CLOJURE_INSTALL_SCRIPT)

# Regra para instalar o Leiningen
install-leiningen:
	curl -o $(LEIN_PATH) $(LEIN_URL)
	chmod +x $(LEIN_PATH)
	sudo apt install -y rlwrap