from TransizioniBinarie import *


class mdt_universale:

    def __init__(self, file_input_transizioni):

        self.__file = file_input_transizioni

        self.nastro1 = convert_binario_transizioni(
            leggi_transizioni(file_input_transizioni))

        if len(self.nastro1) == 0:
            raise ValueError("Non e' presente alcuna descrizione nel file")

        self.stato_attuale = self.nastro1[0]["stato_partenza"]
        self.testina = 0

        self.__stringa_inserita = " "
        self.__MAX_COMPUTAZIONI = 100000

    def calcola_prossima_configurazione(self, stato, simbolo):

        i = 0
        trovato = False
        configurazione = dict()

        while i < len(self.nastro1) and trovato == False:

            if self.nastro1[i]["simbolo_letto"] == self.nastro_stringa[self.testina] and self.stato_attuale == self.nastro1[i]["stato_partenza"]:

                configurazione["simbolo"] = self.nastro1[i]["simbolo_scritto"]
                configurazione["stato"] = self.nastro1[i]["stato_arrivo"]
                configurazione["movimento"] = self.nastro1[i]["movimento"]

                trovato = True

            i = i + 1

        return configurazione

    def __computa(self):

        stop = False
        comp = 0

        while stop == False:

            if self.stato_attuale == accept:
                stop = True

            configurazione = self.calcola_prossima_configurazione(
                self.stato_attuale, self.nastro_stringa[self.testina])

            if len(configurazione) == 0:
                stop = True

            else:
                self.stato_attuale = configurazione['stato']
                self.nastro_stringa[self.testina] = configurazione['simbolo']

                # calcolo il movimento da fare sul njastro della stringa
                movimento_letto = configurazione['movimento']

                if movimento_letto == left:

                    if self.testina == 0:
                        self.nastro_stringa.insert(0, empty)

                    else:
                        self.testina = self.testina - 1

                elif movimento_letto == right:

                    if self.testina + 1 == len(self.nastro_stringa):
                        self.nastro_stringa.append(empty)
                        self.testina = self.testina + 1

                    else:
                        self.testina = self.testina + 1

                else:
                    raise ValueError("Qualcosa è andato storto")

            comp = comp + 1

            if comp == self.__MAX_COMPUTAZIONI:
                stop = True

        return comp

    # metodo privato
    def __reset(self):
        self.__stringa_inserita = " "
        self.testina = 0
        self.nastro_stringa = list()
        self.stato_attuale = self.nastro1[0]["stato_partenza"]

    def computa_stringa(self, stringa_input):

        if self.testina == 0 or self.stato_attuale == self.nastro1[0]["stato_partenza"]:
            self.__reset()

        self.__stringa_inserita = stringa_input
        self.nastro_stringa = stringa_a_lista(
            convert_binario_carattere(stringa_input))
        comp = self.__computa()
        self.__stampa_risultati(comp)

    def __stampa_nastro(self):
        nastro_stampato = self.nastro_stringa
        nastro_stampato = [i for i in nastro_stampato if i != empty]

        stringa_stampata = ""

        for s in nastro_stampato:
            s_ascii = convert_ascii_binario(s)
            stringa_stampata += s_ascii
            stringa_stampata += ' '

        print(stringa_stampata)

    def __stampa_risultati(self, n_computazioni):

        print("Macchina di Turing in input: \n")
        print_lista_transizioni(leggi_transizioni(self.__file))

        print("Macchina di Turing in binario: \n")
        print_lista_transizioni(self.nastro1)

        print("Stringa in input: %s" % self.__stringa_inserita)
        print("Stringa in binario computata dalla macchina: %s" % convert_binario_carattere(self.__stringa_inserita))

        stato_stampato = self.stato_attuale
        self.__stampa_nastro()

        if n_computazioni == self.__MAX_COMPUTAZIONI:
            print("La macchina e' andata in loop")

        else:
            print("Numero di computazioni: %d" % n_computazioni)

        if self.stato_attuale == accept:
            print("La stringa e' stata accettata")

        else:
            print("La stringa e' stata rifiutata")
