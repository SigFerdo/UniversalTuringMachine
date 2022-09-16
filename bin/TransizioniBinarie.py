import re

empty = "0000"
accept = "$"
error = "$$"
left = "110"
right = "101"


def leggi_transizioni(nome_file):

    file_transizioni = open(nome_file, "r")

    righe = file_transizioni.readlines()

    list_transizione_dict = list()

    for r in righe: 
        transizione_dict = dict()#ok
        lista_simboli = r.split("-")

        transizione_dict["stato_partenza"] = lista_simboli[0]
        transizione_dict["simbolo_letto"] = lista_simboli[1]
        transizione_dict["simbolo_scritto"] = lista_simboli[2]
        transizione_dict["movimento"] = lista_simboli[3]

        lista_simboli[4] = str(lista_simboli[4]).replace("\n", "")
        transizione_dict["stato_arrivo"] = lista_simboli[4]

        list_transizione_dict.append(transizione_dict)

    file_transizioni.close()

    return list_transizione_dict


def convert_binario_carattere(stringa):

    bin_stringa = ""

    if stringa == "empty":
        bin_stringa = empty

    else:
        bin_stringa = ' '.join(format(ord(c), 'b') for c in stringa)

    return bin_stringa


def convert_ascii_binario(bin_stringa):
    return "".join([chr(int(binary, 2)) for binary in bin_stringa.split(" ")])


def numero_stato(stato):
    return int(re.findall(r'\d+', stato)[0])


def convert_binario_stato(simbolo_stato):

    binario_stato = ""

    if simbolo_stato == "qaccept":
        binario_stato = accept

    else:
        numero_stato_letto = numero_stato(simbolo_stato)

        for i in range(0, numero_stato_letto + 1):
            binario_stato = binario_stato + "1"

    return binario_stato


def convert_binario_movimento(movimento):

    movimento_binario = ""

    if movimento == "L":
        movimento_binario = left

    if movimento == "R":
        movimento_binario = right

    return movimento_binario


def convert_binario_transizioni(lista_transizioni):

    lista_transizioni_binarie = list()

    for tr in lista_transizioni:
        transizone_binaria = dict()

        transizone_binaria["stato_partenza"] = convert_binario_stato(
            tr["stato_partenza"])
        transizone_binaria["simbolo_letto"] = convert_binario_carattere(
            tr["simbolo_letto"])
        transizone_binaria["simbolo_scritto"] = convert_binario_carattere(
            tr["simbolo_scritto"])
        transizone_binaria["movimento"] = convert_binario_movimento(
            tr["movimento"])
        transizone_binaria["stato_arrivo"] = convert_binario_stato(
            tr["stato_arrivo"])

        lista_transizioni_binarie.append(transizone_binaria)

    return lista_transizioni_binarie


def list_to_string(l):
    return ''.join(l)


def stringa_a_lista(s):
    return s.split(" ")


def print_lista_transizioni(lista_transizioni):

    for e in lista_transizioni:

        chiavi = e.keys()

        for c in chiavi:
            print("%s : %s" % (c, e[c]))

        print("\n")
