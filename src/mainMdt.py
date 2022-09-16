import os
from mdt_universale import *

lista_files = list()

try:
    for file in os.listdir("mdt_files"):
        if file.endswith(".txt"):
            lista_files.append(file)

except FileNotFoundError:
    print("Errore: cartella non trovata")

if len(lista_files) == 0:
    print("Errore: nessun file di testo presente")

else:
    print("Inserisci il nome del file che contiene la descrizione della macchina di turing")
    file_descrizione_mdt = str(input())

    while file_descrizione_mdt not in lista_files:
        print("File non trovato, inserisci di nuovo il nome del file")
        file_descrizione_mdt = str(input())

    macchina = mdt_universale("mdt_files/%s" % file_descrizione_mdt)
    print("Inserisci la stringa in input alla macchina")
    stringa_input = str(input())

    macchina.computa_stringa(stringa_input)
    


